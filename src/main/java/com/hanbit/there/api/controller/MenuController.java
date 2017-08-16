package com.hanbit.there.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hanbit.there.api.service.MenuService;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	
	@RequestMapping(value="/json", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)//client가 받은게 text가 아니고 json이라고 알려줌
	public String getMenu() {
		return menuService.getMenu();
	}
	
}
