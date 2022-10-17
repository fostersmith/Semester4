package programs;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JFrame;

public class JDorm extends JFrame implements ItemListener {

	private static final String[] OPTIONS = {"Private Room","Apartment-Style","Private Bath","Loft Bed","Double-As-Single","Bike Storage","Rec Room","Cable TV","All-You-Can-Eat Meal Plan","Music Practice Rooms","Library"};
	
	public JDorm() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400,200);
		setResizable(false);
		setVisible(true);
	}
	
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
