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

    public Bullet(double x, double y, double theta) {
        super(x, y, theta);
    }

    @Override
    int maxHealth() {
        return 1;
    }

    @Override
    void collision(Entity e) {
        if(e instanceof Enemy){
            e.takeDamage(DAMAGE);
        }
    }
    
    
    
}
