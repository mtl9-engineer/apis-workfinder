package com.workfinder.app.services;

import com.workfinder.app.models.Company;
import com.workfinder.app.models.Employee;
import com.workfinder.app.models.Offer;
import com.workfinder.app.repositories.CompanyRepository;
import com.workfinder.app.repositories.EmployeeRepository;
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
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @InjectMocks
    CompanyService companyService;

    @Mock
    CompanyRepository companyRepository;
    

    private Company company;
    @BeforeEach
    public  void setUp(){
        company = new Company();
        company.setSize(10000);
        company.setName("APPLE");
        company.setEmail("careers@apple.com");
        company.setId("appleId");
       
    }

    @Test
    public void addCompany(){
        Mockito.when(companyRepository.save(any(Company.class))).thenReturn(company);
        Company expectedCompany = companyService.addCompany(company);

        Assertions.assertEquals("appleId",expectedCompany.getId());
        Assertions.assertEquals("APPLE",expectedCompany.getName());
    }

    @Test
    public void updateCompany(){
        Company company2 = new Company();
        company2.setId("googleId");
        company2.setEmail("careers@google.com");
        company2.setSize(10000);
        company2.setName("GOOGLE");

        Mockito.when(companyRepository.findById("appleId")).thenReturn(Optional.of(company));
        Mockito.when(companyRepository.save(any(Company.class))).thenReturn(company2);

        Company updatedCompany = companyService.updateCompany(company2,"appleId");

        Assertions.assertEquals("googleId",updatedCompany.getId());
    }


    @Test
    public void getCompanyById(){
        Mockito.when(companyRepository.findById("appleId")).thenReturn(Optional.of(company));

        Company expectedCompany = companyService.getCompanyById("appleId");

        Assertions.assertEquals("APPLE",expectedCompany.getName());

    }

    @Test
    public void getAllCompanys(){
        List<Company> companys =  new ArrayList<>();
        companys.add(company);

        Mockito.when(companyRepository.findAll()).thenReturn(companys);

        List<Company> expectedCompanys = companyService.getAllCompanys();
        Assertions.assertEquals(1,expectedCompanys.size());
    }

}
