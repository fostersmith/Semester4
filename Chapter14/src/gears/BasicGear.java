package gears;

public class BasicGear extends Gear {

	private int teeth;
	private double rotation; // [0.0,1.0)
	
	@Override
	public void applyTravel(double dist) {
		rotation += dist/teeth;
		rotation %= 1d;
	}
	
}
