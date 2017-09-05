package com.hanbit.there.api.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class LoggingAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void allContorrlerMethod() {
		
	}
	
	@Pointcut(value="allContorrlerMethod()", argNames="joinPoint")
	public void logRequest(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature(); // 메소드에 대한 정보를 가지고 있음
		String methodName = signature.toShortString(); // 간단 버전
		
		//Request, Response 정보 보관
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		String remoteAddr = request.getRemoteAddr(); //IP
		String uri = request.getRequestURI();
		
		logger.debug(methodName + " has requested by " + remoteAddr + "(" + uri + ")");
		/*
		String methodName = signature.getName();
		//어떤 형태의 클래스인지 알고 싶을 때
		methodName += "(";
		Class[] paramTypes = signature.getParameterTypes(); // java 8
		for (Class paramType : paramTypes) {
			methodName += paramType.getSimpleName() + ", ";
		}
		methodName += ")";
		*/
	}
	
/*	
	//@Before("execution(리턴타입 패키지.모든클래스.모든메소드(모든파라미터))")
	//@Before("execution(* com.hanbit.there.api.controller.*.*(..))") // api.controller 만
	//api..controller 내부 모든 controller 패키지를 찾음 - AspectJ의 Pointcut 표현식
	// || execution(* com.hanbit.there.api..service.*.*(..)) 추가 - pointcut의 조합
	@Before(value="allControllerMethod() || allServiceMethod()", argNames="joinPoint")
	public void logController(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().toShortString();
		logger.debug(methodName + " has called (before)");
	}
	
	//@Pointcut - "allServiceMethod()" pointcut 어노테이션으로 pointcut 표현식을 변수화 할 수 있음
	//within 명시자 - 메소드가 아닌 특정 타입에 속하는 메서드를 pointcut으로 설정
	@Pointcut("within(com.hanbit.there.api..service.*)")
	public void allServiceMethod() {
		
	}
	// controller도 변수화
	// @annotation(import전체경로) - 어노테이션 별로 찾을 수 있음
	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void allControllerMethod() {
		
	}
	
	@After(value="allServiceMethod()", argNames="joinPoint")
	public void logService(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().toShortString();
		logger.debug(methodName + " has called (after)");
	}
	
	// @After는 에러는 신경 쓰지 않음 - @AfterReturning 정상적 종료까지 기다림
	// 에러 나면 - @AfterThrow가 출력 - 에러 정보
	@AfterReturning("allServiceMethod()")
	public void logServiceReturn() {
		logger.debug("method returned");
	}
*/
}
