package exception;

public class InvalidNumberOfSeatsException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public InvalidNumberOfSeatsException(String errorMessage) {
		super(errorMessage);
	}
}
