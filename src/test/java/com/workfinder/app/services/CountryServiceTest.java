package com.workfinder.app.services;

import com.workfinder.app.models.Country;
import com.workfinder.app.repositories.CountryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CountryServiceTest {

    @InjectMocks
    CountryService countryService;

    @Mock
    CountryRepository countryRepository;
    

    private Country country;
    @BeforeEach
    public  void setUp(){
        country = new Country();
        country.setId("countryId");
        country.setName("Morocco");
       
    }

    @Test
    public void addCountry(){
        Mockito.when(countryRepository.save(any(Country.class))).thenReturn(country);
        Country expectedCountry = countryService.addCountry(country);

        Assertions.assertEquals("countryId",expectedCountry.getId());
        Assertions.assertEquals("Morocco",expectedCountry.getName());
    }

    @Test
    public void updateCountry(){
        Country country2 = new Country();
        country2.setId("countryId2");
        country2.setName("Morocco");

        Mockito.when(countryRepository.findById("countryId")).thenReturn(Optional.of(country));
        Mockito.when(countryRepository.save(any(Country.class))).thenReturn(country2);

        Country updatedCountry = countryService.updateCountry(country2,"countryId");

        Assertions.assertEquals("countryId2",updatedCountry.getId());
    }


    @Test
    public void getCountryById(){
        Mockito.when(countryRepository.findById("countryId")).thenReturn(Optional.of(country));

        Country expectedCountry = countryService.getCountryById("countryId");

        Assertions.assertEquals("Morocco",expectedCountry.getName());

    }

    @Test
    public void getAllCountrys(){
        List<Country> countrys =  new ArrayList<>();
        countrys.add(country);

        Mockito.when(countryRepository.findAll()).thenReturn(countrys);

        List<Country> expectedCountrys = countryService.getAllCountrys();
        Assertions.assertEquals(1,expectedCountrys.size());
    }

}
