package com.workfinder.app.services;

import com.workfinder.app.models.Employee;
import com.workfinder.app.models.Favorite;
import com.workfinder.app.repositories.EmployeeRepository;
import com.workfinder.app.repositories.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Favorite addFavorite(Favorite Favorite){
        return favoriteRepository.save(Favorite);
    }

    public List<Favorite> getFavoritesByEmployee(String employeeId){
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if(employee.isPresent()){
            return favoriteRepository.findEmployeeByEmployeeId(employeeId);
        }
        else{
            return null;
        }
    }

    public Favorite updateFavorite(Favorite Favorite , String id){
        Optional<Favorite> optionalExistedFavorite = favoriteRepository.findById(id);
        if(optionalExistedFavorite.isPresent()){
            Favorite existedFavorite = optionalExistedFavorite.get();
            existedFavorite = Favorite;
            return favoriteRepository.save(existedFavorite);
        }
        else {
            return null;
        }
    }

    public void deleteFavorite(String id){
        favoriteRepository.deleteById(id);
    }

    public List<Favorite> getAllFavorites(){
        return favoriteRepository.findAll();
    }

    public Favorite getFavoriteById(String id){
        Optional<Favorite> optionalFavorite = favoriteRepository.findById(id);
        if(optionalFavorite.isPresent()){
            return optionalFavorite.get();
        }
        else {
            return null;
        }

    }
}
