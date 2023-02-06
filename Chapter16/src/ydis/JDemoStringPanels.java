package ydis;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;

public class JDemoStringPanels extends JFrame {
	final int GAP = 15;
	JStringPanel p1 = new JStringPanel("abc", 
		new Font("TimesRoman", Font.BOLD, 16), Color.BLACK, 20, 20);
	JStringPanel p2 = new JStringPanel("def",
		new Font("Arial", Font.ITALIC, 26), Color.RED, 20, 60);
	JStringPanel p3 = new JStringPanel("ghi",
		new Font("Boopee", Font.BOLD, 32), Color.BLUE, 20, 80);
	JStringPanel p4 = new JStringPanel("jkl",
		new Font("Serif", Font.PLAIN, 40), Color.MAGENTA, 20, 120);
	
	public JDemoStringPanels() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(2, 2, GAP, GAP));
		getContentPane().setBackground(Color.BLACK);
		add(p1);
		add(p2);
		add(p3);
		add(p4);
	}
	
	public static void main(String[] args) {
		JDemoStringPanels f1 = new JDemoStringPanels();
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setVisible(true);
		f1.setSize(500,500);
	}
}
