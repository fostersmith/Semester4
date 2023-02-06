package chapter15test;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Simon extends JFrame implements ActionListener {
	
	static Simon f1;
	static Thread t1;
	
	public static final int SHOWING = 0, LISTENING = 1, LOST = 3;
	
	public static final Color LIGHTBLUE = new Color(173,224,255),
			LIGHTRED = new Color(255,183,183),
			LIGHTGREEN = new Color(173,255,173),
			LIGHTYELLOW = new Color(255,255,217),
			BLUE = Color.BLUE,
			RED = Color.RED,
			GREEN = Color.GREEN,
			YELLOW = Color.YELLOW;
	
	Map<JButton, Color> lightColorMap = new HashMap<>();
	Map<JButton, Color> darkColorMap = new HashMap<>();
	
	JButton blueB = new JButton(), 
			redB = new JButton(), 
			greenB = new JButton(), 
			yellowB = new JButton();
	
	JLabel gameOverLabel = new JLabel("Game Over!", JLabel.CENTER);
	
	JPanel mainPanel = new JPanel(new GridLayout(2,2));
	JPanel gameOverPanel = new JPanel(new BorderLayout());
	
	JPanel panelOfPanels = new JPanel(new CardLayout());
	
	JButton[] buttonList = {blueB, redB, greenB, yellowB};
	List<JButton> pattern = new ArrayList<>();
	int userClickCounter;
	
	int state = SHOWING;
	
	int waitTime = 2000;
	
	public Simon() {
		super("Simon");
		
		lightColorMap.put(blueB, LIGHTBLUE);
		lightColorMap.put(redB, LIGHTRED);
		lightColorMap.put(greenB, LIGHTGREEN);
		lightColorMap.put(yellowB, LIGHTYELLOW);
		darkColorMap.put(blueB, BLUE);
		darkColorMap.put(redB, RED);
		darkColorMap.put(greenB, GREEN);
		darkColorMap.put(yellowB, YELLOW);
		
		for(JButton b : buttonList)
			b.addActionListener(this);

		
		mainPanel.add(blueB);		
		mainPanel.add(redB);		
		mainPanel.add(greenB);		
		mainPanel.add(yellowB);
		
		blueB.setBackground(LIGHTBLUE);
		redB.setBackground(LIGHTRED);
		greenB.setBackground(LIGHTGREEN);
		yellowB.setBackground(LIGHTYELLOW);
		
		gameOverLabel.setFont(new Font("Jokerman", Font.BOLD, 20)); //TODO
		gameOverPanel.add(gameOverLabel, BorderLayout.CENTER);
		
		add(mainPanel);
	}
	
	public void run() {
		pattern.clear();
		userClickCounter = 0;
		
		addButtonToPattern();
		showPattern();
	}
	
	public void addButtonToPattern() {
		int i = (int) (Math.random()*buttonList.length);
		pattern.add(buttonList[i]);
	}
	
	public void showPattern() {
		state = SHOWING;
		Thread showThread = new Thread() {
			@Override
			public void run() {
				for(JButton b : buttonList)
					b.setEnabled(false);
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				for(int i = 0; i < pattern.size(); ++i) {
					Toolkit.getDefaultToolkit().beep();
					
					JButton button = pattern.get(i);
					button.setBackground(darkColorMap.get(button));
					
					repaint();
					
					try {
						Thread.sleep(waitTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					button.setBackground(lightColorMap.get(button));
				}
				
				for(JButton b : buttonList)
					b.setEnabled(true);
			}
		};
		showThread.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if(src == pattern.get(userClickCounter)) {
			if(userClickCounter == pattern.size() - 1) {
				waitTime = Math.max(waitTime - 50, 300);
				userClickCounter = 0;
				addButtonToPattern();
				showPattern();
			} else
				++userClickCounter;
		} else {
			state = LOST;
			this.getContentPane().remove(mainPanel);
			this.getContentPane().add(gameOverPanel);
			revalidate();
			repaint();
			int option = JOptionPane.showConfirmDialog(null, "Try again?", "Try again?", JOptionPane.YES_NO_OPTION, 
					JOptionPane.INFORMATION_MESSAGE);
			if(option == JOptionPane.YES_OPTION) {
				remove(gameOverPanel);
				add(mainPanel);
				run();
			}
		}
	}

	public static void main(String[] args) {
		f1 = new Simon();
		f1.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f1.setVisible(true);
		f1.setSize(500, 500);
		
		//t1 = new Thread(f1);
		//t1.start();
		
		f1.run();
	}

}
