package extra;

import java.awt.Dimension;
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
	
	public void removeCards(int overallIndex) {
		for(int i = faceUp.size()+faceDown.size()-1; i >= overallIndex; i -- ) {
			cardMap[getCard(i).hashCode()] = -1;
			this.remove(getCard(i));
			if(faceUp.size() > 0)
				faceUp.remove(faceUp.size()-1);
			else
				faceDown.remove(faceDown.size()-1);
		}
		if(faceUp.size() == 0 && faceDown.size() != 0)
			faceDown.get(faceDown.size()-1).setState(Card.FACEUP);
		if(faceDown.size() + faceUp.size() > 0)
			getCard(faceDown.size()+faceUp.size()-1).setBounds(fullCard(faceDown.size()+faceUp.size()-1));
		revalidate();
		repaint();
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
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(Card.PX_WIDTH, (faceDown.size()+faceUp.size())*PX_VISIBLE_SPACE+Card.PX_HEIGHT);
	}
	
}
