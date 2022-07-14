package com.workfinder.app.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;


@Data
@Entity
public class Company extends User{

    private String name;
    private String description;
    private int size;


    @OneToMany
    private List<Country> countries;

}
