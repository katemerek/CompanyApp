package com.github.katemerek.companyapp.controller;

import com.github.katemerek.companyapp.dto.DepartmentDto;
import com.github.katemerek.companyapp.dto.DepartmentResponse;
import com.github.katemerek.companyapp.mapper.DepartmentMapper;
import com.github.katemerek.companyapp.model.Department;
import com.github.katemerek.companyapp.service.DepartmentService;
import com.github.katemerek.companyapp.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DepartmentMapper departmentMapper;

    public DepartmentController(DepartmentService departmentService, EmployeeService employeeService, DepartmentMapper departmentMapper) {
        this.departmentService = departmentService;
        this.departmentMapper = departmentMapper;
    }

    @GetMapping("/{id}")
    public DepartmentDto getDepartmentById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }

    @GetMapping
    public List<DepartmentDto> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @PostMapping
    public ResponseEntity<DepartmentResponse> createDepartment(@RequestBody @Valid DepartmentDto departmentDto) {
        Department department = departmentMapper.toDepartment(departmentDto);
        departmentService.createDepartment(department);

        DepartmentResponse response = new DepartmentResponse(department.getId(),
                "New department with id " + department.getId() + " was created successfully");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponse> updateDepartment(@PathVariable Long id, @RequestBody @Valid Department department) {
        Department resultDepartment = departmentService.updateDepartment(id, department);
        DepartmentResponse response = new DepartmentResponse(resultDepartment.getId(), "Department with id " + resultDepartment.getId() + " updated");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DepartmentResponse> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        DepartmentResponse response = new DepartmentResponse(id, "Department with id " + id + " deleted successfully");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
