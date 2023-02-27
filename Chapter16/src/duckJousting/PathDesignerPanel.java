package duckJousting;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.GeneralPath;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PathDesignerPanel extends JPanel implements MouseListener {

	static final int APPEND = 0, MOVE = 1, DELETE = 2;
	
	GeneralPath path = new GeneralPath();
	int selectedIndex = -1;
	ArrayList<ArrayList<double[]>> points = new ArrayList<>();
	double dX, dY;
	int mode = APPEND;
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.draw(path);
	}
	
	public int getSelectedIndex() {
		return selectedIndex;
	}
	
	public void setSelectedIndex(int i) {
		selectedIndex = i;
	}
	
	public int getMode() {
		return mode;
	}
	
	public void setMode(int m) {
		mode = m;
	}
	
	public void updatePath() {
		path.reset();
		if(selectedIndex >= 0) {
			for(int i = 0; i < points.size(); ++i) {
				double[] p = points.get(selectedIndex).get(i);
				if(i != 0) {
					path.lineTo(p[0], p[1]);
				} else {
					path.moveTo(p[0],p[1]);
				}
			}
		}
		
		repaint();
	}
	
	public static ArrayList<double[]> loadPoints(File f){
		//TODO
		return new ArrayList<double[]>();
	}
	
	public static boolean savePoints(File f, ArrayList<double[]> points) {
		//TODO
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write("placeholder");
			bw.flush();
			bw.close();
			return true;
		} catch(IOException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
	public void addPath(ArrayList<double[]> path) {
		
	}
	
	public void addPoint(double x, double y) {
		points.get(selectedIndex).add(new double[] {x,y});
		updatePath();
	}
	
	public void removePoint(int i) {
		points.get(selectedIndex).remove(i);
		updatePath();
	}
	
	public void replacePoint(int i, double x, double y) {
		points.get(selectedIndex).set(i, new double[] {x, y});
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
