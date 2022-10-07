package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

public class DuckScreen extends JPanel implements MouseListener {

	ArrayList<Duck> ducks;
	BufferedImage background;
	BufferedImage canvas;
	int focus;
	int duckHeight, duckWidth;
	Timer ticks, render;
	int speed;
	
	public DuckScreen(ArrayList<Duck> ducks, BufferedImage background) {
		this.ducks = ducks;
		setBackground(background);
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
		addMouseListener(this);
	}
	
	public void setBackground(String path) throws IOException {
		File f = Paths.get(path).toFile();
		setBackground(ImageIO.read(f));
	}
	
	public void setBackground(BufferedImage img) {
		this.background = img;
		canvas = new BufferedImage(background.getWidth(),background.getHeight(),BufferedImage.TYPE_INT_ARGB);
		duckHeight = canvas.getHeight() / 5;
		duckWidth = canvas.getHeight() / 5;
		speed = duckHeight/10;
		for(Duck d:ducks) {
			d.setX(canvas.getWidth()/2);
			d.setY(canvas.getHeight()/2);
		}
		setBounds(0,0,canvas.getWidth(), canvas.getHeight());
		render();
		repaint();
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
		
	private void renderSpriteSheet(DuckSprite sprite, int x0, int y0) {
		Graphics2D g = (Graphics2D)canvas.getGraphics();
		g.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
		int pxHeight = duckHeight/DuckSprite.HEIGHT;
		int pxWidth = duckWidth/DuckSprite.WIDTH;
		for(int x = 0; x < DuckSprite.WIDTH; ++x) {
			for(int y = 0; y < DuckSprite.HEIGHT; ++y) {
				g.setColor(sprite.getPixel(x, y));
				int pxX = x * pxWidth + x0;
				int pxY = y * pxHeight + y0;
				g.fillRect(pxX, pxY, duckWidth/DuckSprite.WIDTH, duckHeight/DuckSprite.HEIGHT);
			}
		}
	}
	
	private void renderLine(int x0, int y0, int x1, int y1, Color c) {
		Graphics g = canvas.getGraphics();
		g.setColor(c);
		g.drawLine(x0, y0, x1, y1);
	}
	
	private void renderNoDucks() {
		Graphics2D g = (Graphics2D)canvas.getGraphics();
		g.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
		g.setColor(Color.RED);
		g.drawString("You Have No Ducks", canvas.getWidth()/2-10, canvas.getHeight()/2);
	}
	
	public synchronized void doTick() {
		if(ducks.size() > 0) {
			boolean changeState;
			if(ducks.get(focus).state == Duck.WALKING || ducks.get(focus).state == Duck.SITTING)
				changeState = Math.random() > 0.9;
			else
				changeState = Math.random() > 0.75;
			changeState = changeState && !ducks.get(focus).hasGoal();
			if(changeState) {
				ducks.get(focus).state = Duck.chooseState();
				System.out.println("new state: "+ducks.get(focus).state);
			}
			if(ducks.get(focus).state == Duck.WALKING)
				ducks.get(focus).step();
			ducks.get(focus).walk(speed);
			if(ducks.get(focus).getX() < 0)
				ducks.get(focus).setX(canvas.getWidth()-duckWidth);
			else if(ducks.get(focus).getX()+duckWidth > canvas.getWidth())
				ducks.get(focus).setX(0);
			if(ducks.get(focus).getY() < 0)
				ducks.get(focus).setY(canvas.getHeight()-duckHeight);
			else if(ducks.get(focus).getY()+duckHeight > canvas.getHeight())
				ducks.get(focus).setY(0);
		}
	}
	
	public synchronized void render() {
		if(ducks.size() > 0) {
			renderSpriteSheet(ducks.get(focus).currentSprite(), ducks.get(focus).getX(), ducks.get(focus).getY());
			if(ducks.get(focus).hasGoal())
				renderLine(ducks.get(focus).getX(),ducks.get(focus).getY(),ducks.get(focus).getGoalX(), ducks.get(focus).getGoalY(), Color.RED);
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

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("click @ "+e.getX()+" "+e.getY());
		if(ducks.size() > 0) {
			ducks.get(focus).goTo(e.getX(), e.getY(),speed);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
}
