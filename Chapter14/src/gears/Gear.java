package gears;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Gear {
	public static final int NORMAL_MODE = 0, CLOCKWISE_MODE = 1, COUNTERCLOCKWISE_MODE = 2;
	private static final Color[] MODE_COLORS = new Color[] {Color.GRAY, Color.PINK, Color.RED};
	
	public static final int TOOTHWIDTH = 10;
	public static final int TOOTHHEIGHT = 5;
	protected boolean highlighted = false;
	
	protected Color color;
	private int renderMode = NORMAL_MODE;
	
	
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
	
	public void disconnectAll() {
		directConnections.clear();
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
	
	public void setRenderMode(int mode) {
		if(mode < 0 || mode >= MODE_COLORS.length) {
			throw new IllegalArgumentException("Received a non-existent render mode");
		}
		renderMode = mode;
		color = MODE_COLORS[mode];
	}

	public void highlight() {highlighted = true;};
	public void unhighlight() {highlighted = false;};

	abstract void applyTravel(double dist);
	abstract void drawGear(Graphics g);
	abstract boolean isConnected(Gear g);
}
