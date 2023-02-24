package programs;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class JSecretPhrase2 extends JPanel implements ActionListener {

	String phrase = "BUDDY HOLLY BY WEEZER!";
	
	JButton[] keys = new JButton[26];
	
	JPanel labelPanel = new JPanel(new FlowLayout());
	
	JLabel title = new JLabel("Secret Phrase Game");
	JLabel instructionLabel = new JLabel("Play our game - Guess the phrase                Enter one letter");
	JLabel guessLabel = new JLabel();
	JLabel responseLabel = new JLabel();
	JLabel instructionLabel2 = new JLabel("                        Enter one letter");
	
	HangmanPanel hangman = new HangmanPanel(100, 200);
	
	Set<Character> guesses = new HashSet<Character>();
	int incorrectGuesses = 0;
	
	public JSecretPhrase2() {
		
		labelPanel.setPreferredSize(new Dimension(720, 150));
		
		add(hangman);
		
		title.setFont(new Font("Jokerman", Font.BOLD, 40));
		instructionLabel.setFont(new Font("HoboSTD", Font.BOLD, 25));
		guessLabel.setFont(new Font("Helvetica", Font.BOLD, 30));
		responseLabel.setFont(new Font("Helvetica", Font.BOLD, 15));
		instructionLabel2.setFont(new Font("Helvetica", Font.BOLD, 15));
		labelPanel.add(title);
		labelPanel.add(instructionLabel);
		labelPanel.add(guessLabel);
		labelPanel.add(responseLabel);
		labelPanel.add(instructionLabel2);
		
		setGuessLabel();

		add(labelPanel);
		
		setLayout(new FlowLayout());
		for(int i = 0; i < keys.length; ++i) {
			char key = (char) ('A'+i);
			keys[i] = new JButton(Character.toString(key));
			keys[i].addActionListener(this);
			add(keys[i]);
		}
		
	}
	
	void setGuessLabel() {
		StringBuilder txt = new StringBuilder();
		for(int i = 0; i < phrase.length(); ++i) {
			if(Character.isAlphabetic(phrase.charAt(i)) && !guesses.contains(phrase.charAt(i))) {
				txt.append("*");
			} else {
				txt.append(phrase.charAt(i));
			}
		}
		guessLabel.setText(txt.toString());
	}
	
	boolean checkWin() {
		for(int i = 0; i < phrase.length(); ++i) {
			if(Character.isAlphabetic(phrase.charAt(i))) {
				if(!guesses.contains(phrase.charAt(i)))
					return false;
			}
		}
		return true;
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
		guesses.add(key);
		if(phrase.contains(Character.toString(key))) {
			responseLabel.setText("Correct!");
		} else {
			responseLabel.setText("Incorrect :(");
			++incorrectGuesses;
		}
		hangman.setWrongGuesses(incorrectGuesses);
		if(incorrectGuesses >= 6) {
			for(int i = 0; i < keys.length;  ++i)
				keys[i].setEnabled(false);
			JOptionPane.showMessageDialog(null, "Lose!");
		}
		keys[buttonIndex].setEnabled(false);;
		setGuessLabel();
		System.out.println(key);
		
		if(checkWin()) {
			for(int i = 0; i < keys.length;  ++i)
				keys[i].setEnabled(false);
			JOptionPane.showMessageDialog(null, "Win!");
		}
	}
	
	public static void main(String[] args) {
		JFrame f1 = new JFrame("Secret phrase");
		f1.add(new JSecretPhrase2());
		f1.setSize(720,500);
		f1.setResizable(false);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setVisible(true);
	}

}
