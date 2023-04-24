package com.example.kursbdmvn.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Gender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    String name;

    @OneToMany(mappedBy = "gender", fetch = FetchType.LAZY)
    List<Participant> participants;

    @Override
    public String toString(){
        return name;
    }

}
