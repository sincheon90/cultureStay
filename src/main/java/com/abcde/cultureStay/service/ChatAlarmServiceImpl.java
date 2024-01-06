package com.abcde.cultureStay.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abcde.cultureStay.dao.ChatAlarmDAO;
import com.abcde.cultureStay.vo.Alarm;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ChatAlarmServiceImpl implements ChatAlarmService{
	
	@Autowired
	ChatAlarmDAO dao;

	@Override
	public void createChatAlarm(Alarm alarm) {
		dao.createAlarm(alarm);
		
	}

	@Override
	public ArrayList<Alarm> showAlarm(String id) {
		ArrayList<Alarm> alarm = dao.showAlarm(id);
		return alarm;
	}

	@Override
	public void updateCheck(String id) {
		dao.updateAlarm(id);
		
	}

	@Override
	public int alarmNum(String id) {
		int num = dao.alarmNum(id);
		return num;
	}

}
