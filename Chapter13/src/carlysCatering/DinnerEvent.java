/*
 * Foster Smith
 * 03/02/22
 * DinnerEvent.java
 * case problems 1a
 */
package carlysCatering;

public class DinnerEvent extends Event {
	protected int entreeChoice, side1Choice, side2Choice, dessertChoice;
	protected static final String[] entrees = {"Duck","Steak","Fettucini Alfredo","Şɕæłŀöpʂ"}, 
			sides = {"Chef's Salad","Bread Rolls","Soup"}, 
			desserts = {"Tiramisu","Chocolate Cake","Vanilla Cake","Pudding"}; 
	protected Employee[] staff;
	
	public DinnerEvent(String eventNum, int guestNum, String phoneNum,int entreeChoice, int side1Choice, int side2Choie, int dessertChoice) {
		super(eventNum,guestNum,phoneNum);
		this.entreeChoice = entreeChoice;
		this.side1Choice = side2Choice;
		this.dessertChoice = dessertChoice;
	}
	
	public static String getMenu() {
		StringBuilder menu = new StringBuilder();
		
		menu.append("Welcome to the restaurant\n\n");
		menu.append("Entrees:");
		for(int i = 0; i < entrees.length; ++i) {
			menu.append("\n\t"+(i+1)+". "+entrees[i]);
		}
		
		menu.append("\nSides:");
		for(int i = 0; i < sides.length; ++i) {
			menu.append("\n\t"+(i+1)+". "+sides[i]);
		}
		
		menu.append("\nDesserts:");
		for(int i = 0; i < desserts.length; ++i) {
			menu.append("\n\t"+(i+1)+". "+desserts[i]);
		}		
		
		return menu.toString();
	}
	
	public Employee[] getStaff() {
		return staff;
	}

	public void setStaff(Employee[] staff) {
		this.staff = staff;
	}
	
}
