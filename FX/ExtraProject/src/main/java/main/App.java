package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import static javafx.scene.input.KeyCode.LEFT;
import static javafx.scene.input.KeyCode.RIGHT;
import static javafx.scene.input.KeyCode.SPACE;
import static javafx.scene.input.KeyCode.UP;
import javafx.scene.input.KeyEvent;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    
    public static final int W = 800, H = 800;
    
    private static ArrayList<Entity> entities = new ArrayList<>();
    private static Player p = new Player(W/2, H/2, 0);
    
    @Override
    public void start(Stage stage) throws IOException {     
        scene = new Scene(new Group(), W, H);
        addEntity(p);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent e){
                switch(e.getCode()){
                    case UP: p.setUpDown(true); break;
                    case LEFT: p.setLeftDown(true); break;
                    case RIGHT: p.setRightDown(true); break;
                    case SPACE: p.setSpaceDown(true); break;
                }
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent e){
                switch(e.getCode()){
                    case UP: p.setUpDown(false); break;
                    case LEFT: p.setLeftDown(false); break;
                    case RIGHT: p.setRightDown(false); break;
                    case SPACE: p.setSpaceDown(false); break;

                }
            }
        });
        stage.setScene(scene);
        stage.show();
        
        App app = this;
        
        AnimationTimer timer = new AnimationTimer() {
            long last = -1;
            @Override
            public void handle(long now) {
                long deltaTime = last > 0 ? now-last : 0;
                
                double spawnChancePerc = deltaTime/1E9;
                if(Math.random() < spawnChancePerc){
                    addEntity(new Enemy(Math.random()*W,Math.random()*H,Math.random()*Math.PI*2));
                }
                
                for(int i = 0; i < entities.size(); ++i){
                    Entity e = entities.get(i);
                    e.update(entities, deltaTime, app);
                    for(int j = 0; j < entities.size(); ++j){
                        if(j != i){
                            Entity f = entities.get(j);
                            for(int g = 0; g < f.getPoints().size()/2; ++g){
                                if(e.contains(f.getPoints().get(2*g),f.getPoints().get(2*g+1))){
                                    e.collision(f);
                                    break;
                                }
                            }
                        }
                    }
                    
                    if(e.getHealth() <= 0)
                        destroyEntity(e);
                    
                    if(e.getX() < 0){
                        e.moveTo(W, e.getY());
                        if(e instanceof Bullet)
                            destroyEntity(e);
                    } else if(e.getX() > W){
                        e.moveTo(0, e.getY());
                        if(e instanceof Bullet)
                            destroyEntity(e);
                    }
                    
                    if(e.getY() < 0){
                        e.moveTo(e.getX(), H);
                        if(e instanceof Bullet)
                            destroyEntity(e);
                    } else if(e.getY() > H){
                        e.moveTo(e.getX(), 0);
                        if(e instanceof Bullet)
                            destroyEntity(e);
                    }
                    
                }
                last = now;
             }
        };
        timer.start();
        
        addEntity(new Enemy(50,50,0));
    }

    public void addEntity(Entity e){
        entities.add(e);
        ((Group)scene.getRoot()).getChildren().add(e);
    }
    
    public void destroyEntity(Entity e){
        entities.remove(e);
        ((Group)scene.getRoot()).getChildren().remove(e);
        System.gc();
    }
    
    public Player getPlayer(){
        return p;
    }
    
    public static void main(String[] args) {
        launch();
    }

}