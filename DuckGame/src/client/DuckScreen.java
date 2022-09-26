package client;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import lib.Duck;

public class DuckScreen extends JPanel {

	ArrayList<Duck> ducks;
	BufferedImage background;
	BufferedImage canvas;
	int focus;
	
	public DuckScreen(ArrayList<Duck> ducks, BufferedImage background) {
		canvas = new BufferedImage(background.getWidth(),background.getHeight(),BufferedImage.TYPE_INT_ARGB);
		this.ducks = ducks;
		this.background = background;
		focus = 0;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		render();
		g2d.drawImage(canvas, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(canvas.getWidth(), canvas.getHeight());
	}
	
	public void render() {

	}
	
	public void doTick() {
		
	}
	
	public void reset() {
		
	}

	
}
