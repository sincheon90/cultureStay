package com.abcde.cultureStay.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.abcde.cultureStay.vo.Alarm;

@Mapper
public interface ChatAlarmDAO {

	void createAlarm(Alarm alarm);

	ArrayList<Alarm> showAlarm(String id);

	void updateAlarm(String id);

	int alarmNum(String id);

}
