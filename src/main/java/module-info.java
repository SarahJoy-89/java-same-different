module com.example.javaproject1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.javaproject1 to javafx.fxml;
    exports com.example.javaproject1;
    exports controller;
    exports model;
    opens controller to javafx.fxml;
    opens model to javafx.base;

}