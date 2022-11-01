package extra;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;

public class TableauPile extends JPanel {
	
	public static final int PX_VISIBLE_SPACE = 20; //vertical distance between cards
	ArrayList<Card> faceDown, faceUp;
	int[] cardMap = fiftyTwoNegativeOnes(); // maps card's hashcode to overall index - works because of custom hashcode in Card
	
	public TableauPile() {
		super();
		setLayout(null);
		faceDown = new ArrayList<>();
		faceUp = new ArrayList<>();
	}
	
	public TableauPile(Card[] faceDown) {
		this();
		for(Card c : faceDown)
			faceDownAdd(c);
	}
	
	public void faceUpAdd(Card c) {
		c.setState(Card.FACEUP);
		stackCard(c);
		cardMap[c.hashCode()] = faceUp.size() + faceDown.size();
		faceUp.add(c);
		
	}
	
	private void faceDownAdd(Card c) {
		c.setState(Card.FACEDOWN);
		stackCard(c);
		cardMap[c.hashCode()] = faceDown.size();
		faceDown.add(c);
	}
	
	private void stackCard(Card c) {
		if(faceDown.size() + faceUp.size() != 0) {
			int lastIndex = faceDown.size() + faceUp.size() - 1;
			getCard(lastIndex).setBounds(coveredCard(lastIndex));
		}
		add(c);
		int i = faceDown.size() + faceUp.size();
		c.setBounds(fullCard(i));
	}
	
	private static int cardTop(int overallIndex) {
		return overallIndex*PX_VISIBLE_SPACE;
	}
	
	private static Rectangle coveredCard(int overallIndex) {
		return new Rectangle(0, cardTop(overallIndex), Card.PX_WIDTH, PX_VISIBLE_SPACE);
	}
	
	private static  Rectangle fullCard(int overallIndex) {
		return new Rectangle(0, cardTop(overallIndex), Card.PX_WIDTH, Card.PX_HEIGHT);
	}
	
	public Card getCard(int overallIndex) {
		if(overallIndex >= faceDown.size())
			return faceUp.get(overallIndex-faceDown.size());
		else
			return faceDown.get(overallIndex);
	}
	
	public Card[] removeCards(int overallIndex) {
		Card[] removedCards = new Card[faceUp.size()+faceDown.size()-overallIndex];
		int removedI = 0;
		for(;overallIndex < faceUp.size() + faceDown.size();) {
			Card removed = getCard(overallIndex);
			removedCards[removedI] = removed;
			removedI++;
			if(overallIndex >= faceDown.size()) { // in face up
				faceUp.remove(overallIndex-faceDown.size());
			} else { // in face down
				faceDown.remove(overallIndex);
			}
			this.remove(removed);
		}
		if(faceUp.size() == 0 && faceDown.size() != 0) {
			Card flipped = faceDown.get(faceDown.size()-1);
			faceDown.remove(faceDown.size()-1);
			flipped.setState(Card.FACEUP);
			faceUp.add(flipped);
		}
		if(faceUp.size() + faceDown.size() != 0)
			getCard(faceDown.size() + faceUp.size() - 1).setBounds(fullCard(faceDown.size() + faceUp.size() - 1));
		revalidate();
		repaint();
		print();
		return removedCards;
	}
	
	public void addAllCards(Card[] cards) {
		for(Card c : cards)
			faceUpAdd(c);
		print();
	}
	
	public void highlightCards(int overallIndex) {
		if(overallIndex < faceDown.size())
			throw new IllegalArgumentException("Overall index would highlight face down cards");
		for(int i = faceUp.size()+faceDown.size()-1; i >= overallIndex; i -- ) {
			getCard(i).setState(Card.HIGHLIGHTED);
		}
	}
	
	public void unhighlight() {
		for(Card c : faceUp)
			c.setState(Card.FACEUP);
	}
	
	public boolean contains(Card c) {
		return cardMap[c.hashCode()] > -1;
	}
	
	public int indexOf(Card c) {
		return cardMap[c.hashCode()];
	}

	private static int[] fiftyTwoNegativeOnes() {
		int[] arr = new int[52];
		for(int i = 0; i < arr.length; ++i)
			arr[i] = -1;
		return arr;
	}
	
	public Card getTopCard() {
		return getCard(faceUp.size()+faceDown.size()-1);
	}
	
	public void print() {
		System.out.print("Hidden: ");
		for(Card c : faceDown)
			System.out.print(c+", ");
		System.out.println();
		System.out.print("Shown: ");
		for(Card c : faceUp)
			System.out.print(c+", ");
		System.out.println();
		System.out.print("Map: ");
		for(int i : cardMap)
			System.out.print(i+", ");
		System.out.print("");
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if(faceUp.size() + faceDown.size() > 0)
			super.paintComponent(g);
		else {
			Graphics2D g2d = (Graphics2D)g;
			g2d.setPaint(Color.GRAY);
			g2d.fillRect(0,0,Card.PX_WIDTH,Card.PX_HEIGHT);
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(Card.PX_WIDTH, (6+Card.KING-Card.ACE)*PX_VISIBLE_SPACE+Card.PX_HEIGHT);
	}
	
}
