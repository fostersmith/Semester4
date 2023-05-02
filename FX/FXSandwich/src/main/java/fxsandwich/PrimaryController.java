package fxsandwich;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PrimaryController {

    private final static String[] proteins = {"Chicken", "Ham", "Beef", "Tofu"};
    private final static float[] proteinPrices = {2f, 2.5f, 2f, 3f};
    
    private final static String[] breads = {"Rye", "Wheat", "White"};
    private final static float[] breadPrices = {1f, 0.5f, 0.5f};
    
    static {
        for(int i = 0; i < proteins.length; ++i){
            proteins[i] = proteins[i] + " ($"+proteinPrices[i]+")";        
        }
        
        for(int i = 0; i < breads.length; ++i){
            breads[i] = breads[i] + " ($"+breadPrices[i]+")";        
        }
    }
    
    @FXML
    Label priceLabel;
    
    @FXML
    ComboBox proteinBox;
    
    @FXML
    ComboBox breadBox;
    
    @FXML
    private void calcPrice(){
        float price = 0f;
        String proteinVal = (String)proteinBox.getValue();
        String breadVal = (String)breadBox.getValue();
        
        if(proteinVal != null && breadVal != null){
        
            for(int i = 0; i < proteins.length; ++i){
                if(proteinVal.equals(proteins[i])){
                    price += proteinPrices[i];
                    System.out.println("Did it");
                    break;
                }
            }

            for(int i = 0; i < breads.length; ++i){
                if(breadVal.equals(breads[i])){
                    price += breadPrices[i];
                    break;
                }
            }
            
            priceLabel.setText("Price: $"+price);
        } else
            priceLabel.setText("Please enter a selection for all items");

    }
    
    @FXML
    public void initialize(){        

        breadBox.getItems().addAll(breads);
        proteinBox.getItems().addAll(proteins);
    }
        
}
