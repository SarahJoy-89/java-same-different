package controller;

import Database.DBConnection;
import Database.Query;
import com.example.javaproject1.LogHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


public class MainMenuController implements Initializable {

    Stage stage;
    Parent scene;

    String language;

    static Connection conn = DBConnection.getConnection();
    private Statement stmt;
    private LogHelper logHelper;

    private ResourceBundle resourceBundle = ResourceBundle.getBundle("language_files/rebu");



    // Check language to determine labels
    // ResourceBundle rb = ResourceBundle.getBundle("language_files/rebu");

    // log file in the user's home directory
    String fileName = "./login_activity.txt";
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
    private Button logInButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button resetButton;


    /**
     * Clears username and password fields on click
     * @param event
     */
    @FXML
    void clearfields(ActionEvent event) {
        password.clear();
        username.clear();
    }

    /**
     * Validates username and password for log in to application. Display
     * language and time varies based on system locale.
     *
     * Within this method are two Lambda functions used to streamline
     * the logging of either a successful login in,
     * or a failed login, to the login_activity.txt file.
     *
     * The two Lambda Functions are goodLog() and badLog()
     * @param event login
     * @throws IOException
     */
    @FXML
    void onActionLogIn(ActionEvent event) throws IOException {
        String uname = username.getText();
        String pword = password.getText();


        // Get ready to write to the log!
        // Get the date and time in string format for the log entry
        LocalDateTime currentMoment = LocalDateTime.now();
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = currentMoment.format(formatDate);

        // Do some DB stuff to authenticate
        int id = Query.getUserID(uname);


        if (Query.checkPassword(id, pword)) {

            // write to log
            try (FileWriter logFileWriter = new FileWriter(logFile, true)) {
                logFileWriter.append(goodlog.logThis(uname, formattedDate));
            } catch (IOException ioe) {
                System.err.println("Error writing to log file");
            }

            Appointment appointment = Query.appointmentSoon();
            Alert loginAlert = new Alert(Alert.AlertType.INFORMATION);
            if (appointment == null) {
                loginAlert.setHeaderText("No appointments coming up!");
                loginAlert.show();
            } else {
                loginAlert.setHeaderText("Appointment " + appointment.getAppointment_ID() + " at " + appointment.getStartLocal().format(formatDate));
                loginAlert.show();
            }

            // Now go to the next frame
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((getClass().getResource("/com/example/javaproject1/MainTable.fxml")));
            Parent scene = loader.load();

            stage.setScene(new Scene(scene));
            stage.show();

            MainTable controller = loader.getController();
            controller.init();

        } else {
            //write 'negative' logging line if no match
            displayAlert(1);

            try (FileWriter logFileWriter = new FileWriter(logFile, true)) {
                logFileWriter.append(badLog.logThis(uname, formattedDate));
                // also clear out the text fields
                password.clear();
                username.clear();

            } catch (IOException ioe) {
                System.err.println("Error writing to log file");
            }

          }
        }

    /**
     * Clicking this button exits the program
     * @param event
     */
    @FXML
    void onActionExitProgram(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Initialize method sets up main interfce
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ZoneId z = ZoneId.systemDefault();
        String s = z.getId();
        // resourceBundle = ResourceBundle.getBundle(rb.getBaseBundleName());

        // Set label timezone to user timezone
        timezone.setText(s);

        user_name.setText(resourceBundle.getString("user_name"));
        pass_word.setText(rb.getString("password"));
        time_zone.setText(rb.getString("timezone"));

        logInButton.setText(resourceBundle.getString("login"));
        resetButton.setText(resourceBundle.getString("reset"));
        exitButton.setText(resourceBundle.getString("exit"));



    }

    /**
     * Lambda function takes two strings and returns a string for logging of successfully login attempt
     */
    LogHelper goodlog=(s1, s2)-> {
        return "User " + s1 + " successfully logged in at " + s2 + "\n";
    };

    /**
     * Lambda function takes two strings and returns a string for logging of a failed login attempt
     */
    LogHelper badLog=(s1, s2)->{
        return "User " + s1 + " gave invalid log in at " + s2 + "\n";
    };

    private void displayAlert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {

            case 1:
                alertError.setTitle(resourceBundle.getString("error"));
                alertError.setHeaderText(resourceBundle.getString("login_failure"));
                alertError.showAndWait();
                break;

            case 2:
                alertError.setTitle("Erreur");
                alertError.setHeaderText("Nom d'utilisateur ou mot de passe incorrect. Veuillez réessayer");
                alertError.showAndWait();
                break;

        }
    }
}
