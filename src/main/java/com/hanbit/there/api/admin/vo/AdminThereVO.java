package com.hanbit.there.api.admin.vo;

public class AdminThereVO {
	private String id;
	private String groupId;
	private String name;
	private String summary;
	private String background;
	private int infoCount;
	private int trafficCount;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getBackground() {
		return background;
	}
	public void setBackground(String background) {
		this.background = background;
	}
	public int getInfoCount() {
		return infoCount;
	}
	public void setInfoCount(int infoCount) {
		this.infoCount = infoCount;
	}
	public int getTrafficCount() {
		return trafficCount;
	}
	public void setTrafficCount(int trafficCount) {
		this.trafficCount = trafficCount;
	}
	
}

