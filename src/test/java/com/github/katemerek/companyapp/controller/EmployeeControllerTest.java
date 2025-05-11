package com.github.katemerek.companyapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.katemerek.companyapp.dto.EmployeeDto;
import com.github.katemerek.companyapp.mapper.EmployeeMapper;
import com.github.katemerek.companyapp.model.Department;
import com.github.katemerek.companyapp.model.Employee;
import com.github.katemerek.companyapp.repository.DepartmentRepository;
import com.github.katemerek.companyapp.repository.EmployeeRepository;
import com.github.katemerek.companyapp.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the {@link EmployeeController}
 */
@WebMvcTest({EmployeeController.class})
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockitoBean
    private EmployeeService employeeService;

    @MockitoBean
    private DepartmentRepository departmentRepository;

    @MockitoBean
    private EmployeeRepository employeeRepository;

    @MockitoBean
    EmployeeMapper employeeMapper;

    private Employee createEmployee_Mock() {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setPosition("ryadovoi");
        employee.setSalary(BigDecimal.valueOf(10000));
        employee.setDepartment(createDepartment_Mock());
        return employee;
    }

    public Department createDepartment_Mock() {
        Department department = new Department();
        department.setId(1);
        department.setName("Department 1");
        return department;
    }

    private EmployeeDto createEmployeeDto_Mock() {
        return new EmployeeDto("John", "Doe", "kukumber", BigDecimal.valueOf(10000), 1L);
    }

    private List<EmployeeDto> createListOfEmployee_Mock() {
        EmployeeDto one = new EmployeeDto("John", "Doe", "kukumber", BigDecimal.valueOf(10000), 1L);
        EmployeeDto two = new EmployeeDto("Liza", "Doe", "kukumber", BigDecimal.valueOf(80000), 2L);
        return List.of(one, two);
    }

    @Test
    public void getEmployeeById_ShouldReturnEmployeeDto() throws Exception {
        when(employeeService.getEmployeeById(anyLong())).thenReturn(createEmployeeDto_Mock());

        mockMvc.perform(get("/api/employee/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(createEmployeeDto_Mock())))
                .andDo(print());
    }

    @Test
    public void getAllEmployees_ShouldReturnListOfEmployeeDto() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(createListOfEmployee_Mock());

        mockMvc.perform(get("/api/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andDo(print());
    }

    @Test
    public void addEmployee_ShouldReturnCreated() throws Exception {
        when(departmentRepository.existsById(1L)).thenReturn(true);
        when(employeeMapper.toEmployee(any(EmployeeDto.class))).thenReturn(createEmployee_Mock());

        mockMvc.perform(post("/api/employee")
                        .content(mapper.writeValueAsString(createEmployee_Mock()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andDo(print());
    }

    @Test
    public void updateEmployee_ShouldReturnOk() throws Exception {
        when(departmentRepository.existsById(1L)).thenReturn(true);
        when(employeeService.updateEmployee(anyLong(), any(EmployeeDto.class))).thenReturn(createEmployee_Mock());

        mockMvc.perform(put("/api/employee/{id}", "1")
                        .content(mapper.writeValueAsString(createEmployeeDto_Mock()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void updateEmployee_ShouldReturnNotFound() throws Exception {
        when(employeeRepository.existsById(999L)).thenReturn(false);
        when(employeeMapper.toEmployeeForUpdate(anyLong(), any(EmployeeDto.class)))
                .thenReturn(null);

        mockMvc.perform(put("/api/employees/{id}", 999L)
                        .content(mapper.writeValueAsString(createEmployeeDto_Mock()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(employeeRepository, never()).save(any());
    }

    @Test
    public void deleteEmployee_ShouldReturnOk() throws Exception {
        mockMvc.perform(delete("/api/employee/{1}", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
