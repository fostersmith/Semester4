package programs;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class HangmanPanel extends JPanel {

	int width, height;
	int wrongGuesses = 0;
	
	public HangmanPanel(int w, int h) {
		width = w;
		height = h;
	}
	
	public void setWrongGuesses(int num) {
		wrongGuesses = num;
		repaint();
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		int counter = wrongGuesses;
		
		//placeholder
		g2d.drawRect(0,0,width-1,height-1);
		
		if(counter > 0) {
			//head
			g2d.drawOval(width/4, width/4, width/2, width/2);
			--counter;
		} else
			return;
		
		if(counter > 0) {
			//torso
			g2d.drawLine(width/2, width/4 + width/2, width/2, (2*height)/3);
			--counter;
		} else
			return;
		
		if(counter > 0) {
			//left arm
			g2d.drawLine(width/7, height/2, width/2, height/2);
			--counter;
		} else
			return;
		
		if(counter > 0) {
			//right arm
			g2d.drawLine(width/2, height/2, (6*width)/7, height/2);
			--counter;
		} else
			return;
		
		if(counter > 0) {
			//left leg
			g2d.drawLine(width/2, (2*height)/3, width/4, (15*height)/16);
			--counter;
		} else
			return;
		
		if(counter > 0) {
			//right leg
			g2d.drawLine(width/2, (2*height)/3, (3*width)/4, (15*height)/16);
			--counter;
		} else
			return;
	}
	
}
