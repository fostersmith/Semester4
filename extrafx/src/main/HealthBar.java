/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Foster
 */
public class HealthBar extends Rectangle {
    
    private static final int L = 50, H = 10;
    
    private final int MAX;
    private int health;
    public Rectangle base;
    
    public HealthBar(int max){
        super(0, H);
        base = new Rectangle(0, H);
        MAX = max;
        setFill(Color.RED);
        base.setFill(Color.GREY);
        /*MAX = max;
        setFill(Color.RED);
        //setWidth(L);
        //setHeight(H);
        setHealth(max);*/
    }
    
    public void moveTo(double x, double y){
        this.setTranslateX(x-L/2);
        this.setTranslateY(y-H/2);
        base.setTranslateX(x-L/2);
        base.setTranslateY(y-H/2);
    }

    
    public void setHealth(int health){
        if(health < MAX){
            setWidth(L*health/MAX);
            base.setWidth(L);
        }
        else{
            setWidth(0);
            base.setWidth(0);
        }
        /*this.health = health;
        int newLen = L*(health/MAX);
        if(health < MAX){
            setWidth(newLen);
        } else{
            setWidth(0);
        }*/
    }
    
}
