package client;

import java.awt.Dimension;
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
	JButton addDuckButton, changePasswordButton, changeUsernameButton, changeBackgroundButton, startupButton;
	
	// gameplay
	JPanel gamePanel, gameButtonPanel;
	JButton nextButton, prevButton, gameplayToStartupButton;
	DuckScreen screen;
	static final int GAME_BUTTON_HEIGHT = 40;

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == nextButton && screen.ducks.size() > 0) {
			screen.focus++;
			screen.focus %= screen.ducks.size();
			screen.render();
		} else if(e.getSource() == prevButton && screen.ducks.size() > 0) {
			screen.focus--;
			screen.focus %= screen.ducks.size();
			screen.render();
		} else if(e.getSource() == addDuckButton) {
			doAddDuck();
		} else if(e.getSource() == settingsButton) {
			loadSettings();
			System.out.println("settings");
		} else if(e.getSource() == startupButton || e.getSource() == gameplayToStartupButton) {
			loadStartup();
		} else if(e.getSource() == playButton) {
			loadGameplay();
		} else if(e.getSource() == saveButton) {
			try {
				DuckGameConfig.writeToFile(config, confPath, config.password);
				for(int i = 0; i < screen.ducks.size(); ++i)
					Duck.saveToFile(screen.ducks.get(i), config.ducks.get(i), config.password);
				JOptionPane.showMessageDialog(null,"Success");
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "An IO Error Occurred\nOriginal: "+e1);
			}
		} else if(e.getSource() == changePasswordButton) {
			String passwordStr = JOptionPane.showInputDialog(null, "Enter new password");
			if(passwordStr != null) {
				try {
					int password = Integer.parseInt(passwordStr);
					config.password = password;
					JOptionPane.showMessageDialog(null, "Success. Changes will not be reflected until saved");
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Invalid password. Changes will not take place");
				}
			}
		} else if(e.getSource() == changeUsernameButton) {
			String username = JOptionPane.showInputDialog(null, "Enter new username");
			if(username != null) {
				config.username = username;
				JOptionPane.showMessageDialog(null, "Success. Changes will not be reflected until saved");
			}			
		} else if(e.getSource() == changeBackgroundButton) {
			String path = JOptionPane.showInputDialog(null, "Enter new path");
			if(path != null) {
				try {
					screen.setBackground(path);
					config.setBackground(path);
					gameButtonPanel.setBounds(0, screen.background.getHeight(), screen.background.getWidth(), GAME_BUTTON_HEIGHT);
					gamePanel.setPreferredSize(new Dimension(screen.background.getWidth(), screen.background.getHeight()+GAME_BUTTON_HEIGHT));
					JOptionPane.showMessageDialog(null, "Success. Changes will not be reflected until saved");
				} catch(IOException e1) {
					JOptionPane.showMessageDialog(null, "Encountered an IOException and couldn't process the path. Check that the file exists and is an image");
				}
			}
		}
		if(e.getSource() == gameplayToStartupButton)
			screen.stop();
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
		changeBackgroundButton = new JButton("Change Background");
		startupButton = new JButton("Startup Menu");
		addDuckButton.addActionListener(this);
		changePasswordButton.addActionListener(this);
		changeUsernameButton.addActionListener(this);
		startupButton.addActionListener(this);
		addDuckButton.addActionListener(this);
		changeBackgroundButton.addActionListener(this);
		
		settingsPanel = new JPanel(new GridLayout(1,4));
		settingsPanel.add(addDuckButton);
		settingsPanel.add(changePasswordButton);
		settingsPanel.add(changeUsernameButton);
		settingsPanel.add(changeBackgroundButton);
		settingsPanel.add(startupButton);
		
		nextButton = new JButton("Next");
		prevButton = new JButton("Previous");
		gameplayToStartupButton = new JButton("Return to Startup Menu");
		nextButton.addActionListener(this);
		prevButton.addActionListener(this);
		gameplayToStartupButton.addActionListener(this);
		screen.render();
		
		gameButtonPanel = new JPanel(new GridLayout(1,3));
		gameButtonPanel.add(prevButton);
		gameButtonPanel.add(nextButton);
		gameButtonPanel.add(gameplayToStartupButton);
		gamePanel = new JPanel();
		gamePanel.setLayout(null);
		gamePanel.add(screen);
		screen.setBounds(0,0,screen.background.getWidth(),screen.background.getHeight());
		gamePanel.add(gameButtonPanel);
		gameButtonPanel.setBounds(0, screen.background.getHeight(), screen.background.getWidth(), GAME_BUTTON_HEIGHT);
		gamePanel.setPreferredSize(new Dimension(screen.background.getWidth(), screen.background.getHeight()+GAME_BUTTON_HEIGHT));
		
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
				f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f1.setResizable(false);
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
		DuckGameConfig config;
		try {
			config = DuckGameConfig.readFromFile(file, password);
		} catch(Exception e) {
			throw new IOException("An Error Occurred while loading file. Login information may be incorrect or file may be corrupted.");
		}
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
