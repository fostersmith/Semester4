package programs;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class CreateRandomEmployeeFile extends JFrame implements ActionListener {

	public static final String FILEPATH = "employees.txt";
	public static final int MAX_ID = 99;
	
	public static final int FNAMELEN = 10;
	public static final int LNAMELEN = 10;
	public static final float MAXPAY = 100.0f;
	
	public static final String DELIM = ",";
	
	public static final String DEFAULT_REC = "00,         ,          ,000.00\n";
	
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
	
	DecimalFormat payFormat = new DecimalFormat("000.00");
	
	public CreateRandomEmployeeFile() {
		super("Create Employee File");
		file = Paths.get(FILEPATH);
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(file, CREATE)));
			for(int i = 0; i < 99; ++i) {
				writer.write(DEFAULT_REC, 0, DEFAULT_REC.length());
			}
			writer.flush();
			fc = (FileChannel) Files.newByteChannel(file, WRITE);
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
	
	public void writeRecord(int id, String fName, String lName, float pay) throws IOException {
		if(id < 0 || id > MAX_ID)
			throw new IllegalArgumentException("ID out of range");
		if(pay < 0 || pay > MAXPAY)
			throw new IllegalArgumentException("Pay out of range");
		if(fName.contains(DELIM) || lName.contains(DELIM))
			throw new IllegalArgumentException("Can't use delimiter in name");
		
		fName = String.format("%"+(FNAMELEN-1)+"s", fName);
		if(fName.length() > FNAMELEN)
			fName = fName.substring(0, FNAMELEN-1);
		
		lName = String.format("%"+(LNAMELEN)+"s", lName);
		if(lName.length() > LNAMELEN)
			lName = lName.substring(0, LNAMELEN-1);

		String rec = String.format("%02d", id)+DELIM+fName+DELIM+lName+DELIM+payFormat.format(pay)+"\n";
		fc.position(id*DEFAULT_REC.length());
		ByteBuffer buff = ByteBuffer.wrap(rec.getBytes());
		fc.write(buff);
		
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
	}
	
}
