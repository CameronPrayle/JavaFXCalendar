package com.example.calendertest2;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static com.example.calendertest2.HowManyDaysInTheMonth.howManyDays;

public class CalendarController {
    @FXML
    private VBox vContainer;
    @FXML
    private HBox titleBar;
    @FXML
    private Label titleLabel;
    @FXML
    private HBox menuHbox;
    @FXML
    private ImageView upArrow;
    @FXML
    private ImageView downArrow;
    @FXML
    private ImageView moonIcon;
    @FXML
    private Label monthLabel;
    @FXML
    private Label yearLabel;
    @FXML
    private GridPane calenderGrid;
    @FXML
    private GridPane markerInfoBar;
    @FXML
    private TextField markerNameField;
    @FXML
    private ColorPicker markerColourPicker;
    @FXML
    private CheckBox markerNotifCheck;
    @FXML
    private CheckBox markerGiftCheck;
    @FXML
    private TextField markerGiftDesc;
    @FXML
    private Label markerInfoClose;
    @FXML
    private GridPane markerEditInfoBar;
    @FXML
    private TextField markerEditNameField;
    @FXML
    private ColorPicker markerEditColourPicker;
    @FXML
    private CheckBox markerEditNotifCheck;
    @FXML
    private CheckBox markerEditGiftCheck;
    @FXML
    private TextField markerEditGiftDesc;
    @FXML
    private Label markerEditInfoClose;
    @FXML
    private Label addBirthdayLabel;
    @FXML
    private Label editBirthdayLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label nameEditLabel;
    @FXML
    private Label colourLabel;
    @FXML
    private Label colourEditLabel;
    @FXML
    private Button userFinishButton;
    @FXML
    private Button userEditButton;
    @FXML
    private Button userDeleteButton;
    @FXML
    private Label errorText;
    @FXML
    private Label errorEditText;

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

    // Initialise nodes needed for adding marker
    VBox currentDayNode;
    Label currentDayLabel;

    //Initialise Bday markers list
    ArrayList<BirthdayMarker> bdayMarkers = new ArrayList<BirthdayMarker>();

    //Initialise birthday marker that has been currently clicked
    BirthdayMarker currentBDaymarker;

    // Current theme, checked when theme is changed to apply opposite settings to current theme
    String currentTheme = "light";


    @FXML
    public void initialize(){
        readBDayMarkerCSV();
        changeTheme();
        markerInfoBar.setVisible(false);
        markerInfoBar.managedProperty().bind(markerInfoBar.visibleProperty());
        markerEditInfoBar.setVisible(false);
        markerEditInfoBar.managedProperty().bind(markerEditInfoBar.visibleProperty());
        markerGiftDesc.setVisible(false);
        markerEditGiftDesc.setVisible(false);
        errorText.setVisible(false);
        errorEditText.setVisible(false);
    }

    //Reads the BdayMarker CSV file and creates birthday marker objects using its contents. Stores the birthday markers in a list to display them in the calendar
    private void readBDayMarkerCSV() {
        try {
            File f = new File("BDayMarker.csv");
            Scanner readFile = new Scanner(f);
            while (readFile.hasNextLine()){
                // Get data for current lines birthday
                String currentLine = readFile.nextLine();
                // Split up current line by commas
                String[] currentLineSplit = currentLine.split(",");
                String day = currentLineSplit[1];
                String month = currentLineSplit[2];
                String year = currentLineSplit[3];
                String name = currentLineSplit[0];
                String colour = currentLineSplit[4];
                boolean notif;
                boolean gift;
                String giftDesc = currentLineSplit[7];

                if(currentLineSplit[5].equals("true")){
                    notif = true;
                }else{
                    notif = false;
                }
                if(currentLineSplit[6].equals("true")){
                    gift = true;
                }else{
                    gift = false;
                }

                // Make BirthdayMarker object, add it to BirthdayMarker list
                bdayMarkers.add(new BirthdayMarker(name,day,month,year,colour,notif,gift,giftDesc));
            }
        }catch (Exception e){}
    }


    //  Writes the days of the month in the calendar grid
    public void setCalender(String day, int dayAsNum ,String month, String year){
        markerEditInfoBar.setVisible(false);
        markerInfoBar.setVisible(false);
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
            if(currentTheme.equals("light")){
                l1.setTextFill(Color.web("#000000"));
            }else{
                l1.setTextFill(Color.web("#FEFEFE"));
            }
            VBox v1 = new VBox();
            v1.setId("grid"+dayAsNum);
            v1.getStyleClass().add("dayGridStyle");
            v1.getChildren().add(l1);
            v1.setOnMouseClicked(e -> { dayClicked(v1,l1); });

            // Search through the bday marker array for bdays matching the current date and add them to the calendar
            for(int i=0; i<bdayMarkers.size(); i++){
                if(dayAsNum == Integer.parseInt(bdayMarkers.get(i).day) && month.equals(bdayMarkers.get(i).month)){
                    Label l2 = new Label(bdayMarkers.get(i).name);
                    HBox h1 = new HBox();
                    h1.getStyleClass().add("bdayMarkerStyle");
                    String[] splitBDayColour = bdayMarkers.get(i).colour.split("0x");
                    h1.setStyle("-fx-background-color: #"+ splitBDayColour[1] + ";");
                    h1.getChildren().add(l2);
                    int j = i;
                    h1.setOnMouseClicked(e -> { markerClicked(h1,l2,bdayMarkers.get(j)); });
                    v1.getChildren().add(h1);
                }
            }
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
            Label dayLabel = new Label(daysArray[i]);
            if(currentTheme.equals("light")){
                dayLabel.setTextFill(Color.web("#000000"));
            }else{
                dayLabel.setTextFill(Color.web("#FEFEFE"));
            }
            calenderGrid.add(dayLabel, i, 0);
        }
    }

    //  Sets the top labels to current month and year
    public void setMenuMonthYear(String month, String year){
        monthLabel.setText(month);
        yearLabel.setText(year);
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


    boolean markerHasBeenClicked=false;
    public void dayClicked(VBox v1, Label l1){
        //Reset fields to default for when user picks another date
        errorText.setVisible(false);
        markerNameField.setText("");
        markerColourPicker.setValue(Color.web("#FFFFFF"));
        markerNotifCheck.setSelected(false);
        markerGiftCheck.setSelected(false);
        markerGiftDesc.setText("");

        //If a node is already highlighted, unhighlight it
        if(!(currentDayNode==null)){
            currentDayNode.setStyle("-fx-background-color: transparent;");
        }

        //Show bar and assign the current node
        markerInfoBar.setVisible(true);
        currentDayNode=v1;
        currentDayLabel=l1;

        // Checks so 2 menus can't show at the same time
        if(markerHasBeenClicked){
            markerInfoBar.setVisible(false);
        }else{
            markerEditInfoBar.setVisible(false);
        }

        markerHasBeenClicked=false;

        //Add listeners for showing gift text box
        markerGiftCheck.selectedProperty().addListener( e -> {
            if(markerGiftCheck.isSelected()){
                markerGiftDesc.setVisible(true);
            }else{
                markerGiftDesc.setVisible(false);
            }
        });

        // Change node BG colour with selected theme
        if(currentTheme=="light"){
            currentDayNode.setStyle("-fx-background-color: #CEE8F0;");
        }else{
            currentDayNode.setStyle("-fx-background-color: #BB86FC;");
        }
    }

    public void closeNode(){
        markerInfoBar.setVisible(false);
        currentDayNode.setStyle("-fx-background-color: transparent;");
    }

    public void addMarker(){
        // Get data for selected birthday
        String day = currentDayLabel.getText();
        String month = cMonth;
        String year = cYear;
        String name = markerNameField.getText();
        String colour = String.valueOf(markerColourPicker.getValue());
        boolean notif = markerNotifCheck.isSelected();
        boolean gift = markerGiftCheck.isSelected();
        String giftDesc = markerGiftDesc.getText();

        //Check to see if text fields contain ","
        if(name.contains(",") || giftDesc.contains(",")){
            errorText.setVisible(true);
        }else {
            errorEditText.setVisible(false);

            // Make BirthdayMarker object, add it to BirthdayMarker list
            BirthdayMarker b1 = new BirthdayMarker(name, day, month, year, colour, notif, gift, giftDesc);
            b1.addMarkerToFile();
            bdayMarkers.add(b1);
            calenderGrid.getChildren().clear();
            setCalender(cDay, cDayAsNum, cMonth, cYear);

            //Reset fields to default for when user picks another date
            markerNameField.setText("");
            markerColourPicker.setValue(Color.web("#FFFFFF"));
            markerNotifCheck.setSelected(false);
            markerGiftCheck.setSelected(false);
            markerGiftDesc.setText("");
        }
    }

    public void closeEditNode(){
        markerEditInfoBar.setVisible(false);
    }

    private void markerClicked(HBox h1, Label l2, BirthdayMarker b1) {
        errorEditText.setVisible(false);
        // Set user input fields to the birthday marker details
        currentBDaymarker = b1;
        markerEditInfoBar.setVisible(true);
        markerEditNameField.setText(b1.name);
        markerEditColourPicker.setValue(Color.valueOf(b1.colour));
        markerEditNotifCheck.setSelected(b1.notification);
        markerEditGiftCheck.setSelected(b1.gift);
        markerEditGiftDesc.setText(b1.giftDesc);

        if(markerEditGiftCheck.isSelected()){
            markerEditGiftDesc.setVisible(true);
        }

        //Add listeners for showing gift text box
        markerEditGiftCheck.selectedProperty().addListener( e -> {
            if(markerEditGiftCheck.isSelected()){
                markerEditGiftDesc.setVisible(true);
            }else{
                markerEditGiftDesc.setVisible(false);
            }
        });

        markerHasBeenClicked = true;
    }
    public void editMarker(){
        //Add new instance of birthday marker
        String day = currentDayLabel.getText();
        String month = cMonth;
        String year = cYear;
        String name = markerEditNameField.getText();
        String colour = String.valueOf(markerEditColourPicker.getValue());
        boolean notif = markerEditNotifCheck.isSelected();
        boolean gift = markerEditGiftCheck.isSelected();
        String giftDesc = markerEditGiftDesc.getText();

        //Check to see if text fields contain ","
        if(name.contains(",") || giftDesc.contains(",")){
            errorEditText.setVisible(true);
        }else {
            errorEditText.setVisible(false);
            deleteMarker(); // Delete the old marker
            BirthdayMarker b1 = new BirthdayMarker(name, day, month, year, colour, notif, gift, giftDesc);
            b1.addMarkerToFile();
            bdayMarkers.add(b1);

            //Redraw Calendar
            calenderGrid.getChildren().clear();
            setCalender(cDay, cDayAsNum, cMonth, cYear);
        }
    }

    public void deleteMarker() {
        // Rebuild CSV file to not include the old birthday marker
        List<String> fileContents = new ArrayList<>();
        try {
            File f = new File("BDayMarker.csv");
            Scanner readFile = new Scanner(f);
            while (readFile.hasNextLine()) {
                String currentLine = readFile.nextLine();
                String[] currentLineSplit = currentLine.split(",");
                String day = currentLineSplit[1];
                String month = currentLineSplit[2];
                String year = currentLineSplit[3];
                String name = currentLineSplit[0];
                if (!(currentBDaymarker.name.equals(name) && currentBDaymarker.day.equals(day) && currentBDaymarker.month.equals(month) && currentBDaymarker.year.equals(year))) {
                    fileContents.add(currentLine);
                } else {
                    bdayMarkers.remove(currentBDaymarker);
                }
            }
            //Rewrite file using fileContents
            FileWriter fw = new FileWriter("BDayMarker.csv");
            for (String fileContent : fileContents) {
                fw.write(fileContent + "\n");
            }
            fw.close();
        } catch (Exception e) {}

        calenderGrid.getChildren().clear();
        setCalender(cDay, cDayAsNum, cMonth, cYear);
    }

    //Change colour of theme based on current theme
    public void changeTheme(){
        String primaryBackgroundColour;
        String secondaryBackgroundColour;
        String tertiaryBackgroundColour;
        String primaryTextColour;
        String secondaryTextColour = "#000000";

        if(currentTheme.equals("light")){
            primaryBackgroundColour = "-fx-background-color: #121212;";
            secondaryBackgroundColour = "-fx-background-color: #282828;";
            tertiaryBackgroundColour = "-fx-background-color: #BB86FC;";
            primaryTextColour = "#FEFEFE";
            moonIcon.setImage(new Image(getClass().getResourceAsStream("img/WhiteMoon.png")));
            upArrow.setImage(new Image(getClass().getResourceAsStream("img/upArrowWhite.png")));
            downArrow.setImage(new Image(getClass().getResourceAsStream("img/downArrowWhite.png")));
            currentTheme="dark";
        }else{
            primaryBackgroundColour = "-fx-background-color: #FEFEFE;";
            secondaryBackgroundColour = "-fx-background-color: #E8E8E8;";
            tertiaryBackgroundColour = "-fx-background-color: #CEE8F0;";
            primaryTextColour = "#000000";
            moonIcon.setImage(new Image(getClass().getResourceAsStream("img/moon.png")));
            upArrow.setImage(new Image(getClass().getResourceAsStream("img/upArrow.png")));
            downArrow.setImage(new Image(getClass().getResourceAsStream("img/downArrow.png")));
            currentTheme="light";
        }
        vContainer.setStyle(primaryBackgroundColour);
        monthLabel.setTextFill(Color.web(primaryTextColour));
        yearLabel.setTextFill(Color.web(primaryTextColour));
        addBirthdayLabel.setTextFill(Color.web(primaryTextColour));
        editBirthdayLabel.setTextFill(Color.web(primaryTextColour));
        nameLabel.setTextFill(Color.web(primaryTextColour));
        nameEditLabel.setTextFill(Color.web(primaryTextColour));
        colourLabel.setTextFill(Color.web(primaryTextColour));
        colourEditLabel.setTextFill(Color.web(primaryTextColour));
        userFinishButton.setTextFill(Color.web(secondaryTextColour));
        userEditButton.setTextFill(Color.web(secondaryTextColour));
        userDeleteButton.setTextFill(Color.web(secondaryTextColour));
        userFinishButton.setStyle(tertiaryBackgroundColour);
        userEditButton.setStyle(tertiaryBackgroundColour);
        userDeleteButton.setStyle(tertiaryBackgroundColour);
        markerNotifCheck.setTextFill(Color.web(primaryTextColour));
        markerEditNotifCheck.setTextFill(Color.web(primaryTextColour));
        markerGiftCheck.setTextFill(Color.web(primaryTextColour));
        markerEditGiftCheck.setTextFill(Color.web(primaryTextColour));
        markerNameField.setStyle(tertiaryBackgroundColour);
        markerEditNameField.setStyle(tertiaryBackgroundColour);
        markerColourPicker.setStyle(tertiaryBackgroundColour);
        markerEditColourPicker.setStyle(tertiaryBackgroundColour);
        markerInfoBar.setStyle(secondaryBackgroundColour);
        markerEditInfoBar.setStyle(secondaryBackgroundColour);
        titleBar.setStyle(secondaryBackgroundColour);
        menuHbox.setStyle(secondaryBackgroundColour);
        titleLabel.setTextFill(Color.web(primaryTextColour));
        markerInfoClose.setTextFill(Color.web(primaryTextColour));
        markerEditInfoClose.setTextFill(Color.web(primaryTextColour));
        markerGiftDesc.setStyle(tertiaryBackgroundColour);
        markerEditGiftDesc.setStyle(tertiaryBackgroundColour);

        calenderGrid.getChildren().clear();
        setCalender(cDay, cDayAsNum, cMonth, cYear);
    }
}