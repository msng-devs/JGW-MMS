package com.jaramgroupware.mms.service;

import com.jaramgroupware.mms.dto.attendanceCode.serviceDto.AttendanceCodeServiceDto;
import com.jaramgroupware.mms.utils.exception.CustomException;
import com.jaramgroupware.mms.utils.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class AttendanceCodeService {

    @Autowired
    private final RedisTemplate<String,Long> redisTemplate;

    @Autowired
    private final RedisTemplate<Long,String> redisTemplateId;

    @Transactional
    public String createCode(AttendanceCodeServiceDto attendanceCodeDto){

        ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(attendanceCodeDto.getCode(),attendanceCodeDto.getTimeTableId());
        redisTemplate.expire(attendanceCodeDto.getCode(), Duration.ofMinutes(attendanceCodeDto.getMinute()));

        ValueOperations<Long, String> valueOperationsId = redisTemplateId.opsForValue();
        valueOperationsId.set(attendanceCodeDto.getTimeTableId(),attendanceCodeDto.getCode());
        redisTemplateId.expire(attendanceCodeDto.getTimeTableId(), Duration.ofMinutes(attendanceCodeDto.getMinute()));

        return attendanceCodeDto.getCode();
    }


    @Transactional
    public void revokeCode(String key){
        redisTemplate.delete(key);
    }

    @Transactional(readOnly = true)
    public boolean validationKey(String key){

        ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key) == null;

    }

    @Transactional(readOnly = true)
    public AttendanceCodeServiceDto findKey(String key){
        ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
        Long timeTableId = valueOperations.get(key);

        if(timeTableId == null) throw new CustomException(ErrorCode.INVALID_ATTENDANCE_CODE);

        return AttendanceCodeServiceDto.builder()
                .code(key)
                .timeTableId(timeTableId)
                .minute(redisTemplate.getExpire(key).intValue())
                .build();
    }
    @Transactional(readOnly = true)
    public boolean checkHasKey(Long id){

        ValueOperations<Long,String> valueOperations = redisTemplateId.opsForValue();

        String key = valueOperations.get(id);
        return key != null;
    }

    @Transactional(readOnly = true)
    public AttendanceCodeServiceDto findKeyById(Long id){

        ValueOperations<Long,String> valueOperations = redisTemplateId.opsForValue();

        String key = valueOperations.get(id);
        if(key == null) throw new CustomException(ErrorCode.INVALID_TIMETABLE_ID);
        return AttendanceCodeServiceDto.builder()
                .code(key)
                .timeTableId(id)
                .minute(redisTemplate.getExpire(key).intValue())
                .build();
    }
}
