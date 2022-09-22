package lib;

import java.awt.Image;
import java.io.IOException;
import java.nio.file.Path;

public class Duck {
	
	final static int WALKING = 0, SITTING = 1, STANDING = 2, STANDING2 = 3;
	final int ID;
	Image[] walking;
	Image sitting, standing, standing2;
	int rarity;
	double x, y;
	double eggChance;
	int state;
	double[] velocity;
	
	public Duck(int iD, Path file, Image[] walking, Image sitting, Image standing, Image standing2, int rarity, double x, double y, double eggChance) {
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
	
	public static Duck readFromClientFile(Path file, int password) {
		return null;
	}
	
	/**
	 * Saves the duck to a file encrypted with the given key
	 * @param file
	 * @param key
	 * @throws IOException
	 */
	public void saveToServerFile(Path file, int serverKey) throws IOException {
		
	}
	
	/**
	 * Saves the duck to a file with the server key encrypted with the password
	 * @param file
	 * @param key
	 * @throws IOException
	 */
	public void saveToClientFile(Path file, int serverKey, int password) throws IOException {
		
	}
	
}
