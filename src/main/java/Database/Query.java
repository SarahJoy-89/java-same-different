package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import model.Appointment;
import model.Customer;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static Database.DBConnection.conn;

public class Query {
    private static String query;
    private static Statement statement;
    private static ResultSet rs;

    /**
     * Helper function that takes a string for username and returns the user_id value
     * from the database
     *
     * @param username This is like a user's name
     * @return int This will be the User ID
     */
    public static int getUserID(String username) {
        int i = 0;
        query = "SELECT User_ID from USERS where User_Name = \"" + username + "\"";

        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            // increment pointer one line!
            if (rs.next()) {
                i = rs.getInt("User_ID");
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return i;
    }


    /**
     * Takes an integer corresponding to a user ID and checks if it matches with the password
     * on file in the database.
     * @param user_id User ID
     * @param pword the Password
     * @return True if password is correct/matches, else return False
     */
    public static boolean checkPassword(int user_id, String pword) {
        query = "SELECT User_ID, Password from USERS where User_ID = " + user_id;

        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(query);

            if (rs.next()) {
                if (rs.getString(2).equals(pword)) {
                    return true;
                } else
                    return false;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return false;
    }

    /**
     * Uses some time logic to see if there is an appointment beginning within 15 minutes of
     * a successful log in. Returns the relevant Appointment object if a match. If no match,
     * returns null.
     * @return Appointment object occuring withing 15 minutes of login (or null if none)
     */
    public static Appointment appointmentSoon() {
        // Get the time right now, but in UTC
        LocalDateTime rightNowUTC = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
        LocalDateTime inFifteenMinutes = rightNowUTC.plusMinutes(15);

        Timestamp loginTime = Timestamp.valueOf(rightNowUTC);
        Timestamp thoon = Timestamp.valueOf(inFifteenMinutes);

        query = "SELECT Appointment_ID, Start from appointments WHERE Start BETWEEN \"" + loginTime + "\" AND \"" + thoon + "\"";

        Appointment appointment = new Appointment();

        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            // check to see if you get a result!
            if (rs.next()) {
                appointment.setAppointment_ID(rs.getInt(1));
                appointment.setStartDB(rs.getTimestamp(2));
                return appointment;
            } else
                return null;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return appointment;

    }

    /**
     * Deletes a customer from the database based on the ID of that customer.
     * @param customerID Integer ID of customer
     */
    public static void deleteCustomer(int customerID) {
        query = "DELETE from CUSTOMERS where Customer_ID=" + customerID;

        try {
            statement = conn.createStatement();
            // nothing to return so not gathering a return value
            // just execute and delete!
            statement.executeUpdate(query);

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     * Takes a customer object created in client code, based on an existing customer record.
     * Updates fields based on the data members of the object, matches to an existing
     * customer ID in the customers database.
     * @param customer Customer object containing updated data to be written
     */
    public static void updateCustomer(Customer customer) {
        query = "UPDATE customers \n" +
                "SET customer_name=\"" + customer.getCustomerName() + "\", address=\"" + customer.getAddress() +
                "\", postal_code=\"" + customer.getPostalCode() + "\", phone=\"" + customer.getPhoneNumber() +
                "\", division_id= (SELECT division_ID from first_level_divisions WHERE first_level_divisions.division=\"" + customer.getFirstLevelDivision() + "\") " +
                "\nWHERE Customer_ID=" + customer.getCustomer_ID();

        try {
            statement = conn.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

    }

    /**
     * Adds a new customer to the database. Customer ID is auto-generated by the
     * Java SQL API
     * @param customer Customer object containing data to be added
     */
    public static void addCustomer(Customer customer) {
        query = "INSERT INTO customers (customer_name, address, postal_code, phone, division_ID)" +
                "VALUES (\"" + customer.getCustomerName() + "\", \"" + customer.getAddress() + "\", \""
                + customer.getPostalCode() + "\", \"" + customer.getPhoneNumber() + "\", (SELECT division_ID from first_level_divisions WHERE first_level_divisions.division=\"" + customer.getFirstLevelDivision() + "\") )";

        try {
            statement = conn.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     * Updates an existing entry in the appointment database based on the appointment ID
     * of the appoinment (must be already existing)
     * @param appointment Apointment object carrying data to be updated
     */
    public static void updateAppointment(Appointment appointment) {
        query = "UPDATE appointments \n" +
                "SET Title=\"" + appointment.getTitle() + "\", Description=\"" + appointment.getDescription() + "\", Location=\"" +
                appointment.getLocation() + "\", Type=\"" + appointment.getType() + "\", Start=\"" + appointment.getStartDB() + "\", End=\"" +
                appointment.getEndDB() + "\", Customer_ID=" + appointment.getCustomer() + ", User_ID=" + appointment.getUser() +
                ", Contact_ID=(SELECT Contact_ID from contacts WHERE contacts.Contact_Name=\"" + appointment.getContact() + "\") \n" +
                "WHERE Appointment_ID=" + appointment.getAppointment_ID();

        try {
            statement = conn.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }

    /**
     * Adds a new appointment to the appointments table of the database,
     * based on the data in an Appointment object
     * @param appointment Appointment to add to database
     */
    public static void addAppointment(Appointment appointment) {
        query = "INSERT into appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) " +
                "VALUES (\"" + appointment.getTitle() + "\", \"" + appointment.getDescription() + "\", \"" + appointment.getLocation() + "\", \"" + appointment.getType() + "\", \"" +
                appointment.getStartDB() + "\", \"" + appointment.getEndDB() + "\", " + appointment.getCustomer() + ", " + appointment.getUser() +
                ", (SELECT Contact_ID from contacts WHERE contacts.Contact_Name=\"" + appointment.getContact() + "\") )";


        try {
            statement = conn.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     * Used to return the list of countries from the country table to
     * populate a combo box
     *
     * @return ResultSet The set of countries
     */
    public static ObservableList<String> getCountries() {
        ObservableList<String> allCountries = FXCollections.observableArrayList();
        query = "SELECT Country from Countries";
        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                allCountries.add(rs.getString(1));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return allCountries;
    }

    /**
     * This method is used to populate combo box for first level divisions
     *
     * @param countryName Name from Database of a country
     * @return ResultSet Set of all first level division areas in that country
     */
    public static ResultSet getFLD(String countryName) {
        query = "SELECT first_level_divisions.Division from first_level_divisions, countries "
                + "WHERE first_level_divisions.Country_ID=countries.Country_ID AND countries.Country=\"" + countryName +"\"";
        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return rs;
    }

    /**
     * Deletes all appointments connected to a customer ID
     *
     * @param customerID The ID of a Customer about to be deleted
     */
    public static void deleteAppointments(int customerID) {
        query = "DELETE from appointments WHERE Customer_ID=" + customerID;
        try {
            statement = conn.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     * Deletes a single appointment based on the appointment ID field
     * @param appointmentID the ID of the appointment to be deleted
     */
    public static void deleteSingleAppointment(int appointmentID) {
        query = "DELETE from appointments WHERE Appointment_ID=" + appointmentID;
        try {
            statement = conn.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /**
     * Returns a String for the country name fetched from Countries table
     * @param countryID foreign key
     * @return name of country
     */
    public static String getCountry(int countryID) {
        String country = "";
        query = "SELECT from countries WHERE Country_ID=" + countryID;
        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            country = rs.getString(2);
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        }
        return country;
    }

    /**
     * Returns country ID values that corresponds to string value in country database
     * @param country name of country
     * @return country ID value from database
     */
    public static int getCountryID(String country) {
        int countryID = 0;
        query = "SELECT country_ID from countries WHERE country=\"" + country + "\"";
        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            countryID = rs.getInt(1);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return countryID;
    }

    /**
     * Retuns the name of a country from the countries database, based on the
     * first-level division ID that corresponds to the foreign key
     * @param fld first-level division that corresponds to a country
     * @return String of the country name
     */
    public static String getCountry(String fld) {
        String country = "";
        query = "SELECT countries.Country from countries, first_level_divisions WHERE countries.Country_ID=first_level_divisions.Country_ID AND Division=\"" + fld + "\"";

        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            country = rs.getString(1);
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        }
        return country;
    }

    /**
     * Populates and returns a reference to an Observable List object for
     * the purposes of populating cells in a Table. Also pulls foreign key data
     * from associated database tables
     * @return ObservableList containing all customer data
     */
    public static ObservableList<Customer> getCustomers() {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        query = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, "
        + "customers.Phone, first_level_divisions.Division from customers, first_level_divisions WHERE "
        + "customers.Division_ID=first_level_divisions.Division_ID";

        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                allCustomers.add(new Customer(rs.getInt(1), rs.getString(2),rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6)));
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return allCustomers;
    }

    /**
     * Creates an Observalbe List of customers sorted out by their country
     * @param countryID corresponds to country ID in Database
     * @return ObservableList of customers that meet criteria
     */
    public static ObservableList<Customer> getCustomersByCountryID(int countryID) {
        ObservableList<Customer> someCustomers = FXCollections.observableArrayList();

        query = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, first_level_divisions.Division FROM customers, first_level_divisions WHERE customers.Division_ID=first_level_divisions.Division_ID\n" +
                "AND first_level_divisions.Country_ID=" + countryID;

        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                someCustomers.add(new Customer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));

            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return someCustomers;
    }

    /**
     * Populates and returns an ObservableList full of all appointments in the appointment data
     * as well as data from other tables associated with the foreign keys
     * @return ObservableList with all appointments
     */
    public static ObservableList<Appointment> getAppointments() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        query = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, contacts.Contact_Name, " +
                "appointments.Type,"
                + "appointments.Start, appointments.End, appointments.Customer_ID, appointments.User_ID from appointments, "
                + "contacts WHERE appointments.Contact_ID=contacts.Contact_ID";

        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                allAppointments.add(new Appointment(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getTimestamp(7),
                        rs.getTimestamp(8), rs.getInt(9), rs.getInt(10)));
            }
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return allAppointments;
    }

    /**
     * Queries database to return all appointments occurring within 7 days of the time of the query.
     * Used to populate MainTable after radio button for week is pressed
     * @return ObservableList<Appointment>
     */
    public static ObservableList<Appointment> getWeekAppointments() {
        ObservableList<Appointment> weekAppointments = FXCollections.observableArrayList();
        LocalDateTime rightNowUTC = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
        LocalDateTime oneWeekOut = rightNowUTC.plusDays(7);

        Timestamp todayNow = Timestamp.valueOf(rightNowUTC);
        Timestamp week = Timestamp.valueOf(oneWeekOut);




        query = "SELECT Appointment_ID, Title, Description, Location, (SELECT Contact_Name FROM contacts WHERE Contact_ID=appointments.Contact_ID), "
                + "Type, Start, End, Customer_ID, User_ID  FROM appointments WHERE Start BETWEEN \"" + todayNow + "\" AND \"" + week + "\"";


        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(query);

            while(rs.next()) {
                weekAppointments.add(new Appointment(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getTimestamp(7),
                        rs.getTimestamp(8), rs.getInt(9), rs.getInt(10)));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return weekAppointments;
    }

    /**
     * Queries database to return an ObservableList of all the appointments in the current month
     * @return ObservableList<Appointment>
     */
    public static ObservableList<Appointment> getMonthAppointments() {
        ObservableList<Appointment> monthAppointments = FXCollections.observableArrayList();
        query = "SELECT Appointment_ID, Title, Description, Location, (SELECT Contact_Name FROM contacts WHERE Contact_ID=appointments.Contact_ID), "
                + "Type, Start, End, Customer_ID, User_ID  FROM appointments WHERE month(Start)=month(current_date())";

        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(query);

            while(rs.next()) {
                monthAppointments.add(new Appointment(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getTimestamp(7),
                        rs.getTimestamp(8), rs.getInt(9), rs.getInt(10)));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return monthAppointments;
    }

    /**
     * Returns an Observable list containing all Contact names from the contacts database.
     * Used to populate a ComboBox in the UI
     * @return ObservableList of all contact names
     */
    public static ObservableList<String> getContacts() {
        ObservableList<String> allContacts = FXCollections.observableArrayList();
        query = "SELECT Contact_Name from contacts";

        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            while(rs.next()) {
                allContacts.add(rs.getString(1));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return allContacts;
    }

    /**
     * Takes a SQL Timestamp (must convert to UTC first for compatibility) to check if the meeting
     * conflicts with any other scheduled meeting. Returns true for a conflict, false for not conflict.
     * @param appointmentID
     * @param startTime UTC equivalent start time
     * @param endTime UTC equivalent end time
     * @return true if there is a conflict, false if no conflicts
     */
    public static boolean checkForMeetings(int appointmentID, Timestamp startTime, Timestamp endTime) {
        query = "SELECT * from appointments WHERE Start BETWEEN \"" + startTime + "\" AND \"" + endTime + "\"";
        String query2 = "SELECT * from appointments WHERE End BETWEEN \"" + startTime + "\" AND \"" + endTime + "\"";
        String query3 = "SELECT * from appointments WHERE Start=\"" + startTime + "\"";



        try {

            statement = conn.createStatement();
            rs = statement.executeQuery(query3);

            if (rs.next()) {
                if (rs.getInt(1) == appointmentID) {
                    return false;
                } else
                    return true;
            }
            rs = statement.executeQuery(query2);

            if (rs.next())
            {
                return true;
            }

            rs = statement.executeQuery(query);
            if (rs.next()) {
                return true;
            }

            return false;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return true;
    }

    /**
     * Queries appointemnts table and gets unique types, returns as ObservableList
     * @return ObservableList<String> allTypes
     */
    public static ObservableList<String> getTypes() {
        ObservableList<String> allTypes = FXCollections.observableArrayList();
        query = "SELECT DISTINCT Type FROM Appointments";

        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()) {
                allTypes.add(rs.getString(1));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return allTypes;
    }

    /**
     * Query the Start times of the appointment database and return an ObservableList of
     * distinct month names to populate a combobox in the reports
     * @return ObservableList Months
     */
    public static ObservableList<String> getMonthNames() {
        ObservableList<String> months = FXCollections.observableArrayList();
        query = "SELECT distinct monthname(Start) from appointments";

        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()) {
                months.add(rs.getString(1));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return months;
    }

    /**
     * Pass in the name of a contact to return a list of all appointments for that contact.
     * Used to create a schedule for each contact in the comapny
     * @param contact name of company contact
     * @return ObservableList of appointments
     */
    public static ObservableList<Appointment> getAppointmentsByContact(String contact) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        query = "SELECT Appointment_ID, Title, Type, Description, Start, End, Customer_ID FROM appointments, contacts WHERE appointments.Contact_ID=contacts.Contact_ID AND contacts.Contact_Name="
                + "\"" + contact + "\"";

        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            while(rs.next()) {
                appointments.add(new Appointment(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getTimestamp(5), rs.getTimestamp(6), rs.getInt(7)));
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return appointments;
    }

    /**
     * Takes appointment "type" and a given month and returns the count of appointments that meet those two criteria
     * @param type Appointment's "type" field
     * @param month calendar month of year
     * @return total count of type in that month
     */
    public static int getTypeCount(String type, int month) {
        int count = 0;
        query = "SELECT COUNT(*) FROM appointments WHERE Type=\"" +type + "\" and month(Start)=" + month;

        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            count = rs.getInt(1);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return count;
    }


}
