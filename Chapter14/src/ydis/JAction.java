package ydis;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class JAction extends JFrame implements ActionListener {
	JLabel label = new JLabel("Enter your name");
	JTextField field = new JTextField(12);
	JButton button = new JButton("OK");

	public JAction() {
		super("Frame with components");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		button.addActionListener(this);
		field.addActionListener(this);
		add(label);
		add(field);
		add(button);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		label.setText("Thank you");
		button.setText("Done");
	}
	
	public static void main(String[] args) {
		JAction aFrame = new JAction();
		final int WIDTH = 250;
		final int HEIGHT = 100;
		aFrame.setSize(WIDTH, HEIGHT);
		aFrame.setVisible(true);
	}
}
