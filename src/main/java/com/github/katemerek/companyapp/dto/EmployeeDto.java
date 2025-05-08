package com.github.katemerek.companyapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * DTO for {@link com.github.katemerek.companyapp.model.Employee}
 */
public record EmployeeDto(@Size(max = 30) @NotBlank String firstName, @Size(max = 30) @NotBlank String lastName,
                          @Size(max = 60) @NotBlank String position, @PositiveOrZero BigDecimal salary,
                          long departmentId) {
}