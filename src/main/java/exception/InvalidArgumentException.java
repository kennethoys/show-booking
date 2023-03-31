package exception;

public class InvalidArgumentException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public InvalidArgumentException() {
		super("Wrong input or wrong number of arguments");
	}
	
	public InvalidArgumentException(String errorMessage) {
		super(errorMessage);
	}
}
