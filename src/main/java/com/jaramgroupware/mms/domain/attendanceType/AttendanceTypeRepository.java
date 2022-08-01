package com.jaramgroupware.mms.domain.attendanceType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceTypeRepository extends JpaRepository<AttendanceType,Integer> {
}
