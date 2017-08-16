package com.hanbit.there.api.repo;

import java.util.List;

import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.View;
import org.springframework.data.repository.CrudRepository;

import com.hanbit.there.api.domain.Airline;

public interface AirlineRepository extends CrudRepository<Airline, String> {
	@Query("#{#n1ql.selectEntity} WHERE type = $1 AND #{#n1ql.filter}") // 쿼리 추가 - 파라미터 1개. 쿼리를 직업 입력하면 밑의 findByType 메소드는 의미가 없어진다.?  
	List<Airline> findByType(String type); // debug 모드에 의해서 실행되는 쿼리를 확인 할 수 있다.
	// findBy'Type' - domain의 멤버 변수들(@@Field된)의 이름으로 사용하는 듯
	List<Airline> findByTypeOrderById(String type);

	List<Airline> findByTypeOrderByIdDesc(String type);
	// 메소드 명에 따라서 쿼리가 바뀜
	@View(viewName="US") // couchBase 관리자에서 미리 정의 해서 US 만으로 부를 수 있다.
	List<Airline> findUS();
	
}
