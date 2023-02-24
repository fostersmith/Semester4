package carlysCatering;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

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
		
		int sandwichHeight = height/2;
		int sandwichStart = (height-sandwichHeight)/2;
		
		int breadHeight = height/16;
		int meatHeight = (sandwichHeight-2*breadHeight)/4;
		int tomatoHeight = (sandwichHeight-2*breadHeight)/4;
		int lettuceHeight = (sandwichHeight-2*breadHeight)/8; //amount lettuce rises above tomato
		int cheeseHeight = 3*(sandwichHeight-2*breadHeight)/8; //amount cheese rises above top of lettuce
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.orange);
		g2d.fillRect(width/4, sandwichStart+breadHeight+cheeseHeight+lettuceHeight+cheeseHeight+meatHeight, width/2, breadHeight);
		
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
		g2d.fillRect(width/4, sandwichStart, width/2, breadHeight);
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
