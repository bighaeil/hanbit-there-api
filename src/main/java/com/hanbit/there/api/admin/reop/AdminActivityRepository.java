package com.hanbit.there.api.admin.reop;

import java.util.List;

import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.repository.CrudRepository;

import com.hanbit.there.api.domain.Activity;

public interface AdminActivityRepository extends CrudRepository<Activity, String> {

//	List<Activity> findByThereIdOrderByName(String thereId); // 메소드명이 쿼리를 대신함
	// interface는 public 붙일 필요없음 - 무조건다 public
	@Query("SELECT META().id AS _ID," // 버켓명 `hanbit-there` - 안써도됨 - from 에 명시해서?
			+ " META().cas AS _CAS," // 버젼
			+ " name, photos, intro"
			+ " FROM #{#n1ql.bucket} WHERE thereId = $1 AND #{#n1ql.filter}"
			+ " ORDER BY name ASC")
	List<Activity> findByThereIdOrderByName(String thereId); // 쿼리 붙이면 메소드명은 아무렇게나 가능
}
