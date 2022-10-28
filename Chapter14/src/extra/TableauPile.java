package extra;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class TableauPile extends JPanel {
	
	public static final int PX_VISIBLE_SPACE = 10; //vertical distance between cards
	ArrayList<Card> faceDown, faceUp;
	
	public TableauPile() {
		super();
		setLayout(null);
		faceDown = new ArrayList<>();
		faceUp = new ArrayList<>();
	}
	
	public void faceUpAdd(Card c) {
		c.setState(Card.FACEUP);
		faceUp.add(c);
		add(c);
		c.setBounds(0,(faceDown.size()+faceUp.size())*PX_VISIBLE_SPACE,Card.PX_WIDTH,Card.PX_HEIGHT);
	}
	
	public void faceDownAdd(Card c) {
		c.setState(Card.FACEDOWN);
		faceDown.add(c);
		add(c);
		c.setBounds(0,faceDown.size()*PX_VISIBLE_SPACE,Card.PX_WIDTH,Card.PX_HEIGHT);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(Card.PX_WIDTH, (faceDown.size()+faceUp.size()-1)*PX_VISIBLE_SPACE+Card.PX_HEIGHT);
	}
	
}
