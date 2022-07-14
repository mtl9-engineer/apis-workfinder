package com.workfinder.app.repositories;

import com.workfinder.app.models.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite,String> {
    List<Favorite> findEmployeeByEmployeeId(String id);
}
