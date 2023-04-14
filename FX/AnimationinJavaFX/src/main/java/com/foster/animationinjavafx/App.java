package com.foster.animationinjavafx;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Using JavaFX to Use transition and Timeline Animation");
        final Scene scene = new Scene(new Group(), 600, 400);
        scene.setFill(Color.CADETBLUE);
        
        final Circle circ = new Circle(50);
        circ.setFill(Color.RED);
        Rectangle rec = new Rectangle(25,25,50,50);
        rec.setArcHeight(25);
        rec.setArcWidth(15);
        rec.setFill(Color.BLANCHEDALMOND);
        rec.setTranslateX(250);
        rec.setTranslateY(50);
        ((Group)scene.getRoot()).getChildren().addAll(circ, rec);
        stage.setScene(scene);
        stage.show();
        
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        final KeyValue kv = new KeyValue(circ.centerYProperty(), 500, Interpolator.EASE_BOTH);
        final KeyFrame kf = new KeyFrame(Duration.millis(5000), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        
        FadeTransition ft = new FadeTransition(Duration.millis(3000), rec);
        ft.setFromValue(1.0);
        ft.setToValue(0.1);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();
        FadeTransition fadeTransition =
         new FadeTransition(Duration.millis(1000), rec);
        fadeTransition.setFromValue(1.0f);
        fadeTransition.setToValue(0.3f);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(true);
        
        Path path = new Path();
        path.getElements().add(new MoveTo(20,20));
        path.getElements().add(new CubicCurveTo(380, 0,
         380, 120, 200, 120));
        path.getElements().add(new CubicCurveTo(0, 120,
         0, 240, 380, 240));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(4000));
        pathTransition.setPath(path);
        pathTransition.setNode(circ);
        pathTransition.setOrientation
         (PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.setAutoReverse(true);
        pathTransition.play();

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(2000), rec);
        translateTransition.setFromX(50);
        translateTransition.setToX(375);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(true);
        
        RotateTransition rotateTransition =
        new RotateTransition(Duration.millis(2000), rec);
        rotateTransition.setByAngle(180f);
        rotateTransition.setCycleCount(4);
        rotateTransition.setAutoReverse(true);
        
        ScaleTransition scaleTransition =
        new ScaleTransition(Duration.millis(2000), rec);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(2);
        scaleTransition.setToY(2);
        scaleTransition.setCycleCount(1);
        scaleTransition.setAutoReverse(true);
        
        SequentialTransition sequentialTransition = new SequentialTransition();
       
        sequentialTransition.getChildren().addAll(
         fadeTransition,
         translateTransition,
         rotateTransition,
         scaleTransition);
        sequentialTransition.setCycleCount(Timeline.INDEFINITE);
        sequentialTransition.setAutoReverse(true);
        sequentialTransition.play();
    }

    public static void main(String[] args) {
        launch();
    }

}