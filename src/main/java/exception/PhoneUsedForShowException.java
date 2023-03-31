package exception;

public class PhoneUsedForShowException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public PhoneUsedForShowException(String errorMessage) {
		super(errorMessage);
	}
}
