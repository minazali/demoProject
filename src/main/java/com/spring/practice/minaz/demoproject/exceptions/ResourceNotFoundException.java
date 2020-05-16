package com.spring.practice.minaz.demoproject.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(){}

    public ResourceNotFoundException(String s){
        super(s);
    }
}
