package com.workfinder.app.services;

import com.workfinder.app.models.Employee;
import com.workfinder.app.models.Offer;
import com.workfinder.app.models.Profile;
import com.workfinder.app.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee addEmployee(Employee Employee){
        return employeeRepository.save(Employee);
    }

    public Employee updateEmployee(Employee employee , String id){
        Optional<Employee> optionalExistedEmployee = employeeRepository.findById(id);
        if(optionalExistedEmployee.isPresent()){
            Employee existedEmployee = optionalExistedEmployee.get();
            existedEmployee.setAge(employee.getAge());
            existedEmployee.setCountry(employee.getCountry());
            existedEmployee.setFirstName(employee.getFirstName());
            existedEmployee.setLastName(employee.getLastName());
            existedEmployee.setYearsOfExperience(employee.getYearsOfExperience());
            existedEmployee.setEmail(employee.getEmail());
            existedEmployee.setProfiles(employee.getProfiles());
            existedEmployee.setPassword(employee.getPassword());
            return employeeRepository.save(existedEmployee);
        }
        else {
            return null;
        }
    }

    public void deleteEmployee(String id){
        employeeRepository.deleteById(id);
    }

    public List<Employee> getAllEmployees(List<String> ids){
        if (ids != null){
            return this.getEmployeesByProfiles(ids);
        }
        return employeeRepository.findAll();

    }

    public Employee getEmployeeById(String id){
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isPresent()){
            return optionalEmployee.get();
        }
        else {
            return null;
        }

    }
    public Employee getEmployeeByEmail(String email){
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
        if(optionalEmployee.isPresent()){
            return optionalEmployee.get();
        }
        else {
            return null;
        }
    }

    private List<Employee> getEmployeesByProfiles(List<String> ids){
        return employeeRepository.findProfileByProfilesIdIn(ids);
    }



}
