package com.challenge.api;

import com.challenge.api.model.Employee;
import com.challenge.api.model.MockEmployee;
import com.challenge.api.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllEmployees_returnsList() throws Exception {
        MockEmployee emp = new MockEmployee();
        emp.setUuid(UUID.randomUUID());
        emp.setFirstName("Alice");
        emp.setLastName("Smith");
        emp.setFullName("Alice Smith");
        emp.setJobTitle("Engineer");

        Mockito.when(employeeService.getAllEmployees())
                .thenReturn(Collections.singletonList(emp));

        mockMvc.perform(get("/api/v1/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Alice Smith"));
    }

    @Test
    void getEmployeeByUuid_returnsEmployeeIfExists() throws Exception {
        UUID uuid = UUID.randomUUID();
        MockEmployee emp = new MockEmployee();
        emp.setUuid(uuid);
        emp.setFirstName("Bob");
        emp.setLastName("Jones");
        emp.setFullName("Bob Jones");

        Mockito.when(employeeService.getEmployeeByUuid(uuid))
                .thenReturn(Optional.of(emp));

        mockMvc.perform(get("/api/v1/employee/" + uuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Bob Jones"));
    }

    @Test
    void getEmployeeByUuid_returns404IfNotFound() throws Exception {
        UUID randomId = UUID.randomUUID();
        Mockito.when(employeeService.getEmployeeByUuid(randomId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/employee/" + randomId))
                .andExpect(status().isNotFound());
    }

    @Test
    void createEmployee_returnsCreatedEmployee() throws Exception {
        MockEmployee request = new MockEmployee();
        request.setFirstName("Charlie");
        request.setLastName("Day");
        request.setJobTitle("Consultant");

        MockEmployee response = new MockEmployee();
        response.setUuid(UUID.randomUUID());
        response.setFirstName("Charlie");
        response.setLastName("Day");
        response.setFullName("Charlie Day");
        response.setJobTitle("Consultant");
        response.setContractHireDate(Instant.now());

        Mockito.when(employeeService.createEmployee(Mockito.any()))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Charlie Day"));
    }
}