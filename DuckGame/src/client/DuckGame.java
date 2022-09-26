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

import lib.Duck;
import lib.LoginException;

public class DuckGame extends JFrame implements ActionListener {

	BufferedImage background;
	DuckGameConfig config;
	DuckScreen screen;
	
	// startup
	JButton playButton, settingsButton;
	
	// settings
	JButton addDuckButton, changePasswordButton, changeUsernameButton, saveButton;
	
	// gameplay
	JButton nextButton, prevButton;
	
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
			try {
				Path file;
				int id;
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
				Duck d = Duck.readFromFile(file, id, config.password);
				config.ducks.add(file);
				config.ids.add(id);
				screen.ducks.add(d);
			} catch(NullPointerException ex){
				//canceled
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "An error occurred while adding duck.\nOriginal: "+e);
			}
		}
	}
	
	public DuckGame(DuckGameConfig dgc) throws IOException, LoginException {
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
	}
	
	public void loadStartupMenu() {
		this.removeAll();
		this.setLayout(new GridLayout(1,2));
	}
	
	public void loadSettings() {
		
	}
	
	public void loadGameplay() {
		
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
				DuckGame f1 = new DuckGame(config);
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
