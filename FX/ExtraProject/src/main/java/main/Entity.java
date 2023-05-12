/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.util.ArrayList;
import javafx.scene.Node;

/**
 *
 * @author fsmith
 */
public abstract class Entity extends Node {
    
    protected double x, y;
    protected double theta, speed;
    protected int health;
    
    public Entity(double x, double y, double theta){
        this.x = x;
        this.y = y;
        this.theta = theta;
        health = maxHealth();
    }
    
    public void moveTo(double x, double y){
        double adjX = x - getBoundsInLocal().getWidth()/2;
        double adjY = y - getBoundsInLocal().getHeight()/2;
        
        adjX = adjX < 0 ? 0 : (adjX > App.W ? App.W : adjX);
        adjY = adjY < 0 ? 0 : (adjY > App.H ? App.H : adjY);
        
        relocate(adjX, adjY);
    }
    
    public void moveBy(double dx, double dy){
        moveTo(x + dx, y + dy);
    }
    
    public void doMovement(long deltaTime){
        moveBy(deltaTime*speed*Math.cos(theta), deltaTime*speed*Math.sin(theta));
    }
    
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getTheta(){
        return theta;
    }
    public void setTheta(double theta){
        this.theta = theta;
    }
    public double getSpeed(){
        return speed;
    }
    public void setSpeed(double speed){
        this.speed = speed;
    }
    
    public void setHealth(int health){
        this.health = health;
    }
    public int getHealth(int health){
        return health;
    }
    public void takeDamage(int dmg){
        setHealth(health-dmg);
    }
    
    abstract int maxHealth();
    abstract void collision(Entity e);
    public void update(ArrayList<Entity> entities, long deltaTime){
        doMovement(deltaTime);
    }
}
