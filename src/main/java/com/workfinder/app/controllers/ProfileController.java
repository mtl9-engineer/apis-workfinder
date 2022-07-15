package com.workfinder.app.controllers;

import com.workfinder.app.models.Profile;
import com.workfinder.app.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;


@RestController
@RequestMapping("/api/v1/")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @GetMapping("profiles")
    public ResponseEntity<Object> getAllProfiles(){
        try {
            return new ResponseEntity<>(profileService.getAllProfiles(),HttpStatus.OK);
        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }

    @GetMapping("profiles/{id}")
    public ResponseEntity<Object> getProfileById(@PathVariable String id){
        try {
            Profile profile = profileService.getProfileById(id);
            if(profile != null){
                return new ResponseEntity<>(profile,HttpStatus.OK);
            }
            return new ResponseEntity<>("No Profile Found For This ID",HttpStatus.NOT_FOUND);

        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }

    @PostMapping("profiles")
    public ResponseEntity<Object> addProfile(@RequestBody Profile profile){
        try {
            return new ResponseEntity<>(profileService.addProfile(profile),HttpStatus.OK);
        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }

    @PutMapping("profiles/{id}")
    public ResponseEntity<Object> updateProfile(@RequestBody Profile profile , @PathVariable String id){
        try {
            Profile updatedProfile = profileService.updateProfile(profile,id);
            if(profile != null){
                return new ResponseEntity<>(updatedProfile,HttpStatus.OK);
            }
            return new ResponseEntity<>("No Profile Found For This ID",HttpStatus.NOT_FOUND);

        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }

    @DeleteMapping("profiles/{id}")
    public ResponseEntity<Object> deleteProfile(@PathVariable String id){
        try {
            profileService.deleteProfile(id);
            return new ResponseEntity<>("Profile deleted successfully",HttpStatus.OK);
        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }
}
