package model;

import javax.security.auth.login.FailedLoginException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class Appointment {
    private int appointment_ID;
    private String title;
    private String description;
    private String location;
    private String type;
    private Date start;
    private Date end;
    private Timestamp create_date;
    private String created_by;
    private Timestamp update_date;
    private String updated_by;
    private int customer_ID;
    private int user_ID;
    private int contact_ID;

    public Appointment(int ai, String tt, String d, String l, String ty, Date s, Date e, Timestamp cd, String cb, Timestamp ud, String ub, int cui, int ui, int coi) {
        appointment_ID = ai;
        title = tt;
        description = d;
        location = l;
        type = ty;
        start = s;
        end = e;
        create_date = cd;
        created_by = cb;
        update_date = ud;
        updated_by = ub;
        customer_ID = cui;
        user_ID = ui;
        contact_ID = coi;
    }
}
