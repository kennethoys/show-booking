package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "seats")
public class Seat {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "seat_id")
	private Long seatId;
	
	@Column(name = "seat_row")
	private int row;
	
	@Column(name = "seat_column")
	private int column;
	
	@ManyToOne
	@JoinColumn(name = "ticket_id")
	private Ticket ticket;
	
	@ManyToOne
	@JoinColumn(name = "show_id")
	private Show show;
	
	@Column(name = "seat_status")
	private SeatStatus status;
	
	public enum SeatStatus {
		OPEN,
		BOOKED
	}
	
	public Seat() {}
	
	public Seat(int row, int column, Show show) {
		this.row = row;
		this.column = column;
		this.show = show;
		this.status = SeatStatus.OPEN;
	}
	
	public String getRowAlphabet() {
		return row > 0 && row < 27 ? String.valueOf((char)(row + 64)) : null;
	}
	
	public String getSeatNumber() {
		return getRowAlphabet() + column;
	}
	
	public boolean isOpen() {
		return this.status == SeatStatus.OPEN;
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public Show getShow() {
		return show;
	}

	public void setShow(Show show) {
		this.show = show;
	}

	public SeatStatus getStatus() {
		return status;
	}

	public void setStatus(SeatStatus status) {
		this.status = status;
	}
	
	public void setStatusBooked() {
		this.status = SeatStatus.BOOKED;
	}
	
	public void setStatusOpen() {
		this.status = SeatStatus.OPEN;
	}
}
