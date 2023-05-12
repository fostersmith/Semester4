package main;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class PrimaryController {

    @FXML
    TextField a, b, c;
    @FXML
    RadioButton add, sub;
    @FXML
    Label out;
    
    @FXML
    private void check(){
        try{
            int aN = Integer.parseInt(a.getText()),
                    bN = Integer.parseInt(b.getText()),
                    cN = Integer.parseInt(c.getText());
            
            boolean correct;
            
            if(add.isSelected()){
                correct = aN + bN == cN;
            } else{
                correct = aN - bN == cN;
            } 
            
            if(correct){
                out.setText("Correct!");
            }
            else{
                out.setText("Try again");
            }
            
        } catch(NumberFormatException e){
            out.setText("Enter numbers for all fields");
        }
            
    }
}
