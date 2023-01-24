package carlysCatering;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
	
	JLabel entreeLabel = new JLabel("Entree");
	JLabel side1Label = new JLabel("Side 1");
	JLabel side2Label = new JLabel("Side 2");
	JLabel dessertLabel = new JLabel("Dessert");

	JLabel guestLabel = new JLabel("Guest Number");
	JLabel outputLabel = new JLabel();
	

	JPanel entreePanel = new JPanel(new FlowLayout());
	JPanel side1Panel = new JPanel(new FlowLayout());
	JPanel side2Panel = new JPanel(new FlowLayout());
	JPanel dessertPanel = new JPanel(new FlowLayout());
	JPanel guestPanel = new JPanel(new GridLayout(1, 2));
	JPanel outputPanel = new JPanel(new GridLayout(1,2));
	
	
	public JCarlysCatering() {
		for(int i = 0; i < entreeBoxes.length; ++i) {
			entreeBoxes[i] = new JCheckBox(Event.entrees[i]);
			entreeGroup.add(entreeBoxes[i]);
			entreePanel.add(entreeBoxes[i]);
		}
		for(int i = 0; i < side1Boxes.length; ++i) {
			side1Boxes[i] = new JCheckBox(Event.sides[i]);
			side2Boxes[i] = new JCheckBox(Event.sides[i]);
			side1Group.add(side1Boxes[i]);
			side2Group.add(side2Boxes[i]);
			side1Panel.add(side1Boxes[i]);
			side2Panel.add(side2Boxes[i]);
		}
		for(int i = 0; i < dessertBoxes.length; ++i) {
			dessertBoxes[i] = new JCheckBox(Event.desserts[i]);
			dessertGroup.add(dessertBoxes[i]);
			dessertPanel.add(dessertBoxes[i]);
		}
		
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
		
		submitButton.addActionListener(this);
		
	}
	
	public static void main(String[] args) {
		JFrame f1 = new JCarlysCatering();
		f1.setVisible(true);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setSize(200, 600);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
