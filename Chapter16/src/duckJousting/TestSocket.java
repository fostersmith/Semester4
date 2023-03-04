package duckJousting;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TestSocket extends JPanel implements KeyListener {
	private Socket socket;
	private ObjectInputStream input;
	private DataOutputStream out;
	
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
	
	BufferedImage duckImg, floatyImg, lance1Img, lance2Img;
	BufferedImage duckImgFlipped, floatyImgFlipped, lance1ImgFlipped, lance2ImgFlipped;
	
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
	
	public void writeKeyOn(char key) throws IOException {
		out.writeUTF(key+"_on");
		out.flush();
	}
	
	public void writeKeyOff(char key) throws IOException {
		out.writeUTF(key+"_off");
		out.flush();
	}
	
	public synchronized void setVars(double[] stateArray) {
		// 5 - player 1 vars (x, y, a_x, v_x, v_y)
		// 5 - player 2 vars (x, y, a_x, v_x, v_y)
		// 4 - player 1 falling vars (x, y, v_x, v_y)
		// 4 - player 2 falling vars (x, y, v_x, v_y)
		// 1 - state
		// 1 - rainbowCtr
		// 4*rainbow1.length - rainbow points [(x,y)_1 ,(x,y)_2]
		
		p1_x = stateArray[0];
		p1_y = stateArray[1];
		p1_a_x = stateArray[2];
		p1_v_x = stateArray[3];
		p1_v_y = stateArray[4];
		
		p2_x = stateArray[5];
		p2_y = stateArray[6];
		p2_a_x = stateArray[7];
		p2_v_x = stateArray[8];
		p2_v_y = stateArray[9];
		
		p1_falling_x = stateArray[10];
		p1_falling_y = stateArray[11];
		p1_falling_v_x = stateArray[12];
		p1_falling_v_y = stateArray[13];

		p2_falling_x = stateArray[14];
		p2_falling_y = stateArray[15];
		p2_falling_v_x = stateArray[16];
		p2_falling_v_y = stateArray[17];
		
		state = (int)stateArray[18];
		
		rainbowCtr = (int)stateArray[19];
		//System.out.println("reading rainbow: "+rainbowCtr);
		
		//System.out.println("Rainbow: "+rainbowCtr);
		for(int i = 0; i < RAINBOW_LEN; ++i) {
			//rainbow1[i] = new Point();
			//rainbow2[i] = new Point();
			rainbow1[i].x = (int) stateArray[20+i*4];
			rainbow1[i].y = (int) stateArray[21+i*4];
			rainbow2[i].x = (int) stateArray[22+i*4];
			rainbow2[i].y = (int) stateArray[23+i*4];
		}
	}
	
	public static BufferedImage flipImage(BufferedImage bi) {
		BufferedImage out = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for(int x = 0; x < bi.getWidth(); ++x) {
			for(int y = 0; y < bi.getHeight(); ++y) {
				out.setRGB(bi.getWidth()-1-x, y, bi.getRGB(x, y));
			}
		}
		return out;
	}
	
	public TestSocket(String address, int port) {
		try {
			duckImg = ImageIO.read(new File("joust_duck.png"));
			floatyImg = ImageIO.read(new File("joust_floaty.png"));
			lance1Img = ImageIO.read(new File("joust_lance_1.png"));
			lance2Img = ImageIO.read(new File("joust_lance_2.png"));
			
			duckImgFlipped = flipImage(duckImg);
			floatyImgFlipped = flipImage(floatyImg);
			lance1ImgFlipped = flipImage(lance1Img);
			lance2ImgFlipped = flipImage(lance2Img);
		} catch(IOException e) { e.printStackTrace(); System.exit(1);}
		this.setBackground(new Color(100, 200, 255));

		
		try {
			socket = new Socket(address, port);
			System.out.println("Connected");
			
			input = new ObjectInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			
		} catch(IOException e) {e.printStackTrace();} 
		
	}
	
	public void run() throws ClassNotFoundException, IOException {
		reset();
		double[] stateArray;
		while(state != REMATCH_DECLINE) {
			stateArray = (double[]) input.readObject();
			/*for(int i = 0; i < stateArray.length; ++i) {
				System.out.print(stateArray[i]+",\t");
			}
			System.out.println();*/
			setVars(stateArray);
			repaint();
			System.out.println("read");
			//System.out.println("update");
			
			if(state == REMATCH_ASK) {
				int opt = JOptionPane.showConfirmDialog(null, "Game Over! Play again?", "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(opt == JOptionPane.YES_OPTION) {
						System.out.println("accepted");
						out.writeUTF("accept");
					} else {
						System.out.println("declined");
						out.writeUTF("decline");
					}
			}
		}
		
	}
	
	public static void drawRainbow(Graphics2D g2d, Point[] rainbow1, int rainbowCtr) {
		Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.MAGENTA};
		g2d.setStroke(new BasicStroke(6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		for(int i = 0; i < colors.length; ++i) {
			g2d.setColor(colors[i]);
			int yMod = (i-(colors.length/2))*6+200;
			GeneralPath rainbowPath = new GeneralPath();
			rainbowPath.moveTo(rainbow1[rainbowCtr].x, rainbow1[rainbowCtr].y+yMod);
			for(int n = 1; n < RAINBOW_LEN; ++n) {
				int index = (n+rainbowCtr)%RAINBOW_LEN;
				rainbowPath.lineTo(rainbow1[index].x, rainbow1[index].y+yMod);
			}
			g2d.draw(rainbowPath);
		}
	}	
	@Override
	public synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setColor(Color.BLUE);
		g2d.fillRect(0, 200+FLOATY_HEIGHT/2+FLOATY_RELATIVE_Y, RIGHT_BOUND+35, 400);
		
		g2d.setStroke(new BasicStroke(6.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));


		
		if(state == GAMEPLAY) {
			drawRainbow(g2d, rainbow1, rainbowCtr);
			drawRainbow(g2d, rainbow2, rainbowCtr);
			
			g2d.setColor(Color.red);
			if(p1_v_x <0) {
				g2d.drawImage(floatyImgFlipped,(int)p1_x-FLOATY_RELATIVE_X-FLOATY_WIDTH/2, (int)p1_y+FLOATY_RELATIVE_Y-FLOATY_HEIGHT/2+200, FLOATY_WIDTH, FLOATY_HEIGHT, null);
				g2d.drawImage(duckImgFlipped,(int)p1_x-DUCK_WIDTH/2, (int)p1_y-DUCK_HEIGHT/2 +200, DUCK_WIDTH, DUCK_HEIGHT, null);
				g2d.drawImage(lance1ImgFlipped, (int)p1_x-LANCE_LEN, (int)p1_y-LANCE_HEIGHT/2+200, LANCE_LEN, LANCE_HEIGHT, null);
			} else {
				g2d.drawImage(floatyImg,(int)p1_x+FLOATY_RELATIVE_X-FLOATY_WIDTH/2, (int)p1_y+FLOATY_RELATIVE_Y-FLOATY_HEIGHT/2+200, FLOATY_WIDTH, FLOATY_HEIGHT, null);
				g2d.drawImage(duckImg,(int)p1_x-DUCK_WIDTH/2, (int)p1_y-DUCK_HEIGHT/2 +200, DUCK_WIDTH, DUCK_HEIGHT, null);
				g2d.drawImage(lance1Img,(int)p1_x, (int)p1_y-LANCE_HEIGHT/2+200, LANCE_LEN, LANCE_HEIGHT, null);
			}
			if(p2_v_x > 0) {
				g2d.drawImage(floatyImg,(int)p2_x+FLOATY_RELATIVE_X-FLOATY_WIDTH/2, (int)p2_y+FLOATY_RELATIVE_Y-FLOATY_HEIGHT/2+200, FLOATY_WIDTH, FLOATY_HEIGHT, null);
				g2d.drawImage(duckImg,(int)p2_x-DUCK_WIDTH/2, (int)p2_y-DUCK_HEIGHT/2 +200, DUCK_WIDTH, DUCK_HEIGHT, null);
				g2d.drawImage(lance2Img,(int)p2_x, (int)p2_y-LANCE_HEIGHT/2+200, LANCE_LEN, LANCE_HEIGHT, null);
			} else {
				g2d.drawImage(floatyImgFlipped,(int)p2_x-FLOATY_RELATIVE_X-FLOATY_WIDTH/2, (int)p2_y+FLOATY_RELATIVE_Y-FLOATY_HEIGHT/2+200, FLOATY_WIDTH, FLOATY_HEIGHT, null);
				g2d.drawImage(duckImgFlipped,(int)p2_x-DUCK_WIDTH/2, (int)p2_y-DUCK_HEIGHT/2 +200, DUCK_WIDTH, DUCK_HEIGHT, null);
				g2d.drawImage(lance2ImgFlipped, (int)p2_x-LANCE_LEN, (int)p2_y-LANCE_HEIGHT/2+200, LANCE_LEN, LANCE_HEIGHT, null);
			}
		} else {
			if(state == P1_WIN)
				drawRainbow(g2d, rainbow1, rainbowCtr);
			if(state == P2_WIN)
				drawRainbow(g2d, rainbow2, rainbowCtr);
			
			if(p1_v_x <0) {
				g2d.drawImage(floatyImgFlipped,(int)p1_x-FLOATY_RELATIVE_X-FLOATY_WIDTH/2, (int)p1_y+FLOATY_RELATIVE_Y-FLOATY_HEIGHT/2+200, FLOATY_WIDTH, FLOATY_HEIGHT, null);
				g2d.drawImage(duckImgFlipped,(int)p1_falling_x-DUCK_WIDTH/2, (int)p1_falling_y-DUCK_HEIGHT/2 +200, DUCK_WIDTH, DUCK_HEIGHT, null);
				if(state == P1_WIN)
					g2d.drawImage(lance1ImgFlipped, (int)p1_x-LANCE_LEN, (int)p1_y-LANCE_HEIGHT/2+200, LANCE_LEN, LANCE_HEIGHT, null);
			} else {
				g2d.drawImage(floatyImg,(int)p1_x+FLOATY_RELATIVE_X-FLOATY_WIDTH/2, (int)p1_y+FLOATY_RELATIVE_Y-FLOATY_HEIGHT/2+200, FLOATY_WIDTH, FLOATY_HEIGHT, null);
				g2d.drawImage(duckImg,(int)p1_falling_x-DUCK_WIDTH/2, (int)p1_falling_y-DUCK_HEIGHT/2 +200, DUCK_WIDTH, DUCK_HEIGHT, null);
				if(state == P1_WIN)
					g2d.drawImage(lance1Img,(int)p1_x, (int)p1_y-LANCE_HEIGHT/2+200, LANCE_LEN, LANCE_HEIGHT, null);
			}
			if(p2_v_x >0) {
				g2d.drawImage(floatyImg,(int)p2_x+FLOATY_RELATIVE_X-FLOATY_WIDTH/2, (int)p2_y+FLOATY_RELATIVE_Y-FLOATY_HEIGHT/2+200, FLOATY_WIDTH, FLOATY_HEIGHT, null);
				g2d.drawImage(duckImg,(int)p2_falling_x-DUCK_WIDTH/2, (int)p2_falling_y-DUCK_HEIGHT/2 +200, DUCK_WIDTH, DUCK_HEIGHT, null);
				if(state == P2_WIN)
					g2d.drawImage(lance2Img,(int)p2_x, (int)p2_y-LANCE_HEIGHT/2+200, LANCE_LEN, LANCE_HEIGHT, null);
			} else {
				g2d.drawImage(floatyImgFlipped,(int)p2_x-FLOATY_RELATIVE_X-FLOATY_WIDTH/2, (int)p2_y+FLOATY_RELATIVE_Y-FLOATY_HEIGHT/2+200, FLOATY_WIDTH, FLOATY_HEIGHT, null);
				g2d.drawImage(duckImgFlipped,(int)p2_falling_x-DUCK_WIDTH/2, (int)p2_falling_y-DUCK_HEIGHT/2 +200, DUCK_WIDTH, DUCK_HEIGHT, null);
				if(state == P2_WIN)
					g2d.drawImage(lance2ImgFlipped, (int)p2_x-LANCE_LEN, (int)p2_y-LANCE_HEIGHT/2+200, LANCE_LEN, LANCE_HEIGHT, null);
			}
			
		}
		
	}	
	public void reset() {
		p1_x = 75.0;
		p1_y = -200.0;
		p1_a_x = 0.0; p1_v_x = 0.0; p1_v_y = 0.0;
		p2_x = RIGHT_BOUND-50.0; p2_y = -200.0;
		p2_a_x = 0.0; p2_v_x = 0.0; p2_v_y = 0.0;
		state = GAMEPLAY;
		
		rainbow1 = new Point[10];
		for(int i = 0; i < rainbow1.length; ++i) {
			rainbow1[i] = new Point();
			rainbow1[i].x = (int) p1_x;
			rainbow1[i].y = (int) p1_y;
		}

		rainbow2 = new Point[10];
		for(int i = 0; i < rainbow2.length; ++i) {
			rainbow2[i] = new Point();
			rainbow2[i].x = (int) p2_x;
			rainbow2[i].y = (int) p2_y;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		try {
			switch(e.getKeyCode()) {
			case(KeyEvent.VK_A):
			case(KeyEvent.VK_LEFT):
				writeKeyOn('a');
				break;
			case(KeyEvent.VK_RIGHT):
			case(KeyEvent.VK_D):
				writeKeyOn('d');
				break;
			case(KeyEvent.VK_UP):
			case(KeyEvent.VK_W):
				writeKeyOn('w');
				break;
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		try {
			switch(e.getKeyCode()) {
			case(KeyEvent.VK_A):
			case(KeyEvent.VK_LEFT):
				writeKeyOff('a');
				break;
			case(KeyEvent.VK_RIGHT):
			case(KeyEvent.VK_D):
				writeKeyOff('d');
				break;
			case(KeyEvent.VK_UP):
			case(KeyEvent.VK_W):
				writeKeyOff('w');
				break;
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		JFrame f1 = new JFrame();
		TestSocket panel = new TestSocket("192.168.86.21", 5000);
		f1.addKeyListener(panel);
		f1.add(panel);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setSize(new Dimension(RIGHT_BOUND+35, 400));
		f1.setVisible(true);
		try {
			panel.run();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		f1.setVisible(false);
		f1.dispose();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


}
