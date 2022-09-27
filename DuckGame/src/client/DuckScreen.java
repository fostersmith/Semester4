package client;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import lib.Duck;
import lib.DuckSprite;

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
		renderSpriteSheet(ducks.get(focus).currentSprite());
		this.repaint();
	}
	
	private void renderSpriteSheet(DuckSprite sprite) {
		Graphics2D g = (Graphics2D)canvas.getGraphics();
		g.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
		int pxHeight = canvas.getHeight() / DuckSprite.HEIGHT;
		int pxWidth = canvas.getHeight() / DuckSprite.WIDTH;
		for(int x = 0; x < DuckSprite.WIDTH; ++x) {
			for(int y = 0; y < DuckSprite.HEIGHT; ++y) {
				g.setColor(sprite.getPixel(x, y));
				int pxX = x * pxWidth;
				int pxY = y * pxHeight;
				g.fillRect(pxX, pxY, pxWidth, pxHeight);
			}
		}
	}
	
	public void doTick() {
		
	}
	
	public void reset() {
		
	}

	
}
