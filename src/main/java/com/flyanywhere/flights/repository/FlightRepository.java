package com.flyanywhere.flights.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flyanywhere.flights.model.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, String> {

	List<Flight> findByFlightNumberStartingWith(String airlineCode);

	Flight findByFlightNumber(String flightNumber);

}
