package duckJousting;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyListener;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DuckJoustingFrontend extends JPanel {
	
	final static int GAMEPLAY = 0, P1_WIN = 1, P2_WIN = 2, DRAW = 3;
	double p1_falling_x, p2_falling_x, p1_falling_y, p2_falling_y;
	double p1_falling_v_x, p2_falling_v_x, p1_falling_v_y, p2_falling_v_y;
	int state = GAMEPLAY;
	
	final static int REMATCH = 4, QUIT = 5;
	
	final static double ACCELERATION_CONSTANT = 600;
	final static double JUMP_CONSTANT = 600;
	final static double GRAVITY_ACCELERATION = 2000;
	final static Color P1_C = Color.GREEN, P2_C = Color.CYAN;
	
	final static int RIGHT_BOUND = 1500;
	
	double p1_x , p1_y;
	double p1_a_x, p1_v_x, p1_v_y;
	double p2_x = RIGHT_BOUND, p2_y = -200.0;
	double p2_a_x = 0.0, p2_v_x = 0.0, p2_v_y = 0.0;
	
	final static int FLOATY_WIDTH = 46*3, FLOATY_HEIGHT = 28*3;
	int lanceLen = 41*3, lanceHeight = 7*3, lanceElevation = 8*3;
	int duckHeight = 20*3, duckWidth = 19*3;
	int floatyRelativeX = 2*3, floatyRelativeY = 3*3;
	
	BufferedImage duckImg, floatyImg, lance1Img, lance2Img;
	BufferedImage duckImgFlipped, floatyImgFlipped, lance1ImgFlipped, lance2ImgFlipped;
	
	boolean wPressed, aPressed, dPressed, upPressed, leftPressed, rightPressed;
	
	Point[] rainbow1, rainbow2;
	int rainbowCtr = 0;

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
		g2d.fillRect(0, 200+FLOATY_HEIGHT/2+floatyRelativeY, RIGHT_BOUND+35, 400);
		
		g2d.setStroke(new BasicStroke(6.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));


		
		if(state == GAMEPLAY) {
			drawRainbow(g2d, rainbow1);
			drawRainbow(g2d, rainbow2);
			
			g2d.setColor(Color.red);
			if(p1_v_x <0) {
				g2d.drawImage(floatyImgFlipped,(int)p1_x-floatyRelativeX-FLOATY_WIDTH/2, (int)p1_y+floatyRelativeY-FLOATY_HEIGHT/2+200, FLOATY_WIDTH, FLOATY_HEIGHT, null);
				g2d.drawImage(duckImgFlipped,(int)p1_x-duckWidth/2, (int)p1_y-duckHeight/2 +200, duckWidth, duckHeight, null);
				g2d.drawImage(lance1ImgFlipped, (int)p1_x-lanceLen, (int)p1_y-lanceHeight/2+200, lanceLen, lanceHeight, null);
			} else {
				g2d.drawImage(floatyImg,(int)p1_x+floatyRelativeX-FLOATY_WIDTH/2, (int)p1_y+floatyRelativeY-FLOATY_HEIGHT/2+200, FLOATY_WIDTH, FLOATY_HEIGHT, null);
				g2d.drawImage(duckImg,(int)p1_x-duckWidth/2, (int)p1_y-duckHeight/2 +200, duckWidth, duckHeight, null);
				g2d.drawImage(lance1Img,(int)p1_x, (int)p1_y-lanceHeight/2+200, lanceLen, lanceHeight, null);
			}
			if(p2_v_x > 0) {
				g2d.drawImage(floatyImg,(int)p2_x+floatyRelativeX-FLOATY_WIDTH/2, (int)p2_y+floatyRelativeY-FLOATY_HEIGHT/2+200, FLOATY_WIDTH, FLOATY_HEIGHT, null);
				g2d.drawImage(duckImg,(int)p2_x-duckWidth/2, (int)p2_y-duckHeight/2 +200, duckWidth, duckHeight, null);
				g2d.drawImage(lance2Img,(int)p2_x, (int)p2_y-lanceHeight/2+200, lanceLen, lanceHeight, null);
			} else {
				g2d.drawImage(floatyImgFlipped,(int)p2_x-floatyRelativeX-FLOATY_WIDTH/2, (int)p2_y+floatyRelativeY-FLOATY_HEIGHT/2+200, FLOATY_WIDTH, FLOATY_HEIGHT, null);
				g2d.drawImage(duckImgFlipped,(int)p2_x-duckWidth/2, (int)p2_y-duckHeight/2 +200, duckWidth, duckHeight, null);
				g2d.drawImage(lance2ImgFlipped, (int)p2_x-lanceLen, (int)p2_y-lanceHeight/2+200, lanceLen, lanceHeight, null);
			}
		} else {
			if(state == P1_WIN)
				drawRainbow(g2d, rainbow1);
			if(state == P2_WIN)
				drawRainbow(g2d, rainbow2);
			
			if(p1_v_x <0) {
				g2d.drawImage(floatyImgFlipped,(int)p1_x-floatyRelativeX-FLOATY_WIDTH/2, (int)p1_y+floatyRelativeY-FLOATY_HEIGHT/2+200, FLOATY_WIDTH, FLOATY_HEIGHT, null);
				g2d.drawImage(duckImgFlipped,(int)p1_falling_x-duckWidth/2, (int)p1_falling_y-duckHeight/2 +200, duckWidth, duckHeight, null);
				if(state == P1_WIN)
					g2d.drawImage(lance1ImgFlipped, (int)p1_x-lanceLen, (int)p1_y-lanceHeight/2+200, lanceLen, lanceHeight, null);
			} else {
				g2d.drawImage(floatyImg,(int)p1_x+floatyRelativeX-FLOATY_WIDTH/2, (int)p1_y+floatyRelativeY-FLOATY_HEIGHT/2+200, FLOATY_WIDTH, FLOATY_HEIGHT, null);
				g2d.drawImage(duckImg,(int)p1_falling_x-duckWidth/2, (int)p1_falling_y-duckHeight/2 +200, duckWidth, duckHeight, null);
				if(state == P1_WIN)
					g2d.drawImage(lance1Img,(int)p1_x, (int)p1_y-lanceHeight/2+200, lanceLen, lanceHeight, null);
			}
			if(p2_v_x >0) {
				g2d.drawImage(floatyImg,(int)p2_x+floatyRelativeX-FLOATY_WIDTH/2, (int)p2_y+floatyRelativeY-FLOATY_HEIGHT/2+200, FLOATY_WIDTH, FLOATY_HEIGHT, null);
				g2d.drawImage(duckImg,(int)p2_falling_x-duckWidth/2, (int)p2_falling_y-duckHeight/2 +200, duckWidth, duckHeight, null);
				if(state == P2_WIN)
					g2d.drawImage(lance2Img,(int)p2_x, (int)p2_y-lanceHeight/2+200, lanceLen, lanceHeight, null);
			} else {
				g2d.drawImage(floatyImgFlipped,(int)p2_x-floatyRelativeX-FLOATY_WIDTH/2, (int)p2_y+floatyRelativeY-FLOATY_HEIGHT/2+200, FLOATY_WIDTH, FLOATY_HEIGHT, null);
				g2d.drawImage(duckImgFlipped,(int)p2_falling_x-duckWidth/2, (int)p2_falling_y-duckHeight/2 +200, duckWidth, duckHeight, null);
				if(state == P2_WIN)
					g2d.drawImage(lance2ImgFlipped, (int)p2_x-lanceLen, (int)p2_y-lanceHeight/2+200, lanceLen, lanceHeight, null);
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

	public DuckJoustingFrontend() {
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

	public static void main(String[] args) {
		JFrame f1 = new JFrame();
		DuckJoustingFrontend panel = new DuckJoustingFrontend();
		f1.add(panel);
		//f1.addKeyListener(panel);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setSize(new Dimension(RIGHT_BOUND+35, 400));
		f1.setVisible(true);
		panel.reset();
		/*int opt;
		do {
			panel.reset();
			opt = panel.run();
		} while(opt == REMATCH);*/
		
		//f1.setVisible(false);
		//f1.dispose();
	}

}
