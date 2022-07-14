package com.workfinder.app.services;

import com.workfinder.app.models.Applyment;
import com.workfinder.app.models.Employee;
import com.workfinder.app.models.Offer;
import com.workfinder.app.repositories.ApplymentRepository;
import com.workfinder.app.repositories.EmployeeRepository;
import com.workfinder.app.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplymentService {

    @Autowired
    private ApplymentRepository applymentRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Applyment addApplyment(Applyment applyment){
        return applymentRepository.save(applyment);
    }

    public List<Applyment> getApplymentsByEmployee(String employeeId){
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if(employee.isPresent()){
            return applymentRepository.findEmployeeByEmployeeId(employeeId);
        }
        else {
            return null;
        }
    }

    public List<Applyment> getApplymentsByOffer(String offerId){
        Optional<Offer> offer = offerRepository.findById(offerId);
        if(offer.isPresent()){
            return applymentRepository.findOfferByOfferId(offerId);
        }
        else {
            return null;
        }
    }

    public Applyment updateApplyment(Applyment applyment , String id){
        Optional<Applyment> optionalExistedApplyment = applymentRepository.findById(id);
        if(optionalExistedApplyment.isPresent()){
            Applyment existedApplyment = optionalExistedApplyment.get();
            existedApplyment.setEmployee(applyment.getEmployee());
            existedApplyment.setOffer(applyment.getOffer());
            return applymentRepository.save(existedApplyment);
        }
        else {
            return null;
        }
    }

    public void deleteApplyment(String id){
        applymentRepository.deleteById(id);
    }

    public List<Applyment> getAllApplyments(){
       return applymentRepository.findAll();
    }

    public Applyment getApplymentById(String id){
        Optional<Applyment> optionalApplyment = applymentRepository.findById(id);
        if(optionalApplyment.isPresent()){
            return optionalApplyment.get();
        }
        else {
            return null;
        }

    }
}
