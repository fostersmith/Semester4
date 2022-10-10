package ydis;

import javax.swing.JFrame;

public class CreateJFrameWithComponents {
	public static void main(String[] args) {
		JFrameWithComponents aFrame = new JFrameWithComponents();
		final int WIDTH = 350;
		final int HEIGHT = 100;
		aFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		aFrame.setSize(WIDTH, HEIGHT);
		aFrame.setVisible(true);
	}
}
