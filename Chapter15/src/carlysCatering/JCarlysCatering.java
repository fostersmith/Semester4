package carlysCatering;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class JCarlysCatering extends JFrame implements ActionListener {
	
	ButtonGroup entreeGroup = new ButtonGroup();
	ButtonGroup side1Group = new ButtonGroup();
	ButtonGroup side2Group = new ButtonGroup();
	ButtonGroup dessertGroup = new ButtonGroup();
	
	JCheckBox[] entreeBoxes = new JCheckBox[Event.entrees.length];
	JCheckBox[] side1Boxes = new JCheckBox[Event.sides.length];
	JCheckBox[] side2Boxes = new JCheckBox[Event.sides.length];
	JCheckBox[] dessertBoxes = new JCheckBox[Event.desserts.length];
	
	JButton submitButton = new JButton("Calculate Total");
	
	
	JTextField guestField = new JTextField();
	
	JLabel entreeLabel = new JLabel("Entree", SwingConstants.CENTER);
	JLabel side1Label = new JLabel("Side 1", SwingConstants.CENTER);
	JLabel side2Label = new JLabel("Side 2", SwingConstants.CENTER);
	JLabel dessertLabel = new JLabel("Dessert", SwingConstants.CENTER);

	JLabel guestLabel = new JLabel("Guest Number");
	JLabel outputLabel = new JLabel();
	

	JPanel entreePanel = new JPanel(new FlowLayout());
	JPanel side1Panel = new JPanel(new FlowLayout());
	JPanel side2Panel = new JPanel(new FlowLayout());
	JPanel dessertPanel = new JPanel(new FlowLayout());
	JPanel guestPanel = new JPanel(new GridLayout(1, 2));
	JPanel outputPanel = new JPanel(new GridLayout(1,2));
	
	
	public JCarlysCatering() {
		super("JCarlysCatering");
		
		submitButton.setBackground(Color.MAGENTA);
		
		for(int i = 0; i < entreeBoxes.length; ++i) {
			entreeBoxes[i] = new JCheckBox(Event.entrees[i]);
			entreeGroup.add(entreeBoxes[i]);
			entreePanel.add(entreeBoxes[i]);
		}
		entreeGroup.setSelected(entreeBoxes[0].getModel(), true);
		for(int i = 0; i < side1Boxes.length; ++i) {
			side1Boxes[i] = new JCheckBox(Event.sides[i]);
			side2Boxes[i] = new JCheckBox(Event.sides[i]);
			side1Group.add(side1Boxes[i]);
			side2Group.add(side2Boxes[i]);
			side1Panel.add(side1Boxes[i]);
			side2Panel.add(side2Boxes[i]);
		}
		side1Group.setSelected(side1Boxes[0].getModel(), true);
		side2Group.setSelected(side2Boxes[0].getModel(), true);
		for(int i = 0; i < dessertBoxes.length; ++i) {
			dessertBoxes[i] = new JCheckBox(Event.desserts[i]);
			dessertGroup.add(dessertBoxes[i]);
			dessertPanel.add(dessertBoxes[i]);
		}
		dessertGroup.setSelected(dessertBoxes[0].getModel(), true);
		
		guestPanel.add(guestLabel);
		guestPanel.add(guestField);
		
		outputPanel.add(outputLabel);
		outputPanel.add(submitButton);
		
		setLayout(new GridLayout(10,1));
		
		add(entreeLabel);
		add(entreePanel);
		add(side1Label);
		add(side1Panel);
		add(side2Label);
		add(side2Panel);
		add(dessertLabel);
		add(dessertPanel);
		add(guestPanel);
		add(outputPanel);
		entreePanel.setBackground(Color.RED);
		side1Panel.setBackground(Color.ORANGE);
		side2Panel.setBackground(Color.YELLOW);
		dessertPanel.setBackground(Color.GREEN);
		guestPanel.setBackground(Color.BLUE);
		outputPanel.setBackground(Color.MAGENTA);


		submitButton.addActionListener(this);
		
	}
	
	public static int getSelectionIndex(ButtonGroup b, JCheckBox[] boxes) {
		for(int i = 0; i < boxes.length; ++i) {
			if(b.isSelected(boxes[i].getModel()))
				return i;
		}
		return -1;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int entree = getSelectionIndex(entreeGroup, entreeBoxes);
		int side1 = getSelectionIndex(side1Group, side1Boxes);
		int side2 = getSelectionIndex(side2Group, side2Boxes);
		int dessert = getSelectionIndex(dessertGroup, dessertBoxes);
		int guests;
		try {
			guests = Integer.parseInt(guestField.getText().trim());
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "You must enter a valid guest number");
			return;
		}
		Event ev = new Event(entree, side1, side2, dessert, guests);
		outputLabel.setText("Total Price: $"+ev.getPrice());
	}

	public static void main(String[] args) {
		JFrame f1 = new JCarlysCatering();
		f1.setVisible(true);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setSize(400, 600);
	}
	
}
