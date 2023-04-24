package com.example.kursbdmvn.service;

import com.example.kursbdmvn.entity.TimingPoint;
import com.example.kursbdmvn.entity.TimingRecord;
import com.example.kursbdmvn.repository.TimingPointRepository;
import com.example.kursbdmvn.repository.TimingRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimingRecordService {
    @Autowired
    TimingRecordRepository timingRecordRepository;

    public List<TimingRecord> getAll(){
        return timingRecordRepository.findAll();
    }

    public void save(TimingRecord timingRecord){
        timingRecordRepository.save(timingRecord);
    }

    public void remove(TimingRecord timingRecord){
        timingRecordRepository.delete(timingRecord);
    }
}
