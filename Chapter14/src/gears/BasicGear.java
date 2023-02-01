package gears;

import java.awt.Color;
import java.awt.Graphics;

public class BasicGear extends Gear {

	private int teeth;
	private double rotation; // [0.0,2/pi)
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
		rotation %= 2*Math.PI;
	}
	
	private double nthAngle(int n) {
		return (2*Math.PI*n)/teeth+rotation*Math.PI*2;
	}
	
	public static double vectorAngle(double deltaY, double deltaX) {
		return deltaX == 0 ? (deltaY > 0 ? Math.PI/2 : -Math.PI/2) : Math.atan(deltaY / deltaX);
	}
	
	public static double[] adjustGearRotation(BasicGear fixed, BasicGear move) {
		double theta = Math.atan2((move.y - fixed.y) , (move.x - fixed.x));//(vectorAngle(fixed.y - move.y, fixed.x - move.x) + Math.PI*2) % (Math.PI*2);
		//return new double[] {fixed.rotation - theta, move.rotation-(theta+Math.PI/move.teeth)};
		return new double[] {theta - fixed.rotation, (theta+Math.PI + Math.PI/move.teeth) - move.rotation};
	}
	
	public double getTravelForRotation(double rotation) {
		double radius = teeth/(2*Math.PI);
		return rotation*radius;
	}

	
	@Override
	public void applyTravel(double dist) {
		rotation += 2*Math.PI*dist/teeth;
		rotation %= 2*Math.PI;
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
		g.setColor(color);
		
		double[] baseY = { -TOOTHWIDTH/2d, TOOTHWIDTH/2d, TOOTHWIDTH/2d, -TOOTHWIDTH/2d};
		double radius = diameter/2;
		double yMod = Math.sqrt(Math.pow(radius, 2) - Math.pow(TOOTHWIDTH, 2)/4d);
		double[] baseX = {yMod-1, yMod-1, yMod + TOOTHHEIGHT -1, yMod + TOOTHHEIGHT -1};
		
		for(int i = 0; i < teeth; ++i) {
			
			double theta = Math.PI*2*i/teeth + rotation;
			int d1 = diameter/2, d2 = diameter/2 + TOOTHHEIGHT;
			
			int[] xs = new int[4];
			int[] ys = new int[4];
			
			for(int a = 0; a < xs.length; ++a) {
				xs[a] = (int) (baseX[a]*Math.cos(theta) - baseY[a]*Math.sin(theta) + x);
				ys[a] = (int) (baseX[a]*Math.sin(theta) + baseY[a]*Math.cos(theta) + y);
			}
			
			g.fillPolygon(xs, ys, 4);
			
			//g.drawLine(x + (int)(Math.cos(theta)*d1), y + (int)(Math.sin(theta)*d1), 
			//		   x + (int)(Math.cos(theta)*d2), y + (int)(Math.sin(theta)*d2));
		}
		/*int d1 = diameter/2 ,d2 = diameter/2+TOOTHHEIGHT;
		int deltaY = (int) (diameter/2 - Math.sqrt(Math.pow(TOOTHWIDTH/2d, 2) + Math.pow(diameter/2, 2)));
		double theta = rotation;
		int[] basePointX = new int[] {x-TOOTHWIDTH/2, x+TOOTHWIDTH/2, x+TOOTHWIDTH/2, x-TOOTHWIDTH/2};
		int[] basePointY = new int[] {y-d1+deltaY, y-d1+deltaY, y-d2+deltaY, y-d2+deltaY};
		g.drawPolygon(basePointX, basePointY, 4);*/
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
