/*
 * Foster Smith
 * 09.02.22
 * EmployeeDataEntry.java
 * CH13 #11
 */

package review;

import java.util.Scanner;

public class EmployeeDataEntry {

	public static void main(String[] args) {
		
		final String ESCAPE = "EXIT";
		
		System.out.println("Enter employee data, or '"+ESCAPE+"' to quit");
		
		String entry;
		Scanner in = new Scanner(System.in);
		
		while(true) {
			
			try {
			
				int employeeNum;
				float payRate;
				
				System.out.print("Enter an Employee Number >> ");
				entry = in.nextLine();
				if(entry.toUpperCase().contains(ESCAPE))
					break;
				try {
					employeeNum = Integer.parseInt(entry);
					if(employeeNum < 1000) {
						throw new EmployeeException(EmployeeMessages.LOW_EMPNUM);
					} else if (employeeNum > 9999) {
						throw new EmployeeException(EmployeeMessages.HIGH_EMPNUM);
					}
				} catch(NumberFormatException e) {
					throw new EmployeeException(EmployeeMessages.NONNUMERIC_EMPNUM);
				}
				
				System.out.print("Enter an Hourly Pay Rate >> ");
				entry = in.nextLine();
				if(entry.toUpperCase().contains(ESCAPE))
					break;
				try {
					payRate = (float)Double.parseDouble(entry);
					if(payRate < 9f) {
						throw new EmployeeException(EmployeeMessages.LOW_PAY);
					} else if (payRate > 25f) {
						throw new EmployeeException(EmployeeMessages.HIGH_PAY);
					}
				} catch(NumberFormatException e) {
					throw new EmployeeException(EmployeeMessages.NONNUMERIC_PAY);
				}
				
				System.out.println("Valid employee data");
			} catch(EmployeeException e) {
				System.out.println(e.getMessage());
				break;
			}
		}
		
		in.close();
	}
	
}
