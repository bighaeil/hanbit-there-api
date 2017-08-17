package com.hanbit.there.api.vo;

import java.util.List;

public class ActivityListVO {

	private String title;
	private String type;
	private List<ActivityListItemVO> items;

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<ActivityListItemVO> getItems() {
		return items;
	}
	public void setItems(List<ActivityListItemVO> items) {
		this.items = items;
	}

}
