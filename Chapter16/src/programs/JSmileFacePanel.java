package programs;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class JSmileFacePanel extends JPanel {
	
	int faceWidth = 300;
	int eyeMarginX = 80;
	int eyeMarginY = 100;
	
	int mouthWidth = 200;
	
	@Override
	public void paintComponent(Graphics g) {
		int m = 80;
		int w = 20;
		g.drawOval(0, 0, faceWidth, faceWidth);
		g.drawOval(m, eyeMarginY, w, w);
		g.drawOval(faceWidth-m-w, eyeMarginY, w, w);
		
		g.drawArc((faceWidth-mouthWidth)/2, (faceWidth-mouthWidth)/2, mouthWidth, mouthWidth, 180, 180);
	}

	public static void main(String[] args) {
		JFrame f1 = new JFrame();
		f1.add(new JSmileFacePanel());
		f1.setSize(500,500);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setVisible(true);
	}

}
