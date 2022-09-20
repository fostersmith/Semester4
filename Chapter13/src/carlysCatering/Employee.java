/*
 * Foster Smith
 * 17/03/22
 * Employee.java
 */
package carlysCatering;

public abstract class Employee {
	private int ID;
	private String fName, lName;
	protected int pay;
	protected String title;
	
	public Employee(int iD, String fName, String lName) {
		ID = iD;
		this.fName = fName;
		this.lName = lName;
		setPay(pay);
		setTitle();
	}
	
	//getters
	public int getID() {
		return ID;
	}
	public String getfName() {
		return fName;
	}
	public String getlName() {
		return lName;
	}
	public int getPay() {
		return pay;
	}
	public String getTitle() {
		return title;
	}
	//setters
	public void setID(int iD) {
		ID = iD;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public abstract void setPay(int pay);
	public abstract void setTitle();
	
	public String toString() {
		return lName+", "+fName+"; Title: "+title+"; Pay: "+pay+"; ID: "+ID;
	}
}
