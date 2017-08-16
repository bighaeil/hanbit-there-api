package com.hanbit.there.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanbit.there.api.domain.Airport;
import com.hanbit.there.api.repo.AirportRepository;

@Service
public class AirportService {

	@Autowired
	private AirportRepository airportRepository;

	public Airport getAirport(int id) {
		String key = "airport_" + id;

		return airportRepository.findOne(key);
	}

}
