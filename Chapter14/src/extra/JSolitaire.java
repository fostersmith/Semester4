package extra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public class JSolitaire extends JFrame implements ActionListener {
	
	TableauPile pile;
	
	public JSolitaire() {
		Card card1 = new Card(Card.SPADES,Card.ACE);
		Card card2 = new Card(Card.CLUBS, Card.ACE);
		pile = new TableauPile(new Card[] {card1, card2});
		
		add(pile);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
	public static void main(String[] args) {
		new JSolitaire();
	}

}
