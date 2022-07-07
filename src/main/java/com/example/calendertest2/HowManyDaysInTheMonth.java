package com.example.calendertest2;

public interface HowManyDaysInTheMonth {

    //  Returns the amount of days in a given month
    static int howManyDays(String month, String year){
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

}
