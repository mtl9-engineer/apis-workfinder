package com.workfinder.app.services;

import com.workfinder.app.models.Employee;
import com.workfinder.app.models.Favorite;
import com.workfinder.app.repositories.EmployeeRepository;
import com.workfinder.app.repositories.FavoriteRepository;
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
public class FavoriteServiceTest {

    @InjectMocks
    FavoriteService favoriteService;

    @Mock
    FavoriteRepository favoriteRepository;

    @Mock
    EmployeeRepository employeeRepository;

    private Employee employee;

    private Favorite favorite;
    @BeforeEach
    public  void setUp(){
        favorite = new Favorite();
        employee = new Employee();

        employee.setId("employeeId");
        employee.setFirstName("Taoufik");
        employee.setEmail("mohammedtaoufiklahmidi@gmail.com");
        favorite.setId("favoriteId");
        favorite.setEmployee(employee);
       
    }

    @Test
    public void addFavorite(){
        Mockito.when(favoriteRepository.save(any(Favorite.class))).thenReturn(favorite);
        Favorite expectedFavorite = favoriteService.addFavorite(favorite);

        Assertions.assertEquals("favoriteId",expectedFavorite.getId());
        Assertions.assertEquals("Taoufik",expectedFavorite.getEmployee().getFirstName());
    }

    @Test
    public void updateFavorite(){
        Favorite favorite2 = new Favorite();
        favorite2.setId("favoriteId2");


        Mockito.when(favoriteRepository.findById("favoriteId")).thenReturn(Optional.of(favorite));
        Mockito.when(favoriteRepository.save(any(Favorite.class))).thenReturn(favorite2);

        Favorite updatedFavorite = favoriteService.updateFavorite(favorite2,"favoriteId");

        Assertions.assertEquals("favoriteId2",updatedFavorite.getId());
    }


    @Test
    public void getFavoriteById(){
        Mockito.when(favoriteRepository.findById("favoriteId")).thenReturn(Optional.of(favorite));

        Favorite expectedFavorite = favoriteService.getFavoriteById("favoriteId");

        Assertions.assertEquals("Taoufik",expectedFavorite.getEmployee().getFirstName());

    }

    @Test
    public void getAllFavorites(){
        List<Favorite> favorites =  new ArrayList<>();
        favorites.add(favorite);

        Mockito.when(favoriteRepository.findAll()).thenReturn(favorites);

        List<Favorite> expectedFavorites = favoriteService.getAllFavorites();
        Assertions.assertEquals(1,expectedFavorites.size());
    }

    @Test
    public void getFavoritesByEmployee(){
        List<Favorite> favorites =  new ArrayList<>();
        favorites.add(favorite);

        Mockito.when(employeeRepository.findById("employeeId")).thenReturn(Optional.of(employee));
        Mockito.when(favoriteRepository.findEmployeeByEmployeeId("employeeId")).thenReturn(favorites);

        List<Favorite> expectedFavorites = favoriteService.getFavoritesByEmployee("employeeId");
        Assertions.assertEquals(1,expectedFavorites.size());
    }

}
