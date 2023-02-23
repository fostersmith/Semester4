package programs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JStatePanel extends JPanel {

	double boatx = 1300, boaty = 100;
	double boatVx = 5, boatVy = 2;
	//double theta = Math.atan2(boatVy,boatVx);
	double speed = Math.sqrt(boatVx*boatVx+boatVy*boatVy);
	
	boolean boatMoving = true;
	
	public JStatePanel() {
		setBackground(Color.cyan);
		Thread boatThread = new Thread() {
			@Override
			public void run() {
				while(boatMoving) {
					boolean hitWall = false;
					if(boatx > 1421) {
						boatVx = Math.abs(boatVx)*-1;
						hitWall = true;
					} if(boatx < 1200) {
						boatVx = Math.abs(boatVx);
						hitWall = true;
					} if(boaty > 400) {
						boatVy = Math.abs(boatVy)*-1;		
						hitWall = true;
					} if(boaty < 1) {
						boatVy = Math.abs(boatVy);
						hitWall = true;
					}
					
					/*if(hitWall) {
						theta = Math.atan2(boatVy,boatVx);
					}*/
					
					if(!hitWall && Math.random()>0.9) {
						//theta += Math.random()*0.2d-0.1d;
						//theta %= Math.PI*2;
						double newTheta = Math.random()*2*Math.PI;
						boatVx = Math.cos(newTheta)*speed;
						boatVy = Math.sin(newTheta)*speed;
					}
					
					boatx += boatVx;
					boaty += boatVy;
					repaint();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		
		boatThread.start();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)(g);
		GeneralPath statePath = new GeneralPath();
		g2d.setStroke(new BasicStroke(3.7f,BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2d.setColor(Color.magenta);
		
		double s = 0.0037f;
		double x = 100f;
		double y = -3200f;

		g2d.setColor(Color.gray);
		g2d.fillRect(0, 0, 314, 1000);
		
		
		for(int i = 0; i <= 6; ++i) {
			GeneralPath p = drawState(i, s, x, y);
			g2d.setColor(Color.GRAY);
			g2d.fill(p);
		}
		
		statePath = drawState(2,s,x,y);
		
		g2d.setPaint(new GradientPaint(100, 100, new Color(220, 14, 250), 1000, 1000, new Color(255, 205, 0), true));
		g2d.fill(statePath);
		g2d.setPaint(new GradientPaint(100, 100, new Color(157, 11, 179), 1000, 1000, new Color(184, 147, 0), true));
		g2d.draw(statePath);
		//g2d.setColor(new Color(128, 0, 255));
		g2d.setFont(new Font("Old English Text MT", Font.BOLD, 70));
		g2d.drawString("Massachusetts", 400, 380);
		
		g2d.setColor(Color.BLACK);
		GeneralPath heartPath = drawHeart(100, 960, 340);
		g2d.fill(heartPath);
		g2d.setFont(new Font("Engravers MT", Font.BOLD, 20));
		g2d.drawString("MIT", 960, 340);
		
		try {
			BufferedImage boat = ImageIO.read(new File("sailboat.png"));
			g2d.drawImage(boat, (int)boatx, (int)boaty, 60, 60, null);
		} catch(IOException e) {}
	}
	
	public static GeneralPath drawHeart(double adjustSize,double adjustX,double adjustY)
	{
		double y;
		GeneralPath heartPath=new GeneralPath();for(double i=-2;i<=2;i+=.001)
		{
			y=-Math.sqrt(1-Math.pow((Math.abs(i)-1),2));
			if(i==-2)
			{
				heartPath.moveTo(((i*adjustSize+250)*.1)+adjustX,((y*adjustSize+200)*.1)+adjustY);
			}
			heartPath.lineTo(((i*adjustSize+250)*.1)+adjustX,((y*adjustSize+200)*.1)+adjustY);
		}for(double i=-2;i<=2;i+=.001)
		{
			y=3*(Math.sqrt(1-((Math.sqrt(Math.abs(i))/Math.sqrt(2)))));
			if(i==-2)
			{
				heartPath.moveTo(((i*adjustSize+250)*.1)+adjustX,((y*adjustSize+200)*.1)+adjustY);
			}
			heartPath.lineTo(((i*adjustSize+250)*.1)+adjustX,((y*adjustSize+200)*.1)+adjustY);
		}
		return heartPath;
	}
	
	public static GeneralPath drawState(int i, double s, double x, double y) {
		double[][] points = getStatePoints(i);
		GeneralPath p = new GeneralPath();
		p.moveTo(points[0][0]*s+x, 500-(points[0][1]*s+y));
		for(int n = 0; n < points.length; ++n) {
			p.lineTo(points[n][0]*s+x, 500-(points[n][1]*s+y ));
		}
		p.closePath();
		return p;
	}
	
	public static double[][] getStatePoints(int i){
		try {
			String xName = i+"_xCoords.txt";
			String yName = i+"_yCoords.txt";
			BufferedReader xIn = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(xName))));
			BufferedReader yIn = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(yName))));
		
			String[] xStrings = xIn.readLine().split(",");
			String[] yStrings = yIn.readLine().split(",");
			
			double[][] out = new double[xStrings.length][2];
			for(int n = 0; n < xStrings.length; ++n) {
				out[n][0] = Double.parseDouble(xStrings[n]);
				out[n][1] = Double.parseDouble(yStrings[n]);
			}
			
			xIn.close();
			yIn.close();
			return out;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static void main(String[] args) {
		JFrame f1 = new JFrame("The superior state panel program");
		f1.setResizable(false);
		f1.add(new JStatePanel());
		f1.setSize(1500,900);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setVisible(true);
	}
}


/*
import shapefile

def write_shape(shape, filename):
    with open(filename, 'w') as f:
        for point in shape.points:
            f.write(f'')

r = shapefile.Reader("NEWENGLAND_POLY.shp")
shapes = r.shapes()

i = 0
for shape in shapes:
    
    i += 1*/
