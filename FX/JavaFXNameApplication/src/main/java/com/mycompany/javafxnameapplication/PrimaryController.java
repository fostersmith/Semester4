package com.mycompany.javafxnameapplication;

import javafx.scene.control.*;
import javafx.event.*;

import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private Label label;
    @FXML
    private Button button;
    @FXML
    private TextField textName;
    
    @FXML
    private void handleButtonAction(ActionEvent event)
    {
     label.setText("Thank you!");
     button.setText("Done");
    }
}
