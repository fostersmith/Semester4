package programs;

import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class JDorm extends JFrame implements ItemListener {

	private static final String[] OPTIONS = {"Private Room","Apartment-Style","Private Bath","Loft Bed","Double-As-Single","Bike Storage","Rec Room","Cable TV","All-You-Can-Eat Meal Plan","Music Practice Rooms","Library"};
	
	JCheckBox[] options;
	JTextArea field;
	JPanel optPanel;
	JPanel fieldPanel;
	
	public JDorm() {
		
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
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,400);
		setResizable(true);
		setVisible(true);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		JCheckBox box = (JCheckBox)e.getSource();
		field.append("\n"+(e.getStateChange()==1 ? "+ " : "- ")+box.getText());
		System.out.println(getHeight() +","+ getWidth());
	}
	
	public static void main(String[] args) {
		new JDorm();
	}
	
}
