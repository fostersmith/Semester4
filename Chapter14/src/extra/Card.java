package extra;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

public class Card extends JButton {
	
	public static final int PX_WIDTH = 40, PX_HEIGHT = 80;
	
	public static final int HEARTS = 14, DIAMONDS = 15, CLUBS = 16, SPADES = 17;
	public static final int ACE = 1, JACK = 11, QUEEN = 12, KING = 13;
	public static final int FACEDOWN = 0, FACEUP = 1, HIGHLIGHTED = 2;
	
	private final int suit, value;
	private BufferedImage img;
	private int state;
	
	public Card(int suit, int value) {
		if(!(suit == HEARTS || suit == DIAMONDS || suit == CLUBS || suit == SPADES))
			throw new IllegalArgumentException("Invalid Suit");
		if(value < ACE || value > KING)
			throw new IllegalArgumentException("Invalid Value");
		this.suit = suit;
		this.value = value;
	}
	public int getSuit() {
		return suit;
	}
	public int getValue() {
		return value;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		if(!(state==FACEDOWN || state ==FACEUP || state == HIGHLIGHTED))
			throw new IllegalArgumentException("Invalid state");
		this.state = state;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, PX_WIDTH, PX_HEIGHT, null);
		//TODO highlighting
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PX_WIDTH, PX_HEIGHT);
	}
	
	public static BufferedImage getImage(int suit, int value) {
		if(!(suit == HEARTS || suit == DIAMONDS || suit == CLUBS || suit == SPADES))
			throw new IllegalArgumentException("Invalid Suit");
		if(value < ACE || value > KING)
			throw new IllegalArgumentException("Invalid Value");
		return null; //TODO
	}
}
