package com.workfinder.app.services;

import com.workfinder.app.models.Company;
import com.workfinder.app.models.Offer;
import com.workfinder.app.models.Profile;
import com.workfinder.app.repositories.CompanyRepository;
import com.workfinder.app.repositories.OfferRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {

    @InjectMocks
    OfferService offerService;

    @Mock
    OfferRepository offerRepository;

    @Mock
    CompanyRepository companyRepository;

    private Offer offer;
    private Company company;
    private Profile profile;

    @BeforeEach
    public  void setUp(){
        offer = new Offer();
        company = new Company();
        profile = new Profile();

        profile.setId("profileId");
        profile.setName("Full Stack Developer");

        company.setId("companyId");
        company.setName("APPLE");

        offer.setId("offerId");
        offer.setCompany(company);
        offer.setMinSalary(100000);
        offer.setMaxSalary(200000);
        offer.setProfiles(Arrays.asList(profile));

    }

    @Test
    public void addOffer(){

        Mockito.when(companyRepository.findById("companyId")).thenReturn(Optional.of(company));
        Mockito.when(offerRepository.save(any(Offer.class))).thenReturn(offer);
        Offer expectedOffer = offerService.addOffer(offer,"companyId");

        Assertions.assertEquals("offerId",expectedOffer.getId());
        Assertions.assertEquals("APPLE",expectedOffer.getCompany().getName());
    }

    @Test
    public void updateOffer(){
        Offer offer2 = new Offer();
        offer2.setId("offerId2");

        Mockito.when(offerRepository.findById("offerId")).thenReturn(Optional.of(offer));
        Mockito.when(offerRepository.save(any(Offer.class))).thenReturn(offer2);

        Offer updatedOffer = offerService.updateOffer(offer2,"offerId");

        Assertions.assertEquals("offerId2",updatedOffer.getId());
    }


    @Test
    public void getOfferById(){
        Mockito.when(offerRepository.findById("offerId")).thenReturn(Optional.of(offer));

        Offer expectedOffer = offerService.getOfferById("offerId");

        Assertions.assertEquals("APPLE",expectedOffer.getCompany().getName());

    }

    @Test
    public void getAllOffers(){
        List<Offer> offers =  new ArrayList<>();
        offers.add(offer);

        Mockito.when(offerRepository.findAll()).thenReturn(offers);

        List<Offer> expectedOffers = offerService.getAllOffers(null);
        Assertions.assertEquals(1,expectedOffers.size());
    }

    @Test
    public void getAllOffersByProfiles(){
        List<Offer> offers =  new ArrayList<>();
        offers.add(offer);


        Mockito.when(offerRepository.findProfileByProfilesIdIn(Arrays.asList("profileId"))).thenReturn(offers);

        List<Offer> expectedOffers = offerService.getAllOffers(Arrays.asList("profileId"));
        Assertions.assertEquals(1,expectedOffers.size());
    }

    @Test
    public void getAllOffersByCompany(){
        List<Offer> offers =  new ArrayList<>();
        offers.add(offer);


        Mockito.when(offerRepository.findCompanyByCompanyId("companyId")).thenReturn(offers);

        List<Offer> expectedOffers = offerService.getOffersByCompany("companyId");
        Assertions.assertEquals(1,expectedOffers.size());
    }



}
