package carlysCatering;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class CarlysLogoPanel extends JPanel {

	int width, height;
	
	public CarlysLogoPanel(int w, int h) {
		width = w;
		height = h;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int sandwichHeight = height/3;
		int sandwichStart = (height-sandwichHeight)/2;
		
		int breadHeight = sandwichHeight/4;
		int meatHeight = (sandwichHeight)/6;
		int tomatoHeight = (sandwichHeight)/6; // 2/4+2/6 = 1/2 + 1/3 = 3/6+2/6 = 5/6
		int lettuceHeight = 1*(sandwichHeight)/12; //amount lettuce rises above tomato
		int cheeseHeight = 1*(sandwichHeight)/12; //amount cheese rises above top of lettuce
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.orange);
		g2d.fillOval(width/4-breadHeight/4, sandwichStart+breadHeight+cheeseHeight+lettuceHeight+tomatoHeight+meatHeight, breadHeight/2, breadHeight);
		g2d.fillOval(3*width/4-breadHeight/4, sandwichStart+breadHeight+cheeseHeight+lettuceHeight+tomatoHeight+meatHeight, breadHeight/2, breadHeight);
		g2d.fillRect(width/4, sandwichStart+breadHeight+cheeseHeight+lettuceHeight+tomatoHeight+meatHeight, width/2, breadHeight);
		
		g2d.setColor(Color.PINK);
		g2d.fillRect(width/4, sandwichStart+breadHeight+cheeseHeight+lettuceHeight+tomatoHeight, width/2, meatHeight);
		
		g2d.setColor(Color.RED);
		g2d.fillRect(width/4, sandwichStart+breadHeight+cheeseHeight+lettuceHeight, width/2, tomatoHeight);
		
		g2d.setColor(Color.GREEN);
		int lettuceDiameter = 2*lettuceHeight;
		int numLettuces = (width/2)/(lettuceDiameter);
		g2d.fillRect(width/4+width/(4*numLettuces), sandwichStart+breadHeight+cheeseHeight, width/2-width/(2*numLettuces), width/(4*numLettuces-4));
		for(int i = 0; i < numLettuces; ++i) {
			g2d.fillOval(width/4+(i*(width/2)/numLettuces), sandwichStart+breadHeight+cheeseHeight, lettuceDiameter, lettuceDiameter);
		}
		
		g2d.setColor(Color.yellow);
		g2d.fillPolygon(new int[] {width/4, width/2, (3*width)/4}, new int[] {sandwichStart + breadHeight, sandwichStart + breadHeight + sandwichHeight/4, sandwichStart + breadHeight}, 3);
		
		g2d.setColor(Color.ORANGE);
		g2d.fillOval(width/4-breadHeight/4, sandwichStart, breadHeight/2, breadHeight);
		g2d.fillOval(3*width/4-breadHeight/4, sandwichStart, breadHeight/2, breadHeight);
		g2d.fillRect(width/4, sandwichStart, width/2, breadHeight);
		
		Font logoFont = new Font("Broadway", Font.PLAIN, 100);
		g2d.setColor(Color.BLUE);
		g2d.setFont(logoFont);
				
		Rectangle2D carlys = logoFont.getStringBounds("Carly's", g2d.getFontRenderContext());
		Rectangle2D catering = logoFont.getStringBounds("Catering", g2d.getFontRenderContext());
				
		g2d.setPaint(new GradientPaint( 0, height/7 +(int)carlys.getCenterY(), Color.BLUE,  0, height/7 - 3*(int)carlys.getCenterY(),Color.WHITE, true));
		g2d.drawString("Carly's", width/2 - (int)carlys.getCenterX(), height/7 -(int)carlys.getCenterY());
		g2d.setPaint(new GradientPaint( 0, 6*height/7 + (int)catering.getCenterY(), Color.BLUE,  0, 6*height/7 - 3*(int)catering.getCenterY(),Color.WHITE, true));
		g2d.drawString("Catering", width/2 - (int)catering.getCenterX(), 6*height/7 - (int)catering.getCenterY());
	}
	
	//temp
	public static void main(String[] args) {
		JFrame f1 = new JFrame();
		f1.add(new CarlysLogoPanel(500,500));
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.pack();
		f1.setResizable(false);
		f1.setVisible(true);
	}


}
