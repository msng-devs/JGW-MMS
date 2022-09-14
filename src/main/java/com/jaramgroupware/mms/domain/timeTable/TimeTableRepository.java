package com.jaramgroupware.mms.domain.timeTable;

import com.jaramgroupware.mms.domain.penalty.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimeTableRepository extends JpaRepository<TimeTable,Long> , JpaSpecificationExecutor<TimeTable> {

    //TODO BULK DELETE 추가
    Optional<TimeTable> findTimeTableById(Long id);
    Optional<List<TimeTable>> findAllBy();

}
