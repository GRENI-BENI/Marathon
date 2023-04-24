package com.example.kursbdmvn.service;

import com.example.kursbdmvn.entity.Participant;
import com.example.kursbdmvn.entity.Registration;
import com.example.kursbdmvn.repository.ParticipantRepository;
import com.example.kursbdmvn.repository.RegistrationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService {
    @Autowired
    RegistrationsRepository registrationsRepository;
    public List<Registration> getAll(){
        return registrationsRepository.findAll();
    }

    public void save(Registration registration){
        registrationsRepository.save(registration);
    }

    public void remove(Registration registration){
        registrationsRepository.delete(registration);
    }

    public Registration getById(Long id){
        return registrationsRepository.findById(id).get();
    }
}
