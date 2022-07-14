package com.workfinder.app.controllers;

import com.workfinder.app.models.Company;
import com.workfinder.app.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("api/v1/")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @GetMapping("companies")
    public ResponseEntity<Object> getAllCompanys(){
        try {
            return new ResponseEntity<>(companyService.getAllCompanys(),HttpStatus.OK);
        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }

    @GetMapping("companies/{id}")
    public ResponseEntity<Object> getCompanyById(@PathVariable String id){
        try {
            Company company = companyService.getCompanyById(id);
            if(company != null){
                return new ResponseEntity<>(company,HttpStatus.OK);
            }
            return new ResponseEntity<>("No Company Found For This ID",HttpStatus.NOT_FOUND);
        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }

    @PostMapping("companies")
    public ResponseEntity<Object> addCompany(@RequestBody Company company){
        try {
            return new ResponseEntity<>(companyService.addCompany(company),HttpStatus.OK);
        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }

    @PutMapping("companies/{id}")
    public ResponseEntity<Object> updateCompany(@RequestBody Company company , @PathVariable String id){
        try {
            Company updatedCompany = companyService.updateCompany(company,id);
            if(updatedCompany != null){
                return new ResponseEntity<>(updatedCompany,HttpStatus.OK);
            }
            return new ResponseEntity<>("No Company Found For This ID",HttpStatus.NOT_FOUND);

        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }

    @DeleteMapping("companies/{id}")
    public ResponseEntity<Object> deleteCompany(@PathVariable String id){
        try {
            companyService.deleteCompany(id);
            return new ResponseEntity<>("Company Deleted Successfully",HttpStatus.OK);

        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }
}
