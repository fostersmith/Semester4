module javafxusingcss {
    requires javafx.controls;
    requires javafx.fxml;

    opens javafxusingcss to javafx.fxml;
    exports javafxusingcss;
}
