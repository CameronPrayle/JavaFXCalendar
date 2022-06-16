package com.example.calendertest2;

public class BirthdayMarker {
    String name;
    String day;
    String month;
    String year;
    String colour;
    boolean notification;
    boolean gift;

    public BirthdayMarker(String name, String day, String month, String year, String colour, boolean notification, boolean gift) {
        this.name = name;
        this.day = day;
        this.month = month;
        this.year = year;
        this.colour = colour;
        this.notification = notification;
        this.gift = gift;
    }
}
