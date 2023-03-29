package com.example.javaproject1;

import Database.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class MainMethod extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        // get the computer's default locale!
        // and then load those language messages
        ResourceBundle rb = ResourceBundle.getBundle("language_files/rebu");

        FXMLLoader fxmlLoader = new FXMLLoader(MainMethod.class.getResource("/com/example/javaproject1/MainMenu.fxml"), rb);
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle(rb.getString("welcome"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        //open Connection to DB
        DBConnection.startConnection();
        // To test the French locale uncomment the following line
       // Locale.setDefault(new Locale("fr", "FR"));

        launch();

       DBConnection.closeConnection();
    }



}