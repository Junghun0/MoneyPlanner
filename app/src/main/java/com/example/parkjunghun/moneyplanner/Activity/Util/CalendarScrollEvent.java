package com.example.parkjunghun.moneyplanner.Activity.Util;

public class CalendarScrollEvent {

    int year;
    int month;

    public CalendarScrollEvent(int year , int month) {
        this.year = year;
        this.month = month;
    }

    public int getMonth() {
        return month;
    }

    public int getYear(){
        return year;
    }
}
