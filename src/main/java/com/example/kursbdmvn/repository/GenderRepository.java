package com.example.kursbdmvn.repository;

import com.example.kursbdmvn.entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenderRepository extends JpaRepository<Gender, Long> {
}
