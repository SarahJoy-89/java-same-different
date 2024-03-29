package model;

import Database.Query;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

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

    private Timestamp startDB;
    private Timestamp endDB;


    /**
     * Take data pulled from the database courtesy of the Query class and converts it to an Appointment
     * for ease of use in populating views, working with times, etc. Uses private method to convert the
     * database's UTC datetime (wrapped in a Timestamp) to time in the same timezone as the user.
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
        start = convertTStoZDT(s);
        end = convertTStoZDT(e);
        customer = cu;
        user = u;
        contact = con;

        startLocal = convertZDTtoLocal(start);
        endLocal = convertZDTtoLocal(end);

        // keep these values just in case
        startDB = s;
        endDB = e;
    }

    /**
     * Default constructor
     * Creates an object with most data members
     * initialized to 0 or null string
     */
    public Appointment() {
        appointment_ID = 0;
        title = "";
        description = "";
        location = "";
        type = "";
        customer = 0;
        user = 0;

    }

    /**
     * Constructor for use with Reports view. Accepts a smaller number of initial fields for display.
     * @param id
     * @param ti
     * @param ty
     * @param d
     * @param s
     * @param e
     * @param cus
     */
    public Appointment(int id, String ti, String ty, String d, Timestamp s, Timestamp e, int cus) {
        appointment_ID =id;
        title = ti;
        type = ty;
        description = d;
        start = convertTStoZDT(s);
        end = convertTStoZDT(e);
        customer = cus;

        startLocal = convertZDTtoLocal(start);
        endLocal = convertZDTtoLocal(end);

        // I don't think it matters for this one
        startDB = s;
        endDB = e;
    }

    /**
     * Returns the Appointment ID
     * @return
     */
    public int getAppointment_ID() {
        return appointment_ID;
    }

    /**
     * Returns String containing the Appointment's title
     * @return
     */
    public String getTitle(){
        return title;
    }

    /**
     * Returns a String containing the Appointment's description
     * @return
     */
    public String getDescription(){
        return description;
    }

    /**
     * Returns a String containing the Appointment's location
     * @return
     */
    public String getLocation(){
        return location;
    }

    /**
     * Returns a String containing Appointment's type
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Returns ZonedDateTime corresponding to appointment's start time. This will be the same time as in the database
     * @return
     */
    public ZonedDateTime getStart(){
        return start;
    }

    /**
     * Returns ZonedDateTime corresponding to appointment's end time
     * @return
     */
    public ZonedDateTime getEnd(){
        return end;
    }

    /**
     * Return Customer ID of Appointment
     * @return
     */
    public int getCustomer(){
        return customer;
    }

    /**
     * Returns User ID of Appointment
     * @return
     */
    public int getUser() {
        return user;
    }

    /**
     * Return String for Contact of Appointment
     * @return
     */
    public String getContact() {
        return contact;
    }

    /**
     * Returns the LocalDateTime of the Appointment. This time will be adjusted to the time zone of the user running the application.
     * @return
     */
    public LocalDateTime getStartLocal() { return startLocal; }

    /**
     * Returns the LocalDateTime of the Appointment. This time will be adjusted to the time zone of the user running the applications.
     * @return
     */
    public LocalDateTime getEndLocal() {return endLocal; }

    /**
     * Returns Timestamp form of the start time for writing to Database
     * @return
     */
    public Timestamp getStartDB() {
        return startDB;
    }

    /**
     * Returns Timestamp form of the end time for writing to Database
     * @return
     */
    public Timestamp getEndDB() {
        return endDB;
    }

    /**
     * Sets the Appointment ID of the appointment
     * @param id
     */
    public void setAppointment_ID(int id) {
        appointment_ID = id;
    }

    /**
     * Sets the title of the Appointment
     * @param t
     */
    public void setTitle(String t) {
        title = t;
    }

    /**
     * Sets the description of the Appointment
     * @param des
     */
    public void setDescription(String des) {
        description = des;
    }

    /**
     * Set location of the Appointment
     * @param loc
     */
    public void setLocation(String loc) {
        location = loc;
    }

    /**
     * Set the type of the Appointment
     * @param t
     */
    public void setType(String t) {
        type = t;
    }

    /**
     * Set UserID of the Appointment
     * @param u
     */
    public void setUser(int u) {
        user = u;
    }

    /**
     * Set Contact of the Appointment
     * @param c
     */
    public void setContact(String c) {
        contact = c;
    }

    /**
     * Sets the customer ID of the Appointment.
     * @param cu
     */
    public void setCustomer(int cu) {
        customer = cu;
    }

    /**
     * Sets the start times in Appointment object
     * to local date time, UTC time, and to SQL datetime in UTC
     * @param ldt LocalDateTime to convert and set
     */
    public void setStart(LocalDateTime ldt) {
        startLocal = ldt;
        start = convertYourLocaltoUTC(startLocal);
        startDB = Timestamp.valueOf(start.toLocalDateTime());
    }

    /**
     * Sets the startDB time, based on a SQL datetime value.
     * Converts that time to ZonedDateTime and to local time
     * to set those corresponding data members
     * @param ts SQL datetime value
     */
    public void setStartDB(Timestamp ts) {
        startDB =  ts;
        start = convertTStoZDT(startDB);
        startLocal = convertZDTtoLocal(start);
    }

    /**
     * Sets end time based on local time. Converts that time to UTC.
     * From UTC it's able to set the zoned time member and the
     * SQL datetime for writing the object to the database
     * @param ldt Local end time
     */
    public void setEnd(LocalDateTime ldt) {
        endLocal = ldt;
        end = convertYourLocaltoUTC(endLocal);
        endDB = Timestamp.valueOf(end.toLocalDateTime());
    }

    /**
     * Converts input to the equivalent local time (e.g. UTC time to American/Mountain time)
     * @param zdt This is presumed to be UTC straight from the database
     * @return LocalDateTime in the timezone of the user who is running the program
     */
    private LocalDateTime convertZDTtoLocal(ZonedDateTime zdt) {
        return zdt.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString())).toLocalDateTime();
    }

    private ZonedDateTime convertTStoZDT(Timestamp ts) {
        return ZonedDateTime.of(ts.toLocalDateTime(), ZoneId.of("UTC"));
    }

    private ZonedDateTime convertYourLocaltoUTC(LocalDateTime yourLocal) {
        // first get a Zoned Date Time in the local timezone
        ZonedDateTime zdtLocal = ZonedDateTime.of(yourLocal, ZoneId.systemDefault());
        return zdtLocal.withZoneSameInstant(ZoneOffset.UTC);
    }


    /**
     * Based on created object,does a quick logical check to see
     * if appointment start time is within office hours based on
     * EST. Also checks if appointment end time is set after
     * close of business hours.
     * @return true if appointment time falls within office hours
     */
    public boolean isDuringOfficeHours() {

        int easternStart = start.withZoneSameInstant(ZoneId.of("America/New_York")).getHour();
        int easternEnd = end.withZoneSameInstant(ZoneId.of("America/New_York")).getHour();

        System.out.println("Start: " + easternStart +", End: " + easternEnd);
        // if the appointment start time is before 8 AM EST or after 10 for some reason
        if (easternStart < 8 || easternStart > 22) {
            return false;
        }
        // or if the appointment end time is after 10 PM EST
        if (easternEnd > 22) {
            return false;
        }
        return true;
    }

    /**
     * Runs a comparison in the database to see if the appointment
     * has any conflicts with existing appointments.
     * @return true if there there is a conflict
     */
    public boolean hasConflict() {

        return Query.checkForMeetings(appointment_ID, startDB, endDB);
    }

}
