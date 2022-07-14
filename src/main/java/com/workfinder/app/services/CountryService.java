package com.workfinder.app.services;

import com.workfinder.app.models.Country;
import com.workfinder.app.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    @Autowired
    private CountryRepository countryRepository;

    public Country addCountry(Country Country){
        return countryRepository.save(Country);
    }

    public Country updateCountry(Country country , String id){
        Optional<Country> optionalExistedCountry = countryRepository.findById(id);
        if(optionalExistedCountry.isPresent()){
            Country existedCountry = optionalExistedCountry.get();
            existedCountry.setName(country.getName());
            return countryRepository.save(existedCountry);
        }
        else {
            return null;
        }
    }

    public void deleteCountry(String id){
        countryRepository.deleteById(id);
    }

    public List<Country> getAllCountrys(){
        return countryRepository.findAll();
    }

    public Country getCountryById(String id){
        Optional<Country> optionalCountry = countryRepository.findById(id);
        if(optionalCountry.isPresent()){
            return optionalCountry.get();
        }
        else {
            return null;
        }

    }
}
