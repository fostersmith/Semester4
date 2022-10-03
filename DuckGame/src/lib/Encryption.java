package lib;

public abstract class Encryption {

	public static String encrypt(String plaintext, int password) {
		StringBuilder ciphertext = new StringBuilder();
		for(int c:plaintext.chars().toArray()) {
			ciphertext.append((char)((c+password%65537)));
		}
		return ciphertext.toString();
	}
	
	public static String decrypt(String ciphertext, int password) {
		StringBuilder plaintext = new StringBuilder();
		for(int c:ciphertext.chars().toArray()) {
			plaintext.append((char)((c-password)%65537));
		}
		return plaintext.toString();		
	}
	
}
