module com.example.javaproject1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javaproject1 to javafx.fxml;
    exports com.example.javaproject1;
    exports controller;
    opens controller to javafx.fxml;
}