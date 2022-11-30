package chapter14test;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public abstract class ThreeFieldAddFrame extends JFrame implements ActionListener {
	JButton addButton, mainMenuButton;
	JTextField aField,  bField, cField;
	JLabel aLabel, bLabel, cLabel;
	CSFrame mainMenu;
	
	public ThreeFieldAddFrame(String title, String addButtonLabel, String a, String b, String c, CSFrame mainMenu){
		setTitle(title);
		this.mainMenu = mainMenu;
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
		
		setLayout(new GridLayout(4,2));
		add(addButton);
		add(mainMenuButton);
		add(aField);
		add(aLabel);
		add(bField);
		add(bLabel);
		add(cField);
		add(cLabel);
		setSize(500,125);
		
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
			mainMenu.setVisible(true);
		}
	}
	
	abstract void onAddButton(String a, String b, String c);
}
