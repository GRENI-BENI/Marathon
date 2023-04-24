package com.example.kursbdmvn.repository;

import com.example.kursbdmvn.entity.Competition;
import com.example.kursbdmvn.entity.Distance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistanceRepository extends JpaRepository<Distance,Long> {

    public List<Distance> getDistancesByCompetition(Competition competition);
}
