package extra;

public class Card {
	public static final int HEARTS = 14, DIAMONDS = 15, CLUBS = 16, SPADES = 17;
	public static final int ACE = 1, JACK = 11, QUEEN = 12, KING = 13;
	private final int suit, value;
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
}
