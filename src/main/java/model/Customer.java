package model;

import java.sql.Time;
import java.sql.Timestamp;

public class Customer {
    private int customer_ID;
    private String customer_name;
    private String address;
    private String postal_code;
    private String phone_number;
    private Timestamp create_date;
    private String created_by;
    private Timestamp last_update;
    private String update_by;
    private int division_ID;

    public Customer(int ci, String cn, String ad, String pc, String pn, Timestamp cd, String cb, Timestamp lu, String ub, int di) {
        customer_ID = ci;
        customer_name = cn;
        address = ad;
        postal_code = pc;
        phone_number = pn;
        create_date = cd;
        created_by = cb;
        last_update = lu;
        update_by = ub;
        division_ID = di;

    }

    public int getCustomer_ID() {
        return customer_ID;
    }

    public String getAddress() {
        return address;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setLast_update(Timestamp ts) {
        // So this probably won't work, is likely going to need some
        // sort of work done in the DB classes
        last_update = ts;
    }

    public void setUpdate_by(String updater) {
        update_by = updater;
    }
}
