/*
 * DuckiePicture.java
 * Foster Smith
 * 2/14/23
 */

package practice;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DuckiePicture extends JPanel {

	static JFrame f1;
	static DuckiePicture duckiePanel;
	static Toolkit tk;

	JLabel coordLabel = new JLabel();
	static int frameWidth = 1023, frameHeight = 550;
	static BufferedImage bImage;
	BufferedImage unicornImage, unicorn1Image, rainbowImage;
	int x, y;
	Font coordFont = new Font("HoboSTD", Font.BOLD, 12);

	public DuckiePicture() {
		setBackground(new Color(127, 70, 240));
		coordLabel.setFont(coordFont);
		coordLabel.setForeground(Color.WHITE);
		add(coordLabel);

		// add a mouse motion listener to update x/y coordinates as the user moves the mouse
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent me) {
				x = me.getX();
				y = me.getY();
				coordLabel.setText(x + ", " + y);
				coordLabel.repaint();
			}
		});
	}

	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		Graphics2D gr2D = (Graphics2D)gr;

		GeneralPath duckiePath = new GeneralPath();
		GeneralPath duckieBillPath = new GeneralPath();
		int x = 300;
		int y = 50;
		int[] xPoints = {143+x,162+x,162+x,170+x,174+x,173+x,172+x,190+x,191+x,196+x,195+x,199+x,200+x,202+x,199+x,193+x,191+x,197+x,227+x,243+x,
				277+x,299+x,313+x,324+x,339+x,352+x,360+x,347+x,386+x,364+x,397+x,373+x,383+x,382+x,381+x,371+x,353+x,339+x,321+x,307+x,283+x,268+x,199+x,142+x,121+x,
				107+x,95+x,87+x,80+x,83+x,91+x,73+x,69+x,70+x,78+x,84+x,100+x,130+x,144+x,143+x};
		int[] yPoints = {3 + y,19 + y,27 + y,13 + y,25 + y,23 + y,35 + y,23 + y,37 + y,55 + y,81 + y,95 + y,102 + y,116 + y,133 + y,156 + y,171 + y,172 + y,168 + y,166 + y,
				168 + y,174 + y,182 + y,188 + y,161 + y,144 + y,137 + y,181 + y,146 + y,188 + y,176 + y,211 + y,263 + y,292 + y,295 + y,327 + y,349 + y,362 + y,372 + y,379 + y,381 + y,381 + y,381 + y,381 + y,370 + y,
				355 + y,340 + y,322 + y,295 + y,255 + y,212 + y,129 + y,92 + y,88 + y,67 + y,60 + y,46 + y,33 + y,19 + y,3 + y};



		int[] xBillPoints = {74+x,83+x,86+x,104+x,112+x,128+x,131+x,140+x,155+x,160+x,165+x,169+x,158+x,144+x,115+x,
				90+x,72+x,48+x,56+x,58+x,41+x,19+x,8+x,6+x,9+x,12+x,27+x,36+x,51+x,66+x,74+x};
		int[] yBillPoints = {130 + y,120 + y,108 + y,109 + y,113 + y,127 + y,135 + y,136 + y,141 + y,148 + y,157 + y,160 + y,180 + y,193 + y,205 + y,
				209 + y,208 + y,204 + y,196 + y,190 + y,185 + y,173 + y,161 + y,150 + y,149 + y,150 + y,152 + y,151 + y,145 + y,137 + y,130 + y};

		// creates the border using gradient
		gr2D.setPaint(new GradientPaint(10, 10,Color.red, 397, 381, new Color(42, 61, 250), true));
		gr2D.fillRect(0, 0, frameWidth, 20);
		// -49 on school computers
		gr2D.fill(new Rectangle(0, frameHeight-49, frameWidth, 20));
		gr2D.fillRect(0, 0, 20, frameHeight);
		// -29 on school computers
		gr2D.fill( new Rectangle(frameWidth-29, 0, 23, frameHeight));

		//draw the duck
		BasicStroke aStroke = new BasicStroke(5.0f);
		gr2D.setStroke(aStroke);
		duckiePath.moveTo(xPoints[0], yPoints[0]);
		for(int i = 1; i < xPoints.length; ++i) {
			duckiePath.lineTo(xPoints[i], yPoints[i]);
		}
		duckiePath.closePath();
		gr2D.setColor(new Color(255,153,0));
		gr2D.draw(duckiePath);
		gr2D.setPaint(new GradientPaint(10, 10, new Color(255,255,153), 397, 381, new Color(255, 140, 1), true));
		gr2D.fill(duckiePath);

		//draw the bill
		gr2D.setColor(new Color(255,153,0));
		aStroke = new BasicStroke(10.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		gr2D.setStroke(aStroke);
		duckieBillPath.moveTo(xBillPoints[0], yBillPoints[1]);
		for(int i = 1; i < xBillPoints.length; ++i) {
			duckieBillPath.lineTo(xBillPoints[i], yBillPoints[i]);
		}
		duckieBillPath.closePath();
		gr2D.draw(duckieBillPath);
		gr2D.setPaint(new GradientPaint(0, 144, new Color(250,101,0), 167, 158, new Color(255, 153, 1), true));
		gr2D.fill(duckieBillPath);

		//Eyes -black part
		gr2D.setColor(Color.black);//new Color(255, 255, 180));
		gr2D.fillOval(70+x, 79+y, 15, 35);
		gr2D.fillOval(130+x, 79+y, 20, 40);

		//Eyes -red part
		gr2D.setColor( new Color(255, 100, 102));
		gr2D.fillOval(73+x, 94+y, 8, 12);
		gr2D.fillOval(135+x, 97+y, 8, 12);

		//draw the heart
		gr2D.setColor(Color.red);
		gr2D.setStroke(aStroke);
		GeneralPath heartPath = drawHeart(100, 240+x, 215+y);
		gr2D.draw(heartPath);
		gr2D.setPaint(new GradientPaint(447, 236, new Color(255,205,255), 482, 236, new Color(255, 0, 102), true));
		gr2D.fill(heartPath);
		
		try {
			int unicornY = 25;
			unicornImage = ImageIO.read(new FileInputStream("C:\\Users\\fsmith\\Semester4\\Chapter16\\unicorn.png"));
			unicorn1Image = ImageIO.read(new FileInputStream("C:\\Users\\fsmith\\Semester4\\Chapter16\\unicorn1.jfif"));
			gr2D.drawImage(unicornImage, 25, unicornY, 50, 50, duckiePanel);
			
			for(int i = 0; i < 7; ++i) {
				gr2D.copyArea(25, unicornY, 50, 50, 0, 50);
				unicornY += 50;
			}
			unicornY = 25;
			
			AffineTransform tx = new AffineTransform();
			tx.translate(950, unicornY);
			tx.scale(.4, .4);
			tx.rotate(Math.toRadians(40));
			
			gr2D.drawImage(unicorn1Image, tx, duckiePanel);
			for(int i = 0; i < 7; ++i) {
				gr2D.copyArea(940, unicornY, 50, 50, 0, 50);
				unicornY += 50;
			}
			
		} catch(IOException ex) { ex.printStackTrace(); }
	}


	public GeneralPath drawHeart(double adjustSize,double adjustX,double adjustY)
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

	public static void saveImage() {
		bImage = new BufferedImage(frameWidth - 15, frameHeight - 37, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bImage.createGraphics();
		duckiePanel.paint(g2);

		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter= new FileNameExtensionFilter("JPG Images", "jpg");
		fileChooser.setFileFilter(filter);
		int result = fileChooser.showSaveDialog(duckiePanel);
		if(result == JFileChooser.APPROVE_OPTION) {
			File saveFile = fileChooser.getSelectedFile();
			try {
				ImageIO.write(bImage, "jpg", saveFile);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		duckiePanel = new DuckiePicture();
		tk = Toolkit.getDefaultToolkit();
		Dimension screen = tk.getScreenSize();
		int screenWidth = screen.width;
		int screenHeight = screen.height;
		int frameX = (screenWidth - frameWidth)/2;
		int frameY = (screenHeight - frameHeight)/2;

		f1 = new JFrame();
		f1.setTitle("Duckie");
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setResizable(false);
		f1.setBounds(frameX, frameY, frameWidth, frameHeight);
		f1.setVisible(true);
		f1.add(duckiePanel);

		//set Jframe icon
		Image icon = tk.getImage("duck.png");
		f1.setIconImage(icon);

		//saveImage();
	}

}
