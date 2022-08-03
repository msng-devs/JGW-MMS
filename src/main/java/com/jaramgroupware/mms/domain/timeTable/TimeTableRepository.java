package com.jaramgroupware.mms.domain.timeTable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TimeTableRepository extends JpaRepository<TimeTable,Long> {
    Optional<TimeTable> findTimeTableById(Long id);
    List<TimeTable> findAllBy();
}
