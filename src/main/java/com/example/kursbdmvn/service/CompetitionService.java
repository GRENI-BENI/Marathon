package com.example.kursbdmvn.service;

import com.example.kursbdmvn.entity.Competition;
import com.example.kursbdmvn.repository.CompetitionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetitionService {

    @Autowired
    CompetitionRepository competitionRepository;

    public List<Competition> getAll(){
        return competitionRepository.findAll();
    }

    public void save(Competition competition){
        competitionRepository.save(competition);
    }

    public void remove(Competition competition){
        competitionRepository.delete(competition);
    }

    public Competition getById(Long id){
        return competitionRepository.findById(id).get();
    }
}
