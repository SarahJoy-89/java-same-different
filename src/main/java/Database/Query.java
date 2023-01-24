package Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static Database.DBConnection.conn;

public class Query {
    private static String query;
    private static Statement statement;
    private static ResultSet rs;

    /**
     * Helper function that takes a string for username and returns the user_id value
     * from the database
     *
     * @param username
     * @return int
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
            System.err.println(sqle);
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
            System.err.print(sqle);
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
            System.err.print(sqle);
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
            System.err.println(sqle);
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
            System.err.println(sqle);
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
            System.err.println(sqle);
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
            System.err.println(sqle);
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
            System.err.println(sqle);
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
            System.err.println(sqle);
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
            System.err.println(sqle);
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
            System.err.println(sqle);
        }
        return country;
    }
}
