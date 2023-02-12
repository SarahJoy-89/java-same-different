package controller;

import Database.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ResourceBundle;

public class EditAppt {

    @FXML
    private TextField appt_id;

    @FXML
    private Button cancel;

    @FXML
    private ComboBox<String> contacts;

    @FXML
    private TextField customer_id;

    @FXML
    private TextField description;

    @FXML
    private DatePicker end_date;

    @FXML
    private ComboBox<String> endHour;

    @FXML
    private ComboBox<String> endMinute;

    @FXML
    private TextField locat;

    @FXML
    private Button save;

    @FXML
    private DatePicker startdate;

    @FXML
    private ComboBox<String> startHour;

    @FXML
    private ComboBox<String> startMinute;

    @FXML
    private TextField title;

    @FXML
    private TextField type;

    @FXML
    private TextField user_id;

    Stage stage;
    ObservableList<String> hours = FXCollections.observableArrayList();
    ObservableList<String> minutes = FXCollections.observableArrayList();
    int u_id;
    ResourceBundle resourceBundle;


    @FXML
    void contactID(ActionEvent event) {

    }

    @FXML
    void customerID(ActionEvent event) {

    }

    @FXML
    void editdescription(ActionEvent event) {

    }

    @FXML
    void editenddate(ActionEvent event) {

    }

    @FXML
    void editendtimehour(MouseEvent event) {

    }

    @FXML
    void editlocation(ActionEvent event) {

    }

    @FXML
    void editstartdate(ActionEvent event) {

    }

    @FXML
    void editstarttimehour(MouseEvent event) {

    }

    @FXML
    void editstarttimemin(MouseEvent event) {

    }

    @FXML
    void edittimemin(MouseEvent event) {

    }

    @FXML
    void edittitle(ActionEvent event) {

    }

    @FXML
    void edittype(ActionEvent event) {

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
        controller.init(u_id, resourceBundle);
    }

    @FXML
    void onActionSave(ActionEvent event) {

    }

    @FXML
    void userID(ActionEvent event) {

    }

    public void initData(Appointment appointment, int id, ResourceBundle rb) {

        u_id = id;
        resourceBundle = rb;

        hours.setAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        minutes.addAll("00", "15", "30", "45");

        startHour.setItems(hours);
        startMinute.setItems(minutes);

        endHour.setItems(hours);
        endMinute.setItems(minutes);

        contacts.setItems(Query.getContacts());

        DateTimeFormatter hourFormat = DateTimeFormatter.ofPattern("KK");
        DateTimeFormatter minuteFormat = DateTimeFormatter.ofPattern("mm");


        appt_id.setText(String.valueOf(appointment.getAppointment_ID()));
        title.setText(appointment.getTitle());
        locat.setText(appointment.getLocation());
        description.setText(appointment.getDescription());
        type.setText(appointment.getType());
        customer_id.setText(String.valueOf(appointment.getAppointment_ID()));
        contacts.setValue(appointment.getContact());
        user_id.setText(String.valueOf(appointment.getUser()));
        startdate.setValue(appointment.getStartLocal().toLocalDate());
        end_date.setValue(appointment.getEndLocal().toLocalDate());


        startHour.setValue(appointment.getStartLocal().format(hourFormat));
        startMinute.setValue(appointment.getStartLocal().format(minuteFormat));
        endHour.setValue(appointment.getEndLocal().format(hourFormat));
        endMinute.setValue(appointment.getEndLocal().format(minuteFormat));



    }

}