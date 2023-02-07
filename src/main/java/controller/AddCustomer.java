package controller;

import Database.Query;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.util.Objects;


public class AddCustomer {

    @FXML
    private TextField address;

    @FXML
    private Button cancelBtn;

    @FXML
    private ComboBox countryBox;

    @FXML
    private TextField customerName;

    @FXML
    private ComboBox divisionBox;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField postalCode;

    @FXML
    private Button saveBtn;

    Stage stage;
    Parent scene;
    Customer localCustomer = new Customer();

    @FXML
    void onActionAddAddress(ActionEvent event) {
        localCustomer.setAddress(address.getText());
    }

    @FXML
    void onActionAddName(ActionEvent event) {
        localCustomer.setCustomerName(customerName.getText());
    }

    @FXML
    void onActionAddNumber(ActionEvent event) {
        localCustomer.setPhoneNumber(phoneNumber.getText());
    }

    @FXML
    void onActionAddPostalCode(ActionEvent event) {
        localCustomer.setPostalCode(postalCode.getText());
    }

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaproject1/MainTable.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException {

        // logical check to make sure you have
        // all necessary data members
        // Query.addCustomer(localCustomer);

        // then go back to MainMenu
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaproject1/MainTable.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

}
