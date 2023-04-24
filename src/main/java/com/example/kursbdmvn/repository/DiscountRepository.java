package com.example.kursbdmvn.repository;

import com.example.kursbdmvn.entity.Competition;
import com.example.kursbdmvn.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscountRepository extends JpaRepository<Discount,Long> {
    public List<Discount> getDiscountsByCompetitions(Competition competition);

    public Discount getDiscountByTitle(String title);
}
