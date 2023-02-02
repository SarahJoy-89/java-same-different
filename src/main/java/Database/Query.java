package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public static int getContactID(String username) {
        int i = 0;
        query = "SELECT User_ID from USERS where User_Name = \"" + username + "\"";

        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            // increment pointer one line!
            rs.next();
            i = rs.getInt("User_ID");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return i;
    }

    public static String getContactName(int id) {
        String s = "";
        query = "SELECT User_Name from USERS where User_ID = " + id;

        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            s = rs.getString(1);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return s;
    }

    public static String getCustomerName(int id) {
        String s = "";
        query = "SELECT Customer_Name from CUSTOMERS where Customer_ID = " + id;

        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            s = rs.getString(1);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return s;
    }

    public static int getCustomerID(String cust_name) {
        int id = 0;
        query = "SELECT Customer_ID from CUSTOMERS where Customer_Name = \"" + cust_name + "\"";

        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            id = rs.getInt(1);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return id;
    }

    public static boolean checkPassword(int user_id, String pword) {
        query = "SELECT User_ID, Password from USERS where User_ID = " + user_id;

        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            rs.next();
            if (rs.getString(2).equals(pword)) {
                return true;
            } else
                return false;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        // shouldn't ever get here
        // but need a return value to satisfy Java
        return false;
    }

    public static void deleteCustomer(int customerID) {
        query = "DELETE from CUSTOMERS where Customer_ID=" + customerID;

        try {
            statement = conn.createStatement();
            // nothing to return so not gathering a return value
            // just execute and delete!
            statement.executeQuery(query);

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public static void addCustomer(String custName, String address, String postCode, String phNumber, String fld, String country) {

        int key = 0;
        query = "INSERT into Customers (" + custName + ", " + address + ", " + phNumber + ", " + fld + ", " + country + ")";
        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            // key = rs.ge

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
    public static ResultSet getCountries() {
        query = "SELECT Country from Countries";
        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return rs;
    }

    /**
     * This method is used to populate combo box for first level divisions
     *
     * @param countryID ID from Database of a country
     * @return ResultSet Set of all first level division areas in that country
     */
    public static ResultSet getFLD(int countryID) {
        query = "SELECT Division from first_level_divisions WHERE CountryID=\"" + countryID + "\"";
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
            statement.executeQuery(query);
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
}
