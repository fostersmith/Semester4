package ydis;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class JStringPanel extends JPanel {
	String string;
	Font font;
	Color color;
	int horizontal;
	int vertical;
	
	public JStringPanel(String s, Font f, Color c, int x, int y) {
		string = s;
		font = f;
		color = c;
		horizontal = x;
		vertical = y;
		setBackground(Color.WHITE);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(font);
		g.setColor(color);
		g.drawString(string, horizontal, vertical);
	}
	
}
