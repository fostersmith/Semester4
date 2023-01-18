package boids;

public class Boid {

	private double x, y;
	private double v_x, v_y;
	
	public Boid(double x, double y, double v_x, double v_y) {
		this.x = x;
		this.y = y;
		this.v_x = v_x;
		this.v_y = v_y;
	}
	
	public Boid(double x, double y) {
		this(x,y,0d,0d);
	}
	
	public Boid() {
		this(0d,0d,0d,0d);
	}
	
	public double x() {
		return x;
	}
	public double y() {
		return y;
	}
	public double v_x() {
		return v_x;
	}
	public double v_y() {
		return v_y;
	}
	public double theta() {
		return v_x==0d&&v_y==0d?0d:Math.atan2(v_y, v_x);
	}

}
