package util;

public class InsufficientFundsException extends Exception {
	public InsufficientFundsException(String s) {
		super(s);
	}
	public InsufficientFundsException() {
		super();
	}
}
