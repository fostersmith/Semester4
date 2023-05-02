package inverseKinematics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class InverseKinematics extends JPanel implements MouseListener {

	int d = 100;
	double t0 = 0.1, t1 = Math.PI-0.1;
	
	double goalX = Double.NaN, goalY = Double.NaN;
	
	int ticksPerSecond = 50;
	long tickLen = 1000/ticksPerSecond;
	
	Thread updateThread, repaintThread;
	
	JPanel self;
	
	public InverseKinematics() {
		
		updateThread = new Thread() {
			
			private static double cot(double a) {
				return Math.cos(a)/Math.sin(a);
			}
			
			@Override
			public void run() {
				
				while(true) {
					
					long tickStart = System.currentTimeMillis();
					
					// check to ensure there is a goal
					if(!(Double.isNaN(goalX) || Double.isNaN(goalY))) {
						double x = d*(Math.cos(t0) + Math.cos(t1));
						double y = d*(Math.sin(t0) + Math.sin(t1));
						
						double dx = goalX - x;
						double dy = goalY - y;
						
						double magnitude = Math.sqrt(Math.pow(dx, 2)+Math.pow(dy, 2));
						
						dx /= magnitude;
						dy /= magnitude;
						
						/*dx *= 0.1;
						dy *= 0.1;*/
						
						//dx *= 100;
						//dy *= 100;
						
						double dt1 = ( dy+dx*cot(t0) ) / (d*Math.cos(t1) - d*Math.sin(t1)*cot(t0));
						double dt0 = (dx-dt1*(-d*Math.sin(t1)))/(-d*Math.sin(t0));
						
						magnitude = Math.sqrt( Math.pow(dt0, 2) + Math.pow(dt1, 2) );
						
						dt0 /= magnitude;
						dt1 /= magnitude;
						
						dt0 *= 0.001;
						dt1 *= 0.001;
						
						if(!Double.isNaN(dt0))
							t0 += dt0/ticksPerSecond;
						if(!Double.isNaN(dt1))
							t1 += dt1/ticksPerSecond;
						
						if(Math.sqrt( Math.pow(goalX-x, 2) + Math.pow(goalY-y, 2)) <= 1) {
							goalX = Double.NaN;
							goalY = Double.NaN;
							System.out.println("Got there");
						}
						
					}
					
					long elapsed = System.currentTimeMillis()-tickStart;
					
					repaint();
					
					if(elapsed > 0) {
						try {
							Thread.sleep(tickLen-elapsed);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}					
					
				}
				
			}
			
		};
		
		updateThread.start();
		
		
	}
	
	@Override
	public synchronized void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g.setColor(Color.BLACK);
		
		int x0 = (int)(d*Math.cos(t0));
		int y0 = (int)(d*Math.sin(t0));
		int x1 = x0+(int)(d*Math.cos(t1));
		int y1 = y0+(int)(d*Math.sin(t1));
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(5.0f));
		
		g2d.drawLine(250, 250, 250+x0, 250-y0);
		g2d.drawLine(250+x0, 250-y0, 250+x1, 250-y1);
	}
	
	public static void main(String[] args) {
		InverseKinematics p = new InverseKinematics();
		JFrame f1 = new JFrame();
		p.addMouseListener(p);
		
		f1.setSize(500,500);
		
		f1.add(p);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		goalX = e.getX()-250;
		goalY = 250-e.getY();
		
		System.out.println("click");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
