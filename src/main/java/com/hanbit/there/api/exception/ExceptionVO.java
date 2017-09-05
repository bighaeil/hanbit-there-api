package com.hanbit.there.api.exception;

public class ExceptionVO {
	
	private int errorCode;
	private String message;
	
	public ExceptionVO() {
		
	}

	public ExceptionVO(String message) { // 생성자
		this(0, message); // 아래 생성자 객체 부름
	}
	
	public ExceptionVO(int errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
}
