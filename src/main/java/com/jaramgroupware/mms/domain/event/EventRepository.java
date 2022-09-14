package com.jaramgroupware.mms.domain.event;

import com.jaramgroupware.mms.domain.attendance.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event,Long>, JpaSpecificationExecutor<Event> {
    Optional<Event> findEventById(Long id);
    Optional<List<Event>> findAllBy();
}
