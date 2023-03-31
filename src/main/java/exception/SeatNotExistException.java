package exception;

public class SeatNotExistException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public SeatNotExistException(String errorMessage) {
		super(errorMessage);
	}
}
