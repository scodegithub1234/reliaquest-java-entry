package com.challenge.api.controller;

import com.challenge.api.model.Employee;
import com.challenge.api.model.EmployeeFinal;
import com.challenge.api.service.EmployeeService;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Fill in the missing aspects of this Spring Web REST Controller. Don't forget to add a Service layer [DONE].
 */
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    /**
     * @implNote Need not be concerned with an actual persistence layer. Generate mock Employee models as necessary.
     * @return One or more Employees.
     */
    /* Note: final is needed because of set up */
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /* use getmapping for get methods */
    @GetMapping
    public List<Employee> getAllEmployees() {
        // throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
        return employeeService.getEmployees();
    }

    /**
     * @implNote Need not be concerned with an actual persistence layer. Generate mock Employee model as necessary.
     * @param uuid Employee UUID
     * @return Requested Employee if exists
     */
    @GetMapping("/{uuid}")
    public Employee getEmployeeByUuid(@PathVariable UUID uuid) {
        // throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
        return employeeService
                .getEmployeeByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /**
     * @implNote Need not be concerned with an actual persistence layer.
     * @param requestBody hint!
     * @return Newly created Employee
     */
    /* post mapping for post method */
    @PostMapping
    public Employee createEmployee(@RequestBody EmployeeFinal requestBody) {
        // throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
        return employeeService.createEmployee(requestBody);
    }
}
