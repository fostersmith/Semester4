package util;

import java.util.Random;

public class Deck {
	private Card[] cards;
	private int cardPointer = 0; // This represents the NEXT card to be drawn
	
	/**
	 * Creates a new un-shuffled deck with the standard set of 52 cards
	 */
	public Deck() {
		cards = new Card[52];
		for(int i = 0; i < 52; ++i)
			cards[i] = new Card(i);
	}
	
	/** 
	 * Shuffle cards in-place, reset card pointer
	 */
	public void shuffle() {
		Random randy = new Random();
		for(int p = 0; p < cards.length; ++p) {
			int randIndex = randy.nextInt(cards.length);
			Card temp = cards[p];
			cards[p] = cards[randIndex];
			cards[randIndex] = temp;
		}
		
		cardPointer = 0;
	}
	
	/**
	 * View the next card to be drawn without incrementing cardPointer
	 * Returns null if deck is empty
	 */
	public Card peek() {
		if(cardPointer < cards.length)
			return cards[cardPointer];
		else
			return null;
	}
	
	/**
	 * Return the next card and increment cardpointer
	 * Returns null if deck is empty
	 */
	public Card pop() {
		if(cardPointer < cards.length)
			return cards[cardPointer++];
		else
			return null;
	}
	
	/**
	 * Get the card pointer (index of next card to be drawn)
	 * @return The card pointer
	 */
	public int getCardPointer() {
		return cardPointer;
	}
	/**
	 * Set the card pointer (index of next card to be drawn)
	 * @param cardPointer - the new card pointer
	 */
	public void setCardPointer(int cardPointer) {
		this.cardPointer = cardPointer;
	}
}
