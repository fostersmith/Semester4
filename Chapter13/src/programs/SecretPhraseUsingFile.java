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
import java.util.Scanner;

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
		
		Scanner in = new Scanner(System.in);
		while(true){
			System.out.print("Play again? (y/n) >> ");
			char input = in.nextLine().toUpperCase().charAt(0);
			if(input == 'Y') {
				int index = (int) (Math.random()*phrases.size());
				play(phrases.get(index), in);
			} else {
				break;
			}
		}
		in.close();
	}
	
	public static void play(String phrase, Scanner in) {
		int[][] alphabetToPos = new int[26][0]; // [char-65] = list of indices
		boolean[] guessed = new boolean[phrase.length()];
		phrase = phrase.toUpperCase();
		for(int i = 0; i < phrase.length(); ++i) {
			char c = phrase.charAt(i);
			if(Character.isAlphabetic(c)) {
				int index = c-65;
				alphabetToPos[index] = append(alphabetToPos[index], i);
				guessed[i] = false;
			} else {
				guessed[i] = true;
			}
		}
		
		boolean done = false;
		int guessedWrong = 0;
		while(!done) {
			
			for(int i = 0; i < guessed.length; ++i) {
				if(guessed[i]) {
					System.out.print(phrase.charAt(i));
				} else {
					System.out.print('_');
				}
			}
			System.out.println();
			
			System.out.print("Enter a letter >> ");
			char guess = in.nextLine().toUpperCase().charAt(0);
			if(Character.isAlphabetic(guess)) {
				if(alphabetToPos[guess-65].length > 0) {
					for(int p : alphabetToPos[guess-65])
						guessed[p] = true;
				} else {
					++guessedWrong;
					if(guessedWrong < 5)
						System.out.println("Wrong! You have made "+guessedWrong+"/5 incorrect guesses");
					else
						break;
				}
				done = true;
				for(int i = 0; i < guessed.length; ++i)
					done = done && guessed[i];
			} else {
				System.out.println("Invalid Guess");
			}
		}
		System.out.println("The phrase was '"+phrase+"'");
		if(guessedWrong < 5)
			System.out.println("You Win!");
		else
			System.out.println("You Lose!");
	}
	
	public static int[] append(int[] arr, int add) {
		int[] r = new int[arr.length+1];
		for(int i = 0; i < arr.length; ++i)
			r[i] = arr[i];
		r[arr.length] = add;
		return r;
	}
}
