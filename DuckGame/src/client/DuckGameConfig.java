package client;

import static java.nio.file.StandardOpenOption.READ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import lib.Encryption;

public class DuckGameConfig extends Encryption {

	Path background;
	ArrayList<Path> ducks;
	
	private DuckGameConfig() {
		ducks = new ArrayList<>();
	}
	
	public static DuckGameConfig readFromFile(Path file, int password) throws IOException {
		InputStream input = Files.newInputStream(file, READ);
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String bgpath = decrypt(reader.readLine(), password);
		return null;
	}
	
	public static void writeToFile(DuckGameConfig config, Path file, int password) {
		
	}
	
}
