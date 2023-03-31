package service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exception.InvalidPhoneNumberException;
import exception.PhoneUsedForShowException;
import model.Phone;
import model.Show;
import model.Ticket;
import repository.TicketRepository;

@Service
public class TicketService {
	
	@Autowired
	TicketRepository ticketRepository;
	
	@Autowired
	PhoneService phoneService;
	
	@Autowired
	SeatService seatService;
	
	@Autowired
	ShowService showService;
	
	public Ticket createTicket(int showNumber, String phoneNumber, String[] seatsArray) {
		Show show = showService.getShowByShowNumber(showNumber);
		Phone phone = phoneService.getOrCreatePhone(phoneNumber);
		
		// Check if phone number has already booked current show
		phone.getTickets().forEach(ticket -> {
			if(ticket.getShow().getShowNumber() == showNumber) {
				throw new PhoneUsedForShowException(
					"Phone number is already used for current show"
				);
			}
		});
		
		new Ticket(show, phone, seatsArray);
		showService.saveShow(show);
			
		Ticket savedTicket = getTicketByShowAndPhone(showNumber, phoneNumber);
		return savedTicket;
	}
	
	public Ticket getTicketByShowAndPhone(int showNumber, String phoneNumber) {
		return ticketRepository
				.findAll()
				.stream()
				.filter(ticket -> ticket.getShow().getShowNumber() == showNumber && ticket.getPhone().getPhoneNumber().equals(phoneNumber))
				.findFirst()
				.orElse(null);
	}
	
	public Ticket getTicketByTicketId(Long ticketId) {
		return ticketRepository
				.findAll()
				.stream()
				.filter(ticket -> ticket.getTicketId().equals(ticketId))
				.findFirst()
				.orElse(null);
	}
	
	public boolean cancelTicket(Long ticketId, String phoneNumber) {
		Ticket ticket = getTicketByTicketId(ticketId);
		
		if(!ticket.getPhone().getPhoneNumber().equals(phoneNumber)) {
			throw new InvalidPhoneNumberException(
				"Phone number does not match ticket"
			);
		}
		
		Show show = ticket.getShow();
		long timeDifferenceSecond = ChronoUnit.SECONDS.between(ticket.getTimeBooked(), LocalDateTime.now());
		long cancelWindowSecond = show.getCancelWindowMinute() * 60;
		
		if(timeDifferenceSecond <= cancelWindowSecond) {
			ticket.cancelTicket();
			ticket.getSeats().forEach(seat -> {
				seat.setStatusOpen();
				seat.setTicket(null);
			});
			ticket.setSeats(null);
			showService.saveShow(show);
			return true;
		} else {
			return false;
		}
	}
}
