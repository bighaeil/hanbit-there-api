package com.hanbit.there.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MenuDAO {

	@Autowired
	private SqlSession sqlSession;
	
	public List<Map<String, String>> selectMenu() {
		return sqlSession.selectList("menu.selectMenu");
	}
	
}
