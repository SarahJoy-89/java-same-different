package model;

import Database.Query;

import java.sql.Time;
import java.sql.Timestamp;

public class Customer {
    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private String firstLevelDivision;
    private String country = "";

    /**
     * Constructor taking on all values necessary to build a full Customer object
     * @param ci Customer ID
     * @param cn Customer Name
     * @param ad Address
     * @param pc Postal Code
     * @param pn Phone Number
     * @param di First Level Division
     */
    public Customer(int ci, String cn, String ad, String pc, String pn, String di) {
        customerID = ci;
        customerName = cn;
        address = ad;
        postalCode = pc;
        phoneNumber = pn;
        firstLevelDivision = di;

    }

    /**
     * Constructor taking values for use in report
     * @param ci Customer ID
     * @param cn Customer name
     * @param ad Address
     * @param pc Postal Code
     * @param di First Level Division
     */
    public Customer(int ci, String cn, String ad, String pc, String di) {
        customerID = ci;
        customerName = cn;
        address = ad;
        postalCode = pc;
        firstLevelDivision = di;
    }
    /**
     * Default constructor. Initializes with empty data members.
     */
    public Customer() {
        customerName = "";
        address = "";
        postalCode = "";
        phoneNumber = "";
        firstLevelDivision = "";
    }

    /**
     * return value of Customer ID
     * @return int Customer ID
     */
    public int getCustomer_ID() {
        return customerID;
    }

    /**
     * Return string containing the address
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns String containing the customer name
     * @return customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Returns String containing the postal code
     * @return postal code
     */
    public String getPostalCode(){
        return postalCode;
    }

    /**
     * Returns String containing the customer's phone number
     * @return phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Returns String containing firstLevelDivision
     * @return
     */
    public String getFirstLevelDivision() {
        return firstLevelDivision;
    }

    /**
     * Returns String containing the country for the Customer. If the county field is blank, this method sets it using the firstLevelDivision
     * @return country
     */
    public String getCountry() {
        if (country.isEmpty()) {
            setCountry(firstLevelDivision);
        }

        return country;
    }

    /**
     * Sets int corresponding to Customer ID
     * @param id
     */
    public void setCustomerID(int id) {
        customerID = id;
    }

    /**
     * Sets country based on the firstLevelDivision
     * @param fld Fisrt Level Division
     */
    public void setCountry(String fld) {
        country = Query.getCountry(fld);
    }

    /**
     * Sets Customer name
     * @param name
     */
    public void setCustomerName(String name) {
        customerName = name;
    }

    /**
     * Set Customer address
     * @param addy
     */
    public void setAddress(String addy) {
        address = addy;
    }

    /**
     * Set Postal Code
     * @param pc
     */
    public void setPostalCode(String pc) {
        postalCode = pc;
    }

    /**
     * Set Customer Phone Number
     * @param pn
     */
    public void setPhoneNumber(String pn) {
        phoneNumber = pn;
    }

    /**
     * Set Customer First Level Division
     * @param fld
     */
    public void setFirstLevelDivision(String fld) {
        firstLevelDivision = fld;
    }
}
