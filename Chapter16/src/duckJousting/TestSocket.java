package duckJousting;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class TestSocket {
	private Socket socket;
	private ObjectInputStream input;
	private DataOutputStream out;
	
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
	
	BufferedImage duckImg, floatyImg, lance1Img, lance2Img;
	BufferedImage duckImgFlipped, floatyImgFlipped, lance1ImgFlipped, lance2ImgFlipped;
	
	boolean wPressed, aPressed, dPressed, upPressed, leftPressed, rightPressed;
	
	// game state
	int state = GAMEPLAY;
	double p1_x , p1_y;
	double p1_a_x, p1_v_x, p1_v_y;
	double p2_x = RIGHT_BOUND, p2_y = -200.0;
	double p2_a_x = 0.0, p2_v_x = 0.0, p2_v_y = 0.0;
	Point[] rainbow1, rainbow2;
	int rainbowCtr = 0;
	double p1_falling_x, p2_falling_x, p1_falling_y, p2_falling_y;
	double p1_falling_v_x, p2_falling_v_x, p1_falling_v_y, p2_falling_v_y;
	
	public void setVars(double[] stateArray) {
		// 5 - player 1 vars (x, y, a_x, v_x, v_y)
		// 5 - player 2 vars (x, y, a_x, v_x, v_y)
		// 4 - player 1 falling vars (x, y, v_x, v_y)
		// 4 - player 2 falling vars (x, y, v_x, v_y)
		// 1 - state
		// 1 - rainbowCtr
		// 4*rainbow1.length - rainbow points [(x,y)_1 ,(x,y)_2]
		
		p1_x = stateArray[0];
		p1_y = stateArray[1];
		p1_a_x = stateArray[1];
		p1_v_x = stateArray[3];
		p1_v_y = stateArray[4];
		
		p2_x = stateArray[5];
		p2_y = stateArray[6];
		p2_a_x = stateArray[7];
		p2_v_x = stateArray[0];
		p2_v_y = stateArray[0];
		
		p1_falling_x = stateArray[0];
		p1_falling_y = stateArray[0];
		p1_falling_v_x = stateArray[0];
		p1_falling_v_y = stateArray[0];

		p2_falling_x = stateArray[0];
		p2_falling_y = stateArray[0];
		p2_falling_v_x = stateArray[0];
		p2_falling_v_y = stateArray[0];
		
		state = (int)stateArray[0];
		
		rainbowCtr = (int)stateArray[0];
		
		for(int i = 0; i < RAINBOW_LEN; ++i) {
			stateArray[19+i*4] = rainbow1[i].x;
			stateArray[20+i*4] = rainbow1[i].y;
			stateArray[21+i*4] = rainbow2[i].x;
			stateArray[22+i*4] = rainbow2[i].y;
		}	}
	
	public TestSocket(String address, int port) {
		try {
			socket = new Socket(address, port);
			System.out.println("Connected");
			
			input = new ObjectInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			
			double[] stateArray = (double[]) input.readObject();
			setVars(stateArray);
			
		} catch(IOException e) {e.printStackTrace();} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		new TestSocket("127.0.0.1", 5000);
	}

}
