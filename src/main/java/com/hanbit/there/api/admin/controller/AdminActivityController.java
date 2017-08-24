package com.hanbit.there.api.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanbit.there.api.admin.service.AdminActivityService;
import com.hanbit.there.api.domain.Activity;

@RestController
@RequestMapping("/api/admin")
public class AdminActivityController {
	
	@Autowired
	private AdminActivityService adminActivityService;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@RequestMapping("/{thereId}/activities")
	public List<Activity> getActivities(@PathVariable("thereId")String thereId) {
		return adminActivityService.getActivities(thereId);
	}
	
	@RequestMapping(value="/activity/{id}", method=RequestMethod.OPTIONS)
	public Map hasActivityId(@PathVariable("id") String id) {
		boolean exists = adminActivityService.hasActivityId(id);
		
		Map result = new HashMap();
		result.put("exists", exists);
		
		return result;
	}
	
	@PostMapping("/activity/save") // @RequestMapping의 method=RequestMethod.POST 안써도됨
	public Map saveActivity(@RequestParam("model") String model, @RequestParam("photos") List<MultipartFile> photos) throws Exception { // List<MultipartFile> 파일들 list로 받으면됨 - request 할 필요 없음
		Activity activity = mapper.readValue(model, Activity.class);
		
		adminActivityService.saveActivity(activity, photos);
		
		Map result = new HashMap();
		result.put("status", "OK");
		
		return result;
	}
}
