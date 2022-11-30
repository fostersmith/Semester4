package extra;

public interface CardCollection {
	public Card[] pop(int index);
	public void addAll(Card[] cards);
	public void highlightCards(int index);
	public void unhighlight();
	public Card getCard(int index);
	public Card[] getCards(int index);
}
