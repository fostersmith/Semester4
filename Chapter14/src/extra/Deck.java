package extra;

import java.awt.event.ActionListener;

public class Deck {
	
	private Card[] cards;
	int next = 0;
	
	public Deck(ActionListener listener) {
		cards = genCards();
		for(Card c : cards)
			c.addActionListener(listener);
	}
	
	public void shuffle() {
		for(int i = 0; i < cards.length; ++i) {
			int randInd = (int) (Math.random() * cards.length);
			Card temp = cards[i];
			cards[i] = cards[randInd];
			cards[randInd] = temp;
		}
		next = 0;
	}
	
	public Card cardAt(int i) {
		if(i < 0 || i >= cards.length)
			throw new IllegalArgumentException("Card Index out of Bounds");
		return cards[i];
	}
	
	public Card getNext() {
		Card r = cards[next];
		++next;
		return r;
	}
	
	private static Card[] genCards() {
		Card[] cards = new Card[52];
		int i = 0;
		for(int suit = Card.HEARTS; suit <= Card.SPADES; ++suit) {
			for(int value = Card.ACE; value <= Card.KING; ++value) {
				cards[i] = new Card(suit, value);
				++i;
			}
		}
		return cards;
	}
	
	
}
