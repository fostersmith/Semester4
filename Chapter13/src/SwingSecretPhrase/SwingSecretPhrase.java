package SwingSecretPhrase;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

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
	Font buttonFont = new Font("HoboSTD", Font.BOLD, 24);
	String phrase, fileName, fileString;
	ArrayList<Object> inputList = new ArrayList<>();
	ArrayList<String> phraseList = new ArrayList<>();
	boolean found;
	static SwingSecretPhrase f1;
	
	public SwingSecretPhrase() {
		fileName = "WordList1k.txt";
		Path file = Paths.get(fileName);
		try {
			InputStream iStream = new BufferedInputStream(Files.newInputStream(file));
			BufferedReader reader = new BufferedReader(new InputStreamReader(iStream));
			
			fileString = reader.readLine();
			while(fileString != null) {
				phraseList.add(fileString);
				fileString = reader.readLine();
			}
			reader.close();
			phraseNum = (int)(Math.random() * phraseList.size());
			phrase = phraseList.get(phraseNum);
			
			this.setLayout(new GridLayout(3,1,10,10));
			this.getContentPane().setBackground(Color.black);
			resetPanel.setLayout(null);
			resetPanel.setBackground(Color.black);
			resetButton.addActionListener(this);
			resetButton.setFont(buttonFont);
			resetButton.setBounds(50,10,150,75);
			resetPanel.add(resetButton);
			attemptsLabel.setBounds(250,10,200,75);
			attemptsLabel.setForeground(new Color(255,153,153));
			attemptsLabel.setFont(buttonFont);
			resetPanel.add(attemptsLabel);
			
			add(resetPanel);
			
			phrasePanel.setBackground(Color.black);
			phrasePanel.setLayout(new GridLayout(1,phrase.length(),5,20));
		} catch(IOException e) {
			System.out.println("Message: "+e);
		}
	}
	
	@Override public void actionPerformed(ActionEvent e) {
		System.out.println("action");
	}
	
	public  static void main(String[] args) {
		f1 = new SwingSecretPhrase();
		f1.setTitle("Secret Phrase");
		f1.setBounds(200,200,900,300);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setVisible(true);
	}
}
