package lib;

import java.util.HashMap;
import java.util.Random;

public abstract class Encryption {
	
	private static final String USEABLE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789`~!@#$%^&*()_+-=[]\\{}|;':\",./<>? ";
	//private static final String SPACEFILLER = "^";
	private static final int modulus = USEABLE.length();
	private static final HashMap<Character, Integer> positions = makePositions();
	
	public static String encrypt(String plaintext, int password) {
		/*StringBuilder ciphertext = new StringBuilder();
		for(int c:plaintext.chars().toArray()) {
			ciphertext.append((char)((c+password%65537)));
		}
		return ciphertext.toString();*/
		StringBuilder ciphertext = new StringBuilder();
		for(int i = 0; i < plaintext.length(); ++i) {
			char c = plaintext.charAt(i);
			int pos = (positions.get(c)+password)%modulus;
			ciphertext.append(USEABLE.charAt(pos));
		}
		return ciphertext.toString();
	}
	
	public static String decrypt(String ciphertext, int password) {
		StringBuilder plaintext = new StringBuilder();
		for(int i = 0; i < ciphertext.length(); ++i) {
			char c = ciphertext.charAt(i);
			int pos = (positions.get(c)-password)%modulus;
			if(pos < 0) pos += modulus;
			plaintext.append(USEABLE.charAt(pos));
		}
		return plaintext.toString();		
	}
	
	public static HashMap<Character, Integer> makePositions() {
		HashMap<Character, Integer> positions = new HashMap<>();
		for(int i = 0; i < USEABLE.length(); ++i)
			positions.put(USEABLE.charAt(i), i);
		return positions;
	}
	/*

	public static int[][] getEncryptKey(int password) throws LoginException{
		Random random = new Random(password);
		int[][] encrypt = new int[2][2];
		for(int r = 0; r < encrypt.length; ++r)
			for(int c = 0; c < encrypt.length; ++c)
				encrypt[r][c] = random.nextInt(modulus);
		if(encrypt[0][0]*encrypt[1][1]==encrypt[0][1]*encrypt[1][0]) //non-invertible
			throw new LoginException("Password generates non-invertible matrix");
		return encrypt;
	}
	
	public static int[][] getDecryptKey(int[][] encrypt) throws LoginException{
		int[][] key = new int[2][2];
		int determinant = encrypt[0][0]*encrypt[1][1]-encrypt[0][1]*encrypt[1][0];
		determinant %= modulus;
		if(determinant < 0) determinant += modulus;
		int multinv = modInverse(determinant, modulus);
		if(multinv >= 0) {
			key[0][0] = (encrypt[1][1]);
			key[1][1] = (encrypt[0][0]);
			key[0][1] = (-1*encrypt[0][1]);
			key[1][0] = (-1*encrypt[1][0]);
			for(int r = 0; r < key.length; ++r) {
				for(int c = 0; c < key[0].length; ++c) {
					key[r][c] %= modulus;
					if(key[r][c] < 0) key[r][c] += modulus;
					System.out.println("Inverse["+r+"]["+c+"] = "+key[r][c]);
					key[r][c] = (key[r][c]*multinv)%modulus;
					if(key[r][c] < 0) key[r][c] += modulus;
				}
			}
			return key;
		} else {
			throw new LoginException("Didn't find multiplicative inverse");
		}
	}
	
	public static int modInverse(int A, int M) {
		int[] xy = new int[2];
		int g = gcdExtended(A,M,xy);
		if(g != 1) {
			System.out.println("No Inverse");
			return -1;
		} else {
			int res = (xy[0] % M + M) % M;
			return res;
		}
	}
	
	public static int[] hillEncrypt(int[] plaintext, int password) throws LoginException {
		return matrixTimesVector(getEncryptKey(password), plaintext);
	}
	
	public static int[] hillDecrypt(int[] ciphertext, int password) throws LoginException {
		return matrixTimesVector(getDecryptKey(getEncryptKey(password)),ciphertext);
	}
	
	public static String vectorToString(int[] v) {
		return new String(new char[] {USEABLE.charAt(v[0]),USEABLE.charAt(v[1])});
	}
	
	public static int[] stringToVector(String s) {
		return new int[] {positions.get(s.charAt(0)), positions.get(s.charAt(1))};
	}
	
	/**
	 * matrix must be 2X2 and vector must be 2X1
	 * @param matrix
	 * @param vector
	 *//*
	public static int[] matrixTimesVector(int[][] matrix, int[] vector) {
		return new int[] {(int)((matrix[0][0]*vector[0]+matrix[0][1]*vector[1])%modulus), (int)((matrix[1][0]*vector[0]+matrix[1][1]*vector[1])%modulus)};
	}
	
	public static int gcdExtended(int a, int b, int[] xy) {
		if(a == 0) {
			xy[0] = 0;
			xy[1] = 1;
			return b;
		}
		
		int gcd = gcdExtended(b%a,a,xy);
		int x1 = xy[0];
		int y1 = xy[1];
		
		int tmp = b/a;
		xy[0] = y1 - tmp *x1;
		xy[1] = x1;
		
		return gcd;
	}
	
	public static void main(String[] args) throws LoginException {
		/*String plain = "AD";
		int password = 10;
		int[] plainV = stringToVector(plain);
		System.out.println("Plain: ["+plainV[0]+","+plainV[1]+"]");
		int[] cipherV = hillEncrypt(plainV, password);
		System.out.println("Encrypted: ["+cipherV[0]+","+cipherV[1]+"]");
		String cipher = vectorToString(cipherV);
		System.out.println(cipher);
		int[] plainAttempted = hillDecrypt(cipherV, password);
		System.out.println("PlainAttempted: ["+plainAttempted[0]+","+plainAttempted[1]+"]");*/
		/*int password = 10;
		//int[][] key = getEncryptKey(password);
		int[][] key = {{7,8},{11,11}};
		System.out.println("["+key[0][0]+","+key[0][1]+"\n"+key[1][0]+","+key[1][1]+"]");
		int[][] decrypt = getDecryptKey(key);
	}*/
	
}