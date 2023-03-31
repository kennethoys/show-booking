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

@Entity
@Table(name = "phones")
public class Phone {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "phone_id")
	private Long phoneId;
	
	@Column(name = "phone_number", unique = true)
	private String phoneNumber;
	
	@OneToMany(mappedBy = "phone", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Ticket> tickets;
	
	public Phone() {
		tickets = new HashSet<Ticket> ();
	}
	
	public Phone(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		tickets = new HashSet<Ticket> ();
	}
	
	public Long getPhoneId() {
		return phoneId;
	}

	public void setPhoneId(Long phoneId) {
		this.phoneId = phoneId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Set<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}
}
