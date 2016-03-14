package com.hexade.borntoparty.main.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by himanshusoni on 3/13/16.
 */
public class Event {
    private String _id;
    private String name;
    private String forUser;
    private String createdBy;
    private Long eventDate;
    private Date eDate;
    private String location;
    private HashMap<String,String> inviteStatus;
    private ArrayList<String> activities;
//    private Discussion discussion;

    public String getEventId() {
        return _id;
    }

    public void setEventId(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getForUser() {
        return forUser;
    }

    public void setForUser(String forUser) {
        this.forUser = forUser;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Long getEventDate() {
        return eventDate;
    }

    public void setEventDate(Long eventDate) {
        this.eventDate = eventDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public HashMap<String, String> getInviteStatus() {
        return inviteStatus;
    }

    public void setInviteStatus(HashMap<String, String> inviteStatus) {
        this.inviteStatus = inviteStatus;
    }

    public ArrayList<String> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<String> activities) {
        this.activities = activities;
    }

    public Date geteDate() {
        return eDate;
    }

    public void seteDate(Date eDate) {
        this.eDate = new Date(this.eventDate * 1000);;
    }

    public String getFormattedEventDate(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(eDate);
        return new SimpleDateFormat("MMMM d").format(cal.getTime());
    }
}
