package main;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;

public class PrimaryController {

    @FXML
    ToggleButton subOneP, subMultP, subPandP;
    
    @FXML
    ToggleButton locStudio, locOnSite;
    
    @FXML
    Label priceLabel;
    
    @FXML
    public void calcPrice(){
        int price = 0;
        
        if(subOneP.isSelected()){
            price = 40;
        } else if (subMultP.isSelected()){
            price = 75;
        } else if(subPandP.isSelected()){
            price = 95;
        }
        
        if(locOnSite.isSelected()){
            price += 90;
        }
        
        priceLabel.setText("Price: $"+price);
    }
}
