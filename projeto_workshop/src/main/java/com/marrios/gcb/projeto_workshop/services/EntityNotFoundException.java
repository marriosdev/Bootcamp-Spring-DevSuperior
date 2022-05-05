package com.marrios.gcb.projeto_workshop.services;

public class EntityNotFoundException extends RuntimeException{
    private static final long  serialVersionUID = 1L;
    
    public EntityNotFoundException(String msg) {
        super(msg);
    }
}
