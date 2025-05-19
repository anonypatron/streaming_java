package com.kafka.repository;

import com.kafka.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findTop100ByOrderByEventTimeAsc();
}
