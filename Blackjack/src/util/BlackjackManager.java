package util;

import java.util.List;

import io.PlayerFile;

public class BlackjackManager {
	
	public enum Move { HIT, STAND, SPLIT, DOUBLE_DOWN };
	public enum Stage { BETTING };
	
	private Deck deck;
	
	private PlayerFile pf;
	private int playerBet;
	private List<Card> playerHand;
	
	private Card dealerDownCard;
	private List<Card> dealerUpcards;
	
	private Stage stage; //TODO
	
	public void reset() {
		
	}
	
	public boolean doBet(int bet) throws Exception {
		if(stage == Stage.BETTING) {
			try {
				playerBet = 
			}
		}
	}
	
	public boolean doPlayerMove(Move move) {
		return false; //TODO
	}
	
	public Move getDealerMove() {
		return Move.HIT; //TODO
	}
}
