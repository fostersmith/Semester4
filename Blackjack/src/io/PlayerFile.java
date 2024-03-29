package io;

import java.nio.file.Path;

import util.InsufficientFundsException;

public class PlayerFile {
	
	private Path path;
	private int money;
	
	private PlayerFile(Path p, int m) {
		this.path = p;
		this.money = m;
	}
	
	public PlayerFile(int m) {
		this.path = null;
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
	
	public void takeMoney(int amount) throws InsufficientFundsException {
		if(money >= amount)
			money -= amount;
		else
			throw new InsufficientFundsException();
	}
	
	public int getMoney() {
		return money;
	}
	
	
}
