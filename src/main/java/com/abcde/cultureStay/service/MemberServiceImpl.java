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

	@Override
	public Member selectUser(String userId) {
		Member member = dao.selectUser(userId);
		return member;
	}

	@Override
	public int updateUser(Member member) {
		// updateForm.html에서 비밀번호를 변경한 경우에 암호화
				if(member.getPassword() != null || !member.getPassword().equals("")) {
					String encodedPw = passwordEncoder.encode(member.getPassword());
					member.setPassword(encodedPw);
				}
				
				int result = dao.updateUser(member);
				
				return result;
	}

}
