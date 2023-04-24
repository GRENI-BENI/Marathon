package com.example.kursbdmvn.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 20)
    @NotBlank
    String firstName;

    @Size(max = 20)
    @NotBlank
    String lastName;

    @Past
    LocalDate dateOfBirth;

    @OneToMany(mappedBy = "participant")
    List<Registration> registrations;


    @ManyToOne
            @JoinColumn(name = "gender_id")
    Gender gender;

    @Override
    public String toString(){
        return id+" "+firstName+" "+lastName+" "+dateOfBirth.toString();
    }


}
