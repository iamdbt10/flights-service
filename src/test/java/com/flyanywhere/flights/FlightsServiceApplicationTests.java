package com.flyanywhere.flights;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.flyanywhere.flights.model.Flight;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class FlightsServiceApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void testPostFlight() throws JSONException {
		// Prepare Request
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Flight flightRequest = new Flight();
		flightRequest.setFlightNumber("QF400");
		flightRequest.setArrivalPort("MEL");
		flightRequest.setDeparturePort("SYD");
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		flightRequest.setArrivalTime(LocalDateTime.parse("2020-06-10T09:00:23", formatter));
		flightRequest.setDepartureTime(LocalDateTime.parse("2020-06-10T10:25:23", formatter));

		// Run
		ResponseEntity<Flight> response = restTemplate.postForEntity("/flights", flightRequest, Flight.class);
		Flight testX = response.getBody();

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(testX.getFlightNumber()).isEqualTo("QF400");
		assertThat(testX.getArrivalPort()).isEqualTo("MEL");
		assertThat(testX.getDeparturePort()).isEqualTo("SYD");
		assertThat(testX.getArrivalTime()).isEqualTo(LocalDateTime.parse("2020-06-10T09:00:23", formatter));
		assertThat(testX.getDepartureTime()).isEqualTo(LocalDateTime.parse("2020-06-10T10:25:23", formatter));
	}

	@Test
	void testGetFlightByAirlineCode() throws JSONException {
		// Prepare Request
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

		Flight flightRequestQF = new Flight();
		flightRequestQF.setFlightNumber("QF400");
		flightRequestQF.setArrivalPort("MEL");
		flightRequestQF.setDeparturePort("SYD");
		flightRequestQF.setArrivalTime(LocalDateTime.parse("2020-06-10T09:00:23", formatter));
		flightRequestQF.setDepartureTime(LocalDateTime.parse("2020-06-10T10:25:23", formatter));

		ResponseEntity<Flight> responseQF = restTemplate.postForEntity("/flights", flightRequestQF, Flight.class);
		assertThat(responseQF.getStatusCode()).isEqualTo(HttpStatus.OK);

		Flight flightRequestPX = new Flight();
		flightRequestPX.setFlightNumber("PX400");
		flightRequestPX.setArrivalPort("MNL");
		flightRequestPX.setDeparturePort("SYD");
		flightRequestPX.setArrivalTime(LocalDateTime.parse("2020-06-10T09:00:23", formatter));
		flightRequestPX.setDepartureTime(LocalDateTime.parse("2020-06-10T10:25:23", formatter));

		ResponseEntity<Flight> responsePX = restTemplate.postForEntity("/flights", flightRequestPX, Flight.class);
		assertThat(responsePX.getStatusCode()).isEqualTo(HttpStatus.OK);

		// Run
		ResponseEntity<Flight[]> response = restTemplate.getForEntity("/flights", Flight[].class);
		Flight[] flights = response.getBody();

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertNotNull(flights);
		
		assertThat(flights[0].getFlightNumber()).isEqualTo("QF400");
		assertThat(flights[0].getArrivalPort()).isEqualTo("MEL");
		assertThat(flights[0].getDeparturePort()).isEqualTo("SYD");
		assertThat(flights[0].getArrivalTime()).isEqualTo(LocalDateTime.parse("2020-06-10T09:00:23", formatter));
		assertThat(flights[0].getDepartureTime()).isEqualTo(LocalDateTime.parse("2020-06-10T10:25:23", formatter));
	}

}
