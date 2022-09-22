package client;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import lib.Duck;

public class DuckGame extends JPanel {

	ArrayList<Duck> ducks;
	BufferedImage canvas;
	
	public DuckGame(DuckGameConfig f) {
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		render();
		g2d.drawImage(canvas, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
	}
	
	public void render() {
		
	}
	
	public void doTick() {
		
	}
	
	public static void main(String[] args) {
		String filepath = JOptionPane.showInputDialog("Enter the Path of the Configuration File");
		Path configfile = Paths.get(filepath);
		int password = Integer.parseInt(JOptionPane.showInputDialog("Enter Password"));
		DuckGameConfig config = DuckGameConfig.readFromFile(configfile, password);
		DuckGame f1 = new DuckGame(config);
	}
	
}
