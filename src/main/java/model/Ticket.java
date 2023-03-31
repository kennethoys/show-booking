package model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import exception.SeatBookedException;
import exception.SeatNotExistException;
import model.Seat.SeatStatus;

@Entity
@Table(name = "tickets")
public class Ticket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ticket_id")
	private Long ticketId;
	
	@ManyToOne
	@JoinColumn(name = "show_id")
	private Show show;
	
	@ManyToOne
	@JoinColumn(name = "phone_id")
	private Phone phone;
	
	@Column(name = "ticket_time_booked")
	private LocalDateTime timeBooked;
	
	@Column(name = "ticket_status")
	private TicketStatus status;
	
	@OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Seat> seats;
	
	public enum TicketStatus {
		BOOKED,
		CANCELLED
	}
	
	public Ticket() {}
	
	public Ticket(Show show, Phone phone, String[] seatsArray) {
		List<String> seatsListString = Arrays.asList(seatsArray);
		Set<Seat> allSeats = show.getSeats();
		
		// Check if buyer booked seats that don't exist
		List<String> allSeatsString = allSeats.stream()
				.map(seat -> seat.getSeatNumber())
				.collect(Collectors.toList());
		if(!allSeatsString.containsAll(seatsListString)) {
			throw new SeatNotExistException(
				"Seat numbers are invalid"
			);
		}
		
		Set<Seat> seatsSet = allSeats
				.stream()
				.filter(seat -> seatsListString.contains(seat.getSeatNumber()))
				.collect(Collectors.toSet());
		
		// Check if seats are already booked
		seatsSet.forEach(seat -> {
			if(seat.getStatus().equals(SeatStatus.BOOKED)) {
				throw new SeatBookedException(
					"Seat number " + seat.getSeatNumber() + " for Show number " + seat.getShow().getShowNumber() + " already booked"
				);
			}
		});
		
		this.show = show;
		this.show.getTickets().add(this);
		this.phone = phone;
		this.phone.getTickets().add(this);
		this.timeBooked = LocalDateTime.now();
		this.status = TicketStatus.BOOKED;
		this.seats = seatsSet;
		this.seats.forEach(seat -> {
			seat.setTicket(this);
			seat.setStatusBooked();
		});
	}
	
	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public Show getShow() {
		return show;
	}

	public void setShow(Show show) {
		this.show = show;
	}

	public Phone getPhone() {
		return phone;
	}

	public void setPhone(Phone phone) {
		this.phone = phone;
	}

	public LocalDateTime getTimeBooked() {
		return timeBooked;
	}

	public void setTimeBooked(LocalDateTime timeBooked) {
		this.timeBooked = timeBooked;
	}

	public TicketStatus getStatus() {
		return status;
	}
	
	public boolean isCancelled() {
		return status == TicketStatus.CANCELLED;
	}
	
	public void setStatus(TicketStatus status) {
		this.status = status;
	}
	
	public void cancelTicket() {
		this.status = TicketStatus.CANCELLED;
	}
	
	public Set<Seat> getSeats() {
		return seats;
	}

	public void setSeats(Set<Seat> seats) {
		this.seats = seats;
	}
}
