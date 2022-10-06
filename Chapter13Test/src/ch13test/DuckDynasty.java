/*
 * Foster Smith
 * DuckDynasty.java
 */

package ch13test;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DuckDynasty extends JFrame implements ActionListener{
	//Record Template:
	//ID,Name,Color,Smack\n
	protected static final String DELIM = ",";
	
	ArrayList<Ducky> duckies;
	Path file;

	JLabel IDLabel, nameLabel, colorLabel, smackLabel;
	JTextField IDField, nameField, colorField, smackField;
	JButton submitButton, viewButton;
	JPanel inputPanel, buttonPanel;
	
	public DuckDynasty(String path) throws IOException {
		file = Paths.get(path);
		duckies = new ArrayList<>();
		InputStream input = new BufferedInputStream(Files.newInputStream(file));
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String rec;
		while((rec = reader.readLine()) != null) {
			duckies.add(parseRecord(rec));
		}
		input.close();
		
		inputPanel = new JPanel(new GridLayout(4,2));
		buttonPanel = new JPanel(new FlowLayout());
		
		IDLabel = new JLabel("Enter ducky ID:");
		nameLabel = new JLabel("Enter ducky name:");
		colorLabel = new JLabel("Enter ducky color:");
		smackLabel = new JLabel("Enter smack of:");
		
		IDField = new JTextField();
		nameField = new JTextField();
		colorField = new JTextField();
		smackField = new JTextField();
		
		submitButton = new JButton("Submit");
		viewButton = new JButton("View Duckies");
		
		submitButton.addActionListener(this);
		viewButton.addActionListener(this);
		
		inputPanel.add(IDLabel);
		inputPanel.add(IDField);
		inputPanel.add(nameLabel);
		inputPanel.add(nameField);
		inputPanel.add(colorLabel);
		inputPanel.add(colorField);
		inputPanel.add(smackLabel);
		inputPanel.add(smackField);
		
		buttonPanel.add(submitButton);
		buttonPanel.add(viewButton);
		
		setLayout(new BorderLayout());
		add(inputPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Checks given ID against IDs of all saved ducks
	 * @param ID
	 * @return true if there exists a ducky with a matching ID, else false
	 */
	public boolean checkDuckyID(String ID) {
		for(Ducky d : duckies)
			if(d.getID().equals(ID))
				return true;
		return false;
	}
	
	public void save() throws IOException {
		OutputStream output = new BufferedOutputStream(Files.newOutputStream(file));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
		for(int i = 0; i < duckies.size(); ++i) {
			writer.write(duckies.get(i).toString());
			if(i < duckies.size()-1)
				writer.newLine();
		}
		writer.flush();
		output.close();
	}
	
	public static Ducky parseRecord(String rec) throws IOException {
		String[] fields = rec.split(DELIM);
		if(fields.length != 4)
			throw new IOException("Bad Record");
		return new Ducky(fields[0],fields[1],fields[2],fields[3]);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == submitButton) {
			String ID = IDField.getText();
			String name = nameField.getText();
			String color = colorField.getText();
			String smack = smackField.getText();
			if(!checkDuckyID(ID) && ID.length() == 3) {
				duckies.add(new Ducky(ID, name, color, smack));
				IDField.setText("");
				nameField.setText("");
				colorField.setText("");
				smackField.setText("");
				try {
					save();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "An Error Occurred");
				}
			}
			else if(ID.length() != 3)
				JOptionPane.showMessageDialog(null, "The ID is the wrong length", "Error", JOptionPane.ERROR_MESSAGE);
			else
				JOptionPane.showMessageDialog(null, "The ID already exists", "Error", JOptionPane.ERROR_MESSAGE);
				
		} else if(e.getSource() == viewButton) {
			for(Ducky d : duckies) {
				JOptionPane.showMessageDialog(null, d.getDisplayString(), "Duckies", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		DuckDynasty f1 = new DuckDynasty("duckies.txt");
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setBounds(0,0,400,200);
		f1.setVisible(true);
	}
}
