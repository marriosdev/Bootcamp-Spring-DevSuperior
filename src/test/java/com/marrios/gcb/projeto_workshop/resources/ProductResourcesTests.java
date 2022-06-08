package com.marrios.gcb.projeto_workshop.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marrios.gcb.projeto_workshop.dto.ProductDTO;
import com.marrios.gcb.projeto_workshop.services.ProductService;
import com.marrios.gcb.projeto_workshop.services.exceptions.DatabaseException;
import com.marrios.gcb.projeto_workshop.services.exceptions.ResourceEntityNotFoundException;
import com.marrios.gcb.projeto_workshop.tests.Factory;

@WebMvcTest(ProductResources.class)
public class ProductResourcesTests {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; 

    @MockBean
    private ProductService service;

    private ProductDTO productDTO;

    private PageImpl<ProductDTO> page;

    private Long existingid;
    private Long nonExistingId;
    private Long dependentId;

    
    @BeforeEach
    void setUp() throws Exception {
        nonExistingId = 1L;
        existingid = 2L;
        dependentId = 3L;

        productDTO = Factory.createProductDTO();
        page = new PageImpl<>(List.of(productDTO));
        
        when(service.findAllPaged(any())).thenReturn(page);
        
        when(service.findById(existingid)).thenReturn(productDTO);
        when(service.findById(nonExistingId)).thenThrow(ResourceEntityNotFoundException.class);
    
        when(service.update(eq(existingid), any())).thenReturn(productDTO);
        when(service.update(eq(nonExistingId), any())).thenThrow(ResourceEntityNotFoundException.class);
        
        doNothing().when(service).delete(existingid);
        doThrow(ResourceEntityNotFoundException.class).when(service).delete(nonExistingId);
        doThrow(DatabaseException.class).when(service).delete(dependentId);
        

    }

    @Test 
    public void  deleteDeveRetornarNoContentSeOIdExistir() throws Exception{
        mockMvc.perform(delete("/products/{id}", existingid)).andExpect(status().isNoContent());
    }

    @Test
    public void  deleteDeveRetornarNoContentSeOIdNaoExistir() throws Exception{
        mockMvc.perform(delete("/products/{id}", nonExistingId)).andExpect(status().isNotFound());
    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExists() throws Exception {
        String body = objectMapper.writeValueAsString(productDTO);

        ResultActions result = 
            mockMvc.perform(put("/products/{id}", existingid)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    public void updateShouldReturnNotFoundWhenIdExists() throws Exception {
        String body = objectMapper.writeValueAsString(productDTO);

        ResultActions result = 
            mockMvc.perform(put("/products/{id}", nonExistingId)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test 
    public void fundByIdShouldReturnProductWhenIdExists() throws Exception {
        ResultActions result = 
            mockMvc.perform(get("/products/{id}", existingid)
                .accept(org.springframework.http.MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        
    }

    @Test 
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExists() throws Exception  {
        ResultActions result = 
            mockMvc.perform(get("/products/{id}", nonExistingId)
                .accept(org.springframework.http.MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test 
    public void findAllShouldReturnPage() throws Exception{
        mockMvc.perform(get("/products")).andExpect(status().isOk());
    }
}
