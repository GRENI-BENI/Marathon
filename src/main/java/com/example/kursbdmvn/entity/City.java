package com.example.kursbdmvn.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 30)
    private String name;


    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    List<Competition> competitions;

    @Override
    public String toString(){
        return name;
    }
}
