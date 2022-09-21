package lib;

import java.awt.Image;
import java.io.IOException;
import java.nio.file.Path;

public class Duck {
	
	final int ID;
	Image[] walking;
	Image sitting, standing, standing2;
	int rarity;
	double eggChance;
	
	public Duck(int iD, Path file, Image[] walking, Image sitting, Image standing, Image standing2, int rarity, double eggChance) {
		super();
		ID = iD;
		this.walking = walking;
		this.sitting = sitting;
		this.standing = standing;
		this.standing2 = standing2;
		this.rarity = rarity;
		this.eggChance = eggChance;
	}
	
	/**
	 * Saves the duck to a file with all parameters encrypted with the given key
	 * @param file
	 * @param key
	 * @throws IOException
	 */
	public void saveToClientFile(Path file, int key) throws IOException {
		
	}
	
	/**
	 * Saves the duck to a file in plaintext along with the given key
	 * @param file
	 * @param key
	 * @throws IOException
	 */
	public void saveToServerFile(Path file, int key) throws IOException {
		
	}
	
}
