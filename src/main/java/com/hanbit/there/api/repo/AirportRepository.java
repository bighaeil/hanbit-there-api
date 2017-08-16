package com.hanbit.there.api.repo;

import org.springframework.data.repository.CrudRepository;

import com.hanbit.there.api.domain.Airport;

public interface AirportRepository extends CrudRepository<Airport, String> {

}
