package programs;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class JDiagonalLinesPanel extends JPanel {

	int x = 50, y = 50;
	int w = 300, h = 300;
	int numLines = 13;
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawRect(x, y, w, h);
		for(int i = 0; i <= numLines; ++i) {
			g.drawLine(x, (int)(y + i*(h/(double)numLines)), (int)(x + i*(w/(double)numLines)), y);
		}
		for(int i = 0; i < numLines; ++i) {
			g.drawLine((int)(x + i*(w/(double)numLines)), y+h, x+w, (int)(y + i*(h/(double)numLines)));
		}
	}

	public static void main(String[] args) {
		JFrame f1 = new JFrame();
		f1.add(new JDiagonalLinesPanel());
		f1.setSize(400,450);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setVisible(true);
	}

}
