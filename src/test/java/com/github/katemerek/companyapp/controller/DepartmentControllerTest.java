package com.github.katemerek.companyapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.katemerek.companyapp.dto.DepartmentDto;
import com.github.katemerek.companyapp.mapper.DepartmentMapper;
import com.github.katemerek.companyapp.model.Department;
import com.github.katemerek.companyapp.repository.DepartmentRepository;
import com.github.katemerek.companyapp.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the {@link DepartmentController}
 */
@WebMvcTest({DepartmentController.class})
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DepartmentService departmentService;

    @MockitoBean
    private DepartmentMapper departmentMapper;

    @MockitoBean
    private DepartmentRepository departmentRepository;

    @Autowired
    ObjectMapper mapper;

    public DepartmentDto createDepartmentDto_Mock() {
        return new DepartmentDto("Department 1");
    }

    public Department createDepartment_Mock() {
        Department department = new Department();
        department.setId(1);
        department.setName("Department 1");
        return department;
    }

    private List<DepartmentDto> createListOfDepartments_Mock() {
        DepartmentDto one = new DepartmentDto("zakupki");
        DepartmentDto two = new DepartmentDto("post");
        return List.of(one, two);
    }

    @Test
    public void getDepartmentById_ShouldReturnDepartmentDto() throws Exception {
        when(departmentService.getDepartmentById(anyLong())).thenReturn(createDepartmentDto_Mock());

        mockMvc.perform(get("/api/department/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(createDepartmentDto_Mock())))
                .andDo(print());
    }

    @Test
    public void getAllDepartments_ShouldReturnListOfDepartmentDto() throws Exception {
        when(departmentService.getAllDepartments()).thenReturn(createListOfDepartments_Mock());

        mockMvc.perform(get("/api/department"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andDo(print());
    }

    @Test
    public void createDepartment_ShouldReturnCreated() throws Exception {
        when(departmentMapper.toDepartment(any(DepartmentDto.class))).thenReturn(createDepartment_Mock());

        mockMvc.perform(post("/api/department")
                        .content(mapper.writeValueAsString(createDepartmentDto_Mock()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void updateDepartment_ShouldReturnOk() throws Exception {
        when(departmentRepository.existsById(1L)).thenReturn(true);
        when(departmentService.updateDepartment(anyLong(), any(Department.class))).thenReturn(createDepartment_Mock());

        mockMvc.perform(put("/api/department/{id}", 1)
                        .content(mapper.writeValueAsString(createDepartmentDto_Mock()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void updateDepartment_shouldReturnNotFound() throws Exception {
        Mockito.when(departmentRepository.existsById(999L)).thenReturn(false);

        mockMvc.perform(put("/api/departments/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createDepartmentDto_Mock())))
                .andExpect(status().isNotFound());

        verify(departmentRepository, never()).existsById(999L);
    }

    @Test
    public void deleteDepartment_ShouldReturnOk() throws Exception {
        mockMvc.perform(delete("/api/department/{id}", "0"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
