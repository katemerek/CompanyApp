package com.github.katemerek.companyapp.service;

import com.github.katemerek.companyapp.exceptions.DepartmentNotFoundException;
import com.github.katemerek.companyapp.model.Department;
import com.github.katemerek.companyapp.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Optional<Department> getDepartmentById(Long id) {
        if(!departmentRepository.existsById(id)) {
            throw new DepartmentNotFoundException(id);
        }
        return departmentRepository.findById(id);
    }

    @Transactional
    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Transactional
    public Department updateDepartment(Long id, Department department) {
        if(!departmentRepository.existsById(id)) {
            throw new DepartmentNotFoundException(id);
        }
        return departmentRepository.save(department);
    }

    @Transactional
    public void deleteDepartment(Long id) {
        if(!departmentRepository.existsById(id)) {
            throw new DepartmentNotFoundException(id);
        }
        departmentRepository.deleteById(id);
    }
}
