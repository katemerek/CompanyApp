package com.github.katemerek.companyapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for {@link com.github.katemerek.companyapp.model.Department}
 */
public record DepartmentDto(@NotBlank @Size(max = 60) String name) {

}