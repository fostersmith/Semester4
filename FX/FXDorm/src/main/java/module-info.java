module fxdorm {
    requires javafx.controls;
    requires javafx.fxml;

    opens fxdorm to javafx.fxml;
    exports fxdorm;
}
