package carlysCatering;

public class Event {
	private int entreeChoice, side1Choice, side2Choice, dessertChoice;
	public static final String[] entrees = {"Duck","Steak","Fettucini Alfredo","Şɕæłŀöpʂ"}, 
			sides = {"Chef's Salad","Bread Rolls","Soup"}, 
			desserts = {"Tiramisu","Chocolate Cake","Vanilla Cake","Pudding"}; 

	public int getEntreeChoice() {
		return entreeChoice;
	}
	public void setEntreeChoice(int entreeChoice) {
		this.entreeChoice = entreeChoice;
	}
	public int getSide1Choice() {
		return side1Choice;
	}
	public void setSide1Choice(int side1Choice) {
		this.side1Choice = side1Choice;
	}
	public int getSide2Choice() {
		return side2Choice;
	}
	public void setSide2Choice(int side2Choice) {
		this.side2Choice = side2Choice;
	}
	public int getDessertChoice() {
		return dessertChoice;
	}
	public void setDessertChoice(int dessertChoice) {
		this.dessertChoice = dessertChoice;
	}
	public Event(int entreeChoice, int side1Choice, int side2Choice, int dessertChoice) {
		super();
		this.entreeChoice = entreeChoice;
		this.side1Choice = side1Choice;
		this.side2Choice = side2Choice;
		this.dessertChoice = dessertChoice;
	}

}
