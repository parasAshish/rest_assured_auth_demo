package com.example.test.controller;


import com.example.test.model.Employee;
import com.example.test.model.EmployeeDAO;
import com.example.test.model.Employees;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController

public class RestAssuredController {
    @Autowired
    private EmployeeDAO employeeDao;

    @RequestMapping(value = "/v2/employees", method = RequestMethod.GET)
    public Employees getEmployees()
    {
        return employeeDao.getAllEmployees();
    }

    @RequestMapping(value = "/v2/addEmployee", method = RequestMethod.POST)
    public ResponseEntity<Object> addEmployee(
            @RequestHeader(name = "X-COM-PERSIST", required = true) String headerPersist,
            @RequestHeader(name = "X-COM-LOCATION", defaultValue = "ASIA") String headerLocation,
            @RequestBody Employee employee)
    {
        //Generate resource id
        Integer id = employeeDao.getAllEmployees().getEmployeeList().size() + 1;
        employee.setId(id);

        //add resource
        employeeDao.addEmployee(employee);

        //Create resource location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(employee.getId())
                .toUri();

        //Send location in response
        return ResponseEntity.created(location).build();
    }
}
