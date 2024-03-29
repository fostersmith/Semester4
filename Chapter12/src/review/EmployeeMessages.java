package review;

public class EmployeeMessages {
	private static final String[] messages = {
			"The employee number is not numeric",
			"The employee number is less than 1000",
			"The employee number is more than 9999",
			"The hourly pay rate is not numeric",
			"The hourly pay rate is less than $9.00",
			"The hourly pay rate is more than 25.00"
	};
	
	public static final String NONNUMERIC_EMPNUM = messages[0];
	public static final String LOW_EMPNUM = messages[1];
	public static final String HIGH_EMPNUM = messages[2];
	public static final String NONNUMERIC_PAY = messages[3];
	public static final String LOW_PAY = messages[4];
	public static final String HIGH_PAY = messages[5];
}
