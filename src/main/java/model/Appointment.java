package model;

import javax.security.auth.login.FailedLoginException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class Appointment {
    private int appointment_ID;
    private String title;
    private String description;
    private String location;
    private String type;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private String customer;
    private String user;
    private String contact;

    public Appointment(int ai, String tt, String d, String l, String ty, Date s, Date e, String cu, String u, String con) {
        appointment_ID = ai;
        title = tt;
        description = d;
        location = l;
        type = ty;
        start = ZonedDateTime.ofInstant(s.toInstant(), ZoneId.of("UTC"));
        end = ZonedDateTime.ofInstant(e.toInstant(), ZoneId.of("UTC"));
        customer = cu;
        user = u;
        contact = con;
    }

    public int getAppointment_ID() {
        return appointment_ID;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public String getLocation(){
        return location;
    }

    public String getType() {
        return type;
    }

    public ZonedDateTime getStart(){
        return start;
    }

    public ZonedDateTime getEnd(){
        return end;
    }

    public String getCustomer(){
        return customer;
    }

    public String getUser() {
        return user;
    }

    public String getContact() {
        return contact;
    }


}
