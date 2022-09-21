package carlysCatering;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DisplayDinnerEventFile extends JFrame implements ActionListener {
	
	ArrayList<String[]> recs;
	int onRec = 0;
	JPanel displayPanel, buttonPanel;
	JLabel eventNumLabel, eventTypeLabel, guestsLabel, priceLabel;
	JButton nextButton, backButton;
	boolean buttonDown = false;
	static DisplayDinnerEventFile f1;
	
	private DisplayDinnerEventFile(ArrayList<String[]> recs) {
		
		eventNumLabel = new JLabel();
		eventTypeLabel  = new JLabel();
		guestsLabel = new JLabel();
		priceLabel = new JLabel();
		
		nextButton = new JButton("Next");
		backButton = new JButton("Back");
		
		nextButton.addActionListener(this);
		backButton.addActionListener(this);
		
		displayPanel = new JPanel(new GridLayout(5,1));
		buttonPanel = new JPanel(new GridLayout(1,2));
		
		displayPanel.add(eventNumLabel);
		displayPanel.add(eventTypeLabel);
		displayPanel.add(guestsLabel);
		displayPanel.add(priceLabel);
		
		buttonPanel.add(backButton);
		buttonPanel.add(nextButton);
		
		displayPanel.add(buttonPanel);
		
		this.add(displayPanel);
		this.setBounds(0,0,200,500);
		
		this.recs = recs;
		setRec(0);
	}
	
	public static DisplayDinnerEventFile readFromFile(Path file) throws IOException {
		InputStream input = Files.newInputStream(file);
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		
		ArrayList<String[]> recs = new ArrayList<>();
		String rec;
		while((rec = reader.readLine())!=null) {
			String[] fields = rec.split(",");
			recs.add(fields);
		}
		
		return new DisplayDinnerEventFile(recs);
	}
		
	public void setLabels(String[] rec) {
		eventNumLabel.setText("Event Number: "+rec[0]);
		eventTypeLabel.setText("Event Type: "+rec[1]);
		guestsLabel.setText("Guests: "+rec[2]);
		priceLabel.setText("Price: $"+rec[3]);
	}
	
	public void setRec(int onRec) {
		setLabels(recs.get(onRec));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == nextButton) {
			onRec = (onRec+1)%recs.size();
			setRec(onRec);
		} else if (e.getSource() == backButton) {
			onRec = (onRec+(recs.size()-1))%recs.size();
			setRec(onRec);
		}
		System.out.println("On Record: "+onRec);
	}
	
	public static void main(String[] args) throws IOException {
		
		Path file = Paths.get("events.txt");
		f1 = DisplayDinnerEventFile.readFromFile(file);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setVisible(true);
		
	}
	
}
