package com.github.katemerek.companyapp.repository;

import com.github.katemerek.companyapp.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
