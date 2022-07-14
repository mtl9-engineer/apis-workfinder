package com.workfinder.app.services;

import com.workfinder.app.models.Company;
import com.workfinder.app.models.Offer;
import com.workfinder.app.repositories.CompanyRepository;
import com.workfinder.app.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferService {
    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private CompanyRepository companyRepository;


    public Offer addOffer(Offer offer , String idCompany ){
        Optional<Company> company = companyRepository.findById(idCompany);
        if(company.isPresent()){
            offer.setCompany(company.get());
            Offer savedOffer = offerRepository.save(offer);
            return savedOffer;
        }
        else {
            return null;
        }
    }

    public List<Offer> getOffersByCompany(String companyId ){
        return offerRepository.findCompanyByCompanyId(companyId);

    }

    private List<Offer> getOffersByProfiles(List<String> ids){
        return offerRepository.findProfileByProfilesIdIn(ids);
    }
    public Offer updateOffer(Offer offer , String id){
        Optional<Offer> optionalExistedOffer = offerRepository.findById(id);
        if(optionalExistedOffer.isPresent()){
            Offer existedOffer = optionalExistedOffer.get();
            existedOffer.setCompany(offer.getCompany());
            existedOffer.setDescription(offer.getDescription());
            existedOffer.setMaxSalary(offer.getMaxSalary());
            existedOffer.setProfiles(offer.getProfiles());
            existedOffer.setTitle(offer.getTitle());
            existedOffer.setMinSalary(offer.getMinSalary());
            return offerRepository.save(existedOffer);
        }
        else {
            return null;
        }
    }

    public void deleteOffer(String id){
        offerRepository.deleteById(id);
    }

    public List<Offer> getAllOffers(List<String> ids){
        if(ids != null){
            return this.getOffersByProfiles(ids);
        }
        return offerRepository.findAll();
    }

    public Offer getOfferById(String id){
        Optional<Offer> optionalOffer = offerRepository.findById(id);
        if(optionalOffer.isPresent()){
            return optionalOffer.get();
        }
        else {
            return null;
        }

    }
}
