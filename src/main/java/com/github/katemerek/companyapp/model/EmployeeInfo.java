package com.github.katemerek.companyapp.model;

/**
 * Projection for {@link Employee}
 */
public interface EmployeeInfo {

    String getFullName();

    String getPosition();

    DepartmentInfo getDepartment();
}