package com.hanbit.there.api.exception;

public class HanbitException extends RuntimeException {

	private int errorCode = 500;
	
	public HanbitException(String message) {
		super(message);
	}
	
	public HanbitException(int errorcode, String message) {
		this(message); // 생성자를 불러야 객체가 만든어진다. - super도 가능 - 위 생성자 부름
		
		this.errorCode = errorcode; // 순서 중요
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
