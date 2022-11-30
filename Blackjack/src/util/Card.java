package util;

public class Card {
	
	public enum Suit {
		HEART, DIAMONDS, CLUB, SPADE
	};
	
	public final static int ACE = 1;
	public final static int JACK = 11;
	public final static int QUEEN = 12;
	public final static int KING = 13;
	
	private final Suit suit;
	private final int rank;
	
	public Card(Suit suit, int rank) {
		if(!isValidRank(rank))
			throw new IllegalArgumentException("Invalid argument passed for rank ("+rank+")");
		this.suit = suit;
		this.rank = rank;
	}
	
	public Card(int hash) {
		this(Suit.values()[hash/13], hash%13 + 1);
	}
		
	public Suit getSuit() { return suit; };
	public int getRank() { return rank; };
	
	private static boolean isValidRank(int rank) {
		return rank >= 1 && rank <= 13;
	}
	
	@Override
	public int hashCode() {
		return suit.ordinal()*13 + rank - 1;
	}
	
	@Override
	public String toString() {
		return rank+" of "+suit.name();
	}
}
