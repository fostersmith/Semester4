package SwingSecretPhrase;

import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SwingSecretPhrase extends JFrame implements ActionListener {
	
	int numCorrect = 0, phraseNum, incorrectAttempts = 0;
	JButton[] letterArray = new JButton[26];
	JButton resetButton = new JButton("Reset");
	JLabel attemptsLabel = new JLabel("Attempts: "+incorrectAttempts);
	JPanel letterPanel = new JPanel(new GridLayout(2,13,5,5));
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
			for(int tfNum = 0; tfNum < phrase.length(); ++tfNum) {
				if(phrase.charAt(tfNum) != ' ') {
					inputList.add(new JTextField());
					((JTextField)inputList.get(tfNum)).setFont(buttonFont);
					((JTextField)inputList.get(tfNum)).setEnabled(false);
					((JTextField)inputList.get(tfNum)).setBackground(new Color(87,87,255));
					((JTextField)inputList.get(tfNum)).setHorizontalAlignment(JTextField.CENTER);
					((JTextField)inputList.get(tfNum)).setDisabledTextColor(Color.magenta);
				} else {
					inputList.add(new JLabel(" "));
					numCorrect++;
				}
				phrasePanel.add((Component)inputList.get(tfNum));
			}
			add(phrasePanel);
			letterPanel.setBackground(Color.BLACK);
			for(int j = 0; j < letterArray.length; j++) {
				letterArray[j] = new JButton(Character.toString((char)(j+65)));
				letterPanel.add(letterArray[j]);
				letterArray[j].addActionListener(this);
				letterArray[j].setBackground(Color.MAGENTA);
				letterArray[j].setFont(buttonFont);
			}
			add(letterPanel);
		} catch(IOException e) {
			System.out.println("Message: "+e);
		}
	}
	
	@Override public void actionPerformed(ActionEvent e) {
		found = false;
		if(e.getSource() == resetButton) {
			f1.dispose();
			startGame();
		} else { //is a letter button
			for(int i = 0; i < letterArray.length; i++) {
				if(e.getSource() == letterArray[i]) {
					char letter = letterArray[i].getText().charAt(0);
					letterArray[i].setEnabled(false);
					for(int j = 0; j < phrase.length(); ++j) {
						if(Character.toUpperCase(phrase.charAt(j)) == letter) {
							((JTextField)inputList.get(j)).setText(Character.toString(letter));
							numCorrect++;
							found = true;
						}
					}
				}
			}
			if(!found) {
				incorrectAttempts++;
				attemptsLabel.setText("Attempts: "+incorrectAttempts);
			}
			if(numCorrect == phrase.length()) {
				for(int j = 0; j < letterArray.length; ++j) {
					letterArray[j].setEnabled(false);
				}
				JOptionPane.showMessageDialog(null, "You Uncovered the Secret Phrase", "Success!", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	public static void startGame() {
		f1 = new SwingSecretPhrase();
		f1.setTitle("Secret Phrase");
		f1.setBounds(200,200,900,300);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setVisible(true);
	}
	
	public  static void main(String[] args) {
		startGame();
	}
}
