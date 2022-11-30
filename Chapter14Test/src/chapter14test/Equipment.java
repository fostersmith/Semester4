package chapter14test;

public class Equipment {
	private String item, description;
	private double cost;
	
	public String getItem() {
		return item;
	}
	public String getDescription() {
		return description;
	}
	public double getCost() {
		return cost;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public Equipment(String item, String description, double cost) {
		super();
		this.item = item;
		this.description = description;
		this.cost = cost;
	}
}
