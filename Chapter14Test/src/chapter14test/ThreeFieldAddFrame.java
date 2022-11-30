package chapter14test;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

// Any frame that collects three fields (EquipmentFrame and StudentFrame)
public abstract class ThreeFieldAddFrame extends JFrame implements ActionListener {
	private JButton addButton, mainMenuButton;
	private JTextField aField,  bField, cField;
	private JLabel aLabel, bLabel, cLabel;
	
	public ThreeFieldAddFrame(String title, String addButtonLabel, String a, String b, String c){
		setTitle(title);
		addButton = new JButton(addButtonLabel);
		mainMenuButton = new JButton("Main Menu");
		addButton.addActionListener(this);
		mainMenuButton.addActionListener(this);
		aField = new JTextField();
		bField = new JTextField();
		cField = new JTextField();
		aLabel = new JLabel(a);
		bLabel = new JLabel(b);
		cLabel = new JLabel(c);
		
		GridLayout l = new GridLayout(4,2);
		l.setVgap(10);
		setLayout(l);
		add(addButton);
		add(mainMenuButton);
		add(aLabel);
		add(aField);
		add(bLabel);
		add(bField);
		add(cLabel);
		add(cField);
		setSize(500,200);
		getContentPane().setBackground(Color.MAGENTA);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();
		if(src == addButton) {
			onAddButton(aField.getText(), bField.getText(), cField.getText());
			aField.setText("");
			bField.setText("");
			cField.setText("");
		} else if(src == mainMenuButton) {
			setVisible(false);
			CSFrame.f1.setVisible(true);
		}
	}
	
	abstract void onAddButton(String a, String b, String c);
}
