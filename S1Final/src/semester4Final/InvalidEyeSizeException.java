package semester4Final;

public class InvalidEyeSizeException extends InvalidInputException {

	@Override
	void setMessage() {
		this.message = "Eye size needs to be between 20 & 100";
	}
	
	public InvalidEyeSizeException() {
		setMessage();
	}

}
