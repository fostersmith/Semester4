package client;

import static java.nio.file.StandardOpenOption.READ;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import lib.Encryption;

public class DuckGameConfig extends Encryption {

	private final static Path DEFAULT_BACKGROUND = Paths.get("blank.png");
	String username;
	Path background;
	ArrayList<Path> ducks;
	
	private DuckGameConfig(Path background) {
		this.background = background;
		ducks = new ArrayList<>();
	}
	
	private DuckGameConfig() {
		ducks = new ArrayList<>();
	}
	
	public static DuckGameConfig readFromFile(Path file, int password) throws IOException {
		DuckGameConfig conf = new DuckGameConfig();
		InputStream input = Files.newInputStream(file, READ);
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String username = decrypt(reader.readLine(), password);
		conf.username = username;
		String bgpath = decrypt(reader.readLine(), password);
		conf.background = Paths.get(bgpath);
		String s;
		while((s = reader.readLine())!=null) {
			String duckPath = decrypt(s, password);
			conf.ducks.add(Paths.get(duckPath));
		}
		return conf;
	}
	
	public static void writeToFile(DuckGameConfig config, Path file, int password) throws IOException {
		OutputStream output = new BufferedOutputStream(Files.newOutputStream(file));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
		String s = encrypt(config.username, password);
		writer.write(s, 0, s.length());
		writer.newLine();
		s = encrypt(config.background.toString(), password);
		writer.write(s, 0, s.length());
		for(Path p:config.ducks) {
			writer.newLine();
			s = encrypt(p.toString(), password);
			writer.write(s, 0, s.length());
		}
		writer.flush();
		output.close();
	}

	public static DuckGameConfig createBlankFile(Path file, String username, int password) throws IOException {
		DuckGameConfig con = new DuckGameConfig(DEFAULT_BACKGROUND);
		con.username = username;
		writeToFile(con, file, password);
		return con;
	}
	
}
