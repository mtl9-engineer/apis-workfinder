package com.workfinder.app.controllers;

import com.workfinder.app.models.Employee;
import com.workfinder.app.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("employees")
    public ResponseEntity<Object> getAllEmployees(@RequestBody @Nullable List<String> ids){
        try{
            return new ResponseEntity<>(employeeService.getAllEmployees(ids),HttpStatus.OK);

        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }


    @GetMapping("employee")
    public ResponseEntity<Object> getEmployeeByEmail(@RequestParam String email){
        try {
            return new ResponseEntity<>(employeeService.getEmployeeByEmail(email),HttpStatus.OK);
        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }


    @GetMapping("employees/{id}")
    public ResponseEntity<Object> getEmployeeById(@PathVariable String id){
        try {
            Employee employee = employeeService.getEmployeeById(id);
            if(employee != null){
                return new ResponseEntity<>(employee,HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("No Employee Found For This ID",HttpStatus.NOT_FOUND);
            }
        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }


    @PostMapping("employees")
    public ResponseEntity<Object> addEmployee(@RequestBody Employee employee){
        try {
            return new ResponseEntity<>(employeeService.addEmployee(employee),HttpStatus.OK);
        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }

    @PutMapping("employees/{id}")
    public ResponseEntity<Object> updateEmployee(@RequestBody Employee employee , @PathVariable String id){
        try {
            Employee updatedEmployee = employeeService.updateEmployee(employee,id);
            if(updatedEmployee != null){
                 return new ResponseEntity<>(updatedEmployee,HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("No Employee Found For This ID",HttpStatus.NOT_FOUND);
            }
        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }

    @DeleteMapping("employees/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable String id){
        try {
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>("No Employee Found For This ID",HttpStatus.NOT_FOUND);
        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }
}
