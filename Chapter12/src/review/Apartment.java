/*
 * Foster Smith
 * 09.02.22
 * Apartment.java
 * CH13 #6
 */

package review;

public class Apartment {

	private String streetAddr, apptmtNum;
	private float rent;
	private int bedrooms;
	
	public Apartment(String streetAddr, String apptmtNum, int bedrooms, float rent) throws ApartmentException {
		if(apptmtNum.length() != 3 || bedrooms < 1 || bedrooms > 4 || rent < 500f || rent > 2500f)
			throw new ApartmentException("\nAddress: " + streetAddr +
					"\nApartment #: "+apptmtNum +
					"\nBedrooms: "+bedrooms+
					"\nRent: "+rent+"\n");
		this.streetAddr = streetAddr;
		this.apptmtNum = apptmtNum;
		this.bedrooms = bedrooms;
		this.rent = rent;
	}
	
}
