package app;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import exception.InvalidArgumentException;
import model.Seat;
import model.Show;
import model.Ticket;
import service.PhoneService;
import service.SeatService;
import service.ShowService;
import service.TicketService;

@Component
public class ShowBookingRunner implements CommandLineRunner {
	
	@Autowired
	PhoneService phoneService;
	
	@Autowired
	SeatService seatService;
	
	@Autowired
	ShowService showService;
	
	@Autowired
	TicketService ticketService;
	
	private Scanner scanner = new Scanner(System.in);
	
	public void run(String... args) throws Exception {
		init();
		boolean running = true;
		while(running) {
			try {
				System.out.println(
					  "\nPlease type the following to select user type:"
					+ "\n1 Admin"
					+ "\n2 Buyer"
					+ "\n3 Quit");
			
				String selectUserInput = scanner.nextLine();
			
				switch(selectUserInput) {
				case "1":
					adminChoices();
					break;
				case "2":
					buyerChoices();
					break;
				case "3":
					running = false;
					break;
				default:
					throw new InvalidArgumentException();
				}
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		scanner.close();
	}
	
	public void adminChoices() {
		System.out.println(
			  "\nPlease select the next command for admin: "
			+ "\nTo setup show: "
			+ "\n1 <Show Number> <Number of Rows> <Number of seats per row> <Cancellation window in minutes>"
			+ "\nTo view show: "
			+ "\n2 <Show Number>"
		);
		
		String adminInput = scanner.nextLine();
		String[] adminInputArray = adminInput.split(" ");
		
		if(adminInputArray.length != 2 && adminInputArray.length != 5) {
			throw new InvalidArgumentException();
		}
		
		String adminChoice = adminInputArray[0];
		
		switch(adminChoice) {
		case "1":
			int showNumber = Integer.parseInt(adminInputArray[1]);
			int row = Integer.parseInt(adminInputArray[2]);
			int column = Integer.parseInt(adminInputArray[3]);
			int cancelWindowMinute = Integer.parseInt(adminInputArray[4]);
			Show show = showService.createShow(showNumber, row, column, cancelWindowMinute);
	
			if(show != null) {
				System.out.println("Show has been created");
			} else {
				System.out.println("Failed to create show");
			}
			break;
		case "2":
			int showNumber2 = Integer.parseInt(adminInputArray[1]);
			Show show2 = showService.getShowByShowNumber(showNumber2);
			
			if(show2 != null) {
				System.out.println("\nShow number: " + show2.getShowNumber());
				show2.getTickets().forEach(ticket -> {
					if(!ticket.isCancelled()) {
						System.out.print(
							  "\nTicket number: " + ticket.getTicketId()
							+ "\nShow number: " + ticket.getShow().getShowNumber()
							+ "\nPhone number: " + ticket.getPhone().getPhoneNumber()
							+ "\nSeats: "
						);
						
						List<Seat> seats = new ArrayList<Seat> (ticket.getSeats());
						seats.sort(Comparator.comparing(Seat::getRow).thenComparing(Seat::getColumn));
						seats.forEach(seat -> {
							System.out.print(seat.getSeatNumber() + " ");
						});
						System.out.println();
					}
				});
				
			} else {
				System.out.println("Show doesn't exist");
			}
			break;
		default:
			throw new InvalidArgumentException();
		}
	}
	
	public void buyerChoices() {
		System.out.println(
				  "\nPlease select the next command for buyer: "
				+ "\nView available seats: "
				+ "\n1 <Show Number>"
				+ "\nBook show: "
				+ "\n2 <Show Number> <Phone#> <Comma separated list of seats> "
				+ "\nCancel ticket: "
				+ "\n3 <Ticket#> <Phone#>"
		);
		
		String buyerInput = scanner.nextLine();
		String[] buyerInputArray = buyerInput.split(" ");
		
		if(buyerInputArray.length != 2 && buyerInputArray.length != 3 && buyerInputArray.length != 4) {
			throw new InvalidArgumentException();
		}
		
		String buyerChoice = buyerInputArray[0];
		
		switch(buyerChoice) {
		case "1":
			int showNumber = Integer.parseInt(buyerInputArray[1]);
			Show show = showService.getShowByShowNumber(showNumber);
			
			if(show != null) {
				System.out.println("\nShow number " + show.getShowNumber() + " available seats: ");
				
				List<Seat> seats = new ArrayList<Seat> (show.getSeats());
				seats.sort(Comparator.comparing(Seat::getRow).thenComparing(Seat::getColumn));
				int previousRow = 0;
				
				for(int i = 0; i < seats.size(); i++) {
					Seat seat = seats.get(i);
					
					if(seat.isOpen()) {
						int row = seat.getRow();
						
						if(row > previousRow) {
							System.out.println();
							previousRow = row;
						} else if(row == previousRow) {
							System.out.print(" ");
						}
						
						System.out.print(seat.getSeatNumber());
					}
				}
				
				System.out.println();
			} else {
				System.out.println("Show doesn't exist");
			}
			break;
		case "2":
			int showNumber2 = Integer.parseInt(buyerInputArray[1]);
			String phoneNumber = buyerInputArray[2];
			String[] seatsArray = buyerInputArray[3].split(",");
			Ticket ticket = ticketService.createTicket(showNumber2, phoneNumber, seatsArray);
			
			if(ticket != null) {
				System.out.println("Ticket booked with details");
				System.out.println("Ticket number: " + ticket.getTicketId());
				System.out.println("Show number: " + ticket.getShow().getShowNumber());
				System.out.println("Phone number: " + ticket.getPhone().getPhoneNumber());
				
				List<Seat> seatsList = new ArrayList<Seat> (ticket.getSeats());
				seatsList.sort(Comparator.comparing(Seat::getRow).thenComparing(Seat::getColumn));
				System.out.print("Seats: ");
				for(int i = 0; i < seatsList.size(); i++) {
					Seat seat = seatsList.get(i);
					System.out.print(seat.getSeatNumber() + " ");
				}
				System.out.println();
			}
			break;
		case "3":
			long ticketId = Long.parseLong(buyerInputArray[1]);
			String phoneNumber3 = buyerInputArray[2];
			
			if(ticketService.cancelTicket(ticketId, phoneNumber3)) {
				System.out.println("Ticket number " + ticketId + " cancelled");
			} else {
				System.out.println("Too late to cancel Ticket number " + ticketId);
			}
			break;
		default:
			throw new InvalidArgumentException();
		}	
	}
	
	public void init() {
		showService.createShow(1, 4, 3, 2);
		showService.createShow(2, 6, 7, 1);
		showService.createShow(3, 12, 4, 3);
		
		String[] seatsArray1 = {"A1", "A2", "A3"};
		ticketService.createTicket(3, "80341563", seatsArray1);
		String[] seatsArray2 = {"B3"};
		ticketService.createTicket(3, "78413481", seatsArray2);
	}
}