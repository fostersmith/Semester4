package SwingSecretPhrase;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SwingSecretPhrase extends JFrame implements ActionListener {
	
	int numCorrect = 0, phraseNum, incorrectAttempts = 0;
	JButton[] letterArray = new JButton[26];
	JButton resetButton = new JButton("Reset");
	JLabel attemptsLabel = new JLabel("Attempts: "+incorrectAttempts);
	JPanel letterPanel = new JPanel(new GridLayout(1,13,5,5));
	JPanel phrasePanel = new JPanel();
	JPanel resetPanel = new JPanel();
	static SwingSecretPhrase f1;
	
	@Override public void actionPerformed(ActionEvent e) {
	}
	
	public  static void main(String[] args) {
		f1 = new SwingSecretPhrase();
		f1.setTitle("Secret Phrase");
		f1.setBounds(200,200,900,300);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setVisible(true);
	}
}
