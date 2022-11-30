package chapter14test;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

// Used for any frame which uses three buttons (CSFrame and ViewFrame)
public abstract class ThreeButtonFrame extends JFrame implements ActionListener {

	private JButton a, b, c;
	
	public ThreeButtonFrame(String title, String a, String b, String c) {
		super(title);
		this.a = new JButton(a);
		this.b = new JButton(b);
		this.c = new JButton(c);
		
		this.a.addActionListener(this);
		this.b.addActionListener(this);
		this.c.addActionListener(this);
		
		GridLayout l = new GridLayout(3,1);
		l.setVgap(15);
		setLayout(l);
		add(this.a);
		add(this.b);
		add(this.c);
		setSize(500,225);
		getContentPane().setBackground(Color.MAGENTA);

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton)e.getSource();
		if(src == a) {
			onA();
		} else if(src == b) {
			onB();
		} else if(src == c) {
			onC();
		}
	}
	
	abstract void onA();
	abstract void onB();
	abstract void onC();

}