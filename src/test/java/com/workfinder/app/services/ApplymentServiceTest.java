package com.workfinder.app.services;

import com.workfinder.app.models.Applyment;
import com.workfinder.app.models.Employee;
import com.workfinder.app.models.Offer;
import com.workfinder.app.repositories.ApplymentRepository;
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
public class ApplymentServiceTest {

    @InjectMocks
    ApplymentService applymentService;

    @Mock
    ApplymentRepository applymentRepository;

    @Mock
    OfferRepository offerRepository;

    @Mock
    EmployeeRepository employeeRepository;

    private  Applyment applyment;
    private  Employee employee;
    private  Offer offer;

    @BeforeEach
    public  void setUp(){
        offer = new Offer();
        employee = new Employee();
        applyment = new Applyment();

        offer.setMaxSalary(10000);
        offer.setTitle("Offer 1");
        offer.setId("offerId");
        offer.setMinSalary(9000);

        employee.setFirstName("FirstName");
        employee.setLastName("LastName");
        employee.setAge(22);
        employee.setId("employeeId");

        applyment.setOffer(offer);
        applyment.setEmployee(employee);
        applyment.setId("applymentId");
    }

    @Test
    public void addApplyment(){
        Mockito.when(applymentRepository.save(any(Applyment.class))).thenReturn(applyment);
        Applyment expectedApplyment = applymentService.addApplyment(applyment);

        Assertions.assertEquals("applymentId",expectedApplyment.getId());
        Assertions.assertEquals("Offer 1",expectedApplyment.getOffer().getTitle());
    }

    @Test
    public void updateApplyment(){
        Applyment applyment2 = new Applyment();
        applyment2.setId("applymentId2");
        applyment2.setOffer(offer);
        applyment2.setEmployee(employee);

        Mockito.when(applymentRepository.findById("applymentId")).thenReturn(Optional.of(applyment));
        Mockito.when(applymentRepository.save(any(Applyment.class))).thenReturn(applyment2);

        Applyment updatedApplyment = applymentService.updateApplyment(applyment2,"applymentId");

        Assertions.assertEquals("applymentId2",updatedApplyment.getId());
    }

    @Test
    public void getApplymentsByEmployee(){
        List<Applyment> applymentList  = new ArrayList<>();
        applymentList.add(applyment);

        Mockito.when(employeeRepository.findById("employeeId")).thenReturn(Optional.of(employee));
        Mockito.when(applymentRepository.findEmployeeByEmployeeId("employeeId")).thenReturn(applymentList);

        List<Applyment> expectedApplymentList = applymentService.getApplymentsByEmployee("employeeId");
        Assertions.assertEquals(1,expectedApplymentList.size());
    }

    @Test
    public void getApplymentsByOffer(){
        List<Applyment> applymentList  = new ArrayList<>();
        applymentList.add(applyment);

        Mockito.when(offerRepository.findById("offerId")).thenReturn(Optional.of(offer));
        Mockito.when(applymentRepository.findOfferByOfferId("offerId")).thenReturn(applymentList);

        List<Applyment> expectedApplymentList = applymentService.getApplymentsByOffer("offerId");
        Assertions.assertEquals(1,expectedApplymentList.size());
    }

    @Test
    public void getApplymentById(){
        Mockito.when(applymentRepository.findById("applymentId")).thenReturn(Optional.of(applyment));

        Applyment expectedApplyment = applymentService.getApplymentById("applymentId");

        Assertions.assertEquals("offerId",expectedApplyment.getOffer().getId());

    }

    @Test
    public void getAllApplyments(){
        List<Applyment> applyments =  new ArrayList<>();
        applyments.add(applyment);

        Mockito.when(applymentRepository.findAll()).thenReturn(applyments);

        List<Applyment> expectedApplyments = applymentService.getAllApplyments();
        Assertions.assertEquals(1,expectedApplyments.size());
    }

}
