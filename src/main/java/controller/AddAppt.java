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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddAppt {

        @FXML
        private Button Cancel;

        @FXML
        private TextField appt_id;

        @FXML
        private ComboBox<String> contacts;

        @FXML
        private TextField customer_id;

        @FXML
        private TextField description;

        @FXML
        private DatePicker end_date;

        @FXML
        private ComboBox<String> endMinute;

        @FXML
        private ComboBox<String> endHour;

        @FXML
        private TextField loc;

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

        private int u_id;
        private ResourceBundle resourceBundle;

        @FXML
        void contactID(ActionEvent event) {

        }

        @FXML
        void customerID(ActionEvent event) {

        }

        @FXML
        void enterdescription(ActionEvent event) {

        }

        @FXML
        void enterenddate(ActionEvent event) {

        }


        @FXML
        void enterstartdate(ActionEvent event) {

        }

        @FXML
        void entertitle(ActionEvent event) {

        }

        @FXML
        void entertype(ActionEvent event) {

        }

        @FXML
        void enterlocation(ActionEvent event) {

        }

        /**
         * Exits back to Main Table without saving to database.
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
         * If all fields are filled, saves data and writes a new appointment to the
         * appointments table of the database.
         * @param event
         * @throws IOException
         */
        @FXML
        void onActionSave(ActionEvent event) throws IOException {
                Appointment appointment = new Appointment();

                if (allFieldsGood()) {

                        LocalDate toBeStart = startdate.getValue();
                        LocalDate toBeEnd = end_date.getValue();
                        String start_hour = startHour.getValue();
                        String end_hour = endHour.getValue();
                        String start_minute = startMinute.getValue();
                        String end_minute = endMinute.getValue();
                        LocalDateTime startingTime = LocalDateTime.of(toBeStart.getYear(), toBeStart.getMonthValue(), toBeStart.getDayOfMonth(), Integer.parseInt(start_hour), Integer.parseInt(start_minute));
                        LocalDateTime endingTime = LocalDateTime.of(toBeEnd.getYear(), toBeEnd.getMonthValue(), toBeEnd.getDayOfMonth(), Integer.parseInt(end_hour), Integer.parseInt(end_minute));
                        // Set the times
                        appointment.setStart(startingTime);
                        appointment.setEnd(endingTime);

                        appointment.setTitle(title.getText());
                        appointment.setDescription(description.getText());
                        appointment.setLocation(loc.getText());
                        appointment.setType(type.getText());

                        appointment.setContact(contacts.getValue());
                        appointment.setCustomer(Integer.parseInt(customer_id.getText()));
                        appointment.setUser(Integer.parseInt(user_id.getText()));

                        if (appointment.hasConflict()) {
                                Alert conflictAlert = new Alert (Alert.AlertType.ERROR, "Conflict");
                                conflictAlert.showAndWait();
                        } else
                                if (appointment.isDuringOfficeHours()) {
                                        // update the database with Appointment object
                                        Query.addAppointment(appointment);

                                        // then go back to main menu

                                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                                        FXMLLoader loader = new FXMLLoader();
                                        loader.setLocation((getClass().getResource("/com/example/javaproject1/MainTable.fxml")));
                                        Parent scene = loader.load();

                                        stage.setScene(new Scene(scene));
                                        stage.show();

                                        MainTable controller = loader.getController();
                                        controller.init();
                                }
                                else {
                                        Alert afterHoursAlert = new Alert (Alert.AlertType.ERROR, "Outside business hours");
                                        afterHoursAlert.showAndWait();
                                }
                } else {
                        displayAlert();
                }

        }

        @FXML
        void userID(ActionEvent event) {

        }

        /**
         * Initializes the UI for the Add Appointment view.
         *
         */
        public void init() {

                hours.setAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                        "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
                minutes.addAll("00", "15", "30", "45");

                startHour.setItems(hours);
                startMinute.setItems(minutes);

                endHour.setItems(hours);
                endMinute.setItems(minutes);

                contacts.setItems(Query.getContacts());


        }

        private boolean allFieldsGood() {
                if (customer_id.getText() == "") {
                        return false;
                }
                if (title.getText() == "") {
                        return false;
                }

                if (description.getText() == "") {
                        return false;
                }
                if (type.getText() == "") {
                        return false;
                }

                if (loc.getText() == "") {
                        return false;
                }

                if (user_id.getText() == "") {
                        return false;
                }

                if (contacts.getValue() == null) {
                        return false;
                }

                if (startdate.getValue() == null) {
                        return false;
                }

                if (end_date.getValue() == null) {
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
