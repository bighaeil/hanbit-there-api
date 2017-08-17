package com.hanbit.there.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hanbit.there.api.domain.Activity;
import com.hanbit.there.api.service.ActivityService;

@RestController
@RequestMapping("/api")
public class ActivityController {

	@Autowired
	private ActivityService activityService;

	@RequestMapping("/activity/{id}")
	public Activity getActivity(@PathVariable("id") String id) {
		return activityService.getActivity(id);
	}

}
