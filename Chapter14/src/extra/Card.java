package extra;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class Card extends JButton {
	
	public static final int PX_WIDTH = 80, PX_HEIGHT = 160;
	
	public static final int HEARTS = 14, DIAMONDS = 15, CLUBS = 16, SPADES = 17;
	public static final int ACE = 1, JACK = 11, QUEEN = 12, KING = 13;
	public static final int FACEDOWN = 0, FACEUP = 1, HIGHLIGHTED = 2;
	
	private static final BufferedImage[] cardImages = createImageMap();
	private static final BufferedImage facedown = getFaceDown();
	
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
		img = getImage(suit, value);
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
		//TODO clear
		if(state == FACEDOWN) {
			g.drawImage(facedown, 0, 0, PX_WIDTH, PX_HEIGHT, null);
		} else {
			g.drawImage(img, 0, 0, PX_WIDTH, PX_HEIGHT, null);			
		}
		if(state == HIGHLIGHTED) {
			//TODO highlighting			
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PX_WIDTH, PX_HEIGHT);
	}
	
	@Override
	public int hashCode() {
		return (suit-HEARTS)*(SPADES-HEARTS)+(value-ACE);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		return state == other.state && suit == other.suit;
	}
	
	public static BufferedImage[] createImageMap() {
		try {
			BufferedImage[] images = new BufferedImage[52];
			int i = 0;
			for(int suite = HEARTS; suite <= SPADES; ++suite) {
				for(int value = ACE; value <= KING; ++value) {
					StringBuilder path = new StringBuilder();
					switch(suite) {
					case HEARTS:
						path.append("s");
						break;
					case DIAMONDS:
						path.append("l");
						break;
					case CLUBS:
						path.append("k");
						break;
					case SPADES:
						path.append("p");
						break;
					}
					switch(value) {
					case ACE:
						path.append("a");
						break;
					case JACK:
						path.append("j");
						break;
					case QUEEN:
						path.append("q");
						break;
					case KING:
						path.append("k");
						break;
					default:
						path.append(Integer.toString(value));
					}
					BufferedImage img = ImageIO.read(new File("cards\\"+path+".png"));
					images[i] = img;
					++i;
				}
			}
			return images; //TODO
		} catch(IOException e) {
			JOptionPane.showMessageDialog(null, "Failed to fetch image files for some cards. Stopping execution.");
			System.exit(1);
			return null;
		}
	}
	
	public static BufferedImage getFaceDown() {
		try {
			return ImageIO.read(new File("cards\\facedown.png"));
		} catch(IOException e) {
			JOptionPane.showMessageDialog(null, "Failed to fetch image file for facedown card. Stopping execution.");
			System.exit(1);
			return null;
		}
	}
	
	public static BufferedImage getImage(int suit, int value) {
		if(!(suit == HEARTS || suit == DIAMONDS || suit == CLUBS || suit == SPADES))
			throw new IllegalArgumentException("Invalid Suit");
		if(value < ACE || value > KING)
			throw new IllegalArgumentException("Invalid Value");
		return cardImages[(suit-HEARTS)*(SPADES-HEARTS)+(value-ACE)];
	}
}
