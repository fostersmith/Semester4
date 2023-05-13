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
    
    public static final double TURN_SPEED = Math.PI/5E9;
    public static final double SPEED = 50/1E9;
    
    private static final double TAU = 2*Math.PI;
    
    public Enemy(double x, double y, double theta){
        super(x, y, theta, SPEED);
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
    public void update(ArrayList<Entity> entities, long deltaTime, App controller){
        super.update(entities, deltaTime, controller);
        
        // find the player
        Player p = controller.getPlayer();
        if(p != null){
            double targetAngle = Math.atan2(p.y-y, p.x-x);
            theta = ((theta%TAU) + TAU) % TAU;
            targetAngle = ((targetAngle%TAU) + TAU) % TAU;
            
            double dir = Math.signum(theta-targetAngle)*Math.signum(Math.abs(targetAngle-theta)-Math.PI);
            if(dir == 0)
                dir = 1;
            double turnAmount = deltaTime*TURN_SPEED;
            setTheta(theta + dir*turnAmount);
            /*if(Math.abs(dif) <= turnAmount){
                //setTheta(targetAngle);
                setTheta(theta + Math.signum(dif)*turnAmount);
            } else{
                setTheta(theta + Math.signum(dif)*turnAmount);
            }*/
        }
    }
    
    private static final double[][] basePoints = {{-20, 10}, {20, 10}, {20, -10}, {-20, -10}};
    @Override
    double[][] basePoints() {
        return basePoints;
    }

}
