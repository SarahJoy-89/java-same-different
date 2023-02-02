package model;

import javax.security.auth.login.FailedLoginException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.sql.Date;

public class Appointment {
    private int appointment_ID;
    private String title;
    private String description;
    private String location;
    private String type;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private int customer;
    private int user;
    private String contact;

    private LocalDateTime startLocal;
    private LocalDateTime endLocal;

    /**
     * Take data pulled from the database courtesy of the Query class and converts it to an Appointment
     * for ease of use in populating views, working with times, etc. Uses private method to convert the
     * database's UTC datetime (wrapped in a Timestamp) to time in the same timezone as the user
     * @param ai appointment ID
     * @param tt title of appointment
     * @param d short description of appointment
     * @param l location of appointment
     * @param con contact for the appointment
     * @param ty type of appointment
     * @param s start time of appointment
     * @param e end time of appointment
     * @param cu customer ID
     * @param u user ID
     */
    public Appointment(int ai, String tt, String d, String l, String con, String ty, Timestamp s, Timestamp e, int cu, int u) {
        appointment_ID = ai;
        title = tt;
        description = d;
        location = l;
        type = ty;
        // The times in the DB are in UTC so these ZonedDateTime members are set to that
        start = ZonedDateTime.of(s.toLocalDateTime(), ZoneId.of("UTC"));
        end = ZonedDateTime.of(e.toLocalDateTime(), ZoneId.of("UTC"));
        customer = cu;
        user = u;
        contact = con;

        startLocal = convertZDTtoLocal(start);
        endLocal = convertZDTtoLocal(end);
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

    public int getCustomer(){
        return customer;
    }

    public int getUser() {
        return user;
    }

    public String getContact() {
        return contact;
    }

    public LocalDateTime getStartLocal() { return startLocal; }

    public LocalDateTime getEndLocal() {return endLocal; }

    /**
     * Converts input to the equivalent local time (e.g. UTC time to American/Mountain time)
     * @param zdt This is presumed to be UTC straight from the database
     * @return LocalDateTime in the timezone of the user who is running the program
     */
    private LocalDateTime convertZDTtoLocal(ZonedDateTime zdt) {
        return zdt.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString())).toLocalDateTime();
    }

}
