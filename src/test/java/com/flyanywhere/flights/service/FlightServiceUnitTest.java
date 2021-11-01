package com.flyanywhere.flights.service;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flyanywhere.flights.model.Flight;
import com.flyanywhere.flights.repository.FlightRepository;

@ExtendWith(MockitoExtension.class)
public class FlightServiceUnitTest {

	@Mock
	private FlightRepository flightRepository;

	private FlightService flightService;

	@BeforeEach
	public void setUp() {
		flightService = new FlightService(flightRepository);
	}

	@Test
	void testGetFlights_ReturnAllFlights() throws Exception {

		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

		given(flightRepository.findAll()).willReturn(List.of(new Flight("PX400", "MNL", "SYD", LocalDateTime.parse("2020-06-10T09:00:23", formatter),
				LocalDateTime.parse("2020-06-10T10:25:23", formatter))));
		
		List<Flight> flights = flightService.getFlights(null);
		
		assertThat(flights.get(0).getFlightNumber()).isEqualTo("PX400");
		assertThat(flights.get(0).getDeparturePort()).isEqualTo("MNL");
		assertThat(flights.get(0).getArrivalPort()).isEqualTo("SYD");
		assertThat(flights.get(0).getDepartureTime()).isEqualTo("2020-06-10T09:00:23");
		assertThat(flights.get(0).getArrivalTime()).isEqualTo("2020-06-10T10:25:23");
		
		verify(flightRepository).findAll();
		
	}

	@Test
	void testGetFlights_givenAirlineCode_returnFilteredFlights() throws Exception {

		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

		given(flightRepository.findByFlightNumberStartingWith("PX")).willReturn(List.of(new Flight("PX400", "MNL", "SYD", LocalDateTime.parse("2020-06-10T09:00:23", formatter),
				LocalDateTime.parse("2020-06-10T10:25:23", formatter))));
		
		List<Flight> flights = flightService.getFlights("PX");
		
		assertThat(flights.get(0).getFlightNumber()).isEqualTo("PX400");
		assertThat(flights.get(0).getDeparturePort()).isEqualTo("MNL");
		assertThat(flights.get(0).getArrivalPort()).isEqualTo("SYD");
		assertThat(flights.get(0).getDepartureTime()).isEqualTo("2020-06-10T09:00:23");
		assertThat(flights.get(0).getArrivalTime()).isEqualTo("2020-06-10T10:25:23");
		
		verify(flightRepository).findByFlightNumberStartingWith("PX");
	}

}
