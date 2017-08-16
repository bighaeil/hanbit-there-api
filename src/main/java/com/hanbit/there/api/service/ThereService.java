package com.hanbit.there.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanbit.there.api.dao.ThereDAO;
import com.hanbit.there.api.vo.ThereVO;

@Service
public class ThereService {
	
	@Autowired
	private ThereDAO thereDAO;
	
	public ThereVO getThere(String id) {
		ThereVO there = thereDAO.selectThere(id);
		there.setLocation(thereDAO.selectLocation(id));
		there.setAreaInfo(thereDAO.selectInfos(id));
		there.setTraffics(thereDAO.selectTraffics(id));
		
		return there;
	}
	
}
