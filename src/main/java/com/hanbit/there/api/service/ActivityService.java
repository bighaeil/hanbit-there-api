package com.hanbit.there.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanbit.there.api.dao.ThereDAO;
import com.hanbit.there.api.domain.Activity;
import com.hanbit.there.api.repo.ActivityRepository;
import com.hanbit.there.api.vo.ThereVO;

@Service
public class ActivityService {

	@Autowired
	private ActivityRepository activityRepository;
	@Autowired
	private ThereDAO thereDAO;

	public Activity getActivity(String id) {
		Activity activity = activityRepository.findOne(id);

		String thereId = activity.getThereId();
		ThereVO there = thereDAO.selectThere(thereId);

		activity.setThere(there);

		return activity;
	}

}
