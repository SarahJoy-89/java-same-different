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

    public Customer(int ci, String cn, String ad, String pc, String pn, String di) {
        customerID = ci;
        customerName = cn;
        address = ad;
        postalCode = pc;
        phoneNumber = pn;
        firstLevelDivision = di;

    }

    public int getCustomer_ID() {
        return customerID;
    }

    public String getAddress() {
        return address;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPostalCode(){
        return postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFirstLevelDivision() {
        return firstLevelDivision;
    }

    public String getCountry() {
        if (country.isEmpty()) {
            setCountry(firstLevelDivision);
        }

        return country;
    }

    public void setCountry(String fld) {
        country = Query.getCountry(fld);
    }
}
