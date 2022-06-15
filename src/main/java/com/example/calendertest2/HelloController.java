package com.example.calendertest2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class HelloController {
    @FXML
    private VBox vContainer;
    @FXML
    private HBox menuHbox;
    @FXML
    private ImageView upArrow;
    @FXML
    private ImageView downArrow;
    @FXML
    private Label monthLabel;
    @FXML
    private Label yearLabel;
    @FXML
    private GridPane calenderGrid;

    //  Month and Days Arrays
    String[] monthsArray = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul","Aug", "Sep", "Oct", "Nov", "Dec"};
    String[] daysArray = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

    //  Get Date
    Date cDate = new Date();

    //  Separate date into current(c) Day, DayAsNum, Month, Year
    String[] cDateSplit = cDate.toString().split(" ");
    String cDay = cDateSplit[0];
    int cDayAsNum = Integer.parseInt(cDateSplit[2]);
    String cMonth = cDateSplit[1];
    String cYear = cDateSplit[5];

    //  Initialise day current month began and ended, used to incrementing/decrementing month
    String dayCMonthBegan="";
    String dayCMonthEnded="";

    @FXML
    public void initialize(){
        setCalender(cDay, cDayAsNum, cMonth, cYear);
    }


    //  Writes the days of the month in the calendar grid
    public void setCalender(String day, int dayAsNum ,String month, String year){

        setWeekDays();
        setMenuMonthYear(month, year);

        int currentPositionInDaysList = 0;

//      Get the position the day lies in the days list
        for(int i=0; i<daysArray.length; i++){
            if(daysArray[i].equals(day)){
                currentPositionInDaysList=i;
            }
        }

//      Getting the position of the day in days array when the month starts, used to position the days in the grid
        while(dayAsNum!=1){

//          If at the beginning of array, go back to the end array
            if(currentPositionInDaysList==-1){
                currentPositionInDaysList=6;
            }

//          decrement position and day
            currentPositionInDaysList -= 1;
            dayAsNum -= 1;
        }
        if(currentPositionInDaysList==-1){
            currentPositionInDaysList=6;
        }
        dayCMonthBegan = daysArray[currentPositionInDaysList];

        int rowPosition = 1;
        int dayLimit = howManyDays(month, year); //holds the amount of days in the month

//      loop through the days of the month
        while(dayAsNum!=dayLimit+1){

//          Resets column position and increments row position if column position goes past the boundaries of the grid
            if(currentPositionInDaysList > 6){
                rowPosition += 1;
                currentPositionInDaysList=0;
            }

//          Add the day to the grid with event setup
            Label l1 = new Label(String.valueOf(dayAsNum));
            l1.setId("day"+dayAsNum);
            VBox v1 = new VBox();
            v1.setId("grid"+dayAsNum);
            v1.getStyleClass().add("dayGridStyle");
            v1.getChildren().add(l1);
            v1.setOnMouseClicked(e -> { dayClicked(v1); });
            calenderGrid.add((v1), currentPositionInDaysList, rowPosition);

            dayCMonthEnded = daysArray[currentPositionInDaysList];
//          Increment column position and day
            currentPositionInDaysList +=1;
            dayAsNum+=1;
        }
    }

    // Writes Mon-Sun in the grid
    public void setWeekDays(){
        for(int i=0; i<daysArray.length; i++){
            calenderGrid.add(new Label(daysArray[i]), i, 0);
        }
    }

    //  Sets the top labels to current month and year
    public void setMenuMonthYear(String month, String year){
        monthLabel.setText(month);
        yearLabel.setText(year);
    }

    //  Returns the amount of days in a given month
    public int howManyDays(String month, String year){
        int dayLimit=0;
        switch (month){
            case("Jan"): dayLimit = 31; break;
            case("Feb"):
                //Check for Leap year
                int yearValue = Integer.parseInt(year);
                boolean leap = false;
                // if the year is divisible by 4
                if (yearValue % 4 == 0){
                    // if the year is century
                    if (yearValue % 100 == 0){
                        // if year is divided by 400
                        if (yearValue % 400 == 0){
                            leap = true;
                        }else
                            leap = false;
                    }else
                        leap = true;
                }else
                    leap = false;

                if (!leap){
                    dayLimit = 28;
                }else{
                    dayLimit = 29;
                }
                break;
            case("Mar"): dayLimit = 31; break;
            case("Apr"): dayLimit = 30; break;
            case("May"): dayLimit = 31; break;
            case("Jun"): dayLimit = 30; break;
            case("Jul"): dayLimit = 31; break;
            case("Aug"): dayLimit = 31; break;
            case("Sep"): dayLimit = 30; break;
            case("Oct"): dayLimit = 31; break;
            case("Nov"): dayLimit = 30; break;
            case("Dec"): dayLimit = 31; break;
        }
        return dayLimit;
    }

    public void incrementMonth(){
        calenderGrid.getChildren().clear(); //Clear grid

        //Looping through daysArray to go to next day from where last month ended
        for(int i=0; i<daysArray.length; i++){
            if(daysArray[i].equals(dayCMonthEnded)){
                try{cDay = daysArray[i+1];}catch (Exception e){cDay=daysArray[0];} //if the next day goes out the bounds of array then the next day must be the Monday
                cDayAsNum=1; // set to 1 as the day after month ends will be the 1st day of the new month
            }
        }

        // Same method as day increment, used separate string im using cMonth to compare with array
        String newMonth = "";
        for(int j=0; j<monthsArray.length; j++){
            if(monthsArray[j].equals(cMonth)){
                try{newMonth = monthsArray[j+1];}catch (Exception e){newMonth=monthsArray[0];}
            }
        }

        cMonth = newMonth;

        //If the new month = Jan then a new year must have been passed
        if(cMonth.equals("Jan")){
            cYear = String.valueOf(Integer.parseInt(cYear)+1);
        }

        setCalender(cDay, cDayAsNum, cMonth, cYear);
    }

    public void decrementMonth(){
        calenderGrid.getChildren().clear();

        // Same method as increment but reverse and using where the last month began to get the day the next month ends
        for(int i=0; i<daysArray.length; i++){
            if(daysArray[i].equals(dayCMonthBegan)){
                try{cDay = daysArray[i-1];}catch (Exception e){cDay=daysArray[6];}
            }
        }

        String newMonth = "";
        for(int j=0; j<monthsArray.length; j++){
            if(monthsArray[j].equals(cMonth)){
                try{newMonth = monthsArray[j-1];}catch (Exception e){newMonth=monthsArray[11];}
            }
        }

        cMonth = newMonth;

        //If the new month = Dec then the previous year must be viewed
        if(cMonth.equals("Dec")){
            cYear = String.valueOf(Integer.parseInt(cYear)-1);
        }

        // Since program gets the day the last month ends the cDay needs to be set to the last numeric day value of the month
        int dayLimit = howManyDays(newMonth,cYear);
        cDayAsNum=dayLimit;

        setCalender(cDay, cDayAsNum, cMonth, cYear);
    }

    public void dayClicked(VBox v1){
        v1.setStyle("-fx-background-color: #1e1c1c;");
    }

}