package ydis;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SketchPad extends JPanel implements MouseListener, MouseMotionListener {

	int xStart, yStart;
	int xStop, yStop;
	BasicStroke aStroke = new BasicStroke(5.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
	
	public SketchPad() {
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		xStart = e.getX();
		yStart = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		xStop = e.getX();
		yStop = e.getY();
		repaint();
		xStart = xStop;
		yStart = yStop;
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D gr2D = (Graphics2D)g;
		Line2D.Float line = new Line2D.Float((float)xStart, (float)yStart, (float)xStop, (float)yStop);
		gr2D.setStroke(aStroke);
		gr2D.draw(line);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.add(new SketchPad());
		frame.setSize(400, 400);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
