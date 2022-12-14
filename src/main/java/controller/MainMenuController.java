package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.time.ZoneId;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import Database.DBConnection;
import Database.Query;


public class MainMenuController implements Initializable {

    Stage stage;
    Parent scene;

    String language;

    static Connection conn = DBConnection.getConnection();
    private Statement stmt;



    // Check language to determine labels
    // ResourceBundle rb = ResourceBundle.getBundle("language_files/rebu");

    // log file in the user's home directory
    String fileName = System.getProperty("user.home") + "/login_activity.txt";
    File logFile = new File(fileName);

    @FXML
    private PasswordField password;
    @FXML
    private Label user_name;

    @FXML
    private Label pass_word;

    @FXML
    private Label timezone;

    @FXML
    private Label time_zone;

    @FXML
    private TextField username;



    @FXML
    void clearfields(ActionEvent event) {
        password.clear();
        username.clear();
    }

    @FXML
    void onActionLogIn(ActionEvent event) throws IOException {
        String uname = username.getText();
        String pword = password.getText();

        // ResultSet rs;

        // Get ready to write to the log!
        // Get the date and time in string format for the log entry
        LocalDateTime currentMoment = LocalDateTime.now();
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = currentMoment.format(formatDate);

        // Do some DB stuff to authenticate
        int id = Query.getContactID(uname);
        System.out.println("User ID: " + id + ", Password:" + pword +", Username: " + uname);

        if (Query.checkPassword(id, pword)) {
            // Create log entry for positive log in
            String logLine = "User " + uname + " successfully logged in at " + formattedDate + "\n";

            // write to log
            try (FileWriter logFileWriter = new FileWriter(logFile, true)) {
                logFileWriter.append(logLine);
            } catch (IOException ioe) {
                System.err.println("Error writing to log file");
            }
            // Now go to the next frame
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaproject1/MainTable.fxml")) );
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            //write 'negative' logging line if no match
            String logLine = "User " + uname + " gave invalid log in at " + formattedDate + "\n";
            try (FileWriter logFileWriter = new FileWriter(logFile, true)) {
                logFileWriter.append(logLine);
                // also clear out the text fields
                password.clear();
                username.clear();

            } catch (IOException ioe) {
                System.err.println("Error writing to log file");
            }

          }
        }


    @FXML
    void onActionExitProgram(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ZoneId z = ZoneId.systemDefault();
        String s = z.getId();

        // Set label timezone to user timezone
        timezone.setText(s);

        user_name.setText(rb.getString("user_name"));
        pass_word.setText(rb.getString("password"));
        time_zone.setText(rb.getString("timezone"));

    }

    private void displayAlert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {

            case 1:
                alertError.setTitle("Error");
                alertError.setHeaderText("Username or Password incorrect. Please try again");
                alertError.showAndWait();
                break;

            case 2:
                alertError.setTitle("Erreur");
                alertError.setHeaderText("Nom d'utilisateur ou mot de passe incorrect. Veuillez r??essayer");
                alertError.showAndWait();
                break;

        }
    }
}
