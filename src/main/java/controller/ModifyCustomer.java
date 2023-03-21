package controller;

import Database.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import javax.xml.transform.Result;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;


public class ModifyCustomer {

    @FXML
    private ComboBox countryBox;

    @FXML
    private ComboBox division;

    @FXML
    private TextField custID;

    @FXML
    private TextField editName;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField editAddress;

    @FXML
    private TextField phonenumber;

    @FXML
    private TextField postalCode;

    @FXML
    private Button saveBtn;

    Stage stage;
    Parent scene;
    private int user_id;
    private ResourceBundle resourceBundle;

    /**
     * Returns to main table without saving any new customer data
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

    @FXML
    void onActionEditAddress(ActionEvent event) {

    }

    @FXML
    void onActionEditName(ActionEvent event) {

    }

    @FXML
    void onActionEditPhone(ActionEvent event) {

    }


    @FXML
    void onActionEditPostalCode(ActionEvent event) {

    }

    /**
     * Creates a new Customer object and sets all data members based on
     *  information in the form. Calls updateCustomer() method to updated
     *  data for associated customer in the database
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        // Initialize new Customer object to throw into DB
        Customer tempCustomer = new Customer();

        tempCustomer.setCustomerID(Integer.parseInt(custID.getText()));
        tempCustomer.setCustomerName(editName.getText());
        tempCustomer.setAddress(editAddress.getText());
        tempCustomer.setPostalCode(postalCode.getText());
        tempCustomer.setPhoneNumber(phonenumber.getText());
        tempCustomer.setFirstLevelDivision(division.getValue().toString());
        // update the database with data from temp customer object
        Query.updateCustomer(tempCustomer);
        // then go back to the Main Table
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
     * Updates the contents of the division combobox based on
     * the country that is selected
     * @param event
     */
    @FXML
    void onActionChangeCountry(ActionEvent event) {
        division.getItems().clear();
        updateDivisionList(countryBox.getValue().toString());
    }

    /**
     * Takes a customer object passed in from main table and initializes the
     * modifycustomer form
     * @param customer
     */
    public void initData(Customer customer) {


        custID.setText(String.valueOf(customer.getCustomer_ID()));
        editName.setText(customer.getCustomerName());
        editAddress.setText(customer.getAddress());
        phonenumber.setText(customer.getPhoneNumber());
        postalCode.setText(customer.getPostalCode());
        countryBox.setItems(Query.getCountries());

        // String countryName = customer.getCountry();

        countryBox.getSelectionModel().select(customer.getCountry());

        // now populate the FLD list
       // rs = Query.getFLD(countryBox.getValue().toString());
        updateDivisionList(countryBox.getValue().toString());
        division.getSelectionModel().select(customer.getFirstLevelDivision());


    }

    /**
     * updates contents of divisionList combobox based
     * on which country is selected in countries combobox
     * @param countryName
     */
    private void updateDivisionList(String countryName) {
        ResultSet rs = Query.getFLD(countryName);

        try {
            while (rs.next()) {
                division.getItems().add(rs.getString(1));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

    }

}