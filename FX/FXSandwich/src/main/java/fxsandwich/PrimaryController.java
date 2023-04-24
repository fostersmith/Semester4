package fxsandwich;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PrimaryController {

    private final static String[] proteins = {"Chicken", "Ham", "Beef", "Tofu"};
    private final static float[] proteinPrices = {2f, 2.5f, 2f, 3f};
    
    private final static String[] breads = {"Rye", "Wheat", "White"};
    private final static float[] breadPrices = {1f, 0.5f, 0.5f};
    
    @FXML
    Label priceLabel;
    
    @FXML
    ComboBox proteinBox;
    
    @FXML
    ComboBox breadBox;
    
    @FXML
    private void calcPrice(){
        float price = 0f;
        priceLabel.setText("Price: $"+price);
    }
    
    @FXML
    public void initialize(){
        String[] pricedProteins = new String[proteins.length];
        String[] pricedBreads = new String[breads.length];
        
        for(int i = 0; i < proteins.length; ++i){
            pricedProteins[i] = proteins[i] + " ($"+proteinPrices[i]+")";        
        }
        
        for(int i = 0; i < breads.length; ++i){
            pricedBreads[i] = breads[i] + " ($"+breadPrices[i]+")";        
        }

        breadBox.getItems().addAll(pricedBreads);
        proteinBox.getItems().addAll(pricedProteins);
    }
        
}
