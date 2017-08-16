package com.hanbit.there.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanbit.there.api.dao.MenuDAO;
import com.hanbit.there.api.vo.MenuVO;
import com.hanbit.there.api.vo.SubMenuVO;

@Service
public class MenuService {
	
	@Autowired
	private MenuDAO menuDAO;
	
	private String menuCache; // menu는 바뀔일이 없으니까
	
	public String getMenu() {
		if (loadCache() == null) {
			List<Map<String, String>> rawMenus = menuDAO.selectMenu();
			
			List<MenuVO> menus = new ArrayList<>();
			String prevGroupName = "";
			MenuVO currentMenu = null;
			
			for (Map<String, String> rawMenu : rawMenus) {
				String groupName = rawMenu.get("group_name");
				
				if (!prevGroupName.equals(groupName)) {
					MenuVO menu = new MenuVO();
					menu.setName(groupName);
					menus.add(menu);
					
					List<SubMenuVO> subMenus = new ArrayList<>();
					menu.setSubMenus(subMenus);
					currentMenu = menu;
				}
				
				List<SubMenuVO> subMenus = currentMenu.getSubMenus();
				
				SubMenuVO subMenu = new SubMenuVO();
				subMenu.setId(rawMenu.get("id"));
				subMenu.setName(rawMenu.get("name"));
				
				subMenus.add(subMenu);
				
				prevGroupName = groupName;
			}
			
			saveCache(menus);
		}
		
		return loadCache();
	}
	
	public void saveCache(List<MenuVO> menus) {
		ObjectMapper mapper = new ObjectMapper(); // java 타입을 JSON 타입으로 만든다.
		try {
			menuCache = mapper.writeValueAsString(menus);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e); // 받는 쪽에서 try catch 안해도 됨 - 어짜피 에러는 나있는 상태
		}
	}
	
	public String loadCache() {
		return menuCache;
	}
	
	public void invalidateCache() {
		menuCache = null;
	}

}
