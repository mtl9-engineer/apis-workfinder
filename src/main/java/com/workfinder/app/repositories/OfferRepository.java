package com.workfinder.app.repositories;

import com.workfinder.app.models.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OfferRepository extends JpaRepository<Offer,String> {

    List<Offer> findProfileByProfilesIdIn(List<String> ids);
    List<Offer> findCompanyByCompanyId(String companyId);
}
