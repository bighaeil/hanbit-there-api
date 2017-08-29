package com.hanbit.there.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hanbit.there.api.service.MemberService;
import com.hanbit.there.api.vo.MemberVO;

@RestController
@RequestMapping("/api/member")
public class MemberController {

	@Autowired
	private MemberService memberService;

	@PostMapping("/signup")
	public Map signUp(@RequestParam("email") String email, @RequestParam("password") String password) {

		MemberVO memberVO = new MemberVO();
		memberVO.setEmail(email);
		memberVO.setPassword(password);

		memberService.signUp(memberVO);

		Map result = new HashMap();
		result.put("status", "ok");

		return result;
	}
	
	@PostMapping("/signin")
	public Map signIn(@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("remember") boolean remember,
			HttpSession session) {

		MemberVO memberVO = memberService.signIn(email, password); // 에러 있을 경우 exception으로 처리
		// 로그인 시 세션 값 설정 - 누구가 로그인 하고 있다.
		session.setAttribute("signedIn", true); 
		session.setAttribute("uid", memberVO.getUid());
		session.setAttribute("email", memberVO.getEmail());

		Map result = new HashMap();
		result.put("email", memberVO.getEmail());

		return result;
	}

	@RequestMapping("/get")
	public Map getMemeber(HttpSession session) {
		Map member = new HashMap();
		
		if (session.getAttribute("signedIn") == null) {
			member.put("signedIn", false);
		}
		else {
			member.put("signedIn", true);
			member.put("email", session.getAttribute("email"));
		}
		
		return member;
	}
	
	@RequestMapping("/signout")
	public Map signOut(HttpSession session) {
		session.invalidate(); // 세션 폐기
		
		Map result = new HashMap();
		result.put("status", "ok");
		
		return result;
	}

}