package duckJousting;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PathDesignerPanel extends JPanel implements MouseListener {

	static final int APPEND = 0, MOVE = 1, DELETE = 2;
	
	GeneralPath path = new GeneralPath();
	ArrayList<double[]> points = new ArrayList<>();
	double dX, dY;
	int mode = APPEND;
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.draw(path);
	}
	
	public void updatePath() {
		path.reset();
		for(int i = 0; i < points.size(); ++i) {
			double[] p = points.get(i);
			if(i != 0) {
				path.lineTo(p[0], p[1]);
			} else {
				path.moveTo(p[0],p[1]);
			}
		}
		
		repaint();
	}
	
	public void addPoint(double x, double y) {
		points.add(new double[] {x,y});
		updatePath();
	}
	
	public void removePoint(int i) {
		points.remove(i);
		updatePath();
	}
	
	public void replacePoint(int i, double x, double y) {
		points.set(i, new double[] {x, y});
		updatePath();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(mode == APPEND) {
			addPoint(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public PathDesignerPanel() {
		addMouseListener(this);
	}
	
	public static void main(String[] args) {
		JFrame f1 = new JFrame("Path Designer");
		f1.add(new PathDesignerPanel());
		f1.setSize(720,500);
		f1.setResizable(false);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setVisible(true);

	}


}
