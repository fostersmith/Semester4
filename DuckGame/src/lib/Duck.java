package lib;

import java.io.BufferedInputStream;
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

public class Duck extends Encryption {
	
	public final static int WALKING = 0, SITTING = 1, STANDING = 2, STANDING2 = 3;
	int ID;
	public DuckSprite[] walking;
	public DuckSprite sitting, standing, standing2;
	int rarity;
	double x, y;
	double eggChance;
	public int state;
	public int walkIndex;
	double[] velocity;
	
	private Duck(int iD, DuckSprite[] walking, DuckSprite sitting, DuckSprite standing, DuckSprite standing2, int rarity, double x, double y, double eggChance) {
		super();
		ID = iD;
		this.walking = walking;
		this.sitting = sitting;
		this.standing = standing;
		this.standing2 = standing2;
		this.rarity = rarity;
		this.x = x;
		this.y = y;
		this.eggChance = eggChance;
		state = SITTING;
		walkIndex = 0;
		velocity = new double[] {0d,0d};
	}
	private Duck() {
		ID = 0;
		walking = new DuckSprite[] {new DuckSprite(), new DuckSprite()};
		sitting = new DuckSprite();
		standing = new DuckSprite();
		standing2 = new DuckSprite();
		rarity = 0;
		x = 0d;
		y = 0d;
		eggChance = 0d;
		state = STANDING;
		velocity = new double[] {0d,0d};
	}
	
	/**
	 * File Specs:
	 * ID
	 * rarity
	 * x,y
	 * eggchance
	 * [sitting]
	 * [standing]
	 * [standing2]
	 * 
	 * [walking 0]
	 * 
	 * [walking 1]
	 * 
	 * [walking ..]
	 * 
	 * @param file
	 * @param supposedID
	 * @param password
	 * @return
	 * @throws IOException
	 * @throws LoginException
	 */
	public static Duck readFromFile(Path file, int supposedID, int password) throws IOException, LoginException {
		InputStream input = new BufferedInputStream(Files.newInputStream(file));
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		int id;
		try {
			id = Integer.parseInt(decrypt(reader.readLine(), password));
			if(id != supposedID) 
				throw new LoginException();
		} catch(NumberFormatException e) {
			throw new LoginException();
		}
		int rarity;
		try {
			rarity = Integer.parseInt(decrypt(reader.readLine(), password));
		} catch(NumberFormatException e) {
			throw new LoginException();
		}
		double x, y;
		try {
			String[] line = decrypt(reader.readLine(), password).split(",");
			x = Double.parseDouble(line[0]);
			y = Double.parseDouble(line[1]);
		} catch(NumberFormatException e) {
			throw new LoginException();
		}
		double eggChance;
		try {
			eggChance = Double.parseDouble(decrypt(reader.readLine(),password));
		} catch(NumberFormatException e) {
			throw new LoginException();
		}
		DuckSprite sitting = DuckSprite.readSpriteSegment(reader, password);
		DuckSprite standing = DuckSprite.readSpriteSegment(reader, password);
		DuckSprite standing2 = DuckSprite.readSpriteSegment(reader, password);
		ArrayList<DuckSprite> walkingL = new ArrayList<>();
		String line = reader.readLine();
		while(line != null) {
			walkingL.add(DuckSprite.readSpriteSegment(reader, password));
			line = reader.readLine();
		}
		DuckSprite[] walking = new DuckSprite[walkingL.size()];
		walkingL.toArray(walking);
		input.close();
		return new Duck(id, walking, sitting, standing, standing2, rarity, x, y, eggChance);
	}
	
	/**
	 * File Specs:
	 * ID
	 * rarity
	 * x,y
	 * eggchance
	 * [sitting]
	 * [standing]
	 * [standing2]
	 * 
	 * [walking 0]
	 * 
	 * [walking 1]
	 * 
	 * [walking ..]
	 *
	 * @param d
	 * @param file
	 * @param password
	 * @throws IOException
	 */
	public static void saveToFile(Duck d, Path file, int password) throws IOException {
		OutputStream output = new BufferedOutputStream(Files.newOutputStream(file));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
		String s = encrypt(Integer.toString(d.ID), password);
		writer.write(s);
		writer.newLine();
		s = encrypt(Integer.toString(d.rarity), password);
		writer.write(s);
		writer.newLine();
		s = encrypt(d.x+","+d.y, password);
		writer.write(s);
		writer.newLine();
		s = encrypt(Double.toString(d.eggChance), password);
		writer.write(s);
		writer.newLine();
		DuckSprite.writeSpriteSegment(writer, password, d.sitting);
		DuckSprite.writeSpriteSegment(writer, password, d.standing);
		DuckSprite.writeSpriteSegment(writer, password, d.standing2);
		for(DuckSprite ds : d.walking) {
			writer.newLine();
			DuckSprite.writeSpriteSegment(writer, password, ds);
		}
		
		writer.flush();
		output.close();
	}
	
	public synchronized void step() {
		walkIndex++;
		walkIndex %= 2;
	}
	
	public DuckSprite currentSprite() {
		switch(state) {
		case SITTING:
			return sitting;
		case STANDING:
			return standing;
		case STANDING2:
			return standing2;
		case WALKING:
			return walking[walkIndex];
		}
		return null;
	}
	
	public static int chooseState() {
		return (int)(Math.random()*4);
	}
	
	public static void main(String[] args) throws IOException, LoginException {
		Path file = Paths.get("testduck.dck");
		Duck d = new Duck();
	
		DuckSprite sitting = DuckSprite.readFromImage(Paths.get("duckwalk\\sitting.png"));
		DuckSprite standing = DuckSprite.readFromImage(Paths.get("duckwalk\\standing.png"));
		DuckSprite standing2 = DuckSprite.readFromImage(Paths.get("duckwalk\\standing2.png"));
		DuckSprite walking0 = DuckSprite.readFromImage(Paths.get("duckwalk\\walk0.png"));
		DuckSprite walking1 = DuckSprite.readFromImage(Paths.get("duckwalk\\walk1.png"));

		DuckSprite[] walking = {walking0, walking1};
		
		d.sitting = sitting;
		d.standing = standing;
		d.standing2 = standing2;
		d.walking = walking;
		
		Duck.saveToFile(d, file, 2);
		
		Duck d2 = Duck.readFromFile(file, 0, 2);
	}
}
