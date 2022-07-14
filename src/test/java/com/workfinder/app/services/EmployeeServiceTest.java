package com.workfinder.app.services;

import com.workfinder.app.models.Employee;
import com.workfinder.app.models.Profile;
import com.workfinder.app.repositories.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @InjectMocks
    EmployeeService employeeService;

    @Mock
    EmployeeRepository employeeRepository;
    

    private Employee employee;
    private Profile profile;
    @BeforeEach
    public  void setUp(){
        employee = new Employee();
        profile = new Profile();

        employee.setId("employeeId");
        employee.setEmail("mohammedtaoufiklahmidi@gmail.com");
        employee.setLastName("Lahmidi");
        employee.setFirstName("Mohammed Taoufik");

        profile.setId("profileId");
        profile.setName("Mobile Developer");

        employee.setProfiles(Arrays.asList(profile));
    }

    @Test
    public void addEmployee(){
        Mockito.when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        Employee expectedEmployee = employeeService.addEmployee(employee);

        Assertions.assertEquals("employeeId",expectedEmployee.getId());
        Assertions.assertEquals("Mohammed Taoufik",expectedEmployee.getFirstName());
    }

    @Test
    public void updateEmployee(){
        Employee employee2 = new Employee();
        employee2.setId("employeeId2");
        employee2.setEmail("lahmidimohammedtaoufik@gmail.com");

        Mockito.when(employeeRepository.findById("employeeId")).thenReturn(Optional.of(employee));
        Mockito.when(employeeRepository.save(any(Employee.class))).thenReturn(employee2);

        Employee updatedEmployee = employeeService.updateEmployee(employee2,"employeeId");

        Assertions.assertEquals("employeeId2",updatedEmployee.getId());
    }


    @Test
    public void getEmployeeById(){
        Mockito.when(employeeRepository.findById("employeeId")).thenReturn(Optional.of(employee));

        Employee expectedEmployee = employeeService.getEmployeeById("employeeId");

        Assertions.assertEquals("Lahmidi",expectedEmployee.getLastName());

    }

    @Test
    public void getAllEmployees(){
        List<Employee> employees =  new ArrayList<>();
        employees.add(employee);

        Mockito.when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> expectedEmployees = employeeService.getAllEmployees(null);
        Assertions.assertEquals(1,expectedEmployees.size());
    }

    @Test
    public void getAllEmployeesByProfiles(){
        List<Employee> employees =  new ArrayList<>();
        employees.add(employee);


        Mockito.when(employeeRepository.findProfileByProfilesIdIn(Arrays.asList("profileId"))).thenReturn(employees);

        List<Employee> expectedEmployees = employeeService.getAllEmployees(Arrays.asList("profileId"));
        Assertions.assertEquals(1,expectedEmployees.size());
    }

    @Test
    public void getAllEmployeeEmail(){
        Mockito.when(employeeRepository.findByEmail("mohammedtaoufiklahmidi@gmail.com")).thenReturn(Optional.of(employee));

        Employee expectedEmployee = employeeService.getEmployeeByEmail("mohammedtaoufiklahmidi@gmail.com");
        Assertions.assertEquals("Lahmidi",expectedEmployee.getLastName());
    }

}
