package gears;

import java.util.ArrayList;
import java.util.List;

public abstract class Gear {
	private List<Gear> directConnections = new ArrayList<>();
	
	public List<Gear> getDirectConnections(){
		return directConnections;
	}
	
	public void disconnect(Gear g) {
		directConnections.remove(g);
	}
	
	public void connect(Gear g) {
		directConnections.add(g);
	}
	
	abstract void applyTravel(double dist);
}
