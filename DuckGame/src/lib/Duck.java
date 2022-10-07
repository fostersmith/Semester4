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
	double theta;
	boolean hasGoal;
	int goalX, goalY;
	
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
		theta = 0;
		hasGoal = false;
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
	
	public int getX() {
		return (int)x;
	}
	
	public int getY() {
		return (int)y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void goTo(int gX, int gY, int speed) {
		this.goalX = gX;
		this.goalY = gY;
		state = WALKING;
		hasGoal = true;
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
	
	public void walk(int speed) {
		if(state == WALKING) {
			if(!hasGoal) {
				theta += (Math.random()-0.5);
				velocity[0] = Math.cos(theta) * speed;
				velocity[1] = Math.sin(theta) * speed;
			} else {
				velocity[0] = goalX-x;
				velocity[1] = goalY-y;
				// v[0]^2 + v[1]^2 = speed^2
				// v[0] = sqrt(-v[1]^2 + speed^2)
				// v[1] = sqrt(-v[0]^2 + speed^2)
				double temp = velocity[0];
				double a = Math.pow(speed, 2)-Math.pow(velocity[1], 2);
				if(a < 0)
					velocity[0] = Math.sqrt(Math.abs(a))*-1;
				else
					velocity[0] = Math.sqrt(a);
				double b = Math.pow(speed, 2)-Math.pow(temp, 2);
				if(b < 0)
					velocity[1] = Math.sqrt(Math.abs(b))*-1;
				else
					velocity[1] = Math.sqrt(b);
				System.out.println("("+x+","+y+") ->" +"("+goalX+","+goalY+")"+" = ("+velocity[0]+","+velocity[1]+")");
			}
			x += velocity[0];
			y += velocity[1];
			if(hasGoal) {
				if((goalX-speed-1 < x && goalX+speed+1 > x) && (goalY-speed-1 < y && goalY+speed+1 > y) ) {
					hasGoal = false;
					System.out.println("reached goal");
				}
			}
		}
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
	
	public boolean hasGoal() {
		return hasGoal;
	}
	
	public static void main(String[] args) throws IOException, LoginException {
		/*
		Path file = Paths.get("duck2.dck");
		Duck d = new Duck();
		d.ID = 1;
	
		DuckSprite sitting = DuckSprite.readFromImage(Paths.get("duck2\\duck2sitting.png"));
		DuckSprite standing = DuckSprite.readFromImage(Paths.get("duck2\\duck2standing.png"));
		DuckSprite standing2 = DuckSprite.readFromImage(Paths.get("duck2\\duck2standing2.png"));
		DuckSprite walking0 = DuckSprite.readFromImage(Paths.get("duck2\\duck2walking0.png"));
		DuckSprite walking1 = DuckSprite.readFromImage(Paths.get("duck2\\duck2walking1.png"));

		DuckSprite[] walking = {walking0, walking1};
		
		d.sitting = sitting;
		d.standing = standing;
		d.standing2 = standing2;
		d.walking = walking;
		
		Duck.saveToFile(d, file, 0);
		
		Duck d2 = Duck.readFromFile(file, 1, 0);
		*/
		
		/*Path file = Paths.get("mallard.dck");
		Duck d = new Duck();
		d.ID = 3;
	
		DuckSprite sitting = DuckSprite.readFromImage(Paths.get("mallard\\sitting.png"));
		DuckSprite standing = DuckSprite.readFromImage(Paths.get("mallard\\standing.png"));
		DuckSprite standing2 = DuckSprite.readFromImage(Paths.get("mallard\\standing2.png"));
		DuckSprite walking0 = DuckSprite.readFromImage(Paths.get("mallard\\walking0.png"));
		DuckSprite walking1 = DuckSprite.readFromImage(Paths.get("mallard\\walking1.png"));

		DuckSprite[] walking = {walking0, walking1};
		
		d.sitting = sitting;
		d.standing = standing;
		d.standing2 = standing2;
		d.walking = walking;
		
		Duck.saveToFile(d, file, 90);
		
		Duck d2 = Duck.readFromFile(file, d.ID, 90);*/

	}
}
