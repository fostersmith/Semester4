module com.mycompany.javafxnameapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.javafxnameapplication to javafx.fxml;
    exports com.mycompany.javafxnameapplication;
}
