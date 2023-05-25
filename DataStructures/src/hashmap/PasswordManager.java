package hashmap;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PasswordManager extends JFrame implements ActionListener {

	// Constants
	private static final String algorithm = "AES";
	private static final String dataPath = "passwords.txt";
	private static final IvParameterSpec iv = generateIV();
	
	// Key generated from password
	private SecretKey key;
	// Maps site name to encrypted password
	private HashMap<String, String> encryptedPasswordMap = new HashMap<>();
	
	// Swing components
	JPanel homePanel = new JPanel();
	JButton addEntryButton = new JButton("Add/Edit Entry");
	JButton removeEntryButton = new JButton("Remove Entry");
	JButton getDataButton = new JButton("Get Password");
	JButton saveButton = new JButton("Save Configuration");
	
	//Entry Modification Screen
	JPanel modPanel = new JPanel();
	JLabel modSiteLabel = new JLabel("Site:  ");
	JLabel modPasswordLabel = new JLabel("Password: ");
	JTextField modSiteField = new JTextField();
	JTextField modPasswordField = new JTextField();
	JButton modEnterButton = new JButton("Enter");
	JButton modHomeButton = new JButton("Home");
	
	//Entry Removal Screen
	JPanel removalPanel = new JPanel();
	JLabel removalSiteLabel = new JLabel("Site: ");
	JTextField removalSiteField = new JTextField();
	JButton removalEnterButton = new JButton("Enter");
	JButton removalHomeButton = new JButton("Home");
	
	//Password Retrieval Screen
	JPanel retrievalPanel = new JPanel();
	JLabel retrievalSiteLabel = new JLabel("Site: ");
	JTextField retrievalSiteField = new JTextField();
	JButton retrievalEnterButton = new JButton("Enter");
	JButton retrievalHomeButton = new JButton("Home");
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton)e.getSource();
		
		// Screen Switches
		if(b == addEntryButton) {
			switchToScreen(modPanel);
		} else if(b == removeEntryButton) {
			switchToScreen(removalPanel);
		} else if(b == getDataButton) {
			switchToScreen(retrievalPanel);
		} else if(b == modHomeButton || b == removalHomeButton || b == retrievalHomeButton) {
			switchToScreen(homePanel);
		} 
		// Enter Buttons
		else if(b == modEnterButton) {
			String site = modSiteField.getText();
			String password = modPasswordField.getText();
			modSiteField.setText("");
			modPasswordField.setText("");
			if(!site.equals("") && !password.equals("")) {
				encryptedPasswordMap.put(site, encrypt(password, key));
				JOptionPane.showMessageDialog(null, "Success");
			} else {
				JOptionPane.showMessageDialog(null, "One or more fields was left blank; please try again");
			}
		} else if(b == removalEnterButton) {
			String site = removalSiteField.getText();
			removalSiteField.setText("");
			if(encryptedPasswordMap.containsKey(site)) {
				encryptedPasswordMap.remove(site);
				JOptionPane.showMessageDialog(null, "Success");
			} else {
				JOptionPane.showMessageDialog(null, "Key not found");
			}
		} else if(b == retrievalEnterButton) {
			String site = retrievalSiteField.getText();
			retrievalSiteField.setText("");
			if(encryptedPasswordMap.containsKey(site)) {
				JOptionPane.showMessageDialog(null, decrypt(encryptedPasswordMap.get(site), key));
			} else {
				JOptionPane.showMessageDialog(null, "Key not found");
			}
		}
		// Save Button
		else if(b == saveButton) {
			try {
				writeMap(new File(dataPath), encryptedPasswordMap);
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "An Error Ocurred");
			}
		}
	}
	
	public void switchToScreen(JPanel p) {
		getContentPane().removeAll();
		add(p);
		revalidate();
		repaint();
	}
	
	public PasswordManager(String password) {
		
		for(JButton j : new JButton[] {addEntryButton, removeEntryButton, getDataButton, saveButton, modHomeButton, removalHomeButton, retrievalHomeButton, modEnterButton, removalEnterButton, retrievalEnterButton})
			j.addActionListener(this);
		
		for(JTextField t : new JTextField[] {modSiteField, removalSiteField, retrievalSiteField, modPasswordField})
			t.setPreferredSize(new Dimension(100, 20));

		
		homePanel.setLayout(new FlowLayout());
		homePanel.add(addEntryButton);
		homePanel.add(removeEntryButton);
		homePanel.add(getDataButton);
		homePanel.add(saveButton);
		
		modPanel.setLayout(new FlowLayout());
		modPanel.add(modSiteLabel);
		modPanel.add(modSiteField);
		modPanel.add(modPasswordLabel);
		modPanel.add(modPasswordField);
		modPanel.add(modEnterButton);
		modPanel.add(modEnterButton);
		modPanel.add(modHomeButton);
		
		removalPanel.setLayout(new FlowLayout());
		removalPanel.add(removalSiteLabel);
		removalPanel.add(removalSiteField);
		removalPanel.add(removalEnterButton);
		removalPanel.add(removalHomeButton);
		
		retrievalPanel.setLayout(new FlowLayout());
		retrievalPanel.add(retrievalSiteLabel);
		retrievalPanel.add(retrievalSiteField);
		retrievalPanel.add(retrievalEnterButton);
		retrievalPanel.add(retrievalHomeButton);
		
		this.add(homePanel);
		
		
		 try {
			key = getKeyFromPassword(password, "salt");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		 
		 loadMap(new File(dataPath), encryptedPasswordMap);
		 
		/* String input;
		 Scanner scanner = new Scanner(System.in);
		 do {
			 System.out.print(" >> ");
			 input = scanner.nextLine();
			 if(input.equals("ADD")) {
				 System.out.print("Site: ");
				 String site = scanner.nextLine();
				 System.out.print("Password: ");
				 String encryptedPassword = encrypt( scanner.nextLine(), key );
				 
				 System.out.println(encryptedPassword);
				 
				 encryptedPasswordMap.put(site, encryptedPassword);
			 } else if(!input.equals("QUIT")) {
				 System.out.println(decrypt( encryptedPasswordMap.get(input), key ));
			 }
		 } while(!input.equals("QUIT"));
		 scanner.close();
		 
		 writeMap(new File(dataPath), encryptedPasswordMap);*/
	}
	
	public static SecretKey getKeyFromPassword(String password, String salt)
			  throws NoSuchAlgorithmException, InvalidKeySpecException {
			    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
			    SecretKey originalKey = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
			    return originalKey;
	}
	
	private static IvParameterSpec generateIV() {
		byte[] iv = new byte[16];
	    new SecureRandom().nextBytes(iv);
	    return new IvParameterSpec(iv);
	}

	public void loadMap(File f, Map<String, String> m) {
		try {
			BufferedReader rdr = new BufferedReader(new FileReader(f));

			String line = rdr.readLine();
			while(line != null) {
				String[] data = line.split(",");
				m.put(data[0], data[1]);
				line = rdr.readLine();
			}
			
			rdr.close();
		} catch (IOException e) {
			throw new IllegalArgumentException();
		}
	}
	
	public void writeMap(File f, Map<String, String> m) {
		try {
			BufferedWriter wtr = new BufferedWriter(new FileWriter(f));

			for(Entry<String, String> pair : m.entrySet()) {
				wtr.write(pair.getKey() +"," + pair.getValue() + "\n");
			}
			wtr.close();
		} catch (IOException e) {
			throw new IllegalArgumentException();
		}		
	}
	
	public static SecretKey stringToKey(String s) {
		byte[] decoded = Base64.getDecoder().decode(s);
		return new SecretKeySpec(decoded, 0, decoded.length, algorithm);
	}
	
	public static String decrypt(String ciphertext, SecretKey key) {
		try {
			Cipher cipher = Cipher.getInstance(algorithm);	
			cipher.init(Cipher.DECRYPT_MODE, key);
			
			byte[] plaintext = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
			return new String( plaintext );
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			throw new IllegalArgumentException();
		}
		
	}
	
	public static String encrypt(String ciphertext, SecretKey key) {
		try {
			Cipher cipher = Cipher.getInstance(algorithm);	
			cipher.init(Cipher.ENCRYPT_MODE, key);
			
			return Base64.getEncoder().encodeToString( cipher.doFinal(ciphertext.getBytes()));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
		
	}
	
	public static void main(String[] args) {
		String password = JOptionPane.showInputDialog("Password:");
		PasswordManager f1 = new PasswordManager(password);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setVisible(true);
		f1.setSize(500, 300);
	}
}
