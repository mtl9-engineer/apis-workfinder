package com.workfinder.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends User{
    private String firstName;
    private String lastName;
    private int age;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    List<Profile> profiles ;

    @OneToOne
    Country country;

    private int yearsOfExperience;

}
