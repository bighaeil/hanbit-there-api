package com.hanbit.there.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hanbit.there.api.domain.Airline;
import com.hanbit.there.api.domain.Airport;
import com.hanbit.there.api.service.AirlineService;
import com.hanbit.there.api.service.AirportService;

@RestController
public class AirlineController {

	@Autowired
	private AirlineService airlineService;

	@Autowired
	private AirportService airportService;

	@RequestMapping("/airline/add")
	public Airline addAirline(@RequestParam("id") int id) {
		return airlineService.addAirline(id);
	}

	@RequestMapping("/airline/{id}")
	public Airline getAirline(@PathVariable("id") String id) {
		return airlineService.getAirline(id);
	}

	@RequestMapping("/airport/{id}")
	public Airport getAirport(@PathVariable("id") int id) {
		return airportService.getAirport(id);
	}

	@RequestMapping("/airlines")
	public List<Airline> getAirlines() {
		return airlineService.getAirlines();
	}

}
