/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.util.ArrayList;

/**
 *
 * @author fsmith
 */
public class Player extends Entity {
    
    private static final int MAX_HEALTH = 20;
    
    private boolean leftDown = false, rightDown = false, spaceDown = false, upDown = false;
    private static final double turnSpeed = Math.PI/1E9;
    private boolean hasShot = false;
    
    private long cooldown = 0;
    
    public Player(double x, double y, double theta){
        super(x, y, theta, 0);
    }
    
    @Override
    public int maxHealth(){
        return MAX_HEALTH;
    }
    
    @Override
    public void collision(Entity e){

    }
    
    public void setLeftDown(boolean leftDown){
        this.leftDown = leftDown;
    }
    
    public void setRightDown(boolean rightDown){
        this.rightDown = rightDown;
    }
    
    public void setSpaceDown(boolean spaceDown){
        this.spaceDown = spaceDown;
    }
    
    public void setUpDown(boolean upDown){
        this.upDown = upDown;
    }
    
    public void update(ArrayList<Entity> entities, long deltaTime, App controller){
        if(upDown){
            speed = 100/1E9;
            //System.out.println("speeding");
        }
        else{
            speed = 0;
        }
        
        super.update(entities, deltaTime, controller);
        
        if(leftDown && !rightDown){
            theta -= turnSpeed*deltaTime;
        } else if (rightDown && !leftDown){
            theta += turnSpeed*deltaTime;
        }
        
        
        if(spaceDown && cooldown <= 0){
            controller.addEntity(new Bullet(x, y, theta));
            cooldown = (long)(1E8);
        }
        
        cooldown -= deltaTime;
    }
    
    private static final double[][] basePoints = {{-10, 10}, {-10, -10}, {10, 0}};
    @Override
    double[][] basePoints() {
        return basePoints;
    }

}
