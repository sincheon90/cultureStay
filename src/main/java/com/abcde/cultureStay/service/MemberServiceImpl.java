package com.abcde.cultureStay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.abcde.cultureStay.dao.MemberDAO;
import com.abcde.cultureStay.vo.Member;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
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
				log.debug("멤버겟패스:{}", member.getPassword());
				if(member.getPassword() != null || !member.getPassword().equals("")) {
					String encodedPassword = passwordEncoder.encode(member.getPassword());
					member.setPassword(encodedPassword);
				}
				
				int result = dao.updateUser(member);
				
				return result;
	}

	@Override
	public ArrayList<Member> searchUsersExcludeSelf(String searchUser, String currentUser) {
		Map<String, Object> map = new HashMap<>();
		map.put("searchUser", searchUser);
		map.put("currentUser", currentUser);
		return dao.searchUsersExcludeSelf(map);
	}

}
