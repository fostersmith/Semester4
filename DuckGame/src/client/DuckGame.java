package client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import lib.Duck;
import lib.LoginException;

public class DuckGame extends JFrame implements ActionListener {

	BufferedImage background;
	DuckGameConfig config;
	Path confPath;
	
	// startup
	JPanel startupPanel;
	JButton playButton, settingsButton, saveButton;
	
	// settings
	JPanel settingsPanel;
	JButton addDuckButton, changePasswordButton, changeUsernameButton, startupButton;
	
	// gameplay
	JPanel gamePanel, gameButtonPanel;
	JButton nextButton, prevButton;
	DuckScreen screen;

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == nextButton) {
			screen.focus++;
			screen.focus %= screen.ducks.size();
			screen.render();
		} else if(e.getSource() == prevButton) {
			screen.focus--;
			screen.focus %= screen.ducks.size();
			screen.render();
		} else if(e.getSource() == addDuckButton) {
			doAddDuck();
		} else if(e.getSource() == settingsButton) {
			loadSettings();
			System.out.println("settings");
		} else if(e.getSource() == startupButton) {
			loadStartup();
		} else if(e.getSource() == playButton) {
			loadGameplay();
		} else if(e.getSource() == saveButton) {
			try {
				DuckGameConfig.writeToFile(config, confPath, config.password);
				for(int i = 0; i < screen.ducks.size(); ++i)
					Duck.saveToFile(screen.ducks.get(i), config.ducks.get(i), config.password);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "An IO Error Occurred\nOriginal: "+e1);
			}
		} else if(e.getSource() == changePasswordButton) {
			
		} else if(e.getSource() == changeUsernameButton) {
			
		} else if(e.getSource() == startupButton) {
			
		}
	}
	
	public DuckGame(DuckGameConfig dgc, Path confPath) throws IOException, LoginException {
		this.config = dgc;
		try {
			background = ImageIO.read(dgc.background.toFile());
		} catch(IOException e) {
			throw new IOException("Couldn't read background file\nOriginal Message: "+e);
		}
		ArrayList<Duck> ducks = new ArrayList<>();
		try {
			for(int i = 0; i < dgc.ducks.size(); ++i) {
				ducks.add(Duck.readFromFile(dgc.ducks.get(i), dgc.ids.get(i), dgc.password));
			}
		} catch(Exception e) {
			throw new LoginException("Error occurred while loading duck.\nOriginal: "+e);
		}
		screen = new DuckScreen(ducks, background);
		this.confPath = confPath;
		
		playButton = new JButton("Play");
		settingsButton = new JButton("Settings");
		saveButton = new JButton("Save");
		playButton.addActionListener(this);
		settingsButton.addActionListener(this);
		saveButton.addActionListener(this);
		
		startupPanel = new JPanel(new GridLayout(1,4));
		startupPanel.add(playButton);
		startupPanel.add(settingsButton);
		startupPanel.add(saveButton);
		
		addDuckButton = new JButton("Add Duck");
		changePasswordButton = new JButton("Change Password");
		changeUsernameButton = new JButton("Change username");
		startupButton = new JButton("Startup Menu");
		addDuckButton.addActionListener(this);
		changePasswordButton.addActionListener(this);
		changeUsernameButton.addActionListener(this);
		startupButton.addActionListener(this);
		addDuckButton.addActionListener(this);
		
		settingsPanel = new JPanel(new GridLayout(1,3));
		settingsPanel.add(addDuckButton);
		settingsPanel.add(changePasswordButton);
		settingsPanel.add(changeUsernameButton);
		settingsPanel.add(startupButton);
		
		nextButton = new JButton("Next");
		prevButton = new JButton("Previous");
		nextButton.addActionListener(this);
		prevButton.addActionListener(this);
		screen.render();
		
		gameButtonPanel = new JPanel(new GridLayout(1,2));
		gameButtonPanel.add(prevButton);
		gameButtonPanel.add(nextButton);
		gamePanel = new JPanel(new GridLayout(1,1));
		gamePanel.add(screen);
		//gamePanel.add(gameButtonPanel);
		
		loadStartup();
		
	}
	
	public void loadStartup() {
		this.getContentPane().removeAll();
		this.add(startupPanel);
		pack();
	}
	
	public void loadSettings() {
		this.getContentPane().removeAll();
		this.add(settingsPanel);
		pack();
	}
	
	public void loadGameplay() {
		this.getContentPane().removeAll();
		this.add(gamePanel);
		screen.start();
		pack();
	}
	
	public void doAddDuck() {
		try {
			Path file;
			int id, password;
			String s = JOptionPane.showInputDialog("Enter File Path:");
			if(s == null)
				throw new NullPointerException();
			s += ".dck";
			file = Paths.get(s);
			if(!Files.exists(file))
				throw new IOException("File does not exist");
			s = JOptionPane.showInputDialog("Enter Duck ID");
			if(s == null)
				throw new NullPointerException();
			id = Integer.parseInt(s);
			s = JOptionPane.showInputDialog("Enter Duck's Previous Password");
			if(s == null)
				throw new NullPointerException();
			password = Integer.parseInt(s);
			Duck d = Duck.readFromFile(file, id, password);
			config.ducks.add(file);
			config.ids.add(id);
			screen.ducks.add(d);
			JOptionPane.showMessageDialog(null, "Success");
		} catch(NullPointerException ex){
			//canceled
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "An error occurred while adding duck.\nOriginal: "+ex);
		}
	}
			
	public static void main(String[] args) {
		DuckGameConfig config = null;
		boolean cancel = false;
		Path configfile = null;
		while(config == null && !cancel) {
			String filepath = JOptionPane.showInputDialog("Enter the Path of the Configuration File");
			if(filepath != null) {
				filepath += ".dgc";
				configfile = Paths.get(filepath);
				if(Files.exists(configfile)) {
					try {
						config = doLogin(configfile);
					} catch(NullPointerException e) {
						cancel = true;
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "An IO Exception Occurred\n"+e);
					} catch (LoginException e) {
						JOptionPane.showMessageDialog(null, "Invalid Credentials");
					}
				} else {
					int opt = JOptionPane.showOptionDialog(null, "That file does not exist. Create a new one?", "No File Exists", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
					if(opt == JOptionPane.YES_OPTION) {
						try {
							config = doAccountSetup(configfile);
						} catch(NullPointerException e) {
							cancel = true;
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null, "An IO Exception Occurred\n"+e);
						}
					}
				}
			} else
				cancel = true;
		}
		if(!cancel) {
			try {
				DuckGame f1 = new DuckGame(config, configfile);
				f1.pack();
				f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f1.setVisible(true);
			} catch(Exception e) {
				JOptionPane.showMessageDialog(null, "Error:\n\n"+e);
			}
		}
	}
	
	public static DuckGameConfig doLogin(Path file) throws NullPointerException, IOException, LoginException {
		String input = JOptionPane.showInputDialog("Enter username");
		if(input == null)
			throw new NullPointerException();
		String username = input;
		int password = -1;
		boolean done = false;
		while(!done) {
			input = JOptionPane.showInputDialog("Enter Password");
			if(input == null)
				throw new NullPointerException();
			try {
				password = Integer.parseInt(input);
				done = true;
			} catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Password must be numeric");			}
		}
		DuckGameConfig config = DuckGameConfig.readFromFile(file, password);
		if(!config.username.equals(username))
			throw new LoginException();
		return config;
	}
	
	public static DuckGameConfig doAccountSetup(Path file) throws NullPointerException, IOException {
		String input = JOptionPane.showInputDialog("Enter Username");
		if(input == null)
			throw new NullPointerException();
		String username = input;
		int password = -1;
		boolean done = false;
		while(!done) {
			input = JOptionPane.showInputDialog("Enter Password");
			if(input == null)
				throw new NullPointerException();
			try {
				password = Integer.parseInt(input);
				done = true;
			} catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Password must be numeric");			}
		}
		DuckGameConfig config = DuckGameConfig.createBlankFile(file, username, password);
		return config;
	}
	
}
