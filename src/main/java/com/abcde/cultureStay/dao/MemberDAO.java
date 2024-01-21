package com.abcde.cultureStay.dao;

import org.apache.ibatis.annotations.Mapper;

import com.abcde.cultureStay.vo.Member;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface MemberDAO {

	Member searchId(String searchId);

	void joinMember(Member member);

	Member selectUser(String userId);

	int updateUser(Member member);

    ArrayList<Member> searchUsersExcludeSelf(Map<String, Object> map);
}
