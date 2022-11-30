package ydis;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class JGridLayout extends JFrame {

	private JButton nb = new JButton("North");
	private JButton sb = new JButton("South");
	private JButton eb = new JButton("East");
	private JButton wb = new JButton("West");
	private JButton cb = new JButton("Center");
	
	public JGridLayout() {
		setLayout(new GridLayout(2, 3, 2, 4));
		add(nb, BorderLayout.NORTH);
		add(sb, BorderLayout.SOUTH);
		add(eb, BorderLayout.EAST);
		add(wb, BorderLayout.WEST);
		add(cb, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		JGridLayout jbl = new JGridLayout();
		jbl.setSize(250, 250);
		jbl.setVisible(true);
	}
}
