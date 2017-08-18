package com.hanbit.there.api.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanbit.there.api.admin.reop.AdminActivityRepository;
import com.hanbit.there.api.domain.Activity;

@Service
public class AdminActivityService {
	
	@Autowired
	private AdminActivityRepository adminActivityRepository;
	
	public List<Activity> getActivities(String thereId) {
		return adminActivityRepository.findByThereIdOrderByName(thereId);
	}
}
