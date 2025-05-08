package com.github.katemerek.companyapp.mapper;

import com.github.katemerek.companyapp.dto.EmployeeDto;
import com.github.katemerek.companyapp.exceptions.DepartmentNotFoundException;
import com.github.katemerek.companyapp.model.Department;
import com.github.katemerek.companyapp.model.Employee;
import com.github.katemerek.companyapp.repository.DepartmentRepository;
import com.github.katemerek.companyapp.repository.EmployeeRepository;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeMapper(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
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
    public EmployeeDto toEmployeeDto(Employee employee) {
        return new EmployeeDto(employee.getFirstName(), employee.getLastName(), employee.getPosition(),
                employee.getSalary(), employee.getDepartment().getId());
    }
}
