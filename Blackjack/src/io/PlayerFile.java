package io;

import java.nio.file.Path;

public class PlayerFile {
	
	private Path path;
	private int money;
	
	private PlayerFile(Path p, int m) {
		this.path = p;
		this.money = m;
	}
	
	public static PlayerFile load(Path p) {
		return null;
	}
	
	public void save() {
		
	}
	
	public void addMoney(int amount) {
		money += amount;
	}
	
	public void takeMoney(int amount) throws Exception {
		if(money >= amount)
			money -= amount;
		else
			throw new Exception();
	}
	
	public int getMoney() {
		return money;
	}
	
	
}
