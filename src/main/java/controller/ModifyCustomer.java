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

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaproject1/MainTable.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
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

    @FXML
    void onActionSave(ActionEvent event) {

    }

    public void initData(Customer customer) {

        custID.setText(String.valueOf(customer.getCustomer_ID()));
        editName.setText(customer.getCustomerName());
        editAddress.setText(customer.getAddress());
        phonenumber.setText(customer.getPhoneNumber());
        postalCode.setText(customer.getPostalCode());

        ResultSet rs = Query.getCountries();

        // populate Country list
        try {
            while (rs.next()) {
                countryBox.getItems().add(rs.getString(1));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        // String countryName = customer.getCountry();
        System.out.print((customer.getCountry()));
        countryBox.getSelectionModel().select(customer.getCountry());

        // now populate the FLD list
        rs = Query.getFLD(customer.getCountry());

        try {
            while (rs.next()) {
                division.getItems().add(rs.getString(1));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        division.getSelectionModel().select(customer.getFirstLevelDivision());


    }

}