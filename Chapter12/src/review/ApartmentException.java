/*
 * Foster Smith
 * 09.02.22
 * ApartmentException.java
 * CH13 #6
 */

package review;

public class ApartmentException extends Exception {

	public ApartmentException(String message) {
		super("Unable to construct apartment with following fields: " + message);
	}
	
}
