package com.jaramgroupware.mms.domain.attendance;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AttendanceCustomRepository {

    void bulkInsert(List<Attendance> attendances,String who);
    void bulkUpdate(List<Attendance> attendances,String who);
}
