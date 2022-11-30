package chapter14test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class EquipmentFrame extends ThreeFieldAddFrame {
	
	public EquipmentFrame() {
		super("Add Equipment", "Add Equipment", "Item", "Description",  "Cost");
	}

	@Override
	void onAddButton(String item, String description, String costStr) {
		if(item.equals("")) {
			JOptionPane.showMessageDialog(null, "Enter an Item");
		} else if(description.equals("")) {
			JOptionPane.showMessageDialog(null, "Enter a Description");
		} else {
			try {
				double cost = Double.parseDouble(costStr);
				CSFrame.addEquipment(new Equipment(item, description, cost));
				JOptionPane.showMessageDialog(null, "Successfully Added Equipment");
			} catch(Exception e) {
				JOptionPane.showMessageDialog(null, "Enter a cost");
			}
		}
	}

}