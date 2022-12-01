package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import io.PlayerFile;

public class BlackjackManager {
	
	public enum Move { HIT, STAND, SPLIT, DOUBLE_DOWN };
	public enum Stage { BETTING, INSURING, PLAYING, DONE };
	
	private Deck deck;
	
	private PlayerFile pf;
	private int playerInsurance = 0;
	
	private List<BlackjackHand> playerHand;
	
	private Card dealerDownCard;
	private List<Card> dealerUpcards;
	
	private Stage stage; //TODO
	
	public BlackjackManager(PlayerFile pf) {
		this.pf = pf;
		stage = Stage.BETTING;
		deck = new Deck();
		playerHand = new ArrayList<>();
		dealerUpcards = new ArrayList<>();
		//deal();
	}
	
	public void reset() {
		
	}
	
	public Stage getStage() {return stage;}
	public List<BlackjackHand> playerHand() {return playerHand;}
	public List<Card> dealerHand() {return dealerUpcards;}
	public int getBet(int i) {return playerHand.get(i).bet;}
	public int getInsurance() {return playerInsurance;}
	public boolean handDone(int i) {return playerHand.get(i).done;}
	
	public void deal() {
		while(deck.peek(0).getRank() != deck.peek(1).getRank())
			deck.shuffle();
		playerHand.clear();
		playerHand.add(new BlackjackHand());
		dealerUpcards.clear();
		playerHand.get(0).add(deck.pop());
		playerHand.get(0).add(deck.pop());
		dealerUpcards.add(deck.pop());
		dealerDownCard = deck.pop();
	}
	
	public boolean doBet(int bet) throws Exception {
		if(bet < 1)
			return false;
		if(stage == Stage.BETTING) {
			try {
				pf.takeMoney(bet);
				playerHand.get(0).bet = bet;
				deal();
				stage = Stage.INSURING;
				return true;
			} catch(Exception e) {
				throw new Exception("Bet more money than the player has");
			}
		} else
			return false;
	}
	
	public boolean buyInsurance(int insurance) throws Exception {
		if(insurance < 0)
			throw new IllegalArgumentException("Cannot buy negative insurance");
		if(stage == Stage.INSURING) {
			if(insurance > playerBet/2)
				throw new Exception("Excessive Insurance");
			this.playerInsurance = insurance;
			this.playerBet -= insurance;
			stage = Stage.PLAYING;
			return true;
		} else
			return false;
	}
	
	public boolean hit(int handNum) {
		if(stage == Stage.PLAYING) {
			playerHand.get(handNum).add(deck.pop());
			return true;
		} else
			return false;
	}
	
	public boolean stand(int handNum) {
		if(stage == Stage.PLAYING) {
			playerHand.get(handNum).done = true;
			checkMoveToDone();
			return true;
		} else
			return false;		
	}
	
	public boolean split(int handNum) {
		if(stage == Stage.PLAYING) {
			// Check that cards match
			BlackjackHand hand = playerHand.get(handNum);
			if(hand.size() != 2)
				return false;
			if(hand.get(0).getRank() != hand.get(1).getRank())
				return false;
			BlackjackHand handA = new BlackjackHand();
			BlackjackHand handB = new BlackjackHand();
			handA.add(hand.get(0));
			handB.add(hand.get(1));
			playerHand.remove(handNum);
			playerHand.add(handNum, handA);
			playerHand.add(handNum+1, handB);
			return true;
		} else
			return false;
	}
	
	public void checkMoveToDone() {
		boolean allHandsDone = true;
		for(int i = 0; i < playerHand.size(); ++i) {
			allHandsDone = allHandsDone && playerHand.get(i).done;
		}
		if(allHandsDone)
			stage = Stage.DONE;
	}
	
	public boolean doubleDown(int handNum) {
		if(stage == Stage.PLAYING) {
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
		while(manager.getStage() == Stage.BETTING) {
			System.out.print("Make a bet >> $");
			int bet = in.nextInt();
			in.nextLine();
			try {
				manager.doBet(bet);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		
		printDealerHand(manager.dealerHand());
		printPlayerHand(manager.playerHand().get(0));
		while(manager.getStage() == Stage.INSURING) {
			System.out.print("How much insurance >> $");
			int insurance = in.nextInt();
			in.nextLine();
			try {
				manager.buyInsurance(insurance);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println();
		
		while(manager.getStage() == Stage.PLAYING) {
			printDealerHand(manager.dealerHand());
			for(int i = 0; i < manager.playerHand().size(); ++i) {
				if(!manager.handDone(i)) {
				
					System.out.println("Your bet: $"+manager.getBet());
					if(manager.getInsurance() != 0)
						System.out.println("Your insurance: $"+manager.getInsurance());
					List<Card> hand = manager.playerHand().get(i);
					printPlayerHand(hand);
					System.out.print("Make a move (hit, stand, split, double) >> ");
					String input = in.nextLine().strip().toLowerCase();
					
					switch(input) {
						case "h":
						case "hit":
							System.out.println("Your bet: $"+manager.getBet());
							if(manager.getInsurance() != 0)
								System.out.println("Your insurance: $"+manager.getInsurance());
							System.out.println("Hitting");
							manager.hit(i);
							printPlayerHand(hand);
							break;
						case "st":
						case "stand":
							System.out.println("Your bet: $"+manager.getBet());
							if(manager.getInsurance() != 0)
								System.out.println("Your insurance: $"+manager.getInsurance());
							System.out.println("Standing");
							manager.stand(i);
							printPlayerHand(hand);
							break;
						case "sp":
						case "split":
							System.out.println("Your bet: $"+manager.getBet());
							if(manager.getInsurance() != 0)
								System.out.println("Your insurance: $"+manager.getInsurance());
							System.out.println("Splitting");
							if(manager.split(i)) {
								for(List<Card> h : manager.playerHand())
									printPlayerHand(h);
							} else {
								--i;
								System.out.println("Split unsucessful. Please try another move");
							}
							break;
						case "d":
						case "double":
							System.out.println("Your bet: $"+manager.getBet());
							if(manager.getInsurance() != 0)
								System.out.println("Your insurance: $"+manager.getInsurance());
							System.out.println("Doubling down");
							manager.doubleDown(i);
							printPlayerHand(hand);
							break;
						default:
							--i;
							System.out.println("Move not recognized. Please try again");
							break;
					}
					System.out.println();
				
				}
			}
		}
	}
	
	public static void printPlayerHand(List<Card> hand) {
		System.out.print("Your hand: "+hand.get(0));
		for(int i = 1; i < hand.size(); ++i)
			System.out.print(", "+hand.get(i));
		System.out.println();
	
	
	}
	
	public static void printDealerHand(List<Card> up) {
		System.out.print("Dealer: 1 downcard");
		for(int i = 0; i < up.size(); ++i)
			System.out.print(", "+up.get(i));
		System.out.println();
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
