package client;

import static java.nio.file.StandardOpenOption.READ;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import lib.Duck;
import lib.Encryption;

public class DuckGameConfig extends Encryption {

	private final static Path DEFAULT_BACKGROUND = Paths.get("blank.png");
	String username;
	Path background;
	ArrayList<Path> ducks;
	ArrayList<Integer> ids;
	int password;
	
	private DuckGameConfig(Path background, int password) {
		this.background = background;
		ducks = new ArrayList<>();
		ids = new ArrayList<>();
		this.password = password;
	}
	
	private DuckGameConfig(int password) {
		ducks = new ArrayList<>();
		ids = new ArrayList<>();
		this.password = password;
	}
		
	/*
	 * username
	 * bgpath
	 * duckpath-duckid
	 * duckpath-duckid
	 * ...
	 */
	public static DuckGameConfig readFromFile(Path file, int password) throws IOException {
		DuckGameConfig conf = new DuckGameConfig(password);
		InputStream input = Files.newInputStream(file, READ);
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String username = decrypt(reader.readLine(), password);
		conf.username = username;
		String bgpath = decrypt(reader.readLine(), password);
		conf.background = Paths.get(bgpath);
		String s;
		while((s = reader.readLine())!=null) {
			String[] duck = decrypt(s, password).split("-");
			int duckID = Integer.parseInt(duck[1]);
			conf.ducks.add(Paths.get(duck[0]));
			conf.ids.add(duckID);
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
		for(int i = 0; i < config.ducks.size(); ++i) {
			writer.newLine();
			s = encrypt(config.ducks.get(i).toString()+"-"+config.ids.get(i), password);
			writer.write(s, 0, s.length());
		}
		writer.flush();
		output.close();
	}

	public void setBackground(String path) {
		background = Paths.get(path);
	}
	
	public static DuckGameConfig createBlankFile(Path file, String username, int password) throws IOException {
		DuckGameConfig con = new DuckGameConfig(DEFAULT_BACKGROUND, password);
		con.username = username;
		writeToFile(con, file, password);
		return con;
	}
	
	public static void main(String[] args) throws IOException {
		DuckGameConfig coolconfig = DuckGameConfig.createBlankFile(Paths.get("coolconfig.dgc"), "foster", 0);
	}
	
}
