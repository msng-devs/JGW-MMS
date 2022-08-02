package com.jaramgroupware.mms.domain.timeTable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TimeTableRepository extends JpaRepository<TimeTable,Integer> {
    Optional<TimeTable> findTimeTableById(Long id);
}
