package com.abcde.cultureStay.service;

import java.util.ArrayList;

import com.abcde.cultureStay.vo.Alarm;

public interface ChatAlarmService {

	void createChatAlarm(Alarm alarm);

	ArrayList<Alarm> showAlarm(String member_id);

	void updateCheck(String id);

	int alarmNum(String id);

}
