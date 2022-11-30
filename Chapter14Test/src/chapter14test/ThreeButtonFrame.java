package chapter14test;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public abstract class ThreeButtonFrame extends JFrame implements ActionListener {

	JButton a, b, c;
	
	public ThreeButtonFrame(String a, String b, String c) {
		this.a = new JButton(a);
		this.b = new JButton(b);
		this.c = new JButton(c);
		
		this.a.addActionListener(this);
		this.b.addActionListener(this);
		this.c.addActionListener(this);
		
		setLayout(null);
		add(this.a);
		add(this.b);
		add(this.c);
		this.a.setBounds(0, 0, 500, 50);
		this.b.setBounds(0, 75, 500, 50);
		this.c.setBounds(0, 140, 500, 50);
		setBackground(Color.PINK); //FIXME

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
