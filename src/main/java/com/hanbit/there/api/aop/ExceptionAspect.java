package com.hanbit.there.api.aop;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.hanbit.there.api.exception.ExceptionVO;
import com.hanbit.there.api.exception.HanbitException;

@Aspect
@Component
@Order(10) // 순서 모호할 경우 작은 값이 먼저 실행 - 중간 낄 수 있으니 되도록 크게
public class ExceptionAspect {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionAspect.class);

	private static final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void requestMapping() {

	}
	@Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
	public void postMapping() {

	}
	@Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
	public void getMapping() {

	}

	@Around("requestMapping() || postMapping() || getMapping()")
	public Object handleException(ProceedingJoinPoint pjp) throws UnsupportedEncodingException, IOException {
		try {
			return pjp.proceed();
		}
		catch (Throwable t) {
			ServletRequestAttributes reuqestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			HttpServletResponse response = reuqestAttributes.getResponse();

			String message = "사용자가 많아 서비스가 지연되고있습니다."; // 사용자에게는 사용자용 메시지 전송 - 관리자는 콘솔로 진짜 메시지 확인
			ExceptionVO exceptionVO = null;
			int statusCode = 500;

			if (t instanceof HanbitException) {
				HanbitException e = (HanbitException) t;
				exceptionVO = new ExceptionVO(e.getErrorCode(), e.getMessage());
				statusCode = e.getErrorCode();
			}
			else {
				logger.error(t.getMessage(), t);
				exceptionVO = new ExceptionVO(message);
			}
			
			byte[] bytes = jsonMapper.writeValueAsBytes(exceptionVO);

			response.setStatus(statusCode);
			response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			response.setContentLength(bytes.length);
			response.getOutputStream().write(bytes);
			response.flushBuffer();
		}

		return null;
	}
	
	/*
	@Around("requestMapping() || postMapping() || getMapping()")
	public Object handleException(ProceedingJoinPoint pjp) throws UnsupportedEncodingException, IOException {
		try {
			return pjp.proceed();
		}
		catch (Throwable t) {
			ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			HttpServletResponse response = requestAttributes.getResponse();
			
			String message = "에러 기본 메시지";
			
			if (t instanceof HanbitException) {
				message = t.getMessage();
			}
			else { // 다른 js 에서도 exception 쓸 수 있도록
				logger.error(t.getMessage(), t);
			}
			
			ExceptionVO exceptionVO = new ExceptionVO(t.getMessage());
			
			//String json = "{\"message\":\"오류발생\"}";
			//byte[] bytes = json.getBytes("UTF-8");
			//String json = jsonMapper.writeValueAsString(exceptionVO);
			//exception 상태를 단순 문자열에서 -> 객체로 바꿈 
			byte[] bytes = jsonMapper.writeValueAsString(exceptionVO);
			
			response.setStatus(500); // 200은 성공이니까 500으로 보내자
			response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			response.setContentLength(bytes.length);
			response.getOutputStream().write(bytes);
		}
		
		return null;
	}
	*/
}

/*
SessionAspect {
	
	ExceptionAspect { // 에러를 ExceptionAspect가 처리하길 바란다.
		
		@
		@
		loggin <- 에러가 생길 경우
	}
}
 - 위와 같이 스프링이 Aspect를 잡을 때 (알 수 없음 스프링 마음임)
 - 에러 생기면
 - SessionAspect가 이를 처리 - X
ExceptionAspectSessionAspect {
	
	SessionAspect {
위 처럼 구조가 잡히길 바란다. @Order로 확실하게 만듬
*/