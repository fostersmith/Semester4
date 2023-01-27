package gears;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
		compile(1);
	}
	
	public GearBox(Gear g) {
		gears.add(g);
		rotateGear = g;
		rotateGear.setRenderMode(Gear.CLOCKWISE_MODE);
		compile(1);
	}
	
	public void compile(int gEvenOdd) {
		evenOdd = new HashMap<>();
		Gear root = rotateGear;
		((BasicGear)root).setRotation(0);
		evenOdd.put(root, gEvenOdd);
		setChildrenEvenOdd(root);
		
		//BasicGear.adjustGearRotation((BasicGear)root, (BasicGear)root.getDirectConnections().get(0));
		List<Gear> throwaway = new ArrayList<>();
		throwaway.add(root);
		adjustChildrenRotation(root, throwaway);
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
	
	private void adjustChildrenRotation(Gear g, List<Gear> rotated) {
		for(Gear child : g.getDirectConnections()) {
			if(!rotated.contains(child)) {
				double[] rotations = BasicGear.adjustGearRotation((BasicGear)g, (BasicGear)child);
				//((BasicGear) g).rotate(rotations[0]);
				((BasicGear)child).rotate(rotations[1]);
				double dist = ((BasicGear)g).getTravelForRotation(rotations[0]);
				//g.applyTravel(dist);
				int gEvenOdd = evenOdd.get(g);
				for(Gear rotate : rotated) {
					rotate.applyTravel(dist* evenOdd.get(rotate) * gEvenOdd);
				}
				rotated.add(child);
				adjustChildrenRotation(child, rotated);
			}
		}		
	}

	public int getEvenOdd(Gear g) {
		return evenOdd.get(g);
	}
	
	public void setRotateGear(Gear g, int gEvenOdd) {
		if(!gears.contains(g)) {
			throw new IllegalArgumentException("Received an argument for {g} which was not part of the GearBox");
		}
		for(Gear gear : gears) {
			gear.setRenderMode(Gear.NORMAL_MODE);
		}
		rotateGear = g;
		g.setRenderMode(gEvenOdd == 1 ? Gear.CLOCKWISE_MODE : Gear.COUNTERCLOCKWISE_MODE);
		if(evenOdd.get(g) != gEvenOdd) {
			compile(gEvenOdd);
		}
	}
	
	
	public List<Gear> getGears(){
		return gears;
	}
	
	public void addGear(Gear g) {
		gears.add(g);
		g.setRenderMode(Gear.NORMAL_MODE);
		compile(1);
	}
	
	public void applyMovementToGear(Gear g, double dist) {
		if(!gears.contains(g))
			throw new IllegalArgumentException();
		int gEvenOdd = evenOdd.get(g);
		for(Gear ge : gears) {
			ge.applyTravel(dist * evenOdd.get(ge));
		}
	}
	
	public void rotateGear(Gear g, double theta) {
		double dist = ((BasicGear)g).getTravelForRotation(theta);
		applyMovementToGear(g, dist);
	}
}
