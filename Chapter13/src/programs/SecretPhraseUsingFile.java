/*
 * Foster Smith
 * 9/14/22
 * SecretPhraseUsingFile.java
 * gz 1
 */

package programs;

import static java.nio.file.StandardOpenOption.READ;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SecretPhraseUsingFile {
	
	public static void main(String[] args) throws IOException {
		ArrayList<String> phrases = new ArrayList<>();
		Path f = Paths.get("WordList1k.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(Files.newInputStream(f, READ))));
		String phrase = reader.readLine();
		while(phrase != null) {
			phrases.add(phrase);
			phrase = reader.readLine();
		}
	}
	
	public static void play(String phrase) {
		
	}
}
