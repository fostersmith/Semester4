/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author fsmith
 */
public class Bullet extends Entity {
    public static final int DAMAGE = 2;
    public static final double SPEED = 100/1E9;

    
    public Bullet(double x, double y, double theta) {
        super(x, y, theta, SPEED);
    }

    @Override
    int maxHealth() {
        return 1;
    }

    @Override
    void collision(Entity e) {
        if(e instanceof Enemy){
            e.takeDamage(DAMAGE);
            setHealth(0);
            
        }
    }

    private static final double[][] basePoints = {{-3, 3}, {3, 3}, {3, -3}, {-3, -3}};
    @Override
    double[][] basePoints() {
        return basePoints;
    }
    
    
    
}
