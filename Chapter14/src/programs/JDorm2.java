package programs;

import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class JDorm2 extends JFrame implements ItemListener {

	private static final String[] OPTIONS = {"Private Room","Apartment-Style","Private Bath","Loft Bed","Double-As-Single","Bike Storage","Rec Room","Cable TV","All-You-Can-Eat Meal Plan","Music Practice Rooms","Library"};
	
	JCheckBox[] options;
	JTextArea field;
	JPanel optPanel;
	JPanel fieldPanel;
	
	public JDorm2() {
		
		options = new JCheckBox[OPTIONS.length];
		for(int i = 0; i < OPTIONS.length; ++i) {
			options[i] = new JCheckBox(OPTIONS[i]);
			options[i].addItemListener(this);
		}
		
		field = new JTextArea();
		field.setEditable(false);
		
		optPanel = new JPanel(new FlowLayout());
		//fieldPanel = new JPanel();
		
		setLayout(null);
		for(JCheckBox b : options)
			optPanel.add(b);
		//fieldPanel.add(field);
		
		add(optPanel);
		optPanel.setBounds(0,0, 500, 200);
		add(field);
		field.setBounds(0, 200, 500, 200);
		
		itemStateChanged(null);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,500);
		setResizable(true);
		setVisible(true);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		field.setText("");
		for(JCheckBox b : options) {
			if(b.isSelected())
				field.append(b.getText()+": Yes\n");
			else
				field.append(b.getText()+": No\n");
		}
	}
	
	public static void main(String[] args) {
		new JDorm2();
	}
	
}
