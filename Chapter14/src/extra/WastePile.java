package extra;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class WastePile extends JPanel implements CardCollection {

	ArrayList<Card> cards = new ArrayList<>();
	
	public WastePile() {
		
	}
	
	@Override
	public Card[] pop(int index) {
		//TODO
		return new Card[] {cards.get(cards.size()-1)};
	}

	@Override
	public void addAll(Card[] cards) {
		if(this.cards.size() > 0)
			remove(this.cards.get(this.cards.size()-1));
		for(Card c : cards) {
			c.setState(Card.FACEUP);
			this.cards.add(c);
		}
		this.cards.get(this.cards.size()-1).setState(Card.FACEUP);
		add(this.cards.get(this.cards.size()-1));
	}

	@Override
	public void highlightCards(int index) {
		// index not used
		cards.get(cards.size()-1).setState(Card.HIGHLIGHTED);
	}

	@Override
	public void unhighlight() {
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
			g2d.fillRect(0, 0, Card.WIDTH, Card.HEIGHT);
		}
	}
}
