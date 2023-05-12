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
public class Enemy extends Entity {
    
    public static final int MAX_HEALTH = 4;
    public static final int DAMAGE = 2;
    
    public static final double TURN_SPEED = Math.PI/4000;
    
    public Enemy(double x, double y, double theta){
        super(x, y, theta);
    }
    
    @Override
    public int maxHealth(){
        return MAX_HEALTH;
    }
    
    @Override
    public void collision(Entity e){
        if(e instanceof Player){
            e.takeDamage(DAMAGE);
            setHealth(0);
        }
    }
    
    @Override
    public void update(ArrayList<Entity> entities, long deltaTime){
        super.update(entities, deltaTime);
        
        // find the player
        Player p = null;
        for(Entity e : entities){
            if(e instanceof Player){
                p = (Player)e;
                break;
            }
        }
        if(p != null){
            double targetAngle = Math.atan2(y-p.y, x-p.x);
            if(theta > targetAngle){
                setTheta(Math.max(theta-targetAngle, targetAngle));
            } else{
                setTheta(Math.min(theta+targetAngle, targetAngle));
            }
        }
    }
}
