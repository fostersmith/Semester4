package chapter14test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class EquipmentFrame extends ThreeFieldAddFrame {
	
	public EquipmentFrame(CSFrame mainMenu) {
		super("Add Equipment", "Add Equipment", "Item", "Description",  "Cost", mainMenu);
	}

	@Override
	void onAddButton(String a, String b, String c) {
		if(a.equals("")) {
			JOptionPane.showMessageDialog(null, "Enter an Item");
		} else if(b.equals("")) {
			JOptionPane.showMessageDialog(null, "Enter an Item");
		}
		try {
			double cost = Double.parseDouble(c);
			CSFrame.addEquipment(new Equipment(a, b, cost));
			JOptionPane.showMessageDialog(null, "Success");
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Enter a cost");
		}
	}

}
