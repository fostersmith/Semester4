package programs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;

public class JPizza extends JFrame implements ActionListener {
	private final static int SMALL_PRICE = 7, MEDIUM_PRICE = 9, LARGE_PRICE = 11, EXTRA_LARGE_PRICE = 14;
	private final static int TOPPING_PRICE = 1;
	
	private final static String[] TOPPINGS = {"Anchovies", "Mushrooms", "Pepperoni", "Sausage", "BBQ Chicken"};
	
	ButtonGroup sizeGroup;
	ButtonGroup toppingGroup;
	JCheckBox smallBox, mediumBox, largeBox, xLargeBox;
	JCheckBox cheeseBox;
	JCheckBox[] toppingBoxes;
	
	public JPizza() {
		smallBox = new JCheckBox("Small ($"+SMALL_PRICE+")");
		mediumBox = new JCheckBox("Medium ($"+MEDIUM_PRICE+")");
		largeBox = new JCheckBox("Large ($"+LARGE_PRICE+")");
		xLargeBox = new JCheckBox("Medium ($"+EXTRA_LARGE_PRICE+")");
		sizeGroup = new ButtonGroup();
		sizeGroup.add(smallBox);
		sizeGroup.add(mediumBox);
		sizeGroup.add(largeBox);
		sizeGroup.add(xLargeBox);
		sizeGroup.setSelected(smallBox.getModel(), true);
	}
	
	public int calcPrice() {
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		new JPizza();
	}
}
