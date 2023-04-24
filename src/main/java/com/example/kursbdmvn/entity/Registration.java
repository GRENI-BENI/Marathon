package com.example.kursbdmvn.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "boolean default false")
    private boolean isDisqualified;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "competition_id")
    Competition competition;

    @OneToMany(mappedBy = "registration")
    List<TimingRecord> timingRecords;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    Discount discount;

    @ManyToOne
    @JoinColumn(name = "distance_id")
    Distance distance;

    @Size(max = 150)
    private String disqualificationReason;

    @ManyToOne
    @JoinColumn(name = "participant_id")
    Participant participant;

    @Column(columnDefinition = "boolean default false")
    private boolean isRegistered;
    @Positive
    int bidNumber;
    @Positive
    int timingChipNumber;

    @Override
    public String toString(){
        return String.valueOf(id);
    }
}
