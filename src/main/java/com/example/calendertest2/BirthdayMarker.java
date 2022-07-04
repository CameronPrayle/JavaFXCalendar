package com.example.calendertest2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public void addMarkerToFile(){
        try {
            FileWriter fw = new FileWriter("BDayMarker.csv",true);
            fw.write(name +","+ day +","+ month +","+ year +","+ colour +","+ notification +","+ gift+ "\n");
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
