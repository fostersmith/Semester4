package client;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import lib.Duck;
import lib.DuckSprite;

public class DuckScreen extends JPanel {

	ArrayList<Duck> ducks;
	BufferedImage background;
	BufferedImage canvas;
	int focus;
	Timer ticks, render;
	
	public DuckScreen(ArrayList<Duck> ducks, BufferedImage background) {
		canvas = new BufferedImage(background.getWidth(),background.getHeight(),BufferedImage.TYPE_INT_ARGB);
		this.ducks = ducks;
		this.background = background;
		focus = 0;
		ActionListener tickListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doTick();
			}
		};
		ticks = new Timer(100, tickListener);
		ActionListener renderListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				render();
			}
		};
		render = new Timer(50, renderListener);
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
	
	public synchronized void doTick() {
		System.out.println("tick");
		if(Math.random() >= 0.75) {
			ducks.get(focus).state = Duck.chooseState();
			System.out.println("new state: "+ducks.get(focus).state);
		}
		if(ducks.get(focus).state == Duck.WALKING)
			ducks.get(focus).step();
	}
	
	public synchronized void render() {
		renderSpriteSheet(ducks.get(focus).currentSprite());
		this.repaint();
	}
	
	public void stop() {
		ticks.stop();
		render.stop();
	}
	
	public void start() {
		ticks.restart();
		render.restart();
	}

	
}
