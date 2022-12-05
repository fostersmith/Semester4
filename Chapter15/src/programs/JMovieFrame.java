package programs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class JMovieFrame extends JFrame implements ActionListener {

	private static final String DELIM = ",";
	BorderLayout layout = new BorderLayout();
	JButton northB = new JButton("North");
	JButton eastB = new JButton("East of eden");
	JButton southB = new JButton("Song of the South");
	JButton westB = new JButton("Red Rock West");
	JButton centerB = new JButton("Center Stage");
	HashMap<JButton,String> releaseDateAndStarName = new HashMap<>();
	
	public JMovieFrame() {
		
		releaseDateAndStarName.put(northB, "1994,Elijah Wood as North");
		releaseDateAndStarName.put(eastB, "1955,James Dean as Caleb Trask");
		releaseDateAndStarName.put(southB, "1946,Lucas Till as Bob Zellner");
		releaseDateAndStarName.put(westB, "1993,Nicholas Cage as Michael");
		releaseDateAndStarName.put(centerB, "2000,Amanda Schull as Jody");
		
		setLayout(layout);
		add(northB, BorderLayout.NORTH);
		add(eastB, BorderLayout.EAST);
		add(southB, BorderLayout.SOUTH);
		add(westB, BorderLayout.WEST);
		add(centerB, BorderLayout.CENTER);
		
		northB.addActionListener(this);
		eastB.addActionListener(this);
		southB.addActionListener(this);
		westB.addActionListener(this);
		centerB.addActionListener(this);
		northB.addActionListener(this);
		
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String[] values = releaseDateAndStarName.get((JButton)e.getSource()).split(DELIM);
		JOptionPane.showMessageDialog(null, "Released: "+values[0]+"\nStar: "+values[1]);
	}
	
	public static void main(String[] args) {
		JFrame f = new JMovieFrame();
		f.setVisible(true);
	}
	
	
	
}
