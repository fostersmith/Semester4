package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static double theta;
    
    public static final int W = 200, H = 200;
    
    private static ArrayList<Entity> entities = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent e){
                
            }
        });
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public void addEntity(Entity e){
        entities.add(e);
    }
    
    public static void main(String[] args) {
        launch();
    }

}