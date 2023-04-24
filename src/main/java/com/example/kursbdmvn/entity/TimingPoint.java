package com.example.kursbdmvn.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "timing_point")
public class TimingPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "competition_id")
    Competition competition;

    @OneToMany(mappedBy = "timingPoint")
    List<TimingRecord> timingRecords;

    @Positive
    private int number;
    @Positive
    private float distance;

    @Override
    public String toString(){
        return "n:"+number+"; d:"+distance+"; c:"+competition.getTitle();
    }
}
