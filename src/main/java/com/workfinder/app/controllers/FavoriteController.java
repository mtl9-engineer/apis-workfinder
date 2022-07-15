package com.workfinder.app.controllers;

import com.workfinder.app.models.Favorite;
import com.workfinder.app.services.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;


@RestController
@RequestMapping("/api/v1/")
public class FavoriteController {

    @Autowired
    FavoriteService favoriteService;

    @GetMapping("favorites")
    public ResponseEntity<Object> getAllFavorites(){
        try {
            return new ResponseEntity<>(favoriteService.getAllFavorites(),HttpStatus.OK);
        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }

    @GetMapping("employees/{employeeId}/favorites")
    public ResponseEntity<Object> getFavoritesByEmployee(@PathVariable String employeeId){
        try {
            return new ResponseEntity<>(favoriteService.getFavoritesByEmployee(employeeId),HttpStatus.OK);
        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }

    @GetMapping("favorites/{id}")
    public ResponseEntity<Object> getFavoriteById(@PathVariable String id){
        try {
            Favorite favorite = favoriteService.getFavoriteById(id);
            if(favorite != null){
                return new ResponseEntity<>(favorite,HttpStatus.OK);
            }
            return new ResponseEntity<>("No Favorite Found For This ID",HttpStatus.NOT_FOUND);

        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }

    @PostMapping("favorites")
    public ResponseEntity<Object> addFavorite(@RequestBody Favorite favorite){
        try {
            return new ResponseEntity<>(favoriteService.addFavorite(favorite),HttpStatus.OK);
        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }

    @PutMapping("favorites/{id}")
    public ResponseEntity<Object> updateFavorite(@RequestBody Favorite favorite , @PathVariable String id){
        try {
            Favorite updatedFavorite = favoriteService.updateFavorite(favorite,id);
            if(updatedFavorite != null){
                return new ResponseEntity<>(updatedFavorite,HttpStatus.OK);
            }
            return new ResponseEntity<>("No Favorite Found For This ID",HttpStatus.NOT_FOUND);

        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }

    @DeleteMapping("favorites/{id}")
    public ResponseEntity<Object> deleteFavorite(@PathVariable String id){
        try {
            favoriteService.deleteFavorite(id);
            return new ResponseEntity<>("No Favorite Found For This ID",HttpStatus.OK);
        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }
}
