package com.github.katemerek.companyapp.controller;

import com.github.katemerek.companyapp.dto.EmployeeDto;
import com.github.katemerek.companyapp.dto.EmployeeResponse;
import com.github.katemerek.companyapp.exceptions.DepartmentNotFoundException;
import com.github.katemerek.companyapp.exceptions.EmployeeNotFoundException;
import com.github.katemerek.companyapp.mapper.EmployeeMapper;
import com.github.katemerek.companyapp.model.Employee;
import com.github.katemerek.companyapp.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    public EmployeeController(EmployeeService employeeService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping("/{id}")
    public EmployeeDto getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping
    public List<EmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> addEmployee(@RequestBody @Valid EmployeeDto employeeDto) throws DepartmentNotFoundException {
        Employee resultEmployee = employeeMapper.toEmployee(employeeDto);
        employeeService.addEmployee(resultEmployee);
        EmployeeResponse response = new EmployeeResponse(resultEmployee.getId(),
                "New Employee with id " + resultEmployee.getId() + " was added successfully");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeDto employeeDto)
            throws DepartmentNotFoundException, EmployeeNotFoundException {
        Employee resultEmployee = employeeService.updateEmployee(id, employeeDto);
        EmployeeResponse response = new EmployeeResponse(resultEmployee.getId(),
                "Employee with id " + resultEmployee.getId() + " updated");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeResponse> deleteEmployee(@PathVariable Long id) throws EmployeeNotFoundException {
        employeeService.deleteEmployee(id);
        EmployeeResponse response = new EmployeeResponse(id, "Employee with id " + id + " deleted successfully");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}

