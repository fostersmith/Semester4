package extra;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class StockPile extends JPanel {

	private Deck d;
	private Card next;
	
	public StockPile(Deck d) {
		setLayout(new FlowLayout());
		this.d = d;
		next = d.getNext();
		next.setState(Card.FACEDOWN);
		next.setEnabled(true);
		add(next);
	}
	
	public Card pop() {
		Card ret = next;
		remove(next);
		if(d.next != 52) {
			next = d.getNext();
			next.setState(Card.FACEDOWN);
			next.setEnabled(true);
			add(next);
		} else {
			next = null;
		}
		return ret;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if(d.next != 52) {
			super.paintComponent(g);
		} else {
			Graphics2D g2d = (Graphics2D)g;
			g2d.setPaint(Color.GRAY);
			g2d.fillRect(0, 0, Card.PX_WIDTH, Card.PX_HEIGHT);
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(Card.PX_WIDTH, Card.PX_HEIGHT);
	}
}
