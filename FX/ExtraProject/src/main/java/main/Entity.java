/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.shape.Polygon;

/**
 *
 * @author fsmith
 */
public abstract class Entity extends Polygon {
    
    protected double x, y;
    protected double theta, speed;
    protected int health;
    
    public Entity(double x, double y, double theta, double speed){
        double[][] basePoints = basePoints();
        this.getPoints().addAll(new Double[2*basePoints.length]);
        updatePoints();
        this.theta = theta;
        this.speed = speed;
        health = maxHealth();
        moveTo(x,y);
    }
    
    abstract double[][] basePoints();
    
    public void moveTo(double x, double y){
        /*double adjX = x - getBoundsInLocal().getWidth()/2;
        double adjY = y - getBoundsInLocal().getHeight()/2;
        
        adjX = adjX < 0 ? 0 : (adjX > App.W ? App.W : adjX);
        adjY = adjY < 0 ? 0 : (adjY > App.H ? App.H : adjY);*/
        this.x = x;
        this.y = y;
        this.setTranslateX(x);
        this.setTranslateY(y);
    }
    
    public void moveBy(double dx, double dy){
        moveTo(x + dx, y + dy);
    }
    
    public void doMovement(long deltaTime){
        moveBy(deltaTime*speed*Math.cos(theta), deltaTime*speed*Math.sin(theta));
    }
    
    public void updatePoints(){
        double[][] basePoints = basePoints();
        for(int i = 0; i < basePoints.length; ++i){
            double[] p = basePoints[i];
            double x = p[0] * Math.cos(theta) - p[1] * Math.sin(theta);// + this.x;
            double y = p[0] * Math.sin(theta) + p[1] * Math.cos(theta);// + this.y;
            this.getPoints().set(2*i, x);
            this.getPoints().set(2*i+1, y);
        }
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
    public int getHealth(){
        return health;
    }
    public void takeDamage(int dmg){
        setHealth(health-dmg);
    }
    
    abstract int maxHealth();
    abstract void collision(Entity e);
    public void update(ArrayList<Entity> entities, long deltaTime, App controller){
        doMovement(deltaTime);
        updatePoints();
    }
}
