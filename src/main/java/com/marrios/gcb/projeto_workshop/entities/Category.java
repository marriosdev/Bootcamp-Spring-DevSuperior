package com.marrios.gcb.projeto_workshop.entities;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity // Definindo que isso é uma entidade
@Table(name = "tb_category") //Definindo o nome da tabela no banco
public class Category implements Serializable{

    private static final long serialVersionUID = 1L;

    // Definindo o atributo ID como id autoincrement
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @Column(columnDefinition =  "TIMESTAMP WITHOUT TIME ZONE")
    private Instant created_at;
    
    @Column(columnDefinition =  "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updated_at;

    public Instant getUpdated_at() {
        return updated_at;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public Category() {

    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @PrePersist
    public void prePersist() {
        created_at = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        created_at = Instant.now();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Category other = (Category) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}

