package semester4Final;

public class InvalidFaceSizeException extends InvalidInputException {

	public InvalidFaceSizeException() {
		setMessage();
	}

	@Override
	void setMessage() {
		this.message = "Face size needs to be between 200 & 500";
	}

}
