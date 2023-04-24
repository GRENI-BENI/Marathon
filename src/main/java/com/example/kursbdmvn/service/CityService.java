package com.example.kursbdmvn.service;

import com.example.kursbdmvn.entity.City;
import com.example.kursbdmvn.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    @Autowired
    CityRepository cityRepository;

    public List<City> getAll(){
       return cityRepository.findAll();
    }

    public void remove(City c){
      if(c.getId()!=null&&cityRepository.existsById(c.getId()))
            cityRepository.delete(c);
    }

    public City getFirst(){
        return cityRepository.findAll(PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "id"))).toList().get(0);
    }

    public void save(List<City> cities){
        cityRepository.saveAll(cities);
    }
}
