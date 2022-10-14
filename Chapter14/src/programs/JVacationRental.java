package programs;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class JVacationRental extends JFrame implements ActionListener {
	private static final int PARKSIDE_PRICE = 600, POOLSIDE_PRICE = 750, LAKESIDE_PRICE = 825;
	private static final int PRICE_PER_ROOM = 75;
	private static final int MEAL_PRICE = 200;
	
	private static final String PRICE_LABEL_PREFIX = "Total Price Per Week: $";
	
	int total;
	
	FlowLayout layout;
	JCheckBox parksideBox, poolsideBox, lakesideBox;
	JCheckBox oneRoomBox, twoRoomBox, threeRoomBox;
	JCheckBox noMealBox, mealBox;
	ButtonGroup locationGroup, bedroomGroup, mealGroup;
	JLabel priceLabel;
	
	public JVacationRental() {
		
		parksideBox = new JCheckBox("Parkside ($"+PARKSIDE_PRICE+")");
		poolsideBox = new JCheckBox("Poolside ($"+POOLSIDE_PRICE+")");
		lakesideBox = new JCheckBox("Lakeside ($"+LAKESIDE_PRICE+")");
		locationGroup = new ButtonGroup();
		locationGroup.add(parksideBox);
		locationGroup.add(poolsideBox);
		locationGroup.add(lakesideBox);
		locationGroup.setSelected(parksideBox.getModel(), true);
		parksideBox.addActionListener(this);
		poolsideBox.addActionListener(this);
		lakesideBox.addActionListener(this);
		
		oneRoomBox = new JCheckBox("One Bedroom (+$"+PRICE_PER_ROOM+")");
		twoRoomBox = new JCheckBox("Two Bedrooms (+$"+PRICE_PER_ROOM*2+")");
		threeRoomBox = new JCheckBox("Three Bedrooms (+$"+PRICE_PER_ROOM*3+")");
		bedroomGroup = new ButtonGroup();
		bedroomGroup.add(oneRoomBox);
		bedroomGroup.add(twoRoomBox);
		bedroomGroup.add(threeRoomBox);
		bedroomGroup.setSelected(oneRoomBox.getModel(), true);

		oneRoomBox.addActionListener(this);
		twoRoomBox.addActionListener(this);
		threeRoomBox.addActionListener(this);
		
		noMealBox = new JCheckBox("No Meals");
		mealBox = new JCheckBox("Meals (+$"+MEAL_PRICE+")");
		mealGroup = new ButtonGroup();
		mealGroup.add(noMealBox);
		mealGroup.add(mealBox);
		mealGroup.setSelected(noMealBox.getModel(), true);
		noMealBox.addActionListener(this);
		mealBox.addActionListener(this);
		
		total = calcPrice();
		priceLabel = new JLabel(PRICE_LABEL_PREFIX + total);
		
		layout = new FlowLayout();
		setLayout(layout);
		
		add(parksideBox);
		add(poolsideBox);
		add(lakesideBox);
		add(oneRoomBox);
		add(twoRoomBox);
		add(threeRoomBox);
		add(noMealBox);
		add(mealBox);
		add(priceLabel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	public int calcPrice() {
		int total = 0;
		ButtonModel l = locationGroup.getSelection();
		if(l == parksideBox.getModel())
			total += PARKSIDE_PRICE;
		else if(l == poolsideBox.getModel())
			total += POOLSIDE_PRICE;
		else if(l == lakesideBox.getModel())
			total += LAKESIDE_PRICE;
		
		ButtonModel b = bedroomGroup.getSelection();
		if(b == oneRoomBox.getModel())
			total += PRICE_PER_ROOM;
		else if(b == twoRoomBox.getModel())
			total += PRICE_PER_ROOM * 2;
		else if(b == threeRoomBox.getModel())
			total += PRICE_PER_ROOM * 3;
		
		ButtonModel m = mealGroup.getSelection();
		if(m == mealBox.getModel())
			total += MEAL_PRICE;
		
		return total;
	}
	
	public static void main(String[] args) {
		JVacationRental f1 = new JVacationRental();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		total = calcPrice();
		priceLabel .setText(PRICE_LABEL_PREFIX + total);
		pack();
	}
	
}
