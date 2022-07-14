package com.workfinder.app.models;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
public class Offer {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    String id;
    String title;
    String description;
    double minSalary;
    double maxSalary;


    @ManyToOne
    @JoinColumn(name="id_company")
    private Company company;

    @OneToMany
    List<Profile> profiles;
}
