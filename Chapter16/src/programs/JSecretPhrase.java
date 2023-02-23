package programs;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JSecretPhrase extends JPanel implements ActionListener {

	JButton[] keys = new JButton[26];
	
	JPanel labelPanel = new JPanel();
	
	JLabel title = new JLabel("Secret Phrase Game");
	JLabel instructionLabel = new JLabel("Play our game - Guess the phrase                Enter one letter");
	JLabel guessLabel = new JLabel();
	JLabel responseLabel = new JLabel();
	JLabel instructionLabel2 = new JLabel("Enter one letter");
	
	public JSecretPhrase() {
		
		labelPanel.setPreferredSize(new Dimension(720, 100));
		
		title.setFont(new Font("Jokerman", Font.BOLD, 40));
		instructionLabel.setFont(new Font("HoboSTD", Font.BOLD, 25));
		responseLabel.setFont(new Font("Helvetica", Font.BOLD, 10));
		labelPanel.add(title);
		labelPanel.add(instructionLabel);
		labelPanel.add(guessLabel);
		labelPanel.add(responseLabel);

		add(labelPanel);
		
		setLayout(new FlowLayout());
		for(int i = 0; i < keys.length; ++i) {
			char key = (char) ('A'+i);
			keys[i] = new JButton(Character.toString(key));
			keys[i].addActionListener(this);
			add(keys[i]);
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int buttonIndex = 0;
		for(int i = 0; i < keys.length; ++i) {
			if(keys[i] == e.getSource()) {
				buttonIndex = i;
				i = keys.length;
			}
		}
		
		char key = (char)('A'+buttonIndex);
		System.out.println(key);
	}

	public static void main(String[] args) {
		JFrame f1 = new JFrame("Secret phrase");
		f1.add(new JSecretPhrase());
		f1.setSize(720,500);
		f1.setResizable(false);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setVisible(true);
	}

}
