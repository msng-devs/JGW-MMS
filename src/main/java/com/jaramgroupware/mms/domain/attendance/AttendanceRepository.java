package com.jaramgroupware.mms.domain.attendance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {
    Optional<Attendance> findAttendanceById(Long id);
    Optional<List<Attendance>> findAllBy();
}
