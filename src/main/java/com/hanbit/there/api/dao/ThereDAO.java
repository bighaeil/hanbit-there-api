package com.hanbit.there.api.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanbit.there.api.vo.InfoVO;
import com.hanbit.there.api.vo.LocationVO;
import com.hanbit.there.api.vo.ThereVO;
import com.hanbit.there.api.vo.TrafficVO;

@Repository // 저장소 담당하는 bean들
public class ThereDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	public ThereVO selectThere(String id) {
		return sqlSession.selectOne("there.selectThere", id);
	}
	
	public LocationVO selectLocation(String id) {
		return sqlSession.selectOne("there.selectLocation", id);
	}
	
	public List<InfoVO> selectInfos(String id) {
		return sqlSession.selectList("there.selectInfos", id);
	}
	
	public List<TrafficVO> selectTraffics(String id) {
		return sqlSession.selectList("there.selectTraffics", id);
	}
	
}
