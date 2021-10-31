package com.flyanywhere.flights.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flyanywhere.flights.model.Flight;
import com.flyanywhere.flights.service.FlightService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/flights")
@Slf4j
public class FlightController {
	
	@Autowired
	private FlightService flightService;

	@PostMapping
	public Flight saveFlight(@RequestBody Flight flight) {
		return flightService.saveFlight(flight);	
	}
	
	@GetMapping("/{flightNumber}")
	public Flight getFlightsByFlightNumber(@PathVariable String flightNumber) {
		log.debug("Airline Code: {}", flightNumber );
		return flightService.getFlightByFlightNumber(flightNumber);
	}
	
	@GetMapping
	public List<Flight> getFlights(@RequestParam(required = false) String airlineCode) {
		log.debug("Airline Code: {}", airlineCode );
		return flightService.getFlights(airlineCode);
	}
}
