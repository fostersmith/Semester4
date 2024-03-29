package extra;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class Card extends JButton {
	
	public static final int PX_WIDTH = 80, PX_HEIGHT = 120;
	
	public static final int HEARTS = 14, DIAMONDS = 15, CLUBS = 16, SPADES = 17;
	public static final int ACE = 1, JACK = 11, QUEEN = 12, KING = 13;
	public static final int FACEDOWN = 0, FACEUP = 1, HIGHLIGHTED = 2;
	
	private static final BufferedImage[] cardImages = createImageMap();
	public static final BufferedImage facedown = getFaceDown();
	
	private final int suit, value;
	private BufferedImage img;
	private int state = FACEUP;
	
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
		if(this.state == FACEDOWN && state != FACEDOWN)
			setEnabled(true);
		else if(this.state != FACEDOWN && state == FACEDOWN)
			setEnabled(false);
		this.state = state;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		//TODO clear
		Graphics2D g2d = (Graphics2D)g;
		if(state == FACEDOWN) {
			g2d.drawImage(facedown, 0, 0, PX_WIDTH, PX_HEIGHT, null);
		} else {
			g2d.drawImage(img, 0, 0, PX_WIDTH, PX_HEIGHT, null);			
		}
		if(state == HIGHLIGHTED) {
			g2d.setStroke(new BasicStroke(4));
			g2d.setPaint(Color.CYAN);
			g2d.drawRect(0, 0, PX_WIDTH, PX_HEIGHT);
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PX_WIDTH, PX_HEIGHT);
	}
	
	@Override
	public int hashCode() {
		return (suit-HEARTS)*(KING-ACE+1)+(value-ACE);
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
	
	public static boolean canStack(Card top, Card bottom) {
		boolean suitsWork = (top.suit==DIAMONDS||top.suit==HEARTS)&&(bottom.suit==SPADES||bottom.suit==CLUBS) || (bottom.suit==DIAMONDS||bottom.suit==HEARTS)&&(top.suit==SPADES||top.suit==CLUBS);
		return suitsWork && bottom.value==top.value-1;
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
	
	private static BufferedImage getFaceDown() {
		try {
			return ImageIO.read(new File("cards\\facedown.png"));
		} catch(IOException e) {
			JOptionPane.showMessageDialog(null, "Failed to fetch image file for facedown card. Stopping execution.");
			System.exit(1);
			return null;
		}
	}
	
	private static BufferedImage getImage(int suit, int value) {
		if(!(suit == HEARTS || suit == DIAMONDS || suit == CLUBS || suit == SPADES))
			throw new IllegalArgumentException("Invalid Suit");
		if(value < ACE || value > KING)
			throw new IllegalArgumentException("Invalid Value");
		return cardImages[(suit-HEARTS)*(KING-ACE+1)+(value-ACE)];
	}
	
	@Override
	public String toString() {
		return value+" of "+suit;
	}
}
