package com.example.kursbdmvn.repository;

import com.example.kursbdmvn.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant,Long> {
}
