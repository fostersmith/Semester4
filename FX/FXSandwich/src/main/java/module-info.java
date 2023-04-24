module fxsandwich {
    requires javafx.controls;
    requires javafx.fxml;

    opens fxsandwich to javafx.fxml;
    exports fxsandwich;
}
