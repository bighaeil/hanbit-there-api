package com.hanbit.there.api.repo;

import org.springframework.data.repository.CrudRepository;

import com.hanbit.there.api.domain.Activity;

public interface ActivityRepository extends CrudRepository<Activity, String> {

}
