package com.example.calendertest2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.*;
import java.awt.TrayIcon.MessageType;
import static com.example.calendertest2.HowManyDaysInTheMonth.howManyDays;

public class BirthdayMarker {
    String name;
    String day;
    String month;
    String year;
    String colour;
    boolean notification;
    boolean gift;
    String giftDesc;


    public BirthdayMarker(String name, String day, String month, String year, String colour, boolean notification, boolean gift, String giftDesc) {
        this.name = name;
        this.day = day;
        this.month = month;
        this.year = year;
        this.colour = colour;
        this.notification = notification;
        this.gift = gift;
        if(giftDesc.equals("")){
            giftDesc = "none";
        }
        this.giftDesc = giftDesc;

        if(this.notification == true){
            checkForNotification();
        }
    }
    public void addMarkerToFile(){
        try {
            FileWriter fw = new FileWriter("BDayMarker.csv",true);
            fw.write(name +","+ day +","+ month +","+ year +","+ colour +","+ notification +","+ gift+","+giftDesc+"\n");
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkForNotification(){
        String[] monthsArray = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul","Aug", "Sep", "Oct", "Nov", "Dec"};
        //Get Date
        Date cDate = new Date();
        //Separate date into current(c) Day, Month, Year
        String[] cDateSplit = cDate.toString().split(" ");
        String cDay = cDateSplit[2];
        String cMonth = cDateSplit[1];
        String cYear = cDateSplit[5];

        String bdayMessage;

        //Getting previous month using array used for setting the day to the last day of the month if the day goes to 0 when going back a week
        String prevMonth = null;
        for(int i=0; i<monthsArray.length; i++){
            if(cMonth.equals(monthsArray[i])){
                try{prevMonth=monthsArray[i-1];}catch(Exception e){prevMonth=monthsArray[monthsArray.length-1];} // Same idea as decrement month, where if monthsArray[i-1] fails then the month will be December
            }
        }

        //Set days to be checked using for loop as if the day goes to 0 it needs to be set to the previous months last day
        int weekBeforeBDay = getDayToBeChecked(Integer.parseInt(day),7, prevMonth);
        int threeDaysBeforeBDay = getDayToBeChecked(Integer.parseInt(day),3, prevMonth);
        int dayBeforeBDay = getDayToBeChecked(Integer.parseInt(day),1, prevMonth);

        // If the day a week before the birthday is the same as the current day and the birthday month is equal to the current month
        if(weekBeforeBDay == Integer.parseInt(cDay) && month.equals(cMonth)){
            try {displayNotification(name+"'s Birthday is in a week");} catch (AWTException e) {throw new RuntimeException(e);}
        }else if(threeDaysBeforeBDay == Integer.parseInt(cDay) && month.equals(cMonth)){
            try {displayNotification(name+"'s Birthday is in 3 days");} catch (AWTException e) {throw new RuntimeException(e);}
        }else if(dayBeforeBDay == Integer.parseInt(cDay) && month.equals(cMonth)){
            try {displayNotification(name+"'s Birthday is in 1 days");} catch (AWTException e) {throw new RuntimeException(e);}
        }
    }

    private int getDayToBeChecked(int dayToCheck, int amountOfDaysToGoBack, String prevMonth) {
        for (int i = 0; i < amountOfDaysToGoBack; i++) {
            dayToCheck--;
            if(dayToCheck==0){
                dayToCheck=howManyDays(prevMonth,year);
            }
        }
        return dayToCheck;
    }

    private void displayNotification(String message) throws AWTException {
        //Instantiate SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        //Get image for notification
        Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("img/bdaylogo2.png"));

        //Set image for notification
        TrayIcon trayIcon = new TrayIcon(image, message);

        //Auto size the image
        trayIcon.setImageAutoSize(true);

        //Add the image to the tray
        tray.add(trayIcon);

        //Display the Message
        trayIcon.displayMessage("Birthday Reminder", message, MessageType.INFO);

        //Remove the icon from the tray
        tray.remove(trayIcon);
    }
}
