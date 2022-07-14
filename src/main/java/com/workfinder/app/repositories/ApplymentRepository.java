package com.workfinder.app.repositories;

import com.workfinder.app.models.Applyment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplymentRepository extends JpaRepository<Applyment,String> {

    List<Applyment> findOfferByOfferId(String OfferId);
    List<Applyment> findEmployeeByEmployeeId(String employeeId);
}
