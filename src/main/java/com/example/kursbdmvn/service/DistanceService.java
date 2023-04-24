package com.example.kursbdmvn.service;

import com.example.kursbdmvn.entity.Competition;
import com.example.kursbdmvn.entity.Discount;
import com.example.kursbdmvn.entity.Distance;
import com.example.kursbdmvn.repository.DiscountRepository;
import com.example.kursbdmvn.repository.DistanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DistanceService {
    @Autowired
    DistanceRepository distanceRepository;
    public List<Distance> getAll(){
        return distanceRepository.findAll();
    }

    public void save(Distance distance){
        distanceRepository.save(distance);
    }

    public void remove(Distance distance){
        distanceRepository.delete(distance);
    }

    public List<Distance> getDistancesByCompetition(Competition competition){
        return distanceRepository.getDistancesByCompetition(competition);
    }
}
