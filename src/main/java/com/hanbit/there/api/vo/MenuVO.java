package com.hanbit.there.api.vo;

import java.util.List;

public class MenuVO {

	private String name;
	private List<SubMenuVO> subMenus;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<SubMenuVO> getSubMenus() {
		return subMenus;
	}
	public void setSubMenus(List<SubMenuVO> subMenus) {
		this.subMenus = subMenus;
	}
	
}
