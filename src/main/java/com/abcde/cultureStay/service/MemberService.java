package com.abcde.cultureStay.service;

import com.abcde.cultureStay.vo.Member;

public interface MemberService {

	boolean searchId(String searchId);

	void joinMember(Member member);

}
