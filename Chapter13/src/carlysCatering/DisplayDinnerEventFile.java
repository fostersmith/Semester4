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
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DisplayDinnerEventFile extends JFrame implements ActionListener {
	
	ArrayList<String[]> recs;
	JPanel displayPanel;
	JLabel eventNumLabel, eventTypeLabel, guestsLabel, priceLabel;
	JButton nextButton, backButton, closeButton;
	static DisplayDinnerEventFile f1;
	
	private DisplayDinnerEventFile(ArrayList<String[]> recs) {
		this.recs = recs;
		
		eventNumLabel = new JLabel();
		eventTypeLabel  = new JLabel();
		guestsLabel = new JLabel();
		priceLabel = new JLabel();
		displayPanel = new JPanel(new GridLayout(5,1));
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
		
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == nextButton) {
			
		} else if (e.getSource() == backButton) {
			
		} else if (e.getSource() == closeButton) {
			
		}
	}
	
}
