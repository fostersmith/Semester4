package ydis;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class JAction2 extends JFrame implements ActionListener {
	JLabel label = new JLabel("Enter your name");
	JTextField field = new JTextField(12);
	JButton button = new JButton("OK");

	public JAction2() {
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
		Object source = e.getSource();
		if(source == button)
			label.setText("You clicked the button");
		else
			label.setText("You pressed enter");
	}
	
	public static void main(String[] args) {
		JAction2 aFrame = new JAction2();
		final int WIDTH = 250;
		final int HEIGHT = 100;
		aFrame.setSize(WIDTH, HEIGHT);
		aFrame.setVisible(true);
	}
}
