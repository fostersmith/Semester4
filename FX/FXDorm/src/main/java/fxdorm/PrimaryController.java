package fxdorm;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PrimaryController {

    @FXML
    private CheckBox privateRoom;
    @FXML
    private CheckBox cableConnection;
    @FXML
    private CheckBox kitchenette;
    @FXML
    private CheckBox internet;
    @FXML
    private CheckBox microwave;
    @FXML
    private CheckBox refridgerator;
    
    @FXML
    private TextArea textArea;
    
    

    
    @FXML
    private void enterPressed(){
        CheckBox[] boxes = {privateRoom, cableConnection, kitchenette, internet, microwave, refridgerator};
        
        StringBuilder text = new StringBuilder();
        boolean firstFound = true;
        for(int i = 0; i < boxes.length; ++i){
            System.out.println(i);
            if(boxes[i].isSelected()){
                if(!firstFound){
                    text.append(", ");
                }
                text.append(boxes[i].getText());
                firstFound = false;
            }
        }
        
        textArea.setText(text.toString());
        
    }
}
