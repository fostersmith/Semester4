package useless;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.GeneralPath;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

public class PathDesignerPanel extends JPanel implements MouseListener {

	static final int APPEND = 0, MOVE = 1, DELETE = 2;
	
	File selectedFile = null;
	HashMap<File, ArrayList<double[]>> points = new HashMap<>();
	HashMap<File, Boolean> visible = new HashMap<>();
	double dX, dY;
	int moveInd = -1;
	int mode = APPEND;
	Thread moverThread = new Thread() {
		@Override
		public void run() {
			while(true) {
				if(moveInd > -1) {
					Point pointer = MouseInfo.getPointerInfo().getLocation();
					points.get(selectedFile).set(moveInd, new double[] {pointer.x+dX, pointer.y+dY});
				}
			}
		}
	};
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		for(File f : points.keySet()) {
			if(visible.get(f)) {
				ArrayList<double[]> pointList = points.get(f);
				GeneralPath path = new GeneralPath();
				
				for(int i = 0; i < pointList.size(); ++i) {
					if(i == 0)
						path.moveTo(pointList.get(i)[0], pointList.get(i)[1]);
					else 
						path.lineTo(pointList.get(i)[0], pointList.get(i)[1]);
					
					System.out.println(i+": "+pointList.get(i)[0]+","+pointList.get(i)[1]);
				}
				g2d.draw(path);
			}
		}
	}
	
	public File getSelectedFile() {
		return selectedFile;
	}
	
	public void setSelectedFile(File f) {
		selectedFile = f;
	}
	
	public int getMode() {
		return mode;
	}
	
	public void setMode(int m) {
		mode = m;
	}
	
	public void saveSelected() {
		savePoints(selectedFile, points.get(selectedFile));
	}
		
	public static ArrayList<double[]> loadPoints(File f){
		try {
			ArrayList<double[]> out = new ArrayList<>();
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = br.readLine();
			while(line != null && line != "") {
				String[] parts = line.split(",");
				double x = Double.parseDouble(parts[0]);
				double y = Double.parseDouble(parts[1]);
				out.add(new double[] {x, y});
				line = br.readLine();
			}
			return out;
		} catch (IOException e) {
			return null;
		}
	}
	
	public static boolean savePoints(File f, ArrayList<double[]> points) {
		//TODO
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			for(int i = 0; i < points.size(); ++i) {
				if(i != 0)
					bw.write("\n");
				bw.write(points.get(i)[0]+","+points.get(i)[1]);
			}
			bw.flush();
			bw.close();
			return true;
		} catch(IOException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
	public void addPath(ArrayList<double[]> path, File f) {
		points.put(f, path);
		visible.put(f, true);
	}
	
	public void addPoint(double x, double y) {
		if(selectedFile != null) {
			points.get(selectedFile).add(new double[] {x,y});
			System.out.println(x+","+y);
			repaint();
		}
	}
	
	public void removePoint(int i) {
		points.get(selectedFile).remove(i);
		repaint();
	}
	
	public void replacePoint(int i, double x, double y) {
		points.get(selectedFile).set(i, new double[] {x, y});
		repaint();
	}
	
	public void setVisible(File f, boolean isVisible) {
		visible.put(f, isVisible);
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
		List<double[]> pointList = points.get(selectedFile);
		
		if(pointList.size() > 0 && mode == MOVE) {
			int closestInd = 0;
			double closestDist = -1d;
			for(int i = 0; i < pointList.size(); ++i) {
				double x = pointList.get(i)[0], y = pointList.get(i)[1];
				double dist = Math.sqrt(Math.pow(x-e.getX(), 2)+Math.pow(y-e.getY(), 2));
				if(dist < closestDist || closestDist < 0) {
					closestInd = i;
					closestDist = dist;
				}
			}
			
			moveInd = closestInd;
			dX = pointList.get(moveInd)[0]-e.getX();
			dY = pointList.get(moveInd)[1]-e.getY();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public PathDesignerPanel() {
		moverThread.start();
		addMouseListener(this);
	}
	
	/*public static void main(String[] args) {
		JFrame f1 = new JFrame("Path Designer");
		f1.add(new PathDesignerPanel());
		f1.setSize(720,500);
		f1.setResizable(false);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setVisible(true);

	}*/


}
