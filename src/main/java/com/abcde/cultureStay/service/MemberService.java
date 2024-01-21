package com.abcde.cultureStay.service;

import com.abcde.cultureStay.vo.Member;

import java.util.ArrayList;

public interface MemberService {

	boolean searchId(String searchId);

	void joinMember(Member member);

	Member selectUser(String userId);

	int updateUser(Member member);

    ArrayList<Member> searchUsersExcludeSelf(String searchUser, String username);
}
