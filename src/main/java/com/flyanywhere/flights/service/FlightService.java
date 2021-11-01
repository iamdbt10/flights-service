package com.flyanywhere.flights.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flyanywhere.flights.model.Flight;
import com.flyanywhere.flights.repository.FlightRepository;

@Service
public class FlightService {
	
	@Autowired
	private FlightRepository flightRepository;

	public FlightService(FlightRepository flightRepository) {
		this.flightRepository = flightRepository;
	}

	public List<Flight> getFlights(String airlineCode) {
		if(airlineCode != null) {
			return flightRepository.findByFlightNumberStartingWith(airlineCode);
		}else {
			return flightRepository.findAll();
		}
	}

	public Flight saveFlight(Flight flight) {
		return flightRepository.save(flight);
	}

	public Flight getFlightByFlightNumber(String flightNumber) {
		return flightRepository.findByFlightNumber(flightNumber);
	}

}
