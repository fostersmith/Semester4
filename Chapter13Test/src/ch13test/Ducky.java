package ch13test;

public class Ducky {
	private String ID, name, color, smack;

	public Ducky(String iD, String name, String color, String smack) {
		ID = iD;
		this.name = name;
		this.color = color;
		this.smack = smack;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSmack() {
		return smack;
	}

	public void setSmack(String smack) {
		this.smack = smack;
	}
	
	public String getDisplayString() {
		return "ID #"+ID
				+ "\nName: "+name
				+ "\nColor: "+color
				+ "\nSmack Of: "+smack;
	}
	
	@Override
	public String toString() {
		return ID+DuckDynasty.DELIM+name+DuckDynasty.DELIM+color+DuckDynasty.DELIM+smack;
	}
}
