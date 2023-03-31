package exception;

public class InvalidPhoneNumberException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public InvalidPhoneNumberException(String errorMessage) {
		super(errorMessage);
	}
}
