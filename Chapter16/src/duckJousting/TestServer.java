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

import javax.swing.JOptionPane;

public class TestServer {

	private Socket socket1, socket2;
	private ServerSocket server;
	private DataInputStream in1, in2;
	private ObjectOutputStream out1, out2;

	final static int GAMEPLAY = 0, P1_WIN = 1, P2_WIN = 2, DRAW = 3, REMATCH_ASK = 4, REMATCH_DECLINE = 5;
	
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
	
	int p1Accepted = -1, p2Accepted = -1;
	
	// determines the buttons pressed by player 1 by accepting input from the client in the format "w_on" until the client sends "Over"
	private Thread input1 = new Thread() {
		@Override
		public void run() {
			try {
				String line = "a_off";
				while(!line.equals("Over")) {
					if(state != REMATCH_ASK) {
						char key = line.charAt(0);
						String type = line.split("_")[1];
						if(type.equals("on")) {
							switch(key){
							case 'w':
								wPressed = true;
								break;
							case 'a':
								aPressed = true;
								break;
							case 'd':
								dPressed = true;
								break;
							}
						} else if(type.equals("off")) {
							switch(key){
							case 'w':
								wPressed = false;
								break;
							case 'a':
								aPressed = false;
								break;
							case 'd':
								dPressed = false;
								break;			
							}
						}
					}
					else {
						if(line.equals("accept")) {
							p1Accepted = 1;
							System.out.println("accept");
						} else if(line.equals("decline")) {
							p1Accepted = 0;							
						}
					}
					line = in1.readUTF();
				}	
			} catch(IOException e) {
				quit("An IO Exception occurred for player 1");
			}
			quit("Player 1 quit");
		}
	};

	private Thread input2 = new Thread() {
		@Override
		public void run() {
			try {
				String line = "a_off";
				while(!line.equals("Over")) {
					if(state != REMATCH_ASK) {
						char key = line.charAt(0);
						String type = line.split("_")[1];
						if(type.equals("on")) {
							switch(key){
							case 'w':
								upPressed = true;
								break;
							case 'a':
								leftPressed = true;
								break;
							case 'd':
								rightPressed = true;
								break;
							}
						} else if(type.equals("off")) {
							switch(key){
							case 'w':
								upPressed = false;
								break;
							case 'a':
								leftPressed = false;
								break;
							case 'd':
								rightPressed = false;
								break;			
							}
						}
					}
					else {
						if(line.equals("accept")) {
							p2Accepted = 1;
							System.out.println("accept");
						} else if(line.equals("decline")) {
							p2Accepted = 0;							
						}
					}
					line = in2.readUTF();
				}	
			} catch(IOException e) {
				quit("An IO Exception occurred for player 1");
			}
			quit("Player 2 quit");
		}
	};

	public void quit(String msg) {
		System.out.println(msg);
		
		System.exit(0);
	}
	
	public void writeState(double[] stateArray) throws IOException {
		// 5 - player 1 vars (x, y, a_x, v_x, v_y)
		// 5 - player 2 vars (x, y, a_x, v_x, v_y)
		// 4 - player 1 falling vars (x, y, v_x, v_y)
		// 4 - player 2 falling vars (x, y, v_x, v_y)
		// 1 - state
		// 1 - rainbowCtr
		// 4*rainbow1.length - rainbow points [(x,y)_1 ,(x,y)_2]
		stateArray = new double[10 + 8 + 2 + 4*RAINBOW_LEN];
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
		//System.out.println("Falling y: "+p2_falling_y);
		
		stateArray[18] = state;
		
		stateArray[19] = rainbowCtr;
		//System.out.println("writing rainbow: "+stateArray[19]);
		
		for(int i = 0; i < RAINBOW_LEN; ++i) {
			stateArray[20+i*4] = rainbow1[i].x;
			stateArray[21+i*4] = rainbow1[i].y;
			stateArray[22+i*4] = rainbow2[i].x;
			stateArray[23+i*4] = rainbow2[i].y;
		}
		
		/*for(int i = 0; i < stateArray.length; ++i) {
			System.out.print(stateArray[i]+",\t");
		}
		System.out.println();*/
		out1.writeObject(stateArray);
		out1.flush();
		out2.writeObject(stateArray);
		out2.flush();
		System.gc();
	}
	
	public void reset() {
		p1_x = 76.0;
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
		
		p1Accepted = -1;
		p2Accepted = -1;
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

	public int run() throws IOException {
		double[] stateArray = new double[10 + 8 + 2 + 4*RAINBOW_LEN];

		long deltaTime = 0;
		long nextLog = System.nanoTime();
		while(state == GAMEPLAY) {
			//System.out.println("Update!");
			long start = System.nanoTime();
			update(deltaTime);
			if(start >= nextLog) {
				rainbow1[rainbowCtr] = new Point();
				rainbow1[rainbowCtr].x = (int) p1_x;
				rainbow1[rainbowCtr].y = (int) p1_y;
				
				rainbow2[rainbowCtr] = new Point();
				rainbow2[rainbowCtr].x = (int) p2_x;
				rainbow2[rainbowCtr].y = (int) p2_y;
				rainbowCtr = (rainbowCtr + 1) % RAINBOW_LEN;
				//System.out.println("rainbow: "+rainbowCtr);
				//System.out.println();
				nextLog = start + (long)1E9/30;
			}
			writeState(stateArray);
			deltaTime = System.nanoTime()-start;
			//System.out.println("Delta Time: "+deltaTime);
			/*if(deltaTime < targetFrameLen) {
				try {
					Thread.sleep(targetFrameLen-deltaTime);
					deltaTime = targetFrameLen;
					System.out.println("Waited up to "+deltaTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}*/
		}
		p1_falling_x = p1_x;
		p1_falling_y = p1_y;
		p2_falling_x = p2_x;
		p2_falling_y = p2_y;
		long timeCounter = 0;
		deltaTime = 0;
		if(state == P1_WIN || state == DRAW) {
			p2_falling_v_x = p1_v_x;
			p2_falling_v_y = p1_v_y - 500;
		}
		if(state == P2_WIN || state == DRAW) {
			p1_falling_v_x = p2_v_x;
			p1_falling_v_y = p2_v_y - 500;
		}
		while(timeCounter < 1E9) {
			long start = System.nanoTime();
			double deltaTimeS = deltaTime/1E9;
			if(state == P1_WIN || state == DRAW) {
				p2_falling_v_y += deltaTimeS*GRAVITY_ACCELERATION;
				p2_falling_x += deltaTimeS*p2_falling_v_x;
				p2_falling_y += deltaTimeS*p2_falling_v_y;
			}
			if(state == P2_WIN || state == DRAW) {
				p1_falling_v_y += deltaTimeS*GRAVITY_ACCELERATION;
				p1_falling_x += deltaTimeS*p1_falling_v_x;
				p1_falling_y += deltaTimeS*p1_falling_v_y;
			}
			writeState(stateArray);
			deltaTime = System.nanoTime() - start;
			timeCounter += deltaTime;
			//System.out.println("Falling");
			
		}
		state = REMATCH_ASK;
		writeState(stateArray);

		while(p1Accepted == -1 || p2Accepted == -1) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if(p1Accepted == 1 && p2Accepted == 1) {
			return REMATCH;
		}
		else {
			state = REMATCH_DECLINE;
			writeState(stateArray);
			return QUIT;
		}
		
		
	}

	public TestServer(int port) {
		try {
			server = new ServerSocket(port);
			System.out.println("Server started");
			
			System.out.println("Waiting for client 1...");
			socket1 = server.accept();
			System.out.println("Client 1 connected!");
			
			in1 = new DataInputStream(socket1.getInputStream());
			out1 = new ObjectOutputStream(socket1.getOutputStream());
			
			input1.start();

			System.out.println("Waiting for client 2...");
			socket2 = server.accept();
			System.out.println("Client 2 connected!");
			
			in2 = new DataInputStream(socket2.getInputStream());
			out2 = new ObjectOutputStream(socket2.getOutputStream());
			
			input2.start();

			/*reset();
			while(true) {
				update((long) (1E9/100));
				System.out.println("Update");
				writeState();
				try {
					Thread.sleep((long) (1E3/100));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}*/
		} catch(IOException e) {e.printStackTrace();}
	}
	
	public static void main(String[] args) {
		TestServer server = new TestServer(5000);
		server.reset();
		try {
			int opt = REMATCH;
			while(opt != QUIT) {
				server.reset();
				opt = server.run();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		server.quit("Server terminated");
	}
}
