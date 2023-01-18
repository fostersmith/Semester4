package boids;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Boids extends JFrame {

	public static final double NEIGHBOUR_DIST = 0.1d;
	public static final double WIDTH = 1d, HEIGHT = 1d;
	
	// storing all of the boids in a dynamic array is far from ideal. however, 
	// i couldn't find a better data structure for searching by position which 
	// can also be updated regularly
	List<Boid> boids = new ArrayList<>();
	
	public Boids() {
		// TODO Auto-generated constructor stub
	}
	
	public void update() {
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
