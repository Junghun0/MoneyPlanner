package com.example.parkjunghun.moneyplanner.Activity.Model;

public class CalendarScrollEvent {

    private int year;
    private int month;

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
