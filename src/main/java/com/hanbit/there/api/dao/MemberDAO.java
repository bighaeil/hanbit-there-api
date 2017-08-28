package com.hanbit.there.api.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanbit.there.api.vo.MemberVO;

@Repository
public class MemberDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	public int insertMember(MemberVO memberVO) {
		return sqlSession.insert("member.insertMember", memberVO);
	}
	
	public int countMember(String email) {
		return sqlSession.selectOne("member.countMember", email);
	}
	
}
