package model;

import Database.Query;

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

    public Appointment() {
        appointment_ID = 0;
        title = "";
        description = "";
        location = "";
        type = "";
        customer = 0;
        user = 0;

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

    public Timestamp getStartDB() {
        return startDB;
    }

    public Timestamp getEndDB() {
        return endDB;
    }

    public void setAppointment_ID(int id) {
        appointment_ID = id;
    }

    public void setTitle(String t) {
        title = t;
    }

    public void setDescription(String des) {
        description = des;
    }

    public void setLocation(String loc) {
        location = loc;
    }

    public void setType(String t) {
        type = t;
    }

    public void setUser(int u) {
        user = u;
    }

    public void setContact(String c) {
        contact = c;
    }

    public void setCustomer(int customer) {
        this.customer = customer;
    }

    public void setStart(LocalDateTime ldt) {
        startLocal = ldt;
        start = convertYourLocaltoUTC(startLocal);
        startDB = Timestamp.valueOf(start.toLocalDateTime());
    }


    public void setStartDB(Timestamp ts) {
        startDB =  ts;
        start = convertTStoZDT(startDB);
        startLocal = convertZDTtoLocal(start);
    }

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



    public boolean isDuringOfficeHours() {

        int easternStart = start.withZoneSameInstant(ZoneId.of("EST")).getHour();
        int easternEnd = end.withZoneSameInstant(ZoneId.of("EST")).getHour();

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

    public boolean hasConflict() {
        return Query.checkForMeetings(startDB, endDB);
    }

}
