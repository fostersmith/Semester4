/*
 * Foster Smith
 * 17/03/22
 * Waitstaff.java
 */
package carlysCatering;

public class Coordinator extends Employee {

	public Coordinator(int iD, String fName, String lName) {
		super(iD, fName, lName);
	}

	@Override
	public void setPay(int pay) {
		this.pay = (pay>20)?20:pay;
	}

	@Override
	public void setTitle() {
		title = "coordinator";
	}

}
