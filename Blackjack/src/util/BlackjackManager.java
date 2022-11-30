package util;

import java.util.List;
import java.util.Scanner;

import io.PlayerFile;

public class BlackjackManager {
	
	public enum Move { HIT, STAND, SPLIT, DOUBLE_DOWN };
	public enum Stage { BETTING, INSURING, PLAYING, DONE };
	
	private Deck deck;
	
	private PlayerFile pf;
	private int playerBet;
	private List<Card> playerHand;
	
	private Card dealerDownCard;
	private List<Card> dealerUpcards;
	
	private Stage stage; //TODO
	
	public BlackjackManager(PlayerFile pf) {
		this.pf = pf;
	}
	
	public void reset() {
		
	}
	
	public Stage getStage() {return stage;}
	public List<Card> playerHand() {return playerHand;}
	public List<Card> dealerHand() {return dealerUpcards;}
	
	public boolean doBet(int bet) throws Exception {
		if(stage == Stage.BETTING) {
			try {
				pf.takeMoney(bet);
				playerBet = bet;
				return true;
			} catch(Exception e) {
				throw new Exception("Bet more money than the player has");
			}
		} else
			return false;
	}
	
	public boolean hit() {
		if(stage == Stage.BETTING) {
			//TODO
			return true;
		} else
			return false;
	}
	
	public boolean stand() {
		if(stage == Stage.BETTING) {
			//TODO
			return true;
		} else
			return false;		
	}
	
	public boolean split() {
		if(stage == Stage.BETTING) {
			//TODO
			return true;
		} else
			return false;
	}
	
	public boolean doubleDown() {
		if(stage == Stage.BETTING) {
			//TODO
			return true;
		} else
			return false;
	}
	
	public Move getDealerMove() {
		return Move.HIT; //TODO
	}
	
	public static int getHandValue(List<Card> hand) {
		int sum = 0;
		int numAces = 0;
		for(Card c : hand) {
			int rank = c.getRank();
			if(rank == Card.ACE) {
				++numAces;
			} else if(rank < Card.JACK) {
				sum += rank;
			} else {
				sum += 10;
			}
		}
		if(sum > 21 + numAces)
			return -1; //bust
		if(numAces != 0) {
			for(int i = 0; i < numAces; ++i) {
				if(sum + 11 + numAces-i-1 <= 21) {
					sum += 11;
				} else
					sum += 1;
			}
		}
		return sum;
	}
	
	public static void main(String[] args) {
		BlackjackManager manager = new BlackjackManager(new PlayerFile(100));
		Scanner in = new Scanner(System.in);
		while(manager.getStage() != Stage.DONE) {
			printPlayerHand(manager.playerHand());
			System.out.print("Make a move >> ");
			String input = in.nextLine();
		}
	}
	
	public static void printPlayerHand(List<Card> hand) {
		System.out.print("Your hand: "+hand.get(0));
		for(int i = 1; i < hand.size(); ++i)
			System.out.print(", "+hand.get(i));
	}
	
	/*public static void main(String[] args) {
		Deck d = new Deck();
		d.shuffle();
		Card last = d.pop();
		for(int i = 0; i < 51; ++i) {
			Card c = d.pop();
			ArrayList<Card> list = new ArrayList<Card>();
			list.add(c);
			list.add(last);
			int val = getHandValue(list);
			System.out.println(c+", "+last+"\t == "+val);
			last = c;
		}
	}*/
}
