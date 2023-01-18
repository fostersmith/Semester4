package gears;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GearBox {
	private List<Gear> gears = new ArrayList<>();
	private Map<Gear, Integer> evenOdd;
	
	public GearBox(List<Gear> gears) {
		this.gears.addAll(gears);
		compile();
	}
	
	public void compile() {
		evenOdd = new HashMap<>();
		Gear root = gears.get(0);
		evenOdd.put(root, 1);
		setChildrenEvenOdd(root);
	}
	
	private void setChildrenEvenOdd(Gear g) {
		int gEvenOdd = evenOdd.get(g);
		for(Gear child : g.getDirectConnections()) {
			if(!evenOdd.containsKey(child)) {
				evenOdd.put(child, -1 * gEvenOdd);
				setChildrenEvenOdd(child);
			}
		}
	}
	
	public List<Gear> getGears(){
		return gears;
	}
	
	public void addGear(Gear g) {
		gears.add(g);
	}
	
	public void applyMovementToGear(Gear g, double dist) {
		if(!gears.contains(g))
			throw new IllegalArgumentException();
		int gEvenOdd = evenOdd.get(g);
		for(Gear ge : gears) {
			ge.applyTravel(dist * evenOdd.get(ge) * gEvenOdd);
		}
	}
}
