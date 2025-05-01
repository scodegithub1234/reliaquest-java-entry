package com.challenge.api.service;

import com.challenge.api.model.Employee;
import com.challenge.api.model.EmployeeFinal;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class EmployeeService {

    private final Map<UUID, Employee> database = new HashMap<>();
    
    public EmployeeService() {

    }

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

    public List<Employee> getEmployees() {
        return new ArrayList<>(database.values());
    }

    public Optional<Employee> getEmployeeByUuid(UUID uuid) {
        return Optional.ofNullable(mockDatabase.get(uuid));
    }

    public Employee createEmployee(Employee employee) {
        UUID id = UUID.randomUUID();
        employee.setUuid(id);
        employee.setContractHireDate(Instant.now());
        employee.setFullName(employee.getFirstName() + " " + employee.getLastName());
        database.put(id, employee);
        return employee;

    }
}