package com.workfinder.app.controllers;

import com.workfinder.app.models.Country;
import com.workfinder.app.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/api/v1/")
public class CountryController {

    @Autowired
    CountryService countryService;

    @GetMapping("countries")
    public ResponseEntity<Object> getAllCountrys(){
        try {
            return new ResponseEntity<>(countryService.getAllCountrys(),HttpStatus.OK);
        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }

    @GetMapping("countries/{id}")
    public ResponseEntity<Object> getCountryById(@PathVariable String id){
        try {
            Country country = countryService.getCountryById(id);
            if(country != null){
                return new ResponseEntity<>(country,HttpStatus.OK);
            }
            return new ResponseEntity<>("No Country Found For This ID",HttpStatus.NOT_FOUND);

        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }

    @PostMapping("countries")
    public ResponseEntity<Object> addCountry(@RequestBody Country country){
        try {
            return new ResponseEntity<>(countryService.addCountry(country),HttpStatus.OK);
        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }

    @PutMapping("countries/{id}")
    public ResponseEntity<Object> updateCountry(@RequestBody Country country , @PathVariable String id){
        try {
            Country updatedCountry = countryService.updateCountry(country,id);
            if(updatedCountry != null){
                return new ResponseEntity<>(updatedCountry,HttpStatus.OK);
            }
            return new ResponseEntity<>("No Country Found For This ID",HttpStatus.NOT_FOUND);

        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }

    @DeleteMapping("countries/{id}")
    public ResponseEntity<Object> deleteCountry(@PathVariable String id){
        try {
            countryService.deleteCountry(id);
            return new ResponseEntity<>("Country Deleted Successfully",HttpStatus.OK);
        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }
}
