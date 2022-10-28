/*
 * Calculator.java
 * Foster Smith
 * 10/2/22
 */

package practice;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Calculator extends JFrame implements ActionListener {
	
	JButton[] keys = new JButton[17];
	String[] operatorKeys = {"/","*","-","+","=",".","CLEAR"};
	JPanel keypad = new JPanel(new GridLayout(4,4,10,10));
	JTextField lcd = new JTextField(20);
	Font bigFont = new Font("HoboSTD",Font.BOLD,25);
	Font funFont = new Font("Chiller",Font.BOLD,30);
	double op1 = 0d, op2 = 0d;
	boolean first = true, clearText = true;
	int lastOp = 0;
	DecimalFormat calcPattern = new DecimalFormat("########.########");
	
	static Calculator f1;
	
	
	public Calculator() {
		lcd.setEditable(false);
		lcd.setBackground(Color.WHITE);
		
		//construct and assign captions to the JButtons
		for(int i = 0; i <= 9; ++i) {
			keys[i] = new JButton(String.valueOf(i));	
		}
		for(int i = 10;  i <= 16; ++i) {
			keys[i] = new JButton(operatorKeys[i-10]);
		}
		for(int i = 7;  i <= 10; ++i) { //7, 8, 9, /
			keypad.add(keys[i]);
		}
		for(int i = 4;  i <= 6; ++i) { //4, 5, 6
			keypad.add(keys[i]);
		}
		keypad.add(keys[11]); //*
		for(int i = 1;  i <= 3; ++i) { //1,2,3
			keypad.add(keys[i]);
		}
		keypad.add(keys[12]); //-
		keypad.add(keys[0]); //0
		for(int i = 15;  i >= 13; --i) { //7, 8, 9, divide
			keypad.add(keys[i]);
		}
		for(int i = 0;  i < keys.length; ++i) {
			keys[i].setBackground(new Color(135,14,233));
			keys[i].setForeground(Color.WHITE);
			keys[i].setFont(bigFont);			
			keys[i].addActionListener(this);
		}
		keypad.setBackground(Color.BLACK);
		add(lcd, BorderLayout.NORTH);
		add(keypad, BorderLayout.CENTER);
		add(keys[16], BorderLayout.SOUTH);
	}

	private void keyAction(int keynum) {
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i = 0; i < keys.length; ++i) {
			if(e.getSource() == keys[i]) {
				keyAction(i);
				i = keys.length;
			}
		}
	}
	
	
	public static void main(String[] args) {
		f1 = new Calculator();
		f1.setTitle("Calculator");
		f1.setBounds(200,200,300,350);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setVisible(true);
		// set image properties and add to the frame
		Image icon = Toolkit.getDefaultToolkit().getImage("cards\\sk.png"); //TODO
		f1.setIconImage(icon);
	}
}
