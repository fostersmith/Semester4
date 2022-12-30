package programs;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class JTargetPractice extends JFrame implements MouseListener {

	private static final int BATCH_SIZE = 5;
	
	JLabel[] labels = new JLabel[100];
	
	ArrayList<JLabel> targets = new ArrayList<>();
	
	boolean playing = false;
	LocalDateTime start;
	
	int targetCounter = 0;
	
	public JTargetPractice() {
		setLayout(new GridLayout(10,10));
		for(int i = 0; i < 100; ++i) {
			labels[i] = new JLabel();
			labels[i].addMouseListener(this);
			JPanel container = new JPanel();
			container.add(labels[i]);
			add(container);
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700,700);
		setResizable(false);
		setVisible(true);
		
		start();
	}
	
	public void start() {
		newBatch();
		int option = JOptionPane.showConfirmDialog(null, "Ready?", "Confirm Start", JOptionPane.OK_CANCEL_OPTION);
		if(option == JOptionPane.OK_OPTION) {
			targetCounter = 0;
			playing = true;
			start = LocalDateTime.now();
		} else {
			System.exit(0);
		}
	}
	
	public void end() {
		playing = false;
		int opt = JOptionPane.showConfirmDialog(null, "You clicked "+targetCounter+" targets in 10 seconds. Try again?", "Done", JOptionPane.YES_NO_OPTION);
		if(opt == JOptionPane.YES_OPTION) {
			start();
		} else {
			System.exit(0);
		}
	}
	
	public void newBatch() {
		for(JLabel label : labels) {
			label.setText("");
			label.setForeground(Color.RED);
		}
		
		targets.clear();
		for(int i = 0; i < BATCH_SIZE; ++i) {
			int index;
			do {
				index = (int) (Math.random()*100);
			} while(targets.contains(labels[index]));
			labels[index].setText("X");
			targets.add(labels[index]);
		}
		
		revalidate();
	}
	
	public static void main(String[] args) {
		new JTargetPractice();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(playing && targets.contains(e.getSource())) {
			((JLabel)e.getSource()).setForeground(Color.GREEN);
			targets.remove(e.getSource());
			++targetCounter;
			if(targets.size() == 0)
				newBatch();
		}
		if(LocalDateTime.now().getSecond()-start.getSecond() >= 10)
			end();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
