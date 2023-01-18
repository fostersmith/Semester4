package gears;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public abstract class Gear {
	public static final int TOOTHWIDTH = 10;
	public static final int TOOTHHEIGHT = 5;
	
	private List<Gear> directConnections = new ArrayList<>();
	protected int x, y;
	
	public List<Gear> getDirectConnections(){
		return directConnections;
	}
	
	public void disconnect(Gear g) {
		directConnections.remove(g);
	}
	
	public void connect(Gear g) {
		directConnections.add(g);
	}
	
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	abstract void applyTravel(double dist);
	abstract void drawGear(Graphics g);
}
