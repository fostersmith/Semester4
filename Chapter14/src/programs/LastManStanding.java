package programs;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LastManStanding extends JFrame implements ActionListener, ItemListener {

	JCheckBox[] boxes;
	JPanel boxPanel;
	JButton submitButton;
	JCheckBox[] selected;
	int ptr = -1;
	ArrayList<JCheckBox> available;
	
	public LastManStanding() {
		boxPanel = new JPanel(new GridLayout(2, 5));
		
		available = new ArrayList<>();
		selected = new JCheckBox[3];
		
		boxes = new JCheckBox[10];
		for(int i = 0; i < boxes.length;++i) {
			boxes[i] = new JCheckBox();
			boxPanel.add(boxes[i]);
			available.add(boxes[i]);
			boxes[i].addItemListener(this);
		}
		
		submitButton = new JButton("Submit");
		submitButton.addActionListener(this);
		
		setLayout(null);
		add(boxPanel);
		boxPanel.setBounds(0,0,500,200);
		add(submitButton);
		submitButton.setBounds(200,200,100,50);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setResizable(false);
		setSize(500,220);
		setVisible(true);
	}
	
	
	public void doBotTurn() {
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		new LastManStanding();
	}
}
