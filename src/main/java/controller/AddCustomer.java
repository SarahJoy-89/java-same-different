package controller;

import Database.Query;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    /**
     * On button press, returns to MainTable without saving to database.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation((getClass().getResource("/com/example/javaproject1/MainTable.fxml")));
        Parent scene = loader.load();

        stage.setScene(new Scene(scene));
        stage.show();

        MainTable controller = loader.getController();
        controller.init();

    }

    /**
     * When a new country is selected, method clears out the values in the division box combobox
     * and initializes with the first level divisions from the database.
     * @param event
     */
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

    /**
     * Saves all present customer data to create new customer in database.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {

        if (allFieldsGood()) {
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
            controller.init();
        } else {
            displayAlert();
        }
    }

    /**
     * Initializes Country combobox, displays form for Customer intake.
     */
    public void initData() {

        countryBox.setItems(Query.getCountries());

    }

    private boolean allFieldsGood() {
        if (customerName.getText() == "") {
            return false;
        }
        if (address.getText() == "") {
            return false;
        }
        if (postalCode.getText() == "") {
            return false;
        }
        if (phoneNumber.getText() == "") {
            return false;
        }
        if (divisionBox.getValue() == null) {
            return false;
        }
        return true;
    }

    private void displayAlert() {
        Alert alertError = new Alert(Alert.AlertType.ERROR);

        alertError.setTitle("Error");
        alertError.setHeaderText("Please make sure all fields are filled");
        alertError.showAndWait();

    }

}
