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
    
    private boolean leftDown, rightDown, upDown;
    private static final double turnSpeed = Math.PI/1000;
    private boolean hasShot = false;
    
    public Player(double x, double y, double theta){
        super(x, y, theta);
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
    
    public void setUpDown(boolean upDown){
        if(upDown == false && this.upDown == true){
            hasShot = false;
        }
        this.upDown = upDown;
    }
    
    public void update(ArrayList<Entity> entities, long deltaTime, App controller){
        super.update(entities, deltaTime);
        if(leftDown && !rightDown){
            theta -= turnSpeed*deltaTime;
        } else if (rightDown && !leftDown){
            theta += turnSpeed*deltaTime;
        }
        if(upDown && !hasShot){
            controller.addEntity(new Bullet(x, y, theta));
        }
    }
}
