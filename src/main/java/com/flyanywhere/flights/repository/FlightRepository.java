package com.flyanywhere.flights.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flyanywhere.flights.model.Flight;

public interface FlightRepository extends JpaRepository<Flight, String> {

	List<Flight> findByFlightNumberStartingWith(String string);

	Flight findByFlightNumber(String flightNumber);

}
