package lib;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class DuckSprite extends Encryption {
	
	public final static int WIDTH = 32, HEIGHT = 32;
	private Color[][] spritesheet;
	private final static String COLOR_DELIM = "-", VAL_DELIM = ",";
	
	public DuckSprite(Color[][] spritesheet) {
		this.spritesheet = spritesheet;
	}
	public DuckSprite() {
		spritesheet = new Color[WIDTH][HEIGHT];
		for(int x = 0; x < spritesheet.length; ++x)
			for(int y = 0; y < spritesheet[0].length; ++y)
				spritesheet[x][y] = new Color(0,0,0,0);
	}
	
	//TESTME
	public static DuckSprite readSpriteSegment(BufferedReader reader, int password) throws IOException, LoginException {
		DuckSprite sprite = new DuckSprite();
		String l = "";
		for(int x = 0; x < WIDTH; ++x) {
			l = reader.readLine();
			if(l == null)
				throw new LoginException("File was too short");
			l = decrypt(l, password);
			String[] colors = l.split(COLOR_DELIM);
			if(colors.length != HEIGHT)
				throw new LoginException("Bad file");
			for(int y = 0; y < colors.length; ++y) {
				String[] vals = colors[y].split(VAL_DELIM);
				if(vals.length != 4)
					throw new LoginException("Bad color values");
				int[] valInts = new int[4];
				for(int i = 0; i < 4; ++i)
					valInts[i] = Integer.parseInt(vals[i]);
				sprite.spritesheet[x][y] = new Color(valInts[0],valInts[1],valInts[2],valInts[3]);
			}
		}
		return sprite;
	}
	
	public static void writeSpriteSegment(BufferedWriter writer, int password, DuckSprite sprite) throws IOException {
		for(Color[] row:sprite.spritesheet) {
			StringBuilder rowStr = new StringBuilder();
			rowStr.append(row[0].getRed()+VAL_DELIM+row[0].getGreen()+VAL_DELIM+row[0].getBlue()+VAL_DELIM+row[0].getAlpha());
			for(int i = 1; i < row.length; ++i)
				rowStr.append(COLOR_DELIM+row[i].getRed()+VAL_DELIM+row[i].getGreen()+VAL_DELIM+row[i].getBlue()+VAL_DELIM+row[i].getAlpha());
			String rowStrStr = encrypt(rowStr.toString(), password);
			writer.write(rowStrStr);
			writer.newLine();
		}
	}
	
	public static DuckSprite readFromImage(Path file) throws IOException {
		BufferedImage bi = ImageIO.read(file.toFile());
		if(bi.getWidth() != WIDTH || bi.getHeight() != HEIGHT)
			throw new IOException("Bad Dimensions");
		DuckSprite ds = new DuckSprite();
		for(int x = 0; x < WIDTH; ++x)
			for(int y = 0; y < HEIGHT; ++y)
				ds.spritesheet[x][y] = new Color(bi.getRGB(x, y), true);
		return ds;
	}
	
	public Color getPixel(int x, int y) {
		return spritesheet[x][y];
	}
	
	public void setPixel(int x, int y, Color c) {
		spritesheet[x][y] = c;
	}
	
	public static void refactorDuck(Path duck, int id, int password, Path sittingF, Path standingF, Path standing2F, Path[] walkF) throws IOException, LoginException {
		Duck d = Duck.readFromFile(duck, id, password);
		if(sittingF != null) {
			d.sitting = readFromImage(sittingF);
		}
		if(standingF != null) {
			d.standing = readFromImage(standingF);
		}
		if(standing2F!= null) {
			d.standing2 = readFromImage(standing2F);
		}
		if(walkF != null) {
			d.walking = new DuckSprite[walkF.length];
			for(int i = 0; i < walkF.length; ++i)
				d.walking[i] = readFromImage(walkF[i]);
		}
		Duck.saveToFile(d, duck, password);		
	}
	
	public static void main(String[ ] args) throws IOException, LoginException {
		Path sitting = Paths.get("duckwalk\\sitting.png");
		Path standing = Paths.get("duckwalk\\standing.png");
		Path standing2 = Paths.get("duckwalk\\standing2.png");
		Path walk0 = Paths.get("duckwalk\\walk0.png");
		Path walk1 = Paths.get("duckwalk\\walk1.png");
		refactorDuck(Paths.get("testduck.dck"), 0, 0, sitting, standing, standing2, new Path[] {walk0, walk1});
	}
}
