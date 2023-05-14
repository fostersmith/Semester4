/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author Foster
 */
public class Event {
    private static final int PRICE_PER_GUEST = 35;
	
	private int entreeChoice, side1Choice, side2Choice, dessertChoice;
	private int guests;
	public static final String[] entrees = {"Duck","Steak","Fettucini Alfredo","ÅžÉ•Ã¦Å‚Å€Ã¶pÊ‚"}, 
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
	public int getPrice() {
		return PRICE_PER_GUEST * guests;
	}
	public Event(int entreeChoice, int side1Choice, int side2Choice, int dessertChoice, int guests) {
		super();
                if(guests < 0)
                    throw new IllegalArgumentException("Can't have negative guests");
		this.entreeChoice = entreeChoice;
		this.side1Choice = side1Choice;
		this.side2Choice = side2Choice;
		this.dessertChoice = dessertChoice;
		this.guests = guests;
	}
}
