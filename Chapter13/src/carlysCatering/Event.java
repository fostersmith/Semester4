/*
 * Foster Smith
 * Event.java
 * 11/12/21 
 * Case Problems #1
 */
package carlysCatering;

//REUSED

public class Event {
	
	public final static String[] EVENT_TYPES = { "Wedding", "Baptism", "Birthday","Corporate", "Other" };
	
	public final static int PRICE_PER_GUEST = 35;
	
	public final static int LARGE_EVENT_CUTOFF = 50;
	
	private int eventType;
	
	private String eventID;

	private int attendees;

	private int price;

	private String phoneNumber;
	
	public void setEventNumber(String newID) {
		boolean validNum = Character.isLetter(newID.charAt(0)) && newID.length() == 4;
		for(int i = 1; i < newID.length()&&validNum; ++i) {
			validNum = Character.isDigit(newID.charAt(i));
		}
		if(!validNum) {
			newID = "A000";
		}
		if(Character.isLowerCase(newID.charAt(0))){
			newID = Character.toUpperCase(newID.charAt(0)) + newID.substring(1);
		}
		eventID = newID;
	}

	public void setGuests(int newAttendees) {

		attendees = newAttendees;

		price = attendees * PRICE_PER_GUEST;

	}

	public void setPhoneNumber(String newNum) {
		StringBuilder newPhone = new StringBuilder();
		for(int i = 0; i < newNum.length();++i) {
			if(Character.isDigit(newNum.charAt(i)))
				newPhone.append(Character.toString(newNum.charAt(i)));
		}
		phoneNumber = newPhone.toString();
	}
	
	public void setEventType( int eventType ) {
		
		if( eventType < EVENT_TYPES.length )
			this.eventType = eventType;
		else
			this.eventType = EVENT_TYPES.length - 1;
		
	}
	
	public String getEventNumber() {

		return(eventID);

	}

	public int getGuests() {

		return(attendees);

	}

	public int getPrice() {

		return(price);

	}
	
	public String getPhoneNumber() {
		StringBuilder phoneNumBuilder = new StringBuilder("() -");
		for(int i = 1; i <= 3;++i) {
			phoneNumBuilder.insert(i, phoneNumber.charAt(i-1));
		}
		for(int i = 6; i <= 8; ++i) {
			phoneNumBuilder.insert(i, phoneNumber.charAt(i-3));
		}
		for(int i = 10; i <= 13; ++i) {
			phoneNumBuilder.insert(i, phoneNumber.charAt(i-4));
		}
		return phoneNumBuilder.toString();
	}
	
	public String getEventType() {
		return EVENT_TYPES[eventType];
	}
	
	public Event() {

		this("A000", 0,"0000000000");
	}

	public Event(String eventNum, int guestNum, String phoneNumber) {
		setEventNumber(eventNum);
		setGuests(guestNum);
		setPhoneNumber(phoneNumber);
	}


	

}