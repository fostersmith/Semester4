package duckJousting;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {

	private Socket socket;
	private ServerSocket server;
	private DataInputStream in1;
	private ObjectOutputStream out1;

	final static int GAMEPLAY = 0, P1_WIN = 1, P2_WIN = 2, DRAW = 3;
	
	final static int REMATCH = 4, QUIT = 5;
	
	final static double ACCELERATION_CONSTANT = 600;
	final static double JUMP_CONSTANT = 600;
	final static double GRAVITY_ACCELERATION = 2000;
	final static Color P1_C = Color.GREEN, P2_C = Color.CYAN;
	
	final static int RIGHT_BOUND = 1500;
	final static int RAINBOW_LEN = 10;
	
	
	final static int FLOATY_WIDTH = 46*3, FLOATY_HEIGHT = 28*3;
	final static int LANCE_LEN = 41*3, LANCE_HEIGHT = 7*3, LANCE_ELEVATION = 8*3;
	final static int DUCK_HEIGHT = 20*3, DUCK_WIDTH = 19*3;
	final static int FLOATY_RELATIVE_X = 2*3, FLOATY_RELATIVE_Y = 3*3;
	
	boolean wPressed, aPressed, dPressed, upPressed, leftPressed, rightPressed;
	
	// game state
	double p1_x, p1_y, p1_a_x, p1_v_x, p1_v_y;
	
	double p2_x, p2_y, p2_a_x, p2_v_x, p2_v_y;

	double p1_falling_x, p1_falling_y, p1_falling_v_x, p1_falling_v_y;
	
	double p2_falling_x, p2_falling_y, p2_falling_v_x, p2_falling_v_y;
	
	int state = GAMEPLAY;
	int rainbowCtr = 0;
	Point[] rainbow1, rainbow2;
	
	public void writeState() throws IOException {
		// 5 - player 1 vars (x, y, a_x, v_x, v_y)
		// 5 - player 2 vars (x, y, a_x, v_x, v_y)
		// 4 - player 1 falling vars (x, y, v_x, v_y)
		// 4 - player 2 falling vars (x, y, v_x, v_y)
		// 1 - state
		// 1 - rainbowCtr
		// 4*rainbow1.length - rainbow points [(x,y)_1 ,(x,y)_2]
		
		double[] stateArray = new double[10 + 8 + 2 + 4*RAINBOW_LEN];
		stateArray[0] = p1_x;
		stateArray[1] = p1_y;
		stateArray[2] = p1_a_x;
		stateArray[3] = p1_v_x;
		stateArray[4] = p1_v_y;
		
		stateArray[5] = p2_x;
		stateArray[6] = p2_y;
		stateArray[7] = p2_a_x;
		stateArray[8] = p2_v_x;
		stateArray[9] = p2_v_y;
		
		stateArray[10] = p1_falling_x;
		stateArray[11] = p1_falling_y;
		stateArray[12] = p1_falling_v_x;
		stateArray[13] = p1_falling_v_y;

		stateArray[14] = p2_falling_x;
		stateArray[15] = p2_falling_y;
		stateArray[16] = p2_falling_v_x;
		stateArray[17] = p2_falling_v_y;
		
		stateArray[18] = state;
		
		stateArray[19] = rainbowCtr;
		
		for(int i = 0; i < RAINBOW_LEN; ++i) {
			stateArray[19+i*4] = rainbow1[i].x;
			stateArray[20+i*4] = rainbow1[i].y;
			stateArray[21+i*4] = rainbow2[i].x;
			stateArray[22+i*4] = rainbow2[i].y;
		}
		out1.writeObject(stateArray);
	}
	
	public void reset() {
		p1_x = 75.0;
		p1_y = -200.0;
		p1_a_x = 0.0; p1_v_x = 0.0; p1_v_y = 0.0;
		p2_x = RIGHT_BOUND-50.0; p2_y = -200.0;
		p2_a_x = 0.0; p2_v_x = 0.0; p2_v_y = 0.0;
		state = GAMEPLAY;
		
		rainbow1 = new Point[RAINBOW_LEN];
		for(int i = 0; i < rainbow1.length; ++i) {
			rainbow1[i] = new Point();
			rainbow1[i].x = (int) p1_x;
			rainbow1[i].y = (int) p1_y;
		}

		rainbow2 = new Point[RAINBOW_LEN];
		for(int i = 0; i < rainbow2.length; ++i) {
			rainbow2[i] = new Point();
			rainbow2[i].x = (int) p2_x;
			rainbow2[i].y = (int) p2_y;
		}
	}
	
	public static double magnitude(double x,double y) {
		return Math.sqrt(Math.pow(x,2)+Math.pow(y, 2));
	}

	public void update(long deltaTimeNs) {
		p2_a_x = 0;
		p1_a_x = 0;
		if(leftPressed)
			p2_a_x = -ACCELERATION_CONSTANT;
		if(rightPressed)
			p2_a_x = ACCELERATION_CONSTANT;
		if(upPressed)
			if(p2_y == 0d) {
				p2_v_y = -JUMP_CONSTANT;
			}
		
		if(aPressed) 
			p1_a_x = -ACCELERATION_CONSTANT;
		if(dPressed)
			p1_a_x = ACCELERATION_CONSTANT;
		if(wPressed)
			if(p1_y == 0d) {
				p1_v_y = -JUMP_CONSTANT;
			}

		double deltaTimeS = deltaTimeNs/1E9;
		p1_v_x += Math.signum(p1_a_x)==Math.signum(p1_v_x) ? deltaTimeS*p1_a_x : deltaTimeS*p1_a_x*2;
		p2_v_x += Math.signum(p2_a_x)==Math.signum(p2_v_x) ? deltaTimeS*p2_a_x : deltaTimeS*p2_a_x*2;
		p1_x += p1_v_x*deltaTimeS;
		p2_x += p2_v_x*deltaTimeS;
		
		p1_v_y += deltaTimeS*GRAVITY_ACCELERATION;
		p2_v_y += deltaTimeS*GRAVITY_ACCELERATION;
		p1_y += deltaTimeS*p1_v_y;
		p2_y += deltaTimeS*p2_v_y;
		
		if(p1_y >0) {
			p1_y = 0;
			p1_v_y = 0;
		} 
		if(p2_y > 0) {
			p2_y = 0;
			p2_v_y = 0;
		}
		
		if(p1_x < 20) {
			p1_x = 20;
			p1_v_x = 0;
			p1_a_x = 0;
		} else if(p1_x > RIGHT_BOUND) {
			p1_x = RIGHT_BOUND;
			p1_v_x = 0;
			p1_a_x = 0;
		}
		if(p2_x < 20) {
			p2_x = 20;
			p2_v_x = 0;
			p2_a_x = 0;
		} else if(p2_x > RIGHT_BOUND) {
			p2_x = RIGHT_BOUND;
			p2_v_x = 0;
			p2_a_x = 0;
		}
		
		
		//check for collisions
		double p1_lance_x = p1_v_x < 0 ? p1_x-LANCE_LEN : p1_x+LANCE_LEN,  p2_lance_x = p2_v_x>0 ? p2_x+LANCE_LEN : p2_x-LANCE_LEN;
		//System.out.println("P1 X: "+p1_x);
		//System.out.println("P1 Lance X: "+p1_lance_x);
		double p1_lance_y = p1_y, p2_lance_y = p2_y;
		//System.out.println("P1 Y: "+p1_y);
		//System.out.println("P1 Lance Y: "+p1_lance_y);
		
		boolean p1_hit = false, p2_hit = false;
		
		if(p1_lance_x >= p2_x-DUCK_WIDTH/2 && p1_lance_x <= p2_x+DUCK_WIDTH/2 && p1_lance_y <= p2_y+DUCK_HEIGHT/2 && p1_lance_y >= p2_y-DUCK_HEIGHT/2 && (p1_v_x != 0 || p1_v_y != 0)) {
			p1_hit = true;
		}
		if(p2_lance_x >= p1_x-DUCK_WIDTH/2 && p2_lance_x <= p1_x+DUCK_WIDTH/2 && p2_lance_y <= p1_y+DUCK_HEIGHT/2 && p2_lance_y >= p1_y-DUCK_HEIGHT/2 && (p2_v_x != 0 || p2_v_y != 0)) {
			p2_hit = true;
		}
		
		if(p1_hit && p2_hit) {
			System.out.println(magnitude(p1_v_x, p1_v_y) - magnitude(p2_v_x, p2_v_y));
			if(magnitude(p1_v_x, p1_v_y) - magnitude(p2_v_x, p2_v_y) >= 10) {
				System.out.println("P1 Wins (almost tie)!");
				state = P1_WIN;
			} else if (magnitude(p1_v_x, p1_v_y) - magnitude(p2_v_x, p2_v_y) <= -10) {
				System.out.println("P2 Wins (almost tie)!");
				state = P2_WIN;
			} else {
				System.out.println("Draw!");
				state = DRAW;
			}
		} else if(p1_hit) {
			System.out.println("P1 Wins!");
			state = P1_WIN;
		} else if(p2_hit) {
			System.out.println("P2 Wins!");		
			state = P2_WIN;
		}
				
	
	}

	
	public TestServer(int port) {
		try {
			server = new ServerSocket(port);
			System.out.println("Server started");
			
			System.out.println("Waiting for client 1...");
			socket = server.accept();
			System.out.println("Client 1 connected!");
			
			in1 = new DataInputStream(socket.getInputStream());
			out1 = new ObjectOutputStream(socket.getOutputStream());
			
		} catch(IOException e) {e.printStackTrace();}
	}
	
	public static void main(String[] args) {
		new TestServer(5000);
	}
}
