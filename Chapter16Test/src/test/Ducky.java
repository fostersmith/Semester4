package test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Ducky extends JPanel implements ActionListener {
	
	public static final int SIZE = 500;
	private JButton changeButton = new JButton("Change Colors");
	private int buttonWidth = 150;
	String phrase = "Being Quacky is Fun!";
	Font duckFont = new Font("Jokerman", Font.BOLD, 35);
	static Color textCol = Color.CYAN, duckCol = Color.YELLOW, 
			eyeCol = Color.BLACK, billCol = Color.ORANGE, 
			legCol = Color.ORANGE, wingCol = Color.ORANGE;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		textCol = pickRandomColor();
		duckCol = pickRandomColor();
		eyeCol = pickRandomColor();
		billCol = pickRandomColor();
		legCol = pickRandomColor();
		wingCol = pickRandomColor();
		repaint();
	}
	
	public static Color pickRandomColor() {
		return new Color((int)(Math.random()*Integer.MAX_VALUE));
	}
	
	public Ducky() {
		setLayout(null);
		changeButton.setBounds(SIZE/2-buttonWidth/2, 5, buttonWidth, 20);
		changeButton.addActionListener(this);
		add(changeButton);
		
		setBackground(Color.MAGENTA);
	}
		
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setFont(duckFont);
		g2d.setColor(textCol);
		Rectangle2D phraseRect = duckFont.getStringBounds(phrase, g2d.getFontRenderContext());
		g2d.drawString(phrase, (int)(SIZE-phraseRect.getWidth())/2, SIZE/6);
		
		duck(g2d, SIZE/2-SIZE/4, 2*SIZE/7, SIZE/2);
		g2d.setColor(Color.orange);
		//g2d.drawRect(SIZE/2-SIZE/4, 2*SIZE/7, SIZE/2, SIZE/2);
	}
	
	public static void duck(Graphics2D g, int x, int y, int w) {
		
		//g.setStroke(new BasicStroke(8));
		
		int headX=x+3*w/20, headY=y, headW = 2*w/5;
		int bodyX = x+w/20, bodyY = y+2*w/7, bodyW=19*w/20, bodyH = 3*w/5;
		int legWidth = 8, legHeight = w/3, legSpace = w/8;
		

		//legs
		g.setColor(legCol);
		g.fillRect(bodyX+bodyW/2-legSpace-legWidth/2, y+w-legHeight, legWidth, legHeight);
		g.fillRect(bodyX+bodyW/2+legSpace-legWidth/2, y+w-legHeight, legWidth, legHeight);
		//g.fillRect(x+w/3, y+2*w/3, x+w/3, y+w);
		//g.drawLine(x+w/3, y+2*w/3, x+w/3, y+w);
		//g.drawLine(x+2*w/3, y+2*w/3, x+2*w/3, y+w);
		//feet
		Polygon foot1Polygon = new Polygon();
		foot1Polygon.addPoint(bodyX+bodyW/2-legSpace, y+w); //heel
		foot1Polygon.addPoint(bodyX+bodyW/2-legSpace, y+w-w/16); //ankle
		foot1Polygon.addPoint(bodyX+bodyW/2-legSpace-w/10, y+w); //toe
		g.fillPolygon(foot1Polygon);
		Polygon foot2Polygon = new Polygon();
		foot2Polygon.addPoint(bodyX+bodyW/2+legSpace, y+w); //heel
		foot2Polygon.addPoint(bodyX+bodyW/2+legSpace, y+w-w/16); //ankle
		foot2Polygon.addPoint(bodyX+bodyW/2+legSpace-w/10, y+w); //toe
		g.fillPolygon(foot2Polygon);

		//head
		g.setColor(duckCol);
		g.fillOval(headX, headY, headW, headW);
		//body
		g.fillOval(bodyX, bodyY, bodyW, bodyH);
		
		//bill
		g.setColor(billCol);
		Polygon beakPolygon = new Polygon();
		beakPolygon.addPoint(headX-headW/4, headY+headW/2);
		beakPolygon.addPoint(headX+headW/30, headY+headW/2-headW/7);
		beakPolygon.addPoint(headX+headW/30, headY+headW/2+headW/7);
		g.fillPolygon(beakPolygon);
		//wing
		g.setColor(wingCol);
		g.fillArc(bodyX+3*bodyW/10, bodyY+bodyW/15, bodyW/3, bodyH/2, 180, 180);
		
		//eye
		g.setColor(eyeCol);
		g.fillOval(headX+2*headW/10, headY+4*headW/10, 2*headW/15, 2*headW/15);
		
		//hat
		g.setColor(Color.BLACK);
		Polygon hatPolygon = new Polygon();
		hatPolygon.addPoint(headX+headW/2-headW/4, headY+headW/10);
		hatPolygon.addPoint(headX+headW/2+headW/4, headY+headW/10);
		hatPolygon.addPoint(headX+headW/2, headY-headW/3);
		g.fillPolygon(hatPolygon);
		g.setColor(pickRandomColor());
		g.fillOval(headX+headW/2-headW/7, headY-headW/8, headW/10, headW/10);
		g.setColor(pickRandomColor());
		g.fillOval(headX+headW/2+headW/16, headY-headW/10, headW/10, headW/10);
		g.setColor(pickRandomColor());
		g.fillOval(headX+headW/2-headW/40, headY-headW/4, headW/10, headW/10);

	}

	public static void main(String[] args) {
		JFrame f1 = new JFrame("Ducks Rock!");
		f1.add(new Ducky());
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setSize(SIZE, SIZE);
		f1.setVisible(true);
	}

}
