package duckJousting;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.GeneralPath;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DuckJousting extends JPanel implements KeyListener {

	final static double ACCELERATION_CONSTANT = 500;
	final static double JUMP_CONSTANT = 600;
	final static double GRAVITY_ACCELERATION = 2000;
	final static Color P1_C = Color.GREEN, P2_C = Color.CYAN;
	
	final static int RIGHT_BOUND = 765;
	final static double PLAYER_WIDTH = 40, PLAYER_HEIGHT = 20;
	
	double p1_x = 50.0, p1_y = -500.0;
	double p1_a_x = 0.0, p1_v_x = 0.0, p1_v_y = 0.0;
	double p2_x = 800-50.0, p2_y = 0.0;
	double p2_a_x = 0.0, p2_v_x = 0.0, p2_v_y = 0.0;
	
	//long targetFrameLen = 5000;
	
	boolean running = true;
	
	public DuckJousting() {
		super();
		this.setBackground(Color.BLACK);
	}
	
	public void run() {
		long deltaTime = 0;
		while(running) {
			long start = System.nanoTime();
			update(deltaTime);
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
	}
	
	public void update(long deltaTimeNs) {
		double deltaTimeS = deltaTimeNs/1E9;
		p1_v_x += deltaTimeS*p1_a_x;
		p2_v_x += deltaTimeS*p2_a_x;
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
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(P1_C);
		g2d.fillRect((int)(p1_x-PLAYER_WIDTH/2), (int)(200-PLAYER_HEIGHT/2+p1_y), (int)PLAYER_WIDTH, (int)PLAYER_HEIGHT);
		g2d.setColor(P2_C);
		g2d.fillRect((int)(p2_x-PLAYER_WIDTH/2), (int)(200-PLAYER_HEIGHT/2+p2_y), (int)PLAYER_WIDTH, (int)PLAYER_HEIGHT);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			p1_a_x = -ACCELERATION_CONSTANT;
			break;
		case KeyEvent.VK_RIGHT:
			p1_a_x = ACCELERATION_CONSTANT;
			break;
		case KeyEvent.VK_UP:
			if(p1_y == 0d)
				p1_v_y = -JUMP_CONSTANT;
			break;
		case KeyEvent.VK_A:
			p2_a_x = -ACCELERATION_CONSTANT;
			break;
		case KeyEvent.VK_D:
			p2_a_x = ACCELERATION_CONSTANT;
			break;
		case KeyEvent.VK_W:
			if(p2_y == 0d)
				p2_v_y = -JUMP_CONSTANT;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_RIGHT:
			p1_a_x = 0;
			break;
		case KeyEvent.VK_A:
		case KeyEvent.VK_D:
			p2_a_x = 0;
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
		JFrame f1 = new JFrame();
		DuckJousting panel = new DuckJousting();
		f1.add(panel);
		f1.addKeyListener(panel);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setSize(new Dimension(RIGHT_BOUND+35, 400));
		f1.setVisible(true);
		panel.run();
	}


}
