package extra;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class JSolitaire extends JFrame implements ActionListener {
	
	TableauPile[] piles = new TableauPile[7];
	FoundationPile[] foundation = new FoundationPile[4];
	StockPile stock;
	WastePile waste;
	Deck d;
	int[] cardLocations = new int[52]; // maps card -> location
	int curhighlighted = -1;
	int highlightedIndex = 0;
	// 0-6 - Tableau
	// 7-10 - Foundation
	// 11 - Stock
	// 12 - Waste
	
	public JSolitaire() {
		
		for(int i = 0; i < cardLocations.length; ++i)
			cardLocations[i] = 11; //All are in stock
		
		d = new Deck(this);
		d.shuffle();
		
		for(int p = 0; p < piles.length; ++p) {
			Card[] faceDown = new Card[p];
			for(int c = 0; c < faceDown.length; ++c) {
				faceDown[c] = d.getNext();
				cardLocations[faceDown[c].hashCode()] = p;
			}
			piles[p] = new TableauPile(faceDown);
			Card f = d.getNext();
			cardLocations[f.hashCode()] = p;
			piles[p].faceUpAdd(f);
			
		}
		
		setLayout(null);
		
		for(int p = 0; p < piles.length; ++p) {
			add(piles[p]);
			Dimension size = piles[p].getPreferredSize();
			piles[p].setBounds(p*Card.PX_WIDTH, 0, (int)size.getWidth(), (int)size.getHeight());
		}
		
		//setSize(piles.length*Card.PX_WIDTH,piles[piles.length-1].getPreferredSize().height);
		setSize(Card.PX_WIDTH*piles.length, TableauPile.PX_VISIBLE_SPACE*(piles.length-1)+Card.PX_HEIGHT);
		setTitle("Bad Solitaire");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setResizable(false);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof Card) {
			Card c = (Card)e.getSource();
			int loc = cardLocations[c.hashCode()];
			if(loc<=6) {
				System.out.println("In tableau "+(loc+1));
				
				if(curhighlighted < 0) { // nothing highlighted
					highlightedIndex = piles[loc].indexOf(c);
					piles[loc].highlightCards(highlightedIndex);
					if(curhighlighted > -1)
						piles[curhighlighted].unhighlight();
					curhighlighted = loc;
				} else { //something highlighted
					piles[curhighlighted].removeCards(highlightedIndex);
					curhighlighted = -1;
				}
				revalidate();
				repaint();
			} else if(loc <= 10) {
				System.out.println("In foundation "+(loc-6));
			} else if(loc == 11) {
				System.out.println("In Stock");
			} else {
				System.out.println("In Waste");
			}
		}
	}
	
	public static void main(String[] args) {
		new JSolitaire();
	}

}
