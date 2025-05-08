package com.github.katemerek.companyapp.repository;

import com.github.katemerek.companyapp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
