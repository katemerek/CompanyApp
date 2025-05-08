package com.github.katemerek.companyapp.exceptions;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(long id) {
        super("Employee with id " + id + " not found");
    }
}
