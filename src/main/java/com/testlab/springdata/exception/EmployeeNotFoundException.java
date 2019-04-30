package com.testlab.springdata.exception;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(Long id) {
        super(String.format("Could not find employee %d ",id));
    }
}
