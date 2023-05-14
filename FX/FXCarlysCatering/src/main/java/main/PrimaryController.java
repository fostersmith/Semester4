package main;

import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public class PrimaryController {

    @FXML
    HBox entreeBox, side1Box, side2Box, dessertBox;
    
    @FXML
    Label outLbl;
    
    @FXML
    TextArea guestIn;
    
    ToggleGroup entreeGroup = new ToggleGroup(),
            side1Group = new ToggleGroup(),
            side2Group = new ToggleGroup(),
            dessertGroup = new ToggleGroup();
    
    RadioButton[] entrees = new RadioButton[Event.entrees.length],
        side1s = new RadioButton[Event.sides.length],
        side2s = new RadioButton[Event.sides.length],
        desserts = new RadioButton[Event.desserts.length];
    
    HashMap<Toggle, Integer> choiceMap = new HashMap<>();
    
    Event event;
    
    @FXML
    public void calcPrice(){
        
        try {
        
            int guests = Integer.valueOf(guestIn.getText());
            
            event = new Event(choiceMap.get(entreeGroup.getSelectedToggle()), 
                    choiceMap.get(entreeGroup.getSelectedToggle()),
                    choiceMap.get(entreeGroup.getSelectedToggle()),
                    choiceMap.get(entreeGroup.getSelectedToggle()),
                    guests);
            
            outLbl.setText("Price: $"+event.getPrice());
            
        } catch(NumberFormatException e){
            outLbl.setText("Enter a valid number of guests");
        } catch(IllegalArgumentException e){
            outLbl.setText(e.getMessage());
        }
        
    }
    
    @FXML
    public void initialize(){
        ToggleGroup[] groups = {entreeGroup, side1Group, side2Group, dessertGroup};
        RadioButton[][] cbLists = {entrees, side1s, side2s, desserts};
        String[][] entryLists = {Event.entrees, Event.sides, Event.sides, Event.desserts};
        HBox[] boxes = {entreeBox, side1Box, side2Box, dessertBox};
        
        for(int e = 0; e < cbLists.length; ++e){
            RadioButton[] cbs = cbLists[e];
            String[] strs = entryLists[e];
            ToggleGroup group = groups[e];
            HBox box = boxes[e];
            for(int i = 0; i < cbs.length; ++i){
                RadioButton cb = new RadioButton(strs[i]);
                choiceMap.put(cb, i);
                group.getToggles().add(cb);
                box.getChildren().add(cb);
                if(i == 0)
                    cb.setSelected(true);
            }
        }
    }

}
