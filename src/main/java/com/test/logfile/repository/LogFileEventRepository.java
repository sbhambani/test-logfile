package com.test.logfile.repository;

import com.test.logfile.entity.LogFileEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogFileEventRepository extends JpaRepository<LogFileEventEntity, Long> {

}
