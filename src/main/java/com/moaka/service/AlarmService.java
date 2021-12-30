package com.moaka.service;

import com.moaka.dto.Alarm;
import com.moaka.dto.User;
import com.moaka.mapper.AlarmMapper;
import com.moaka.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AlarmService {
    @Autowired
    AlarmMapper alarmMapper;

    public ArrayList<Alarm> retrieveAlarmByUserNo(int user_no) {
        return alarmMapper.retrieveAlarmByUserNo(user_no);
    }

    @Async
    public void insertAlarm(Alarm alarm) {
        alarmMapper.insertAlarm(alarm);
    }
}
