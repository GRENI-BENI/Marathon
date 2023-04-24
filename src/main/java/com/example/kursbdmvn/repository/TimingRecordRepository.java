package com.example.kursbdmvn.repository;

import com.example.kursbdmvn.entity.TimingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimingRecordRepository extends JpaRepository<TimingRecord,Long> {
}
