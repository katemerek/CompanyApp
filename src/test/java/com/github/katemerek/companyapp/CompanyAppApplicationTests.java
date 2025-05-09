package com.github.katemerek.companyapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CompanyAppApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    public void getDepartmentById_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/department/{0}", "0"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getAllDepartments_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/department"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void createDepartment_ShouldReturnCreated() throws Exception {
        String departmentDto = """
                {
                    "name": "post"
                }""";

        mockMvc.perform(post("/api/department")
                        .content(departmentDto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void updateDepartment_ShouldReturnOk() throws Exception {
        String department = """
                {
                    "id": 0,
                    "name": "bakery"
                }""";

        mockMvc.perform(put("/api/department/{0}", "0")
                        .content(department)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void deleteDepartment_ShouldReturnOk() throws Exception {
        mockMvc.perform(delete("/api/department/{0}", "0"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
