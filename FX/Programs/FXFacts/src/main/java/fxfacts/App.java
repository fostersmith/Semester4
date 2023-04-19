package fxfacts;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    private static String[] factsStr = {"The Transcaucasian Trail (TCT) is planned to have two routes covering a total of 3,000km", 
        "It is currently possible to thru-hike both Georgia and Armenia on the North-South route of the TCT",
        "The two founders of the TCT are Paul Stephens and Jeff Haack",
        "Paul and Jeff were both former Peace Corps Volunteers",
        "Paul and Jeff discovered that they were both trying to start a similar project when they tried to register the same domain name in 2015",
        "All three of the countries which the trail passes through (Georgia, Armenia, and Azerbaijan) are former Soviet countries with a large Russian-speaking population"};
    private Label[] facts = new Label[factsStr.length];
    private int factI = 0;
    
    @Override
    public void start(Stage stage) {
        
        for(int i = 0; i < factsStr.length; ++i){
            facts[i] = new Label(factsStr[i]);
        }
        
        Button button = new Button("Press for more facts!");
        
        BorderPane pane = new BorderPane();
        pane.setTop(button);
        pane.setCenter(facts[factI]);

        button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                ++factI;
                factI %= facts.length;
                pane.setCenter(facts[factI]);
            }
        });
        
        
        var scene = new Scene(pane, 640, 480);
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}