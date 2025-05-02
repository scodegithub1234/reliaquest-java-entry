package com.challenge.api.service;

import com.challenge.api.model.Employee;
import com.challenge.api.model.EmployeeFinal;
import java.time.Instant;
import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final Map<UUID, Employee> database = new HashMap<>();

    /* Initialize the Employee Service with one employee first */
    public EmployeeService() {
        EmployeeFinal employee = new EmployeeFinal();
        employee.setUuid(UUID.randomUUID());
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setFullName("John Doe");
        employee.setJobTitle("Software Engineer");
        employee.setContractHireDate(Instant.now());

        database.put(employee.getUuid(), employee);
    }

    /* In order to add an employee set age and salary constant for mock up - enter three attributes */
    private void addEmployee(String firstName, String lastName, String jobTitle) {
        EmployeeFinal employee = new EmployeeFinal();
        employee.setUuid(UUID.randomUUID());
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setFullName(firstName + " " + lastName);
        employee.setSalary(10000);
        employee.setAge(20);
        employee.setJobTitle(jobTitle);
        employee.setEmail(lastName + "@employee.com");
        employee.setContractHireDate(Instant.now());
        database.put(employee.getUuid(), employee);
    }

    /* get employees in an ArrayList */
    public List<Employee> getEmployees() {
        return new ArrayList<>(database.values());
    }

    /* not necessary do Optional to avoid NullPointerException */
    public Optional<Employee> getEmployeeByUuid(UUID uuid) {
        return Optional.ofNullable(database.get(uuid));
    }

    /* create method to call in Controller for creating a new employee */
    public Employee createEmployee(Employee employee) {
        UUID id = UUID.randomUUID();
        employee.setUuid(id);
        employee.setContractHireDate(Instant.now());
        employee.setFullName(employee.getFirstName() + " " + employee.getLastName());
        database.put(id, employee);
        return employee;
    }
}
