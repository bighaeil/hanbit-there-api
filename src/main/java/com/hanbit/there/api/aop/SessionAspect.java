package com.hanbit.there.api.aop;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hanbit.there.api.HanbitConstants;
import com.hanbit.there.api.annotation.SignInRequired;
import com.hanbit.there.api.exception.HanbitException;

@Aspect
@Component
@Order(20)
public class SessionAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(SessionAspect.class);

	@Around("@annotation(com.hanbit.there.api.annotation.SignInRequired)")
	public Object checkSignedIn(ProceedingJoinPoint pjp) throws Throwable {
		ServletRequestAttributes reuqestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		HttpSession session = reuqestAttributes.getRequest().getSession();

		//만든 annotation의 값(배열)을 가져온다.
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		SignInRequired signInRequired = signature.getMethod().getAnnotation(SignInRequired.class);
		String[] values = signInRequired.value();
		
		if (session.getAttribute(HanbitConstants.SIGNIN_KEY) == null) {
			throw new HanbitException(403, "로그인이 필요합니다.");
		}

		return pjp.proceed();
	}
}

/*
 * 스레드풀 - 들어오는 요청을 처리하는데 끝나면 - 지우지 말고 풀에 다시 돌려줌
 * 스레드로컬 - static 처럼 동작하는데 실행중인 스레드에서만 static - 즉 남이 볼 수 없다.
 * -> request를 자기만 볼 수 있다. - 그러나 스레드 돌려줄 때 - 이전 쓰던 정보 볼 수 있다. 
 * RequestContextHolder를 사용하면 알아서 스레드로컬 사용하고, 지워준다.
 * (resetRequestAttributes() - 어쩌다 에러나는 대표적인 예 - 여러 사용자 사용할 때) 
 * 
 */