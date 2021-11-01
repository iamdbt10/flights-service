package com.flyanywhere.flights.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.flyanywhere.flights.model.Flight;

@DataJpaTest
class FlightRepositoryUnitTest {
	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void findByFlightNumber_returnsFlight() throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		entityManager.persistFlushFind(
				new Flight("PX400", "MNL", "SYD", LocalDateTime.parse("2020-06-10T09:00:23", formatter),
						LocalDateTime.parse("2020-06-10T10:25:23", formatter)));
		Flight flight = this.flightRepository.findByFlightNumber("PX400");

		assertThat(flight.getFlightNumber()).isEqualTo("PX400");
		assertThat(flight.getDeparturePort()).isEqualTo("MNL");
		assertThat(flight.getArrivalPort()).isEqualTo("SYD");
		assertThat(flight.getDepartureTime()).isEqualTo("2020-06-10T09:00:23");
		assertThat(flight.getArrivalTime()).isEqualTo("2020-06-10T10:25:23");
	}
	
	@Test
	public void findByFlightNumber_returnsFilteredFlight() throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		entityManager.persistFlushFind(
				new Flight("PX400", "MNL", "SYD", LocalDateTime.parse("2020-06-10T09:00:23", formatter),
						LocalDateTime.parse("2020-06-10T10:25:23", formatter)));
		entityManager.persistFlushFind(
				new Flight("QF401", "MEL", "SYD", LocalDateTime.parse("2020-06-10T09:00:23", formatter),
						LocalDateTime.parse("2020-06-10T10:25:23", formatter)));
		
		List<Flight> flights = this.flightRepository.findByFlightNumberStartingWith("QF");

		assertThat(flights.size()).isEqualTo(1);
		assertThat(flights.get(0).getFlightNumber()).isEqualTo("QF401");
		assertThat(flights.get(0).getDeparturePort()).isEqualTo("MEL");
		assertThat(flights.get(0).getArrivalPort()).isEqualTo("SYD");
		assertThat(flights.get(0).getDepartureTime()).isEqualTo("2020-06-10T09:00:23");
		assertThat(flights.get(0).getArrivalTime()).isEqualTo("2020-06-10T10:25:23");
	}


}
