package com.example.kursbdmvn.service;

import com.example.kursbdmvn.entity.City;
import com.example.kursbdmvn.entity.Gender;
import com.example.kursbdmvn.repository.CityRepository;
import com.example.kursbdmvn.repository.GenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenderService {
    @Autowired
    GenderRepository genderRepository;

    public List<Gender> getAll(){
        return genderRepository.findAll();
    }

    public void remove(Gender gender){
        if(gender.getId()!=null&&genderRepository.existsById(gender.getId()))
            genderRepository.delete(gender);
    }

    public void save(List<Gender> genders){
        genderRepository.saveAll(genders);
    }
}
