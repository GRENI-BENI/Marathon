package com.example.kursbdmvn.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    String title;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "discount_competition",
            joinColumns = @JoinColumn(name = "discount_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "competition_id",
                    referencedColumnName = "id"))
    List<Competition> competitions;

    @OneToMany(mappedBy = "discount")
    List<Registration> registrations;


    @Max(value = 1)
    @Positive
    float percent;

    @Override
    public String toString(){
        return title;
    }
}
