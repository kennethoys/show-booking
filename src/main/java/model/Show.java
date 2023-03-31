package model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import exception.InvalidNumberOfSeatsException;

@Entity
@Table(name = "shows")
public class Show {
	
	private static final int MAX_ROWS = 26;
	private static final int MAX_SEATS_PER_ROW = 10;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "show_id")
	private Long showId;
	
	@Column(name = "show_number", unique = true)
	private int showNumber;
	
	@Column(name = "show_cancel_window_minute")
	private int cancelWindowMinute = 2;
	
	@OneToMany(mappedBy = "show", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Seat> seats;
	
	@OneToMany(mappedBy = "show", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Ticket> tickets;
	
	public Show() {
		this.seats = new HashSet<Seat> ();
		this.tickets = new HashSet<Ticket> ();
	}
	
	public Show(int showNumber, int rows, int columns, int cancelWindowMinute) {
		this.showNumber = showNumber;
		this.cancelWindowMinute = cancelWindowMinute;
		
		if(!((rows <= MAX_ROWS && rows > 0) && (columns <= MAX_SEATS_PER_ROW && columns > 0))) {
			throw new InvalidNumberOfSeatsException(
				"Number of rows have to be between 1 to 26 inclusive and seats per row between 1 to 10 inclusive"
			);
		}
		
		this.seats = new HashSet<Seat> ();
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				// Start from A for row and 1 for column
				Seat seat = new Seat(i + 1, j + 1, this);
				this.seats.add(seat);
			}
		}
		
		this.tickets = new HashSet<Ticket> ();
	}
	
	public static int getMaxRows() {
		return MAX_ROWS;
	}
	
	public static int getMaxSeatsPerRow() {
		return MAX_SEATS_PER_ROW;
	}
	
	public Long getShowId() {
		return showId;
	}

	public void setShowId(Long showId) {
		this.showId = showId;
	}
	
	public int getShowNumber() {
		return showNumber;
	}

	public void setShowNumber(int showNumber) {
		this.showNumber = showNumber;
	}

	public int getCancelWindowMinute() {
		return cancelWindowMinute;
	}

	public void setCancelWindowMinute(int cancelWindowMinute) {
		this.cancelWindowMinute = cancelWindowMinute;
	}

	public Set<Seat> getSeats() {
		return seats;
	}

	public void setSeats(Set<Seat> seats) {
		this.seats = seats;
	}

	public Set<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}
}
