package extra;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class FoundationPile extends JPanel implements CardCollection  {

	private ArrayList<Card> cards = new ArrayList<>();
	private Card top;
	public int suit;
	
	public FoundationPile(int suit) {
		this.suit = suit;
	}
	
	@Override
	public Card[] pop(int index) {
		return null;
	}

	@Override
	public void addAll(Card[] cardList) {
		for(Card c : cardList) {
			cards.add(c);
			c.setState(Card.FACEUP);
			//c.setEnabled(false);
		}
		if(top != null)
			remove(top);
		top = cardList[cardList.length-1];
		add(top);
	}

	@Override
	public void highlightCards(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unhighlight() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Card getCard(int index) {
		return top;
	}
	
	public boolean canStack(Card c) {
		boolean ret = c.getSuit() == suit;
		System.out.println("Cards : "+cards.size());
		if(cards.size() > 0) {
			ret = ret & c.getValue() == top.getValue() + 1;
			System.out.println(top+" "+c+" = "+ret);
		}
		else
			ret = ret & c.getValue() == 1;
		return ret;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if(top != null) {
			super.paintComponent(g);
		} else {
			Graphics2D g2d = (Graphics2D)g;
			g2d.setPaint(Color.GRAY);
			g2d.fillRect(0, 0, Card.PX_WIDTH, Card.PX_HEIGHT);
		}

	}

	@Override
	public Card[] getCards(int index) {
		return new Card[] {getCard(index)};
	}

}
