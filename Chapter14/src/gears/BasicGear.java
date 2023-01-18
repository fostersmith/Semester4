package gears;

import java.awt.Color;
import java.awt.Graphics;

public class BasicGear extends Gear {

	private int teeth;
	private double rotation; // [0.0,1.0)
	
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
		g.setColor(Color.GRAY);
		double circumference = teeth*(double)TOOTHWIDTH*2;
		int diameter = (int)(circumference/Math.PI);
		
		
		g.fillOval(x - diameter/2, y - diameter/2, diameter, diameter);
		//TODO add teeth
		g.setColor(Color.red);
		g.drawLine(x-5, y, x+5, y);
		g.drawLine(x, y-5, x, y+5);
		for(int i = 0; i < teeth; ++i) {
			double theta = Math.PI*2*i/teeth + rotation * 2 * Math.PI;
			int d1 = diameter/2, d2 = diameter/2 + TOOTHHEIGHT;
			
			g.drawLine(x + (int)(Math.sin(theta)*d1), y - (int)(Math.cos(theta)*d1), 
					   x + (int)(Math.sin(theta)*d2), y - (int)(Math.cos(theta)*d2));
		}
	}
	
}
