package com.challenge.api;

import com.challenge.api.model.Employee;
import com.challenge.api.model.MockEmployee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    private EmployeeService employeeService;

    @BeforeEach
    void setup() {
        employeeService = new EmployeeService();
    }

    @Test
    void getAllEmployees_shouldReturnMockEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        assertFalse(employees.isEmpty(), "Employees list should not be empty");
    }

    @Test
    void getEmployeeByUuid_shouldReturnEmployeeIfExists() {
        Employee sample = employeeService.getAllEmployees().get(0);
        Optional<Employee> found = employeeService.getEmployeeByUuid(sample.getUuid());

        assertTrue(found.isPresent());
        assertEquals(sample.getUuid(), found.get().getUuid());
    }

    @Test
    void getEmployeeByUuid_shouldReturnEmptyIfNotFound() {
        Optional<Employee> found = employeeService.getEmployeeByUuid(UUID.randomUUID());
        assertTrue(found.isEmpty());
    }

    @Test
    void createEmployee_shouldReturnEmployeeWithIdAndFullName() {
        MockEmployee newEmp = new MockEmployee();
        newEmp.setFirstName("Test");
        newEmp.setLastName("User");
        newEmp.setJobTitle("Tester");

        Employee created = employeeService.createEmployee(newEmp);

        assertNotNull(created.getUuid());
        assertEquals("Test User", created.getFullName());
    }
}