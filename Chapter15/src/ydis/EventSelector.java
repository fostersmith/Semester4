package ydis;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class EventSelector extends JFrame implements ActionListener {

	private JMenuBar mainBar  = new JMenuBar();
	private JMenu menu1 = new JMenu("File");
	private JMenu menu2 = new JMenu("Event types");
	private JMenuItem exit = new JMenuItem("Exit");
	private JMenu adult = new JMenu("Adult");
	private JMenu child = new JMenu("Child");
	private JMenuItem adultBirthday = new JMenuItem("Birthday");
	private JMenuItem anniversary = new JMenuItem("Anniversary");
	private JMenuItem retirement = new JMenuItem("Retirement");
	private JMenuItem adultOther = new JMenuItem("Other");
	private JMenuItem childBirthday = new JMenuItem("Birthday");
	private JMenuItem childOther = new JMenuItem("Other");
	
	private JPanel birthdayPanel = new JPanel();
	private JPanel otherPanel = new JPanel();
	private JLabel birthdayLabel = new JLabel("Birthday Events are our specialty");
	private JLabel otherLabel = new JLabel("We have lots of ideas for memorable events");
	private JPanel buttonPanel = new JPanel();
	private JRadioButton radButton1 = new JRadioButton("Formal events");
	private JRadioButton radButton2 = new JRadioButton("Casual events");
	
	public EventSelector() {
		setTitle("Event Selector");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		composeMenus();
		addActionListeners();
		layoutComponents();
	}
	
	public void composeMenus() {
		setJMenuBar(mainBar);
		mainBar.add(menu1);
		mainBar.add(menu2);
		menu1.add(exit);
		menu2.add(adult);
		menu2.add(child);
		adult.add(adultBirthday);
		adult.add(anniversary);
		adult.add(retirement);
		adult.add(adultOther);
		child.add(childBirthday);
		child.add(childOther);
	}
	
	public void addActionListeners() {
		exit.addActionListener(this);
		adultBirthday.addActionListener(this);
		anniversary.addActionListener(this);
		retirement.addActionListener(this);
		adultOther.addActionListener(this);
		childBirthday.addActionListener(this);
		childOther.addActionListener(this);
	}
	
	public void layoutComponents() {
		birthdayPanel.setLayout(new FlowLayout());
		otherPanel.setLayout(new GridLayout(2, 1, 3, 3));
		birthdayPanel.add(birthdayLabel);
		otherPanel.add("other", otherLabel);
		otherPanel.add("buttons", buttonPanel);
		buttonPanel.add(radButton1);
		buttonPanel.add(radButton2);
		add(birthdayPanel);
		add(otherPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		Container con = getContentPane();
		if(source == exit)
			System.exit(0);
		else if(source == childBirthday || source == childOther)
			con.setBackground(Color.PINK);
		else
			con.setBackground(Color.WHITE);
		
		if(source == adultBirthday || source == childBirthday) {
			birthdayPanel.setBackground(Color.YELLOW);
			otherPanel.setBackground(Color.WHITE);
		}
		else {
			birthdayPanel.setBackground(Color.WHITE);
			otherPanel.setBackground(Color.YELLOW);
		}
	}

}
