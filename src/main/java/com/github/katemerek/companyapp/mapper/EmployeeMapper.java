package com.github.katemerek.companyapp.mapper;

import com.github.katemerek.companyapp.dto.EmployeeDto;
import com.github.katemerek.companyapp.exceptions.DepartmentNotFoundException;
import com.github.katemerek.companyapp.model.Department;
import com.github.katemerek.companyapp.model.Employee;
import com.github.katemerek.companyapp.repository.DepartmentRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmployeeMapper {
    private final DepartmentRepository departmentRepository;

    public EmployeeMapper(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Employee toEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setFirstName(employeeDto.firstName());
        employee.setLastName(employeeDto.lastName());
        employee.setPosition(employeeDto.position());
        employee.setSalary(employeeDto.salary());
        Department department = departmentRepository.findById(employeeDto.departmentId())
                .orElseThrow(() -> new DepartmentNotFoundException(employeeDto.departmentId()));
        employee.setDepartment(department);
        return employee;
    }

    public Employee toEmployeeForUpdate(long id, EmployeeDto employeeDto) {
        Employee employee = toEmployee(employeeDto);
        employee.setId(id);
        return employee;
    }

    public EmployeeDto toEmployeeDto(Employee employee) {
        return new EmployeeDto(employee.getFirstName(), employee.getLastName(), employee.getPosition(),
                employee.getSalary(), employee.getDepartment().getId());
    }
}
