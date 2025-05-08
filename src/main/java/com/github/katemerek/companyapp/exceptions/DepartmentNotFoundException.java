package com.github.katemerek.companyapp.exceptions;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException(long id) {
        super("Department with id " + id + " not found");
    }
}
