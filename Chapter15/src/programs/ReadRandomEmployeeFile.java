package programs;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import static java.nio.file.StandardOpenOption.*;

public class ReadRandomEmployeeFile extends JFrame implements ActionListener {

	JTextField idField = new JTextField();
	JButton submitButton = new JButton("Submit");
	
	FileChannel fc;
	
	public ReadRandomEmployeeFile() {
		super("Read Employee File");
		try {
			Path file = Paths.get("employees.txt");
			fc = (FileChannel) Files.newByteChannel(file, READ);
		} catch(IOException e) {
			System.exit(1);
		}
		
		submitButton.addActionListener(this);
		setLayout(new GridLayout(2,1));
		add(idField);
		add(submitButton);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(200,200);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			int id = Integer.parseInt(idField.getText());
			idField.setText("");
			String[] fields = read(id);
			String rec = "ID: "+fields[0]+"\nFirst: "+fields[1]+"\nLast "+fields[2]+"\nPay: "+fields[3];
			JOptionPane.showMessageDialog(null, rec);
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "Something went wrong");
		}
	}
	
	public String[] read(int id) throws IOException {
		byte[] bytes = CreateRandomEmployeeFile.DEFAULT_REC.getBytes();
		ByteBuffer buff = ByteBuffer.wrap(bytes);
		fc.position(id * CreateRandomEmployeeFile.DEFAULT_REC.length());
		fc.read(buff);
		String s = new String(bytes);
		String[] rec = s.split(",");
		for(int i = 0; i < rec.length; ++i)
			rec[i] = rec[i].trim();
		return rec;
	}
	
	public static void main(String[] args) throws IOException {
		ReadRandomEmployeeFile f1 = new ReadRandomEmployeeFile();
		f1.setVisible(true);
	}

}
