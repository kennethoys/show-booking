package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import model.Show;

@SpringBootTest
public class ShowTest {
	
	private final String invalidNumberOfSeats = "Number of rows have to be between 1 to 26 inclusive and seats per row between 1 to 10 inclusive";
	
	@Test
	public void showHappyPath() {
		int showNumber = 1;
		int row = 3;
		int column = 5;
		int cancelWindowMinute = 2;
		Show show = new Show(showNumber, row, column, cancelWindowMinute);
		assertEquals(show.getShowNumber(), showNumber);
		assertEquals(show.getSeats().size(), row * column);
		assertEquals(show.getCancelWindowMinute(), cancelWindowMinute);
	}
	
	@Test
	public void rowSmallerThan1() {
		try {
			int showNumber = 1;
			int row = 0;
			int column = 2;
			int cancelWindowMinute = 2;
			new Show(showNumber, row, column, cancelWindowMinute);
		} catch(Exception e) {
			assertEquals(e.getMessage(), invalidNumberOfSeats);
		}
	}
	
	@Test
	public void rowGreaterThan26() {
		try {
			int showNumber = 1;
			int row = 27;
			int column = 2;
			int cancelWindowMinute = 2;
			new Show(showNumber, row, column, cancelWindowMinute);
		} catch(Exception e) {
			assertEquals(e.getMessage(), invalidNumberOfSeats);
		}
	}
	
	@Test
	public void columnGreaterThan10() {
		try {
			int showNumber = 1;
			int row = 13;
			int column = 11;
			int cancelWindowMinute = 2;
			new Show(showNumber, row, column, cancelWindowMinute);
		} catch(Exception e) {
			assertEquals(e.getMessage(), invalidNumberOfSeats);
		}
	}
	
	@Test
	public void columnSmallerThan1() {
		try {
			int showNumber = 1;
			int row = 13;
			int column = 0;
			int cancelWindowMinute = 2;
			new Show(showNumber, row, column, cancelWindowMinute);
		} catch(Exception e) {
			assertEquals(e.getMessage(), invalidNumberOfSeats);
		}
	}
	
	@Test
	public void showDisplay() {
		int showNumber = 1;
		int row = 3;
		int column = 5;
		int cancelWindowMinute = 2;
		Show show = new Show(showNumber, row, column, cancelWindowMinute);
		assertEquals(show.getShowNumber(), showNumber);
		assertEquals(show.getSeats().size(), row * column);
		assertEquals(show.getCancelWindowMinute(), cancelWindowMinute);
	}
}
