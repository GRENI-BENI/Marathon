package com.example.kursbdmvn.repository;

import com.example.kursbdmvn.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City,Long> {

}
