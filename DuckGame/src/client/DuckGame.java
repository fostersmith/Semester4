package client;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import lib.Duck;

public class DuckGame extends JPanel {

	ArrayList<Duck> ducks;
	BufferedImage canvas;
	DuckGameConfig config;
	
	public DuckGame(DuckGameConfig f) {
		canvas = new BufferedImage(500,500,BufferedImage.TYPE_INT_ARGB);
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
	
	public static void main(String[] args) {
		DuckGameConfig config = null;
		boolean cancel = false;
		while(config == null && !cancel) {
			String filepath = JOptionPane.showInputDialog("Enter the Path of the Configuration File");
			if(filepath != null) {
				filepath += ".dgc";
				Path configfile = Paths.get(filepath);
				if(Files.exists(configfile)) {
					String username = JOptionPane.showInputDialog("Enter Username");
					int password = Integer.parseInt(JOptionPane.showInputDialog("Enter Password"));
					try {
						
						config = DuckGameConfig.readFromFile(configfile, password);
						if(!config.username.equals(username)) {
							JOptionPane.showMessageDialog(null, "Username or password was incorrect", "Login Error", JOptionPane.ERROR_MESSAGE);
							config = null;
						}
						
					} catch(IOException e) {
						
						JOptionPane.showMessageDialog(null, "An IO Error Occurred\n"+e, "Error", JOptionPane.ERROR_MESSAGE);
						
					}
				} else {
					int opt = JOptionPane.showOptionDialog(null, "That file does not exist. Create a new one?", "No File Exists", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
					if(opt == JOptionPane.YES_OPTION) {
						String username = JOptionPane.showInputDialog("Enter Username");
						int password = Integer.parseInt(JOptionPane.showInputDialog("Enter Password"));
						try {
							config = DuckGameConfig.createBlankFile(configfile, username, password);
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null, "An IO Error Occurred\n"+e1, "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			} else
				cancel = true;
		}
		if(!cancel) {
			DuckGame game = new DuckGame(config);
			JFrame f1 = new JFrame();
			f1.add(game);
			game.render();
			f1.pack();
			f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f1.setVisible(true);
		}
	}
	
}
