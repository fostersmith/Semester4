package gears;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GearBox {
	private List<Gear> gears = new ArrayList<>();
	private Map<Gear, Integer> evenOdd;
	public Gear rotateGear;
	
	public GearBox(List<Gear> gears) {
		this.gears.addAll(gears);
		for(Gear gear : gears) {
			gear.setRenderMode(Gear.NORMAL_MODE);
		}
		rotateGear = gears.get(0);
		rotateGear.setRenderMode(Gear.CLOCKWISE_MODE);
		compile();
	}
	
	public GearBox(Gear g) {
		gears.add(g);
		rotateGear = g;
		rotateGear.setRenderMode(Gear.CLOCKWISE_MODE);
		compile();
	}
	
	public void compile() {
		evenOdd = new HashMap<>();
		Gear root = rotateGear;
		evenOdd.put(root, 1);
		setChildrenEvenOdd(root);
	}
	
	public void setRotateGear(Gear g) {
		if(!gears.contains(g)) {
			throw new IllegalArgumentException("Received an argument for {g} which was not part of the GearBox");
		}
		for(Gear gear : gears) {
			gear.setRenderMode(Gear.NORMAL_MODE);
		}
		rotateGear = g;
		g.setRenderMode(Gear.CLOCKWISE_MODE);
		if(evenOdd.get(g) != 1) {
			compile();
		}
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
		g.setRenderMode(Gear.NORMAL_MODE);
		compile();
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
