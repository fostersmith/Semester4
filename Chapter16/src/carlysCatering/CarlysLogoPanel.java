package carlysCatering;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class CarlysLogoPanel extends JPanel {

	int width, height;
	
	public CarlysLogoPanel(int w, int h) {
		width = w;
		height = h;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawRect(width/4, height/4, width/2, height/16);
		g2d.drawPolygon(new int[] {width/4, width/2, (3*width)/4}, new int[] {height/4+height/16, height/4+height/8, height/4+height/16}, 3);
		for(int i = 0; i < 11; ++i) {
			g2d.drawOval(width/4+(i*width/22), height/, i, i)
		}
	}
	
	//temp
	public static void main(String[] args) {
		JFrame f1 = new JFrame();
		f1.add(new CarlysLogoPanel(500,500));
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.pack();
		f1.setResizable(false);
		f1.setVisible(true);
	}


}
