package com.testlab.springdata.exception;

public class NoResourceFoundException extends RuntimeException {
    public NoResourceFoundException(Long id) {
        super(String.format("No resource found for this id %d" , id));
    }
}
