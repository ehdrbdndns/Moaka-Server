package com.moaka.mapper;

import com.moaka.dto.Alarm;
import com.moaka.dto.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface AlarmMapper {
    void insertAlarm(Alarm alarm);
    ArrayList<Alarm> retrieveAlarmByUserNo(int user_no);
}
