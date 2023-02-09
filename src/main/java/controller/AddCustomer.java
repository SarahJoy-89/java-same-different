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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;


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
    private int user_id;
    private ResourceBundle resourceBundle;

    @FXML
    void onActionAddAddress(ActionEvent event) {

    }

    @FXML
    void onActionAddName(ActionEvent event) {

    }

    @FXML
    void onActionAddNumber(ActionEvent event) {

    }

    @FXML
    void onActionAddPostalCode(ActionEvent event) {
    }

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation((getClass().getResource("/com/example/javaproject1/MainTable.fxml")));
        Parent scene = loader.load();

        stage.setScene(new Scene(scene));
        stage.show();

        MainTable controller = loader.getController();
        controller.init(user_id, resourceBundle);

    }

    @FXML
    void onActionChangeCountry(ActionEvent event) {
        String countryName = countryBox.getValue().toString();
        divisionBox.getItems().clear();
        ResultSet rs = Query.getFLD(countryName);

        try {
            while (rs.next()) {
                divisionBox.getItems().add(rs.getString(1));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

    }


    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        // Initialized new data object
        Customer tempCustomer = new Customer();
        tempCustomer.setCustomerName(customerName.getText());
        tempCustomer.setAddress(address.getText());
        tempCustomer.setPostalCode(postalCode.getText());
        tempCustomer.setPhoneNumber(phoneNumber.getText());
        tempCustomer.setFirstLevelDivision(divisionBox.getValue().toString());

        // logical check to make sure you have
        // all necessary data members


        Query.addCustomer(tempCustomer);

        // then go back to MainMenu
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation((getClass().getResource("/com/example/javaproject1/MainTable.fxml")));
        Parent scene = loader.load();

        stage.setScene(new Scene(scene));
        stage.show();

        MainTable controller = loader.getController();
        controller.init(user_id, resourceBundle);
    }

    public void initData(int id, ResourceBundle rb) {
        user_id = id;
        resourceBundle = rb;
        ResultSet rs = Query.getCountries();

        // populate Country list
        try {
            while (rs.next()) {
                countryBox.getItems().add(rs.getString(1));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

    }

}
