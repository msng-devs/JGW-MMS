package com.jaramgroupware.mms.domain.penalty;

import com.jaramgroupware.mms.domain.attendance.Attendance;

import java.util.List;

public interface PenaltyCustomRepository {

    void bulkUpdate(List<Penalty> penalties, String who);
}
