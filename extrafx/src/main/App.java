package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import static javafx.scene.input.KeyCode.LEFT;
import static javafx.scene.input.KeyCode.RIGHT;
import static javafx.scene.input.KeyCode.SPACE;
import static javafx.scene.input.KeyCode.UP;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    
    public static final int W = 800, H = 800;
    
    private static ArrayList<Entity> entities = new ArrayList<>();
    private static Player p = new Player(W/2, H/2, 0);
    
    private static Scene gameOver = new Scene(new VBox(), W, H);
    private static Label gameOverLabel = new Label("Game Over. Score: ");
    private static Button playAgainButton = new Button("Play Again");
    
    private static int score = -(Player.MAX_HEALTH/Enemy.DAMAGE);
    
    private static EventHandler<ActionEvent> playAgainHandler = new EventHandler<>(){
        public void handle(ActionEvent event){
            while(entities.size() > 0)
                destroyEntity(entities.get(0));
            score = 0;
            p = new Player(W/2, H/2, 0);
            addEntity(p);
            stage.setScene(scene);
            timer.start();
        }
    };
    
    private static AnimationTimer timer;
    private static Stage stage;
    
    static
    {
        playAgainButton.setOnAction(playAgainHandler);
        ((VBox)gameOver.getRoot()).setAlignment(Pos.CENTER);
        ((VBox)gameOver.getRoot()).getChildren().addAll(gameOverLabel, playAgainButton);
        
    }
    
    @Override
    public void start(Stage stage) throws IOException {   
        this.stage = stage;
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
        
        timer = new AnimationTimer() {
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
                    for(int j = i; j < entities.size(); ++j){
                        if(j != i){
                            Entity f = entities.get(j);
                            for(int g = 0; g < f.getPoints().size()/2; ++g){
                                if(e.contains(f.getPoints().get(2*g) + f.x - e.x,f.getPoints().get(2*g+1) + f.y - e.y)){
                                    e.collision(f);
                                    f.collision(e);
                                    break;
                                }
                            }
                        }
                    }
                    
                    if(e.getHealth() <= 0)
                        if(e != p){
                            destroyEntity(e);
                            if(e instanceof Enemy)
                                ++score;
                        }
                        else
                            gameOver();
                    
                    if(e.getX() < 0){
                        e.moveTo(0, e.getY());
                        if(e instanceof Bullet)
                            destroyEntity(e);
                    } else if(e.getX() > W){
                        e.moveTo(W, e.getY());
                        if(e instanceof Bullet)
                            destroyEntity(e);
                    }
                    
                    if(e.getY() < 0){
                        e.moveTo(e.getX(), 0);
                        if(e instanceof Bullet)
                            destroyEntity(e);
                    } else if(e.getY() > H){
                        e.moveTo(e.getX(), H);
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
    
    public static void gameOver(){
        /*Scene gameOver = new Scene(new Group(), W, H);
        gameOver.*/
        for(Entity e : entities){
            if(e.getHealth() <= 0 && e instanceof Enemy)
                ++score;
        }
        timer.stop();
        gameOverLabel.setText("Game Over. Score :"+score);
        stage.setScene(gameOver);
       //((Group)scene.getRoot()).getChildren().add(gameOverBox);
    }

    public static void addEntity(Entity e){
        entities.add(e);
        ((Group)scene.getRoot()).getChildren().addAll(e, e.hb.base, e.hb);
    }
    
    public static void destroyEntity(Entity e){
        entities.remove(e);
        ((Group)scene.getRoot()).getChildren().removeAll(e, e.hb, e.hb.base);
        System.gc();
    }
    
    public Player getPlayer(){
        return p;
    }
    
    public static void main(String[] args) {
        launch();
    }

}