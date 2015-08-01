package com.example.arafathossain.smsreceiver;

/**
 * Created by Arafat Hossain on 8/1/2015.
 */
public class SMS {
    private String id;
    private String from;
    private String body;
    private String time;

    public SMS(String from, String body, String time) {
        this.from = from;
        this.body = body;
        this.time = time;
    }

    public SMS(String id, String from, String body, String time) {
        this.id = id;
        this.from = from;
        this.body = body;
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public String getBody() {
        return body;
    }

    public String getTime() {
        return time;
    }

    public String getId() {
        return id;
    }
}
