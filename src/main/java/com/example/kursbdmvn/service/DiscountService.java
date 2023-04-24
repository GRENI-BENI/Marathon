package com.example.kursbdmvn.service;

import com.example.kursbdmvn.entity.Competition;
import com.example.kursbdmvn.entity.Discount;
import com.example.kursbdmvn.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiscountService {
    @Autowired
    DiscountRepository discountRepository;
    public List<Discount> getAll(){
        return discountRepository.findAll();
    }

    public void save(Discount discount){
        discountRepository.save(discount);
    }

    public void remove(Discount discount){
        discountRepository.delete(discount);
    }

    public List<Discount> getByCompetition(Competition competition){
       // List<Competition> com= List.of(new Competition[]{competition});
        return discountRepository.getDiscountsByCompetitions(competition);
    }

    public Discount getNoDiscount(){
        return discountRepository.getDiscountByTitle("No discount");
    }
}
