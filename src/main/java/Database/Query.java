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
}
