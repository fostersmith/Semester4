module javafxscenebuilder {
    requires javafx.controls;
    requires javafx.fxml;

    opens javafxscenebuilder to javafx.fxml;
    exports javafxscenebuilder;
}
