package com.github.katemerek.companyapp.service;

import com.github.katemerek.companyapp.dto.DepartmentDto;
import com.github.katemerek.companyapp.exceptions.DepartmentNotFoundException;
import com.github.katemerek.companyapp.mapper.DepartmentMapper;
import com.github.katemerek.companyapp.model.Department;
import com.github.katemerek.companyapp.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    public List<DepartmentDto> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toDepartmentDto)
                .toList();
    }

    public DepartmentDto getDepartmentById(long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new DepartmentNotFoundException(id));
        return departmentMapper.toDepartmentDto(department);
    }

    @Transactional
    public void createDepartment(Department department) {
        departmentRepository.save(department);
    }

    @Transactional
    public Department updateDepartment(long id, Department department) {
        if (!departmentRepository.existsById(id)) {
            throw new DepartmentNotFoundException(id);
        }
        return departmentRepository.save(department);
    }

    @Transactional
    public void deleteDepartment(long id) {
        if (!departmentRepository.existsById(id)) {
            throw new DepartmentNotFoundException(id);
        }
        departmentRepository.deleteById(id);
    }
}
