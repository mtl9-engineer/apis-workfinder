package com.workfinder.app.services;

import com.workfinder.app.models.Company;
import com.workfinder.app.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;


    public Company addCompany(Company Company){
        return companyRepository.save(Company);
    }

    public Company updateCompany(Company company , String id){
        Optional<Company> optionalExistedCompany = companyRepository.findById(id);
        if(optionalExistedCompany.isPresent()){
            Company existedCompany = optionalExistedCompany.get();
            existedCompany.setEmail(company.getEmail());
            existedCompany.setCountries(company.getCountries());
            existedCompany.setDescription(company.getDescription());
            existedCompany.setName(company.getName());
            existedCompany.setSize(company.getSize());
            existedCompany.setPassword(company.getPassword());
            return companyRepository.save(existedCompany);
        }
        else {
            return null;
        }
    }

    public void deleteCompany(String id){
        companyRepository.deleteById(id);
    }

    public List<Company> getAllCompanys(){
        return companyRepository.findAll();
    }

    public Company getCompanyById(String id){
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if(optionalCompany.isPresent()){
            return optionalCompany.get();
        }
        else {
            return null;
        }

    }
}
