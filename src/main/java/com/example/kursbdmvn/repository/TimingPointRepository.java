package com.example.kursbdmvn.repository;

import com.example.kursbdmvn.entity.TimingPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimingPointRepository extends JpaRepository<TimingPoint,Long> {
}
