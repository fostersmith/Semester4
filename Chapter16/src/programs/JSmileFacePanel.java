package programs;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class JSmileFacePanel extends JPanel {
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawOval(0, 0, 300, 300);
		g.drawOval(75, 100, 10, 10);
		g.drawOval(200, 100, 10, 10);
	}

	public static void main(String[] args) {
		JFrame f1 = new JFrame();
		f1.add(new JSmileFacePanel());
		f1.setSize(500,500);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setVisible(true);
	}

}
