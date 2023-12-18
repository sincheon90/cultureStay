package com.abcde.cultureStay.service;

import java.util.ArrayList;

import com.abcde.cultureStay.vo.Reply;

public interface ReplyService {

	int writeReply(Reply reply);

	int deleteReply(Reply reply);

	ArrayList<Reply> replyList(int boardnum);

}
