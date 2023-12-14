package com.abcde.cultureStay.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abcde.cultureStay.dao.ReplyDAO;
import com.abcde.cultureStay.vo.Reply;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReplyServiceImpl implements ReplyService{
	@Autowired
	ReplyDAO dao;

	@Override
	public int writeReply(Reply reply) {
		int result = dao.writeReply(reply);
		return result;
	}

	@Override
	public int deleteReply(Reply reply) {
		int result = dao.deleteReply(reply);
		return result;
	}

	@Override
	public ArrayList<Reply> replyList(int boardnum) {
		ArrayList<Reply> list = dao.replyList(boardnum);
		return list;
	}

}
