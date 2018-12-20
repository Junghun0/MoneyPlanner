package com.example.parkjunghun.moneyplanner.Activity.Model;

public class Weekly_Update_Event {

    public final String update;
    public final int year;
    public final int month;

    public Weekly_Update_Event(String update, int year, int month){
        this.update = update;
        this.year = year;
        this.month = month;
    }

}
