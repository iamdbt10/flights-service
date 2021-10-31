package com.flyanywhere.flights.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.flyanywhere.flights.model.Flight;
import com.flyanywhere.flights.service.FlightService;

@WebMvcTest
class FlightControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FlightService flightService;

	@Test
	void testGetFlights_ReturnAllFlights() throws Exception {

		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

		when(this.flightService.getFlights(null)).thenReturn(List.of(
				new Flight("PX400", "MNL", "SYD", LocalDateTime.parse("2020-06-10T09:00:23", formatter),
						LocalDateTime.parse("2020-06-10T10:25:23", formatter)),
				new Flight("QF400", "MEL", "TOR", LocalDateTime.parse("2020-06-10T09:00:23", formatter),
						LocalDateTime.parse("2020-06-10T10:25:23", formatter)),
				new Flight("QF401", "TOR", "MEL", LocalDateTime.parse("2020-06-10T09:00:23", formatter),
						LocalDateTime.parse("2020-06-10T10:25:23", formatter))));
		this.mockMvc.perform(get("/flights")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].flightNumber", is("PX400")))
				.andExpect(jsonPath("$[0].departurePort", is("MNL")))
				.andExpect(jsonPath("$[0].arrivalPort", is("SYD")))
				.andExpect(jsonPath("$[0].departureTime", is("2020-06-10T09:00:23")))
				.andExpect(jsonPath("$[0].arrivalTime", is("2020-06-10T10:25:23")))
				.andExpect(jsonPath("$[1].flightNumber", is("QF400")))
				.andExpect(jsonPath("$[1].departurePort", is("MEL")))
				.andExpect(jsonPath("$[1].arrivalPort", is("TOR")))
				.andExpect(jsonPath("$[1].departureTime", is("2020-06-10T09:00:23")))
				.andExpect(jsonPath("$[1].arrivalTime", is("2020-06-10T10:25:23")))
				.andExpect(jsonPath("$[2].flightNumber", is("QF401")))
				.andExpect(jsonPath("$[2].departurePort", is("TOR")))
				.andExpect(jsonPath("$[2].arrivalPort", is("MEL")))
				.andExpect(jsonPath("$[2].departureTime", is("2020-06-10T09:00:23")))
				.andExpect(jsonPath("$[2].arrivalTime", is("2020-06-10T10:25:23")));
		verify(flightService).getFlights(null);
	}

	@Test
	void testGetFlights_givenAirlineCode_returnsFilteredFlights() throws Exception {

		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

		when(this.flightService.getFlights("QF")).thenReturn(List.of(
				new Flight("QF400", "MEL", "TOR", LocalDateTime.parse("2020-06-10T09:00:23", formatter),
						LocalDateTime.parse("2020-06-10T10:25:23", formatter)),
				new Flight("QF401", "TOR", "MEL", LocalDateTime.parse("2020-06-10T09:00:23", formatter),
						LocalDateTime.parse("2020-06-10T10:25:23", formatter))));
		this.mockMvc.perform(get("/flights/?airlineCode=QF")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].flightNumber", is("QF400")))
				.andExpect(jsonPath("$[0].departurePort", is("MEL")))
				.andExpect(jsonPath("$[0].arrivalPort", is("TOR")))
				.andExpect(jsonPath("$[0].departureTime", is("2020-06-10T09:00:23")))
				.andExpect(jsonPath("$[0].arrivalTime", is("2020-06-10T10:25:23")))
				.andExpect(jsonPath("$[1].flightNumber", is("QF401")))
				.andExpect(jsonPath("$[1].departurePort", is("TOR")))
				.andExpect(jsonPath("$[1].arrivalPort", is("MEL")))
				.andExpect(jsonPath("$[1].departureTime", is("2020-06-10T09:00:23")))
				.andExpect(jsonPath("$[1].arrivalTime", is("2020-06-10T10:25:23")));
		verify(flightService).getFlights("QF");
	}

}
