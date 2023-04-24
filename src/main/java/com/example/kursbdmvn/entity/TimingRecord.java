package com.example.kursbdmvn.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Duration;

@Entity
@Data
public class TimingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    Duration timeRecorded;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "registration_id")
    Registration registration;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "timing_point_id")
    TimingPoint timingPoint;


    @Override
    public String toString(){
        return String.valueOf(id);
    }

}
