package com.workfinder.app.repositories;

import com.workfinder.app.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Optional<Employee> findByEmail(String email);
    List<Employee> findProfileByProfilesIdIn(List<String> ids);
}
