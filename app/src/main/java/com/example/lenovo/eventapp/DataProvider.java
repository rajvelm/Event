package com.example.lenovo.eventapp;

/**
 * Created by lenovo on 8/26/2016.
 */
public class DataProvider {

    private int Id;
    private String eventName,eventDesc,eventDate,eventCreatedBy;
    private float eventRating;
    private String eventUrl;

    public DataProvider(int id, String eventName,String eventCreatedBy,float eventRating, String eventUrl, String eventDesc, String eventDate) {
        Id = id;
        this.eventName = eventName;
        this.eventRating = eventRating;
        this.eventUrl = eventUrl;
        this.eventDesc = eventDesc;
        this.eventDate = eventDate;
        this.eventCreatedBy = eventCreatedBy;

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getEventName() {
        return eventName;
    }
    public float getEventRating() {
        return eventRating;
    }

    public void setEventRating(float eventRating) {
        this.eventRating = eventRating;
    }

    public String getEventUrl() {
        return eventUrl;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }


    public void setEventUrl(String eventUrl) {
        this.eventUrl = eventUrl;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventCreatedBy() {
        return eventCreatedBy;
    }

    public void setEventCreatedBy(String eventCreatedBy) {
        this.eventCreatedBy = eventCreatedBy;
    }
}
