package hashmap;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PasswordManager extends JPanel {

	
	
	public PasswordManager(int w, int h, int passcode) {
		setLayout(new FlowLayout());
		setSize(w,h);
	}
	
	public static 
	
	public static void main(String[] args) {
		JFrame f1 = new JFrame();
		PasswordManager panel = new PasswordManager(500, 300);
		f1.add(panel);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setVisible(true);
		f1.setSize(500, 300);
	}
}
