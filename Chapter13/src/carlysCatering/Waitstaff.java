/*
 * Foster Smith
 * 17/03/22
 * Waitstaff.java
 */
package carlysCatering;

public class Waitstaff extends Employee {

	public Waitstaff(int iD, String fName, String lName) {
		super(iD, fName, lName);
	}

	@Override
	public void setPay(int pay) {
		this.pay = (pay>10)?10:pay;
	}

	@Override
	public void setTitle() {
		title = "waitstaff";
	}

}
