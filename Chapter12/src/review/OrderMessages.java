/*
 * Foster Smith
 * 09.02.22
 * OrderMessages.java
 * CH13 #12
 */

package review;

public class OrderMessages {
	public static final String[] ORDER_MESSAGES = {"The item number ordered is not numeric, too low (less than 0), or too high (more\r\n" + 
			"than 9999).", 
			"The quantity is not numeric, too low (less than 1), or too high (more than 12).",
			"The item number is not a currently valid item"};
	public static final int BAD_ITEM_NUM = 0;
	public static final int BAD_QUANTITY = 1;
	public static final int INVALID_ITEM = 2;
}
