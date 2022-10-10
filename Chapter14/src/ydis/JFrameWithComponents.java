package ydis;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class JFrameWithComponents extends JFrame {
	JLabel label = new JLabel("Enter your name");
	JTextField field = new JTextField(12);
	JButton button = new JButton("OK");

	public JFrameWithComponents() {
		super("Frame with components");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		add(label);
		add(field);
		add(button);
	}
}
