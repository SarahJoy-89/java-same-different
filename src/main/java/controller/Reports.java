package controller;

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
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashMap;

public class Reports {
    @FXML private ComboBox<String> contactBox;

    @FXML private TableColumn<Appointment,Integer> apptColumn;
    @FXML private TableColumn<Appointment, String> titleColumn;
    @FXML private TableColumn<Appointment, String> typeColumn;
    @FXML private TableColumn<Appointment, String> descriptionColumn;
    @FXML private TableColumn<Appointment, LocalDateTime> startColumn;
    @FXML private TableColumn<Appointment, LocalDateTime> endColumn;
    @FXML private TableColumn<Appointment, Integer> customerColumn;

    @FXML private TableColumn<Customer, Integer> idColumn;
    @FXML private TableColumn<Customer, String> nameColumn;
    @FXML private TableColumn<Customer, String> addColumn;
    @FXML private TableColumn<Customer, String> postColumn;
    @FXML private TableColumn<Customer, String> fldColumn;

    @FXML private ComboBox<String> typeBox;
    @FXML private ComboBox<String> monthBox;
    @FXML private ComboBox<String> countryBox;

    @FXML private Button backButton;
    @FXML private Button countButton;

    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableView<Customer> customerTable;

    private HashMap<String, Integer> monthMap = new HashMap<>();
    Stage stage;

    @FXML private TextArea totalCountArea;

    public void init() {

        contactBox.setItems(Query.getContacts());
        typeBox.setItems(Query.getTypes());
        monthBox.setItems(Query.getMonthNames());
        countryBox.setItems(Query.getCountries());


        monthMap.put("January", 1);
        monthMap.put("February", 2);
        monthMap.put("March", 3);
        monthMap.put("April", 4);
        monthMap.put("May", 5);
        monthMap.put("June", 6);
        monthMap.put("July", 7);
        monthMap.put("August", 8);
        monthMap.put("Septebmer", 9);
        monthMap.put("October", 10);
        monthMap.put("November", 11);
        monthMap.put("December", 12);

    }

    @FXML
    public void onActionChangeContact(ActionEvent event) {
        String contactName = contactBox.getValue();

        appointmentTable.setItems(Query.getAppointmentsByContact(contactName));

        apptColumn.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startLocal"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endLocal"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));

    }

    @FXML
    public void onActionGoBack(ActionEvent event) throws IOException {
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
    public void onActionGetCount(ActionEvent event) throws IOException {
        String type = typeBox.getValue();
        String month = monthBox.getValue();
        int monthNum = monthMap.get(month);

        int count = Query.getTypeCount(type, monthNum);

        totalCountArea.setText(String.valueOf(count));

    }

    @FXML
    public void onActionChangeCountry(ActionEvent event) {
        int countryID = Query.getCountryID(countryBox.getValue());

        customerTable.setItems(Query.getCustomersByCountryID(countryID));

        idColumn.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        fldColumn.setCellValueFactory(new PropertyValueFactory<>("firstLevelDivision"));

    }
}
