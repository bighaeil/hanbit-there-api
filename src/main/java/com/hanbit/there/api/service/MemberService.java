package com.hanbit.there.api.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanbit.there.api.dao.MemberDAO;
import com.hanbit.there.api.vo.MemberVO;

@Service
public class MemberService {

	private static final char[] CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray(); // uid 만들때 문자로 쓰임

	@Autowired
	private MemberDAO memberDAO;

	public void signUp(MemberVO memberVO) {
		if (memberDAO.countMember(memberVO.getEmail()) > 0) {
			throw new RuntimeException("이미 가입된 이메일 입니다.");
		}

		memberVO.setUid(generateUid());

		// 패스워드 암호화

		memberDAO.insertMember(memberVO);
	}

	private String generateUid() { // 문자열이 아니라 배열로 바꿈
		int length = 12;
		char[] uid = new char[length];

		Random random = new Random();

		for (int i=0; i<length; i++) { //무작위 12문자 생성
			uid[i] = CHARS[random.nextInt(CHARS.length)];
		}

		return new String(uid);
	}

}



