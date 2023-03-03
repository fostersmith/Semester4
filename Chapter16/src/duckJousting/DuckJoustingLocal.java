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
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DuckJoustingLocal extends JPanel implements KeyListener {

	final static int GAMEPLAY = 0, P1_WIN = 1, P2_WIN = 2, DRAW = 3;
	
	final static int REMATCH = 4, QUIT = 5;
	
	final static double ACCELERATION_CONSTANT = 600;
	final static double JUMP_CONSTANT = 600;
	final static double GRAVITY_ACCELERATION = 2000;
	final static Color P1_C = Color.GREEN, P2_C = Color.CYAN;
	
	final static int RIGHT_BOUND = 1500;
	
	
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

	//long targetFrameLen = 5000;
	
	public void reset() {
		p1_x = 75.0;
		p1_y = -200.0;
		p1_a_x = 0.0; p1_v_x = 0.0; p1_v_y = 0.0;
		p2_x = RIGHT_BOUND-50.0; p2_y = -200.0;
		p2_a_x = 0.0; p2_v_x = 0.0; p2_v_y = 0.0;
		state = GAMEPLAY;
		
		wPressed = false;
		aPressed = false;
		dPressed = false;
		upPressed = false;
		leftPressed = false;
		rightPressed = false;
		
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
	
	public static BufferedImage flipImage(BufferedImage bi) {
		BufferedImage out = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for(int x = 0; x < bi.getWidth(); ++x) {
			for(int y = 0; y < bi.getHeight(); ++y) {
				out.setRGB(bi.getWidth()-1-x, y, bi.getRGB(x, y));
			}
		}
		return out;
	}
	
	public DuckJoustingLocal() {
		super();
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
	}
	
	public int run() {
		long deltaTime = 0;
		long nextLog = System.nanoTime();
		while(state == GAMEPLAY) {
			long start = System.nanoTime();
			update(deltaTime);
			if(start >= nextLog) {
				Point temp = rainbow1[0];
				for(int i = 1; i < rainbow1.length; ++i) {
					Point temptemp = temp;
					temp = rainbow1[i];
					rainbow1[i] = temptemp;
				}
				rainbow1[0] = new Point();
				rainbow1[0].x = (int) (p1_x + 0.005*p1_v_x);
				rainbow1[0].y = (int) (p1_y + 0.005*p1_v_y);
				
				temp = rainbow2[0];
				for(int i = 1; i < rainbow2.length; ++i) {
					Point temptemp = temp;
					temp = rainbow2[i];
					rainbow2[i] = temptemp;
				}
				rainbow2[0] = new Point();
				rainbow2[0].x = (int) (p2_x + 0.005*p2_v_x);
				rainbow2[0].y = (int) (p2_y + 0.005*p2_v_y);

				//System.out.println();
				nextLog = start + (long)1E9/30;
			}
			repaint();
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
			repaint();
			deltaTime = System.nanoTime() - start;
			timeCounter += deltaTime;
		}
		
		String msg;
		if(state == P1_WIN) {
			msg = "Player 1 wins! Play again?";
		} else if(state == P2_WIN) {
			msg = "Player 2 wins! Play again?";
		} else {
			msg = "It's a draw! Play again?";
		}
		int opt = JOptionPane.showConfirmDialog(null, msg, "Game Over", JOptionPane.YES_NO_OPTION);
		if(opt == JOptionPane.YES_OPTION)
			return REMATCH;
		else
			return QUIT;
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
	
	public static void drawRainbow(Graphics2D g2d, Point[] rainbow1) {
		g2d.setColor(Color.RED);		
		GeneralPath rainbow = new GeneralPath();
		rainbow.moveTo(rainbow1[0].x, rainbow1[0].y+200-3*6);
		for(int i = 1; i < rainbow1.length; ++i) {
			rainbow.lineTo(rainbow1[i].x, rainbow1[i].y+200-3*6);
		}
		g2d.draw(rainbow);
		g2d.setColor(Color.ORANGE);		
		rainbow = new GeneralPath();
		rainbow.moveTo(rainbow1[0].x, rainbow1[0].y+200-2*6);
		for(int i = 1; i < rainbow1.length; ++i) {
			rainbow.lineTo(rainbow1[i].x, rainbow1[i].y+200-2*6);
		}
		g2d.draw(rainbow);
		g2d.setColor(Color.YELLOW);		
		rainbow = new GeneralPath();
		rainbow.moveTo(rainbow1[0].x, rainbow1[0].y+200-1*6);
		for(int i = 1; i < rainbow1.length; ++i) {
			rainbow.lineTo(rainbow1[i].x, rainbow1[i].y+200-1*6);
		}
		g2d.draw(rainbow);
		g2d.setColor(Color.GREEN);		
		rainbow = new GeneralPath();
		rainbow.moveTo(rainbow1[0].x, rainbow1[0].y+200);
		for(int i = 1; i < rainbow1.length; ++i) {
			rainbow.lineTo(rainbow1[i].x, rainbow1[i].y+200);
		}
		g2d.draw(rainbow);
		g2d.setColor(Color.BLUE);		
		rainbow = new GeneralPath();
		rainbow.moveTo(rainbow1[0].x, rainbow1[0].y+200+1*6);
		for(int i = 1; i < rainbow1.length; ++i) {
			rainbow.lineTo(rainbow1[i].x, rainbow1[i].y+200+1*6);
		}
		g2d.draw(rainbow);
		g2d.setColor(Color.MAGENTA);		
		rainbow = new GeneralPath();
		rainbow.moveTo(rainbow1[0].x, rainbow1[0].y+200+2*6);
		for(int i = 1; i < rainbow1.length; ++i) {
			rainbow.lineTo(rainbow1[i].x, rainbow1[i].y+200+2*6);
		}
		g2d.draw(rainbow);

	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setColor(Color.BLUE);
		g2d.fillRect(0, 200+FLOATY_HEIGHT/2+FLOATY_RELATIVE_Y, RIGHT_BOUND+35, 400);
		
		g2d.setStroke(new BasicStroke(6.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));


		
		if(state == GAMEPLAY) {
			drawRainbow(g2d, rainbow1);
			drawRainbow(g2d, rainbow2);
			
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
				drawRainbow(g2d, rainbow1);
			if(state == P2_WIN)
				drawRainbow(g2d, rainbow2);
			
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

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			leftPressed = true;
			break;
		case KeyEvent.VK_RIGHT:
			rightPressed = true;
			break;
		case KeyEvent.VK_UP:
			upPressed = true;
			break;
		case KeyEvent.VK_A:
			aPressed = true;
			break;
		case KeyEvent.VK_D:
			dPressed = true;
			break;
		case KeyEvent.VK_W:
			wPressed = true;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			leftPressed = false;
			break;
		case KeyEvent.VK_RIGHT:
			rightPressed = false;
			break;
		case KeyEvent.VK_UP:
			upPressed = false;
			break;
		case KeyEvent.VK_A:
			aPressed = false;
			break;
		case KeyEvent.VK_D:
			dPressed = false;
			break;
		case KeyEvent.VK_W:
			wPressed = false;
			break;
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public static double magnitude(double x,double y) {
		return Math.sqrt(Math.pow(x,2)+Math.pow(y, 2));
	}

	public static void main(String[] args) {
		JFrame f1 = new JFrame();
		DuckJoustingLocal panel = new DuckJoustingLocal();
		f1.add(panel);
		f1.addKeyListener(panel);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setSize(new Dimension(RIGHT_BOUND+35, 400));
		f1.setVisible(true);
		int opt;
		do {
			panel.reset();
			opt = panel.run();
		} while(opt == REMATCH);
		
		f1.setVisible(false);
		f1.dispose();
	}


}
