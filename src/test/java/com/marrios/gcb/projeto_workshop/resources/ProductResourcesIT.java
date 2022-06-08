package com.marrios.gcb.projeto_workshop.resources;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marrios.gcb.projeto_workshop.dto.ProductDTO;
import com.marrios.gcb.projeto_workshop.services.ProductService;
import com.marrios.gcb.projeto_workshop.tests.Factory;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductResourcesIT {
 
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; 

    @MockBean
    private ProductService service;
    
    private Long existingId;
    private Long nonExistingId;
    private Long countTotalProducts;


    
    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 10000000L;
        countTotalProducts = 25L;
    }

    @Test
    public void findAllShouldReturnSortedPageWhenSortByName() throws Exception {
        mockMvc.perform(
            get("/products?page=0&size=12&sort=name")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.totalElements").value(countTotalProducts))
        .andExpect(jsonPath("$.content").exists())
        .andExpect(jsonPath("$.content[0].name").value("Macbook Pro"))
        .andExpect(jsonPath("$.content[1].name").value("PC Gamer"));
    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExists() throws Exception {
        ProductDTO productDTO = Factory.createProductDTO();
        
        String body = objectMapper.writeValueAsString(productDTO);
        
        String expectedName = productDTO.getName();
        String expectedDescription = productDTO.getDescription();


        ResultActions result = 
            mockMvc.perform(put("/products/{id}", existingId)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(existingId));
        result.andExpect(jsonPath("$.name").value(expectedName));
        result.andExpect(jsonPath("$.description").value(expectedDescription));
    
    }

    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {
        ProductDTO productDTO = Factory.createProductDTO();
        
        String body = objectMapper.writeValueAsString(productDTO);

        ResultActions result = 
            mockMvc.perform(put("/products/{id}", nonExistingId)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }
}
