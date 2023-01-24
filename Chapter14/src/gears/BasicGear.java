package gears;

import java.awt.Color;
import java.awt.Graphics;

public class BasicGear extends Gear {

	private int teeth;
	private double rotation; // [0.0,1.0)
	private Color highlightColor = Color.GREEN;
	private Color unhighlightColor = Color.RED;
	
	public BasicGear(int x, int y, int teeth) {
		this.x = x;
		this.y = y;
		this.teeth = teeth;
	}
	
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}
	
	public void rotate(double amount) {
		rotation += amount;
		rotation %= 1d;
	}
	
	@Override
	public void applyTravel(double dist) {
		rotation += dist/teeth;
		rotation %= 1d;
	}

	@Override
	void drawGear(Graphics g) {
		g.setColor(color);
		double circumference = teeth*(double)TOOTHWIDTH*2;
		int diameter = (int)(circumference/Math.PI);
		
		
		g.fillOval(x - diameter/2, y - diameter/2, diameter, diameter);
		//TODO add teeth
		g.setColor(highlighted ? highlightColor : unhighlightColor);
		g.drawLine(x-5, y, x+5, y);
		g.drawLine(x, y-5, x, y+5);
		for(int i = 0; i < teeth; ++i) {
			double theta = Math.PI*2*i/teeth + rotation * 2 * Math.PI;
			int d1 = diameter/2, d2 = diameter/2 + TOOTHHEIGHT;
			
			g.drawLine(x + (int)(Math.sin(theta)*d1), y - (int)(Math.cos(theta)*d1), 
					   x + (int)(Math.sin(theta)*d2), y - (int)(Math.cos(theta)*d2));
		}
		int d1 = diameter/2 ,d2 = diameter/2+TOOTHHEIGHT;
		int deltaY = (int) (diameter/2 - Math.sqrt(Math.pow(TOOTHWIDTH/2d, 2) + Math.pow(diameter/2, 2)));
		double theta = rotation * Math.PI*2;
		int[] basePointX = new int[] {x-TOOTHWIDTH/2, x+TOOTHWIDTH/2, x+TOOTHWIDTH/2, x-TOOTHWIDTH/2};
		int[] basePointY = new int[] {y-d1+deltaY, y-d1+deltaY, y-d2+deltaY, y-d2+deltaY};
		g.drawPolygon(basePointX, basePointY, 4);
	}
	
	@Override
	boolean isConnected(Gear g) {
		if(g instanceof BasicGear) {
			BasicGear bg = (BasicGear)g;
			double radius = teeth*(double)(TOOTHWIDTH*2) / (2*Math.PI);
			double gRadius = bg.teeth*(double)(TOOTHWIDTH*2) / (2*Math.PI);
			double dist = Math.sqrt(Math.pow(x - g.x, 2) + Math.pow(y - g.y, 2));
			return dist <= radius + gRadius + 2*TOOTHHEIGHT;
		} else
			return false;
	}
	
}
