package com.example.kursbdmvn.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class Distance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 60)
    private String title;

    @Positive
    float length;

    @ManyToOne
    @JoinColumn(name = "competition_id")
    Competition competition;

    @OneToMany(mappedBy = "distance")
    List<Registration> registrations;

    @Positive
    @Nullable
    Integer minAge;
    @Max(value = 130)
    @Positive
    @Nullable
    Integer maxAge;

    @Positive
    @Nullable
    Integer participantsLimit;

    @Positive
    BigDecimal cost;

    @Override
    public String toString(){
        return title+" "+competition.getTitle();
    }
}
