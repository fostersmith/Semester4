package programs;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class JPizza extends JFrame implements ActionListener {
	private final static int SMALL_PRICE = 7, MEDIUM_PRICE = 9, LARGE_PRICE = 11, EXTRA_LARGE_PRICE = 14;
	private final static int TOPPING_PRICE = 1;
	
	private final static String[] TOPPINGS = {"Anchovies", "Mushrooms", "Pepperoni", "Sausage", "BBQ Chicken"};
	
	ButtonGroup sizeGroup;
	ButtonGroup toppingGroup;
	JCheckBox smallBox, mediumBox, largeBox, xLargeBox;
	JCheckBox cheeseBox;
	JCheckBox[] toppingBoxes;
	FlowLayout layout;
	int price;
	JLabel priceLabel;
	
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
		
		cheeseBox = new JCheckBox("Cheese ($0)");
		toppingBoxes = new JCheckBox[TOPPINGS.length];
		for(int i = 0; i < toppingBoxes.length; ++i) {
			toppingBoxes[i] = new JCheckBox(TOPPINGS[i]+"($"+TOPPING_PRICE+")");
		}
		toppingGroup = new ButtonGroup();
		toppingGroup.add(cheeseBox);
		for(JCheckBox b:toppingBoxes)
			toppingGroup.add(b);
		toppingGroup.setSelected(cheeseBox.getModel(), true);
		
		smallBox.addActionListener(this);
		mediumBox.addActionListener(this);
		largeBox.addActionListener(this);
		xLargeBox.addActionListener(this);
		cheeseBox.addActionListener(this);
		for(JCheckBox b:toppingBoxes)
			b.addActionListener(this);

		price = calcPrice();
		priceLabel = new JLabel("Total: $"+price);

		layout = new FlowLayout();
		setLayout(layout);
		add(smallBox);
		add(mediumBox);
		add(largeBox);
		add(xLargeBox);
		add(cheeseBox);
		for(JCheckBox b:toppingBoxes)
			add(b);
		
		add(priceLabel);
		
		setSize(440,240);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
	}
	
	public int calcPrice() {
		int total = 0;
		ButtonModel m = sizeGroup.getSelection();
		if(m == smallBox.getModel())
			total += SMALL_PRICE;
		else if(m == mediumBox.getModel())
			total += MEDIUM_PRICE;
		else if(m == largeBox.getModel())
			total += LARGE_PRICE;
		else if(m == xLargeBox.getModel())
			total += EXTRA_LARGE_PRICE;
		
		m = toppingGroup.getSelection();
		if(m != cheeseBox.getModel())
			total += TOPPING_PRICE;
		return total;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		price = calcPrice();
		priceLabel.setText("Total: $"+price);
	}
	
	public static void main(String[] args) {
		new JPizza();
	}
}
