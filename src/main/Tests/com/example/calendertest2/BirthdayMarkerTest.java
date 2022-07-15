package com.example.calendertest2;

import org.junit.jupiter.api.Test;

import static com.example.calendertest2.HowManyDaysInTheMonth.howManyDays;
import static org.junit.jupiter.api.Assertions.*;

class BirthdayMarkerTest {

    @Test
    void getDayToBeCheckedTests() {
        BirthdayMarker testDay4 = new BirthdayMarker("test", "4", "Mar", "2022", "0xccffffff", false, false, "none");
        assertEquals(25, testDay4.getDayToBeChecked(4, 7, "Feb"));
        assertEquals(1, testDay4.getDayToBeChecked(4, 3, "Feb"));
        assertEquals(3, testDay4.getDayToBeChecked(4, 1, "Feb"));

        assertEquals(3, testDay4.getDayToBeChecked(10, 7, "May"));
        assertEquals(7, testDay4.getDayToBeChecked(10, 3, "May"));
        assertEquals(9, testDay4.getDayToBeChecked(10, 1, "May"));

        assertEquals(9, testDay4.getDayToBeChecked(16, 7, "Aug"));
        assertEquals(13, testDay4.getDayToBeChecked(16, 3, "Aug"));
        assertEquals(15, testDay4.getDayToBeChecked(16, 1, "Aug"));

        assertEquals(15, testDay4.getDayToBeChecked(22, 7, "Nov"));
        assertEquals(19, testDay4.getDayToBeChecked(22, 3, "Nov"));
        assertEquals(21, testDay4.getDayToBeChecked(22, 1, "Nov"));

        assertEquals(21, testDay4.getDayToBeChecked(28, 7, "Feb"));
        assertEquals(25, testDay4.getDayToBeChecked(28, 3, "May"));
        assertEquals(27, testDay4.getDayToBeChecked(28, 1, "Aug"));

    }
}