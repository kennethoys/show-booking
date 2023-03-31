package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import model.Phone;
import model.Show;
import model.Ticket;

@SpringBootTest
public class TicketTest {
	
	@Test
	public void ticketHappyPath() {
		int showNumber = 1;
		int row = 3;
		int column = 5;
		int cancelWindowMinute = 2;
		Show show = new Show(showNumber, row, column, cancelWindowMinute);
		
		String phoneNumber = "83429354";
		Phone phone = new Phone(phoneNumber);
		
		String[] seatsArray = {"A1", "A2", "A3"};
		
		Ticket ticket = new Ticket(show, phone, seatsArray);
		assertEquals(showNumber, ticket.getShow().getShowNumber());
		assertEquals(phoneNumber, ticket.getPhone().getPhoneNumber());
		assertEquals(seatsArray.length, ticket.getSeats().size());
	}
	
	@Test
	public void seatInvalid() {
		try {
			int showNumber = 1;
			int row = 3;
			int column = 5;
			int cancelWindowMinute = 2;
			Show show = new Show(showNumber, row, column, cancelWindowMinute);
		
			String phoneNumber = "83429354";
			Phone phone = new Phone(phoneNumber);
		
			String[] seatsArray = {"A1", "Z9", "A3"};
			new Ticket(show, phone, seatsArray);
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Seat numbers are invalid");
		}
	}
	
	@Test
	public void seatAlreadyBooked() {
		try {
			int showNumber = 1;
			int row = 3;
			int column = 5;
			int cancelWindowMinute = 2;
			Show show = new Show(showNumber, row, column, cancelWindowMinute);
		
			String phoneNumber = "83429354";
			Phone phone = new Phone(phoneNumber);
		
			String[] seatsArray1 = {"A1", "A3"};
			new Ticket(show, phone, seatsArray1);
			
			String[] seatsArray2 = {"A1"};
			new Ticket(show, phone, seatsArray2);
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Seat number A1 for Show number 1 already booked");
		}
	}
}
