package com.example.kursbdmvn.service;

import com.example.kursbdmvn.entity.Registration;
import com.example.kursbdmvn.entity.TimingPoint;
import com.example.kursbdmvn.repository.TimingPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimingPointService {
@Autowired
    TimingPointRepository timingPointRepository;

    public List<TimingPoint> getAll(){
        return timingPointRepository.findAll();
    }

    public void save(TimingPoint timingPoint){
        timingPointRepository.save(timingPoint);
    }

    public void remove(TimingPoint timingPoint){
        timingPointRepository.delete(timingPoint);
    }

    public TimingPoint getById(Long id){
        return timingPointRepository.findById(id).get();
    }
}
