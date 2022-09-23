package lib;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class Duck extends Encryption {
	
	final static int WALKING = 0, SITTING = 1, STANDING = 2, STANDING2 = 3;
	int ID;
	Image[] walking;
	Image sitting, standing, standing2;
	int rarity;
	double x, y;
	double eggChance;
	int state;
	double[] velocity;
	
	private Duck(int iD, Path file, Image[] walking, Image sitting, Image standing, Image standing2, int rarity, double x, double y, double eggChance) {
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
		state = STANDING;
	}
	
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
		
		
	}
	
	public void saveToFile(Path file, int password) throws IOException {
		
	}
	
}
