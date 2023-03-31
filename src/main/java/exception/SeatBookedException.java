package exception;

public class SeatBookedException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public SeatBookedException(String errorMessage) {
		super(errorMessage);
	}
}
