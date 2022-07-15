package com.workfinder.app.controllers;

import com.workfinder.app.models.Applyment;
import com.workfinder.app.services.ApplymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/api/v1/")
public class ApplymentController {

    @Autowired
    ApplymentService applymentService;

    @GetMapping("applyments")
    public ResponseEntity<Object> getAllApplyments(){
        try {
            return new ResponseEntity<>(applymentService.getAllApplyments(),HttpStatus.OK);
        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }

    @GetMapping("applyments/{id}")
    public ResponseEntity<Object> getApplymentById(@PathVariable String id){
        try {
            Applyment applyment = applymentService.getApplymentById(id);
            if(applyment != null){
                return new ResponseEntity<>(applyment,HttpStatus.OK);
            }
            return new ResponseEntity<>("No Applyment For this Id",HttpStatus.NOT_FOUND);


        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }
    @GetMapping("employees/{employeeId}/applyments")
    public ResponseEntity<Object> getApplymentByEmployee(@PathVariable String employeeId){
        try {
            if(applymentService.getApplymentsByEmployee(employeeId)  != null){
                return new ResponseEntity<>(applymentService.getApplymentsByEmployee(employeeId),HttpStatus.OK);
            }
            return new ResponseEntity<>("No Employee For This ID",HttpStatus.NOT_FOUND);


        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }

    @GetMapping("offers/{offerId}/applyments")
    public ResponseEntity<Object> getApplymentByOffer(@PathVariable String offerId){
        try {
            if(applymentService.getApplymentsByOffer(offerId)  != null){
                return new ResponseEntity<>(applymentService.getApplymentsByOffer(offerId),HttpStatus.OK);
            }
            return new ResponseEntity<>("No Offer For This ID",HttpStatus.NOT_FOUND);

        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }

    @PostMapping("applyments")
    public ResponseEntity<Object> addApplyment(@RequestBody Applyment applyment){
        try {
            return new ResponseEntity<>(applymentService.addApplyment(applyment),HttpStatus.OK);
        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }

    @PutMapping("applyments/{id}")
    public ResponseEntity<Object> updateApplyment(@RequestBody Applyment applyment , @PathVariable String id){
        try {
            Applyment updatedApplyment = applymentService.updateApplyment(applyment,id);
            if(updatedApplyment != null){
                return new ResponseEntity<>(updatedApplyment,HttpStatus.OK);
            }
            return new ResponseEntity<>("No Applyment For this Id",HttpStatus.NOT_FOUND);
        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }

    @DeleteMapping("applyments/{id}")
    public ResponseEntity<Object> deleteApplyment(@PathVariable String id){
        try {
            applymentService.deleteApplyment(id);
            return new ResponseEntity<>("Applyment deleted Successfully",HttpStatus.OK);
        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }
}
