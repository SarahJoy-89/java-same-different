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
    private int divisionID;

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

    public int getDivisionID() {
        return divisionID;
    }

    public void setCountry(String fld) {
        country = Query.getCountry(fld);
    }

    public void setCustomerName(String name) {
        customerName = name;
    }

    public void setAddress(String addy) {
        address = addy;
    }

    public void setPostalCode(String pc) {
        postalCode = pc;
    }

    public void setPhoneNumber(String pn) {
        phoneNumber = pn;
    }

    public void setFirstLevelDivision(String fld) {
        firstLevelDivision = fld;
        divisionID = Query.getDivisionID(fld);
    }
}
