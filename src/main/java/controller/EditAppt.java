package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Appointment;

import java.util.ResourceBundle;

public class EditAppt {

    @FXML
    private TextField appt_id;

    @FXML
    private Button cancel;

    @FXML
    private TextField contact_id;

    @FXML
    private TextField customer_id;

    @FXML
    private TextField description;

    @FXML
    private DatePicker end_date;

    @FXML
    private Spinner<?> endtimehour;

    @FXML
    private Spinner<?> endtimemin;

    @FXML
    private TextField locat;

    @FXML
    private Button save;

    @FXML
    private DatePicker startdate;

    @FXML
    private Spinner<?> starttimehour;

    @FXML
    private Spinner<?> starttimemin;

    @FXML
    private TextField title;

    @FXML
    private TextField type;

    @FXML
    private TextField user_id;

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
    void onActionCancel(ActionEvent event) {

    }

    @FXML
    void onActionSave(ActionEvent event) {

    }

    @FXML
    void userID(ActionEvent event) {

    }

    public void initData(Appointment appointment, int id, ResourceBundle rb) {
        appt_id.setText(String.valueOf(appointment.getAppointment_ID()));
        title.setText(appointment.getTitle());
        locat.setText(appointment.getLocation());
        description.setText(appointment.getDescription());
        type.setText(appointment.getType());
        customer_id.setText(String.valueOf(appointment.getAppointment_ID()));
        contact_id.setText(appointment.getContact());
        user_id.setText(String.valueOf(appointment.getUser()));
        startdate.setValue(appointment.getStartLocal().toLocalDate());
        end_date.setValue(appointment.getEndLocal().toLocalDate());



    }

}