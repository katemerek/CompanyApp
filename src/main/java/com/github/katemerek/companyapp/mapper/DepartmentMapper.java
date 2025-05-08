package com.github.katemerek.companyapp.mapper;

import com.github.katemerek.companyapp.dto.DepartmentDto;
import com.github.katemerek.companyapp.model.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    public Department toDepartment (DepartmentDto departmentDto) {
        Department department = new Department();
        department.setName(department.getName());
        return department;
    }

    public DepartmentDto toDepartmentDto(Department department) {
        return new DepartmentDto(department.getName());
    }
}
