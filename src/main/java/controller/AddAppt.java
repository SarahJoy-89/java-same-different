package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.ResourceBundle;

public class AddAppt {

        @FXML
        private Button Cancel;

        @FXML
        private TextField appt_id;

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
        private TextField loc;

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
        void enterlocation(ActionEvent event) {

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
        void onActionCancel(ActionEvent event) {

        }

        @FXML
        void onActionSave(ActionEvent event) {

        }

        @FXML
        void userID(ActionEvent event) {

        }

        public void init(int id, ResourceBundle rb) {
                u_id = id;
                resourceBundle = rb;

        }

    }
