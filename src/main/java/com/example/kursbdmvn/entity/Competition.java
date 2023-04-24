package com.example.kursbdmvn.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "city_id")
    City city;

    @ManyToMany(mappedBy = "competitions")
    List<Discount> discounts;

    @OneToMany(mappedBy = "competition")
    List<TimingPoint> timingPoints;

    @OneToMany(mappedBy = "competition")
    List<Distance> distances;

    @OneToMany(mappedBy = "competition")
    List<Registration> registrations;

    @NotBlank
    @Column(name = "title")
    @Size(max = 60)
    private String title;


    LocalDate startDate;

    LocalTime raceStartTime;
    LocalTime registrationStartTime;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    String description;


    @Override
    public String toString(){
        return title;
    }

}
