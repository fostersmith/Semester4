package ydis;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class JDemoFontMetrics extends JPanel {
	String movieQuote = "Go ahead, make my day";
	Font courierItalic = new Font("Courier New", Font.ITALIC, 16),
			timesPlain =  new Font("Times New Roman", Font.PLAIN, 16),
			scriptBold = new Font("Freestyle Script", Font.BOLD, 16);
	int ascent, descent, height, leading;
	int x, y;
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		x = 20;
		y = 30;
		g.setFont(courierItalic);
		displayMetrics(g);
		g.setFont(timesPlain);
		displayMetrics(g);
		g.setFont(scriptBold);
		displayMetrics(g);
	}
	
	public void displayMetrics(Graphics g) {
		leading = g.getFontMetrics().getLeading();
		ascent = g.getFontMetrics().getAscent();
		descent = g.getFontMetrics().getDescent();
		height = g.getFontMetrics().getHeight();
		
		g.drawString(movieQuote, x, y += height);
	}
}
