/*
 * Foster Smith
 * 09/19/21
 * GameWithPassword.java
 * gz 3
 */
package programs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GameWithPassword {
	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(System.in);
		Map<String, String> userToPass = new HashMap<>();
		
		Path file = Paths.get("users.txt");
		InputStream input = Files.newInputStream(file);
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		
		String s;
		while((s = reader.readLine()) != null) {
			String[] entry = s.split(",");
			userToPass.put(entry[0],entry[1]);
		}
		input.close();
		
		System.out.print("Username: ");
		String username = in.nextLine();
		System.out.print("Password: ");
		String password = in.nextLine();
		
		if(userToPass.get(username).equals(password))
			playGame();
	}
	
	private static void playGame() throws IOException {
		SecretPhraseUsingFile.main(null);
	}
}
