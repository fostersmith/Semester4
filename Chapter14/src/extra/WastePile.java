package extra;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class WastePile extends JPanel implements CardCollection {

	ArrayList<Card> cards = new ArrayList<>();
	Card top;
	
	public WastePile() {
		setLayout(new FlowLayout());
	}
	
	@Override
	public Card[] pop(int index) {
		if(top != null)
			remove(top);
		Card ret = cards.remove(cards.size()-1);
		if(cards.size() > 0) {
			top = cards.get(cards.size()-1);
			top.setState(Card.FACEUP);
			add(top);
		} else {
			top = null;
		}
		System.out.println("added "+top);
		return new Card[] {ret};
	}

	@Override
	public void addAll(Card[] cards) {
		if(top != null)
			remove(top);
		for(Card c : cards) {
			c.setState(Card.FACEUP);
			this.cards.add(c);
		}
		top = this.cards.get(this.cards.size()-1);
		top.setState(Card.FACEUP);
		add(top);
	}

	@Override
	public void highlightCards(int index) {
		// index not used
		cards.get(cards.size()-1).setState(Card.HIGHLIGHTED);
	}

	@Override
	public void unhighlight() {
		if(cards.size() > 0)
			cards.get(cards.size()-1).setState(Card.FACEUP);
	}

	@Override
	public Card getCard(int index) {
		return cards.get(cards.size()-1);
	}

	@Override
	public void paintComponent(Graphics g) {
		if(cards.size() > 0)
			super.paintComponent(g);
		else {
			Graphics2D g2d = (Graphics2D)g;
			g2d.setPaint(Color.GRAY);
			g2d.fillRect(0, 0, Card.PX_WIDTH, Card.PX_HEIGHT);
		}
	}

	@Override
	public Card[] getCards(int index) {
		return new Card[] {top};
	}
}
