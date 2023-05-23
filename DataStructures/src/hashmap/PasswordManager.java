package hashmap;

import java.awt.FlowLayout;
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
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PasswordManager extends JPanel {

	private static final String algorithm = "AES";
	private static final String dataPath = "passwords.txt";
	private static final IvParameterSpec iv = generateIV();
	
	private SecretKey key;
	
	private HashMap<String, String> encryptedPasswordMap = new HashMap<>();
	
	public PasswordManager(int w, int h, String password) {
		setLayout(new FlowLayout());
		setSize(w,h);
		
		 try {
			key = getKeyFromPassword(password, "salt");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		 
		 loadMap(new File(dataPath), encryptedPasswordMap);
		 
		 String input;
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
		 
		 writeMap(new File(dataPath), encryptedPasswordMap);
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
			System.out.println(plaintext);
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
		JFrame f1 = new JFrame();
		String password = JOptionPane.showInputDialog("Password:");
		PasswordManager panel = new PasswordManager(500, 300, password);
		f1.add(panel);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setVisible(true);
		f1.setSize(500, 300);
	}
}
