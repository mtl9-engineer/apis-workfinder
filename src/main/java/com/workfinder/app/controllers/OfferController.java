package com.workfinder.app.controllers;

import com.workfinder.app.models.Offer;
import com.workfinder.app.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class OfferController {

    @Autowired
    OfferService offerService;

    @PostMapping("companies/{idCompany}/offers")
    public ResponseEntity<Object> addOffer(@PathVariable String idCompany , @RequestBody Offer offer){
        try {
            return new ResponseEntity<>(offerService.addOffer(offer , idCompany),HttpStatus.OK);
        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }

    @GetMapping("offers")
    public ResponseEntity<Object> getAllOffers(@RequestBody @Nullable List<String> ids){
        try {
            return new ResponseEntity<>(offerService.getAllOffers(ids),HttpStatus.OK);
        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }

    @GetMapping("offers/{offerId}")
    public ResponseEntity<Object> getOfferById(@PathVariable String offerId){
        try {
            Offer offer = offerService.getOfferById(offerId);
            if(offer != null){
                return new ResponseEntity<>(offer,HttpStatus.OK);
            }
            return new ResponseEntity<>("No Offer Found For This ID",HttpStatus.NOT_FOUND);

        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }

    @GetMapping("companies/{companyId}/offers")
    public ResponseEntity<Object> getOffersByCompany(@PathVariable String companyId){
        try {
            return new ResponseEntity<>(offerService.getOffersByCompany(companyId),HttpStatus.OK);
        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }

    @DeleteMapping("offers/{id}")
    public ResponseEntity<Object> deleteOffer(@PathVariable String id){
        try {
            offerService.deleteOffer(id);
            return new ResponseEntity<>("Offer deleted successfully",HttpStatus.OK);
        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }

    @PutMapping("offers/{id}")
    public ResponseEntity<Object> updateOffer(@RequestBody Offer offer , @PathVariable String id){
        try {
            Offer updatedOffer = offerService.updateOffer(offer,id);
            if(updatedOffer != null){
                return new ResponseEntity<>(updatedOffer,HttpStatus.OK);
            }
            return new ResponseEntity<>("No Offer Found For This ID",HttpStatus.NOT_FOUND);

        }
        catch (HttpClientErrorException httpClientErrorException){
            return new ResponseEntity<>(httpClientErrorException.getMessage(),httpClientErrorException.getStatusCode());
        }
    }

}
