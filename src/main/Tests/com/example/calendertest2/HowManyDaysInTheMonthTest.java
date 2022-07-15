package com.example.calendertest2;

import org.junit.jupiter.api.Test;

import static com.example.calendertest2.HowManyDaysInTheMonth.howManyDays;
import static org.junit.jupiter.api.Assertions.*;

class HowManyDaysInTheMonthTest {

    @Test
    void currentYearTests() {
        assertEquals(31, howManyDays("Jan", "2022"));
        assertEquals(28, howManyDays("Feb", "2022"));
        assertEquals(31, howManyDays("Mar", "2022"));
        assertEquals(30, howManyDays("Apr", "2022"));
        assertEquals(31, howManyDays("May", "2022"));
        assertEquals(30, howManyDays("Jun", "2022"));
        assertEquals(31, howManyDays("Jul", "2022"));
        assertEquals(31, howManyDays("Aug", "2022"));
        assertEquals(30, howManyDays("Sep", "2022"));
        assertEquals(31, howManyDays("Oct", "2022"));
        assertEquals(30, howManyDays("Nov", "2022"));
        assertEquals(31, howManyDays("Dec", "2022"));
    }

    @Test
    void FebTests() {
        assertEquals(28, howManyDays("Feb", "2005"));
        assertEquals(28, howManyDays("Feb", "2015"));
        assertEquals(28, howManyDays("Feb", "2025"));
        assertEquals(28, howManyDays("Feb", "2035"));
        assertEquals(28, howManyDays("Feb", "2045"));
    }

    @Test
    void FebLeapTests() {
        assertEquals(29, howManyDays("Feb", "2004"));
        assertEquals(29, howManyDays("Feb", "2008"));
        assertEquals(29, howManyDays("Feb", "2012"));
        assertEquals(29, howManyDays("Feb", "2016"));
        assertEquals(29, howManyDays("Feb", "2020"));
        assertEquals(29, howManyDays("Feb", "2024"));
        assertEquals(29, howManyDays("Feb", "2028"));
        assertEquals(29, howManyDays("Feb", "2032"));
        assertEquals(29, howManyDays("Feb", "2036"));
        assertEquals(29, howManyDays("Feb", "2040"));
        assertEquals(29, howManyDays("Feb", "2044"));
        assertEquals(29, howManyDays("Feb", "2048"));
    }


}