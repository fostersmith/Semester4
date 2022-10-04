package hacking;

import static java.nio.file.StandardOpenOption.READ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import lib.Encryption;

public class BruteForceConfigPass extends Encryption {

	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter Path (w/out .dgc) >> ");
		String filename = in.nextLine()+".dgc";
		System.out.print("Enter Username >> ");
		String username = in.nextLine();
		
		Path file = Paths.get(filename);
		
		int maxIter = 1000;
		InputStream input = Files.newInputStream(file, READ);
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String cipher = reader.readLine();
		String attempt;
		input.close();
		for(int i = 0; i < maxIter; ++i) {
			attempt = decrypt(cipher, i);
			if(attempt.equals(username)) {
				System.out.println("Password is "+i);
				i = maxIter;
			}
		}
	}
}
