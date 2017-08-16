package com.hanbit.there.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hanbit.there.api.service.ThereService;
import com.hanbit.there.api.vo.ThereVO;


@RestController // 무조건 json만 - html 아니고
@RequestMapping("/api/there") // 주소 앞에다 뭍일 것 - 이 컨트롤러로 시작하는 주소는 "/api/there"
public class ThereController {
	
	@Autowired
	private ThereService thereService;
	
	@RequestMapping("/{theresId}") // 이런식으로 /api/there/Guam
	public ThereVO getThere(@PathVariable("theresId") String theresId) { // 주소에 있는 값 가져옴 String에

		return thereService.getThere(theresId);
	}
	
}
