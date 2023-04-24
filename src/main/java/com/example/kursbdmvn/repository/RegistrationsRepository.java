package com.example.kursbdmvn.repository;

import com.example.kursbdmvn.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationsRepository extends JpaRepository<Registration,Long> {
}
