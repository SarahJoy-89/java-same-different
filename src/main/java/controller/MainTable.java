package controller;

import Database.DBConnection;
import Database.Query;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ResourceBundle;


public class MainTable  {

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
    @FXML private TableColumn<Appointment, LocalDateTime> startCol;
    @FXML private TableColumn<Appointment, LocalDateTime> endCol;
    @FXML private TableColumn<Appointment, Integer> aptCustNameCol;
    @FXML private TableColumn<Appointment, Integer> userNameCol;

    @FXML private TableView<Customer> customerTable;
    @FXML private TableView<Appointment> appointmentTable;

    static Connection conn = DBConnection.getConnection();
    Stage stage;
    Parent scene;
    private int id;
    private ResourceBundle resourceBundle =  ResourceBundle.getBundle("language_files/rebu");

    /**
     * Opens the Add Appointment view on button press
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionAddAppt(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation((getClass().getResource("/com/example/javaproject1/AddAppt.fxml")));
        Parent scene = loader.load();

        stage.setScene(new Scene(scene));
        stage.show();

        AddAppt controller = loader.getController();
        controller.init();
    }

    /**
     * Opens the AddCustomer view and workflow
     * @param event button press
     * @throws IOException
     */
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

    /**
     * Deletes the highlighted appointment. Throws an error message if no appointment selected.
     * @param event
     */
    @FXML
    void onActionDelAppt(ActionEvent event) {
        Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            Alert noSelection = new Alert(Alert.AlertType.ERROR, "Please select an appointment.");
            noSelection.showAndWait();
        } else {
            Query.deleteSingleAppointment(selectedAppointment.getAppointment_ID());
            Alert deletedApptAlert = new Alert(Alert.AlertType.INFORMATION, "Appointment for " + selectedAppointment.getAppointment_ID() +
                    " of type: " + selectedAppointment.getType() + " successfully deleted.");
            deletedApptAlert.showAndWait();
        }

        init();

    }

    /**
     * deletes all appointments for a selected customer before deleting record for selected customer.
     * @param event
     */
    @FXML
    void onActionDelCust(ActionEvent event) {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            Alert noSelection = new Alert(Alert.AlertType.ERROR, "Please select a customer.");
            noSelection.showAndWait();

        } else {
            Query.deleteAppointments(selectedCustomer.getCustomer_ID());
            Query.deleteCustomer(selectedCustomer.getCustomer_ID());
        }

        Alert deletedCustomerAlert = new Alert(Alert.AlertType.INFORMATION, "Record for " + selectedCustomer.getCustomerName() + "successfully deleted.");
        deletedCustomerAlert.showAndWait();

        init();
    }

    /**
     * Exits from Main Table application back to MainMenuController view (login screen)
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionGoBack(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainTable.class.getResource("/com/example/javaproject1/MainMenu.fxml"), resourceBundle);
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle(resourceBundle.getString("welcome"));
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Opens Controller on selected appointment, and sends object to initialize EditAppt view
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionModAppt(ActionEvent event) throws IOException {

        Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();

        if (appointment == null) {
            Alert noSelection = new Alert(Alert.AlertType.ERROR, "Please select an appointment.");
            noSelection.showAndWait();
        } else {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((getClass().getResource("/com/example/javaproject1/EditAppt.fxml")));
            Parent scene = loader.load();

            stage.setScene(new Scene(scene));
            stage.show();

            EditAppt controller = loader.getController();
            controller.initData(appointment);
        }


    }

    /**
     * Opens controller ModifyCustomer loaded with Customer object selected in table
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionModCust(ActionEvent event) throws IOException {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();

        if (customer == null) {
            Alert noSelection = new Alert(Alert.AlertType.ERROR, "Please select a customer.");
            noSelection.showAndWait();
        } else {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((getClass().getResource("/com/example/javaproject1/ModifyCustomer.fxml")));
            Parent scene = loader.load();

            stage.setScene(new Scene(scene));
            stage.show();

            ModifyCustomer controller = loader.getController();
            controller.initData(customer);
        }
    }

    /**
     * Loads all appointments upon selecting the ViewAll radio button
     * @param event
     */
    @FXML
    void onActionViewAll(ActionEvent event) {

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

    /**
     *  Opens the Reports view on button press
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionViewReports(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation((getClass().getResource("/com/example/javaproject1/Reports.fxml")));
        Parent scene = loader.load();

        stage.setScene(new Scene(scene));
        stage.show();

        Reports controller = loader.getController();
        controller.init();

    }

    /**
     * Loads Appointment Tab with appointments going forward 7 days from current time and date.
     * @param event
     */
    @FXML
    void onActionViewWeek(ActionEvent event) {
        appointmentTable.setItems(Query.getWeekAppointments());

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

    /**
     * Displays all appointments that fall within the current calendar month in Appointment tab.
     * @param event
     */
    @FXML
    void onActionViewMonth(ActionEvent event) {
        appointmentTable.setItems(Query.getMonthAppointments());

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

    /**
     * Initializes Customer and Appointment tabs upon load of the application.
     */
    public void init() {


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

        view_all.setSelected(true);


    }

}