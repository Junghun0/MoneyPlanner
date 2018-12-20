package com.example.parkjunghun.moneyplanner.Activity.Model;

import com.example.parkjunghun.moneyplanner.Activity.Adapter.WeeklyViewAdapter;

public class WeeklyViewItem extends WeeklyViewAdapter {

    private String weekely_date, week_number, weekly_money;

    public void setWeekely_date(String date_from){
        this.weekely_date = date_from;
    }

    public String getWeekely_date(){
        return weekely_date;
    }

    public void setWeek_number(String week_number){
        this.week_number = week_number;
    }

    public String getWeek_number(){
        return week_number;
    }

    public void setWeekly_money(String weekly_money){
        this.weekly_money = weekly_money;
    }

    public String getWeekly_money(){
        return weekly_money;
    }

}
