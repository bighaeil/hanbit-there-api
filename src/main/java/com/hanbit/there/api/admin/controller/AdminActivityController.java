package com.hanbit.there.api.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hanbit.there.api.admin.service.AdminActivityService;
import com.hanbit.there.api.domain.Activity;

@RestController
@RequestMapping("/api/admin")
public class AdminActivityController {
	
	@Autowired
	private AdminActivityService adminActivityService;
	
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
}
