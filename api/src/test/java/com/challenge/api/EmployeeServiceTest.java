package com.challenge.api;

import static org.junit.jupiter.api.Assertions.*;

import com.challenge.api.model.Employee;
import com.challenge.api.model.EmployeeFinal;
import com.challenge.api.service.EmployeeService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmployeeServiceTest {

    /* need a service variable */
    private EmployeeService employeeService;

    /* enter a mock employee before doing each test */
    @BeforeEach
    void setup() {
        employeeService = new EmployeeService();

        EmployeeFinal employee = new EmployeeFinal();
        employee.setUuid(UUID.randomUUID());
        employee.setFirstName("Test");
        employee.setLastName("User");
        employee.setFullName("Test User");
        employee.setJobTitle("Engineer");

        employeeService.createEmployee(employee);
    }

    /* In this test just ensure that the list is returned with setup */
    @Test
    void getEmployees_shouldReturnEmployees() {
        List<Employee> employees = employeeService.getEmployees();
        assertFalse(employees.isEmpty(), "Employees list empty");
    }

    /* return the employee by using the get by uuid method */
    @Test
    void getEmployeeByUuid_shouldReturnEmployeeIfExists() {
        Employee sample = employeeService.getEmployees().get(0);
        Optional<Employee> found = employeeService.getEmployeeByUuid(sample.getUuid());
        assertTrue(found.isPresent());
        assertEquals(sample.getUuid(), found.get().getUuid());
    }

    /* try finding an employee that does not exist and ensure the return is null */
    @Test
    void getEmployeeByUuid_shouldReturnEmptyIfNotFound() {
        Optional<Employee> found = employeeService.getEmployeeByUuid(UUID.randomUUID());
        assertTrue(found.isEmpty());
    }

    /* Ensure the employee is returned after using create properly */
    @Test
    void createEmployee_shouldReturnEmployee() {
        EmployeeFinal employee = new EmployeeFinal();
        employee.setFirstName("Test");
        employee.setLastName("User");
        employee.setJobTitle("Tester");
        Employee sample = employeeService.createEmployee(employee);
        assertNotNull(sample.getUuid());
        assertEquals("Test User", sample.getFullName());
    }
}
