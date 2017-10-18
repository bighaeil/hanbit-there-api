package com.hanbit.there.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanbit.there.api.HanbitConstants;
import com.hanbit.there.api.annotation.SignInRequired;
import com.hanbit.there.api.service.MemberService;
import com.hanbit.there.api.vo.MemberVO;

@RestController
@RequestMapping("/api/member")
public class MemberController {
	// 사용자가 로그인하면 Logging 처리
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class); // import org.slf4j 

	@Autowired
	private MemberService memberService;
	
	private static final ObjectMapper objectMapper = new ObjectMapper();

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
			HttpSession session,
			HttpServletResponse response) {
		
		MemberVO memberVO = memberService.signIn(email, password); // 에러 있을 경우 exception으로 처리

		logger.debug(email + " has signed in."); // Logging 레벨을 설정하지 않으면 기본적으로 INFO 레벨 이상의 로그만 출력 - properties 
		
		if (remember) {
			Cookie cookie = new Cookie("rid", "1234"); // import javax.servlet.http
			cookie.setMaxAge(60); // 초 단위 - 서버가 생각하는 세션과 브라우저가 생각하는 세션이 다름 - 토큰에 유효기간 설정
			response.addCookie(cookie); // Response Cookie에 추가
		}
	
		// 로그인 시 세션 값 설정 - 누구가 로그인 하고 있다.
		session.setAttribute("signedIn", true); 
		session.setAttribute("uid", memberVO.getUid());
		session.setAttribute("email", memberVO.getEmail());
		
		if (memberVO.getDetail() != null) {
			session.setAttribute("avatar", memberVO.getDetail().getAvatar());
		}
		
		Map result = new HashMap();
		result.put("email", memberVO.getEmail());

		return result;
	}

	@RequestMapping("/get")
	public Map getMember(HttpSession session) {
		Map member = new HashMap();

		if (session.getAttribute(HanbitConstants.SIGNIN_KEY) == null) {
			member.put(HanbitConstants.SIGNIN_KEY, false);
		}
		else {
			member.put(HanbitConstants.SIGNIN_KEY, true);
			member.put("email", session.getAttribute("email"));
			member.put("avatar", session.getAttribute("avatar"));
		}

		return member;
	}
	
	@RequestMapping("/signout")
	public Map signOut(HttpSession session, HttpServletRequest request) {
		session.invalidate(); // 세션 폐기
		
		Cookie cookie = WebUtils.getCookie(request, "rid"); // 유효기간 남은 토큰 같이 제거
		
		if (cookie != null) {
			cookie.setValue(null);
		}
		
		Map result = new HashMap();
		result.put("status", "ok");
		
		return result;
	}
	
	@SignInRequired
	@RequestMapping("/detail")
	public MemberVO getMemberDetail(HttpSession session) {
		String uid = (String) session.getAttribute("uid");

		return memberService.getMemberDetail(uid);
	}

	@SignInRequired
	@PostMapping("/save")
	public Map saveMemberDetail(@RequestParam("member") String json, @RequestParam("avatar") MultipartFile image, HttpSession session) throws Exception {
		MemberVO memberVO = objectMapper.readValue(json, MemberVO.class);
		memberVO.setUid((String) session.getAttribute("uid"));
		
		memberService.saveMemberDetail(memberVO, image);

		Map result = new HashMap();
		result.put("status", "ok");

		return result;
	}
	
}