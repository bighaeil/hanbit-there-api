package com.hanbit.there.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SignInRequired {
	// annotation 뒤에 값 넣기
	String[] value() default {}; // 배열이니까 default는 {}
	//String[] -> ({"abc", "123"}) - 배열도 가능
}
