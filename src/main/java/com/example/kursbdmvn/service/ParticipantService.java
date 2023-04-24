package com.example.kursbdmvn.service;

import com.example.kursbdmvn.entity.Discount;
import com.example.kursbdmvn.entity.Participant;
import com.example.kursbdmvn.repository.DiscountRepository;
import com.example.kursbdmvn.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantService {
    @Autowired
    ParticipantRepository participantRepository;
    public List<Participant> getAll(){
        return participantRepository.findAll();
    }

    public void save(Participant participant){
        participantRepository.save(participant);
    }

    public void remove(Participant participant){
        participantRepository.delete(participant);
    }

    public Participant getById(Long id){
        return participantRepository.findById(id).get();
    }
}
