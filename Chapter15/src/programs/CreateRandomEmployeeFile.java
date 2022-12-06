package programs;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class CreateRandomEmployeeFile extends JFrame implements ActionListener {

	private static final String FILEPATH = "employees.txt";
	private static final int MAX_ID = 99;
	
	private static final int FNAMELEN = 10;
	private static final int LNAMELEN = 10;
	private static final float MAXPAY = 100.0f;
	
	private static final String DELIM = ",";
	
	private static final String DEFAULT_REC = "00,         ,          ,000.00\n";
	
	JTextField idF = new JTextField();
	JTextField firstNameF = new JTextField();
	JTextField secondNameF = new JTextField();
	JTextField payF = new JTextField();
	JLabel idL = new JLabel("Employee ID: ");
	JLabel firstNameL = new JLabel("First Name: ");
	JLabel secondNameL = new JLabel("Second Name: ");
	JLabel payL = new JLabel("Hourly Pay: ");
	
	JButton submitB = new JButton("Submit");
	JButton clearB = new JButton("Clear");
	
	FileChannel fc;
	Path file;
	
	public CreateRandomEmployeeFile() {
		super("Create Employee File");
		file = Paths.get(FILEPATH);
		try {
			fc = (FileChannel) Files.newByteChannel(file, WRITE, CREATE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "An IO Exception Occurred. Stopping excution");
			System.exit(1);
		}
		
		submitB.addActionListener(this);
		clearB.addActionListener(this);
		
		setLayout(new GridLayout(5, 2));
		add(idL);
		add(idF);
		add(firstNameL);
		add(firstNameF);
		add(secondNameL);
		add(secondNameF);
		add(payL);
		add(payF);
		add(submitB);
		add(clearB);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 400);
	}
	
	public void clearFields() {
		idF.setText("");
		firstNameF.setText("");
		secondNameF.setText("");
		payF.setText("");
	}
	
	public void writeRecord(int id, String fName, String lName, float pay) {
		if(id < 0 || id > MAX_ID)
			throw new IllegalArgumentException("ID out of range");
		if(pay < 0 || pay > MAXPAY)
			throw new IllegalArgumentException("Pay out of range");
		if(fName.contains(DELIM) || lName.contains(DELIM))
			throw new IllegalArgumentException("Can't use delimiter in name");
		if(fName.length() > FNAMELEN)
			fName = fName.substring(0,FNAMELEN-1);
		else if(fName.length() < FNAMELEN) {
			int padding = fName.length() - FNAMELEN;
			for(int i = 0; i < padding; ++i)
				fName += " ";
		}
		if(lName.length() > LNAMELEN)
			lName = lName.substring(0,LNAMELEN-1);
		else if(lName.length() < LNAMELEN) {
			int padding = lName.length() - LNAMELEN;
			for(int i = 0; i < padding; ++i)
				lName += " ";
		}
		
		String rec = String.format("", id)+DELIM+fName+DELIM+lName+DELIM+String.format("", pay)+"\n";
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == submitB) {
			try {
				int id = Integer.parseInt(idF.getText());
				String fName = firstNameF.getText();
				String lName = secondNameF.getText();
				float pay = Float.parseFloat(payF.getText());
				writeRecord(id, fName, lName, pay);
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "Invalid arguments passed");
				return;
			}
			JOptionPane.showMessageDialog(null, "Success");
		}
		clearFields();
	}
	
	public static void main(String[] args) {
		JFrame f = new CreateRandomEmployeeFile();
		f.setVisible(true);
		
		
		System.out.println(String.format("%10s","017890"));
	}
	
}
