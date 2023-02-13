package programs;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class JFontSizesPanel extends JPanel {

	private static final String phrase = "a phrase";
	
	@Override
	public void paintComponent(Graphics g) {
		for(int i = 0; i < 14; ++i) {
			int size = 6 + i;
			int yPos = i*20+5;
			g.setFont(new Font("Times New Roman", Font.PLAIN, size));
			g.drawString(phrase, 5, yPos);
		}
	}

	public static void main(String[] args) {
		JFrame f1 = new JFrame();
		f1.add(new JFontSizesPanel());
		f1.setSize(500,500);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setVisible(true);
	}

}
