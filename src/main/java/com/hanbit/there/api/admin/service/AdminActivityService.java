package com.hanbit.there.api.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanbit.there.api.admin.reop.AdminActivityRepository;
import com.hanbit.there.api.domain.Activity;
import com.hanbit.there.api.service.FileService;

@Service
public class AdminActivityService {
	
	@Autowired
	private AdminActivityRepository adminActivityRepository;
	
	@Autowired
	private FileService fileService;
	
	public List<Activity> getActivities(String thereId) {
		return adminActivityRepository.findByThereIdOrderByName(thereId);
	}
	
	public boolean hasActivityId(String id) {
		return adminActivityRepository.exists(id);
	}
	
	public void saveActivity(Activity activity, List<MultipartFile> photos) {
		// TODO 파일처리
		
		adminActivityRepository.save(activity);
	}
}
