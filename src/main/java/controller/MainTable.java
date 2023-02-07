package controller;

import Database.DBConnection;
import Database.Query;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;


public class MainTable implements Initializable {

    @FXML
    private Tab customer_tab;

    @FXML
    private Tab Appt_view;

    @FXML
    private Button add_appt;

    @FXML
    private Button add_cust;

    @FXML
    private Button del_appt;

    @FXML
    private Button del_cust;

    @FXML
    private Button go_back;

    @FXML
    private Button mod_appt;

    @FXML
    private Button mod_cust;

    @FXML
    private RadioButton view_all;

    @FXML
    private RadioButton view_month;

    @FXML
    private Button view_reports;

    @FXML
    private RadioButton view_week;

    @FXML private TableColumn<Customer, Integer> custIDColumn;
    @FXML private TableColumn<Customer, String> custNameColumn;
    @FXML private TableColumn<Customer, String> custAddColumn;
    @FXML private TableColumn<Customer, String> custPCColumn;
    @FXML private TableColumn<Customer, String> custPhoneColumn;
    @FXML private TableColumn<Customer, String> custFLDColumn;

    @FXML private TableColumn<Appointment, Integer> aptIDCol;
    @FXML private TableColumn<Appointment, String> titleCol;
    @FXML private TableColumn<Appointment, String> descCol;
    @FXML private TableColumn<Appointment, String> locCol;
    @FXML private TableColumn<Appointment, String> conCol;
    @FXML private TableColumn<Appointment, String> typCol;
    @FXML private TableColumn<Appointment, ZonedDateTime> startCol;
    @FXML private TableColumn<Appointment, ZonedDateTime> endCol;
    @FXML private TableColumn<Appointment, Integer> aptCustNameCol;
    @FXML private TableColumn<Appointment, Integer> userNameCol;

    @FXML private TableView<Customer> customerTable;
    @FXML private TableView<Appointment> appointmentTable;

    static Connection conn = DBConnection.getConnection();
    Stage stage;
    Parent scene;

    @FXML
    void onActionAddAppt(ActionEvent event) {

    }

    @FXML
    void onActionAddCust(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation((getClass().getResource("/com/example/javaproject1/AddCustomer.fxml")));
        Parent scene = loader.load();

        stage.setScene(new Scene(scene));
        stage.show();

        AddCustomer controller = loader.getController();
        controller.initData();

    }

    @FXML
    void onActionDelAppt(ActionEvent event) {

    }

    @FXML
    void onActionDelCust(ActionEvent event) {

    }

    @FXML
    void onActionGoBack(ActionEvent event) {

    }

    @FXML
    void onActionModAppt(ActionEvent event) {

    }

    @FXML
    void onActionModCust(ActionEvent event) throws IOException {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation((getClass().getResource("/com/example/javaproject1/ModifyCustomer.fxml")));
        Parent scene = loader.load();

        stage.setScene(new Scene(scene));
        stage.show();

        ModifyCustomer controller = loader.getController();
        controller.initData(customer);
    }

    @FXML
    void onActionViewAll(ActionEvent event) {

    }

    @FXML
    void onActionViewReports(ActionEvent event) {

    }

    @FXML
    void onActionViewWeek(ActionEvent event) {

    }

    @FXML
    void onActionViewMonth(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerTable.setItems(Query.getCustomers());

        custIDColumn.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        custNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        custAddColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        custPCColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        custPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        custFLDColumn.setCellValueFactory(new PropertyValueFactory<>("firstLevelDivision"));


        appointmentTable.setItems(Query.getAppointments());

        aptIDCol.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        conCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startLocal"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endLocal"));
        aptCustNameCol.setCellValueFactory(new PropertyValueFactory<>("customer"));
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("user"));




    }

}