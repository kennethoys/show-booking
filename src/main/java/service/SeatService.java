package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Seat;
import repository.SeatRepository;

@Service
public class SeatService {
	
	@Autowired
	SeatRepository seatRepository;
	
	public Seat getSeatFromSeatNumber(String seatNumber) {
		return seatRepository
				.findAll()
				.stream()
				.filter(seat -> seatNumber.contains(seat.getSeatNumber()))
				.findFirst()
				.get();
	}
}
