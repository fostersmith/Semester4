package ydis;

import javax.swing.JFrame;

public class JDemoFrame {

	public static void main(String[] args) {
		JFrame aFrame = new JFrame("This is a frame");
		final int WIDTH = 250;
		final int HEIGHT = 250;
		aFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		aFrame.setSize(WIDTH, HEIGHT);
		aFrame.setVisible(true);
	}
	
}
