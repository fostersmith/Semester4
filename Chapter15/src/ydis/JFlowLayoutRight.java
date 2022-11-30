package ydis;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class JFlowLayoutRight extends JFrame {

	private JButton nb = new JButton("North");
	private JButton sb = new JButton("South");
	private JButton eb = new JButton("East");
	private JButton wb = new JButton("West");
	private JButton cb = new JButton("Center");
	
	public JFlowLayoutRight() {
		setLayout(new FlowLayout(FlowLayout.RIGHT));
		add(nb, BorderLayout.NORTH);
		add(sb, BorderLayout.SOUTH);
		add(eb, BorderLayout.EAST);
		add(wb, BorderLayout.WEST);
		add(cb, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		JFlowLayoutRight jbl = new JFlowLayoutRight();
		jbl.setSize(250, 250);
		jbl.setVisible(true);
	}
}
