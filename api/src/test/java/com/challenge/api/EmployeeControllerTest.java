package com.challenge.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.challenge.api.controller.EmployeeController;
import com.challenge.api.model.EmployeeFinal;
import com.challenge.api.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    /* this helps mock the controller */
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    /* ensure the list is returned properly after entering an employee */
    @Test
    void getEmployeesReturnsList() throws Exception {
        EmployeeFinal employee = new EmployeeFinal();
        employee.setUuid(UUID.randomUUID());
        employee.setFirstName("Alice");
        employee.setLastName("Smith");
        employee.setFullName("Alice Smith");
        employee.setJobTitle("Engineer");

        /* used mockito code to simulate getting the endpoint */
        Mockito.when(employeeService.getEmployees()).thenReturn(Collections.singletonList(employee));
        mockMvc.perform(get("/api/v1/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Alice Smith"));
    }

    /* ensure that getting by uuid returns the correct employee */
    @Test
    void getEmployeeByUuidReturnsEmployee() throws Exception {
        UUID uuid = UUID.randomUUID();
        EmployeeFinal employee = new EmployeeFinal();
        employee.setUuid(uuid);
        employee.setFirstName("Bob");
        employee.setLastName("Jones");
        employee.setFullName("Bob Jones");
        employee.setJobTitle("Engineer");
        employee.setContractHireDate(Instant.now());

        /* used mockito code to simulate getting the endpoint */
        Mockito.when(employeeService.getEmployeeByUuid(uuid)).thenReturn(Optional.of(employee));
        mockMvc.perform(get("/api/v1/employee/" + uuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Bob Jones"));
    }

    /* ensure that post method of creating an employee successfully adds the employee */
    @Test
    void createEmployee_returnsCreatedEmployee() throws Exception {
        /* the requestbody for the method */
        EmployeeFinal request = new EmployeeFinal();
        request.setFirstName("Charlie");
        request.setLastName("Li");
        request.setJobTitle("Consultant");

        /* the expected response */
        EmployeeFinal response = new EmployeeFinal();
        response.setUuid(UUID.randomUUID());
        response.setFirstName("Charlie");
        response.setLastName("Li");
        response.setFullName("Charlie Li");
        response.setJobTitle("Consultant");
        response.setContractHireDate(Instant.now());

        /* used mockito code to simulate getting the endpoint */
        Mockito.when(employeeService.createEmployee(Mockito.any())).thenReturn(response);
        mockMvc.perform(post("/api/v1/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Charlie Li"));
    }
}
