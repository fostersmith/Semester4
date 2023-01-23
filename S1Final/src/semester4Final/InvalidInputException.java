package semester4Final;

public abstract class InvalidInputException extends Exception {
	protected String message;

	public String getMessage() {
		return message;
	}

	abstract void setMessage();
	
	public InvalidInputException() {
		message = "You did not enter valid input";
	}
}
