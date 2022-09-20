/*
 * Foster Smith
 * 17/03/22
 * Waitstaff.java
 */
package carlysCatering;

public class Bartender extends Employee {

	public Bartender(int iD, String fName, String lName) {
		super(iD, fName, lName);
	}

	@Override
	public void setPay(int pay) {
		this.pay = (pay>14)?14:pay;
	}

	@Override
	public void setTitle() {
		title = "bartender";
	}

}
