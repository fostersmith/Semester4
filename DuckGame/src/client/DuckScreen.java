package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.imageio.ImageIO;
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
		ticks = new Timer(200, tickListener);
		ActionListener renderListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				render();
			}
		};
		render = new Timer(50, renderListener);
	}
	
	public void setBackground(String path) throws IOException {
		File f = Paths.get(path).toFile();
		background = ImageIO.read(f);
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
	
	private void renderNoDucks() {
		Graphics2D g = (Graphics2D)canvas.getGraphics();
		g.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
		g.setColor(Color.RED);
		g.drawString("You Have No Ducks", canvas.getWidth()/2-10, canvas.getHeight()/2);
	}
	
	public synchronized void doTick() {
		if(ducks.size() > 0) {
			if(Math.random() >= 0.75) {
				ducks.get(focus).state = Duck.chooseState();
				System.out.println("new state: "+ducks.get(focus).state);
			}
			if(ducks.get(focus).state == Duck.WALKING)
				ducks.get(focus).step();
		}
	}
	
	public synchronized void render() {
		if(ducks.size() > 0) {
			renderSpriteSheet(ducks.get(focus).currentSprite());
			this.repaint();
		} else {
			renderNoDucks();
		}
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
