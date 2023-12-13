package com.abcde.cultureStay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.abcde.cultureStay.dao.MemberDAO;
import com.abcde.cultureStay.vo.Member;


@Service
public class MemberServiceImpl implements MemberService{
	@Autowired
	MemberDAO dao;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public boolean searchId(String searchId) {
		Member member = dao.searchId(searchId);
		
		return member == null ? true : false ;
	}

	@Override
	public void joinMember(Member member) {
		String encodedPassword = passwordEncoder.encode(member.getPassword());
		member.setPassword(encodedPassword);
		dao.joinMember(member);
		
	}

}
