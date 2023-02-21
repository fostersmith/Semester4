package ydis;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class J2DLine extends JPanel {
	@Override
	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		Graphics2D gr2D = (Graphics2D)gr;
		Point2D.Float pos1 = new Point2D.Float(80, 20);
		Point2D.Float pos2 = new Point2D.Float(20, 100);
		BasicStroke aStroke = new BasicStroke(15.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
		gr2D.setStroke(aStroke);
		
		gr2D.setStroke(aStroke);
		Line2D.Float line = new Line2D.Float(pos1, pos2);
		gr2D.draw(line);
	}
	
	public static void main(String[] args)
	{
		System.out.println("hello");
		JFrame frame = new JFrame();
		frame.add(new J2DLine());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(90, 160);
		frame.setVisible(true);
	}
}
