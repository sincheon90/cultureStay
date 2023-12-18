package com.abcde.cultureStay.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.abcde.cultureStay.vo.Reply;

@Mapper
public interface ReplyDAO {

	int writeReply(Reply reply);

	int deleteReply(Reply reply);

	ArrayList<Reply> replyList(int boardnum);

}
