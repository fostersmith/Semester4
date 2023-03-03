package duckJousting;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DuckJousting extends JPanel implements KeyListener {

	final static int GAMEPLAY = 0, P1_WIN = 1, P2_WIN = 2, DRAW = 3;
	double p1_falling_x, p2_falling_x, p1_falling_y, p2_falling_y;
	double p1_falling_v_x, p2_falling_v_x, p1_falling_v_y, p2_falling_v_y;
	int state = GAMEPLAY;
	
	final static int REMATCH = 4, QUIT = 5;
	
	final static double ACCELERATION_CONSTANT = 500;
	final static double JUMP_CONSTANT = 600;
	final static double GRAVITY_ACCELERATION = 2000;
	final static Color P1_C = Color.GREEN, P2_C = Color.CYAN;
	
	final static int RIGHT_BOUND = 765;
	final static double FLOATY_WIDTH = 40, FLOATY_HEIGHT = 20;
	
	double p1_x = 50.0, p1_y = -200.0;
	double p1_a_x = 0.0, p1_v_x = 0.0, p1_v_y = 0.0;
	double p2_x = 800-50.0, p2_y = -200.0;
	double p2_a_x = 0.0, p2_v_x = 0.0, p2_v_y = 0.0;
	
	double lanceLen = 50, lanceHeight = 20;
	double duckHeight = 20, duckWidth = 20;
	
	//long targetFrameLen = 5000;
	
	public void reset() {
		p1_x = 50.0;
		p1_y = -200.0;
		p1_a_x = 0.0; p1_v_x = 0.0; p1_v_y = 0.0;
		p2_x = 800-50.0; p2_y = -200.0;
		p2_a_x = 0.0; p2_v_x = 0.0; p2_v_y = 0.0;
		state = GAMEPLAY;
	}
	
	public DuckJousting() {
		super();
		this.setBackground(Color.BLACK);
	}
	
	public int run() {
		long deltaTime = 0;
		while(state == GAMEPLAY) {
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
		
		//check for collisions
		double p1_lance_x = p1_v_x < 0 ? p1_x-lanceLen : p1_x+lanceLen,  p2_lance_x = p2_v_x>0 ? p2_x+lanceLen : p2_x-lanceLen;
		//System.out.println("P1 X: "+p1_x);
		//System.out.println("P1 Lance X: "+p1_lance_x);
		double p1_lance_y = p1_y - lanceHeight, p2_lance_y = p2_y-lanceHeight;
		//System.out.println("P1 Y: "+p1_y);
		//System.out.println("P1 Lance Y: "+p1_lance_y);
		
		boolean p1_hit = false, p2_hit = false;
		
		if(p1_lance_x >= p2_x-duckWidth/2 && p1_lance_x <= p2_x+duckWidth/2 && p1_lance_y <= p2_y-FLOATY_HEIGHT/2 && p1_lance_y >= p2_y-FLOATY_HEIGHT/2-duckHeight) {
			p1_hit = true;
		}
		if(p2_lance_x >= p1_x-duckWidth/2 && p2_lance_x <= p1_x+duckWidth/2 && p2_lance_y <= p1_y-FLOATY_HEIGHT/2 && p2_lance_y >= p1_y-FLOATY_HEIGHT/2-duckHeight) {
			p2_hit = true;
		}
		
		if(p1_hit && p2_hit) {
			System.out.println(magnitude(p1_v_x, p1_v_y) - magnitude(p2_v_x, p2_v_y));
			if(magnitude(p1_v_x, p1_v_y) - magnitude(p2_v_x, p2_v_y) >= 5) {
				System.out.println("P1 Wins (almost tie)!");
				state = P1_WIN;
			} else if (magnitude(p1_v_x, p1_v_y) - magnitude(p2_v_x, p2_v_y) <= -5) {
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
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		if(state == GAMEPLAY) {
			
			g2d.setColor(P1_C);
			g2d.fillRect((int)(p1_x-FLOATY_WIDTH/2), (int)(200-FLOATY_HEIGHT/2+p1_y), (int)FLOATY_WIDTH, (int)FLOATY_HEIGHT);
			g2d.fillRect((int)(p1_x-duckWidth/2), (int)(200-FLOATY_HEIGHT/2-duckHeight+p1_y), (int)duckWidth, (int)duckHeight);
			g2d.setColor(P2_C);
			g2d.fillRect((int)(p2_x-FLOATY_WIDTH/2), (int)(200-FLOATY_HEIGHT/2+p2_y), (int)FLOATY_WIDTH, (int)FLOATY_HEIGHT);
			g2d.fillRect((int)(p2_x-duckWidth/2), (int)(200-FLOATY_HEIGHT/2-duckHeight+p2_y), (int)duckWidth, (int)duckHeight);
			
			g2d.setColor(Color.RED);
			if(p1_v_x < 0) {
				g2d.drawLine((int)p1_x, (int)(200+p1_y-20), (int)(p1_x-lanceLen), (int)(200+p1_y-20));
			} else {
				g2d.drawLine((int)p1_x, (int)(200+p1_y-20), (int)(p1_x+lanceLen), (int)(200+p1_y-20));
			}
	
			if(p2_v_x > 0) {
				g2d.drawLine((int)p2_x, (int)(200+p2_y-20), (int)(p2_x+lanceLen), (int)(200+p2_y-20));
			} else {
				g2d.drawLine((int)p2_x, (int)(200+p2_y-20), (int)(p2_x-lanceLen), (int)(200+p2_y-20));
			}
		} else {
			g2d.setColor(P1_C);
			g2d.fillRect((int)(p1_x-FLOATY_WIDTH/2), (int)(200-FLOATY_HEIGHT/2+p1_y), (int)FLOATY_WIDTH, (int)FLOATY_HEIGHT);
			g2d.fillRect((int)(p1_falling_x-duckWidth/2), (int)(200-FLOATY_HEIGHT/2-duckHeight+p1_falling_y), (int)duckWidth, (int)duckHeight);
			g2d.setColor(P2_C);
			g2d.fillRect((int)(p2_x-FLOATY_WIDTH/2), (int)(200-FLOATY_HEIGHT/2+p2_y), (int)FLOATY_WIDTH, (int)FLOATY_HEIGHT);
			g2d.fillRect((int)(p2_falling_x-duckWidth/2), (int)(200-FLOATY_HEIGHT/2-duckHeight+p2_falling_y), (int)duckWidth, (int)duckHeight);
			
			g2d.setColor(Color.RED);
			if(state == P1_WIN) {
				if(p1_v_x < 0) {
					g2d.drawLine((int)p1_falling_x, (int)(200+p1_falling_y-20), (int)(p1_falling_x-lanceLen), (int)(200+p1_falling_y-20));
				} else {
					g2d.drawLine((int)p1_falling_x, (int)(200+p1_falling_y-20), (int)(p1_falling_x+lanceLen), (int)(200+p1_falling_y-20));
				}
			}
			if(state == P2_WIN) {
				if(p2_v_x < 0) {
					g2d.drawLine((int)p2_falling_x, (int)(200+p2_falling_y-20), (int)(p2_falling_x-lanceLen), (int)(200+p2_falling_y-20));
				} else {
					g2d.drawLine((int)p2_falling_x, (int)(200+p2_falling_y-20), (int)(p2_falling_x+lanceLen), (int)(200+p2_falling_y-20));
				}
			}
		}
}
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			p2_a_x = -ACCELERATION_CONSTANT;
			break;
		case KeyEvent.VK_RIGHT:
			p2_a_x = ACCELERATION_CONSTANT;
			break;
		case KeyEvent.VK_UP:
			if(p2_y == 0d)
				p2_v_y = -JUMP_CONSTANT;
			break;
		case KeyEvent.VK_A:
			p1_a_x = -ACCELERATION_CONSTANT;
			break;
		case KeyEvent.VK_D:
			p1_a_x = ACCELERATION_CONSTANT;
			break;
		case KeyEvent.VK_W:
			if(p1_y == 0d)
				p1_v_y = -JUMP_CONSTANT;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_RIGHT:
			p2_a_x = 0;
			break;
		case KeyEvent.VK_A:
		case KeyEvent.VK_D:
			p1_a_x = 0;
			break;
		}
	}
	
	public static double magnitude(double x,double y) {
		return Math.sqrt(Math.pow(x,2)+Math.pow(y, 2));
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
		int opt;
		do {
			panel.reset();
			opt = panel.run();
		} while(opt == REMATCH);
		
		f1.setVisible(false);
		f1.dispose();
	}


}