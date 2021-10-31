package com.flyanywhere.flights.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Flight {
	
	@Id
	String flightNumber;
	String departurePort;
	String arrivalPort;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
	LocalDateTime departureTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
	LocalDateTime arrivalTime;
	
}
