package controller;

import Database.DBConnection;
import Database.Query;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;


public class MainTable implements Initializable {

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

    static Connection conn = DBConnection.getConnection();
    Stage stage;
    Parent scene;

    @FXML
    void onActionAddAppt(ActionEvent event) {

    }

    @FXML
    void onActionAddCust(ActionEvent event) {

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
    void onActionModCust(ActionEvent event) {

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


    }

}