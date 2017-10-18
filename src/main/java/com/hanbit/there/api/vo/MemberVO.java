package com.hanbit.there.api.vo;

public class MemberVO {

	private String uid;
	private String email;
	private String currentPw;
	private String password;
	private String createDt;
	private String createdBy;
	private MemberDetailVO detail;

	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCreateDt() {
		return createDt;
	}
	public void setCreateDt(String createDt) {
		this.createDt = createDt;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public MemberDetailVO getDetail() {
		return detail;
	}
	public void setDetail(MemberDetailVO detail) {
		this.detail = detail;
	}
	public String getCurrentPw() {
		return currentPw;
	}
	public void setCurrentPw(String currentPw) {
		this.currentPw = currentPw;
	}
}
