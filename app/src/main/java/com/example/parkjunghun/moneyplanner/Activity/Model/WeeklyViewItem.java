package com.example.parkjunghun.moneyplanner.Activity.Model;

import android.widget.LinearLayout;

import com.example.parkjunghun.moneyplanner.Activity.Adapter.WeeklyViewAdapter;

public class WeeklyViewItem extends WeeklyViewAdapter {

    private String weekely_date, week_number, weekly_money;
    private String[] chart_date;
    private LinearLayout show_chart;

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

    public void set_ChartDate(String[] chart_date){
        this.chart_date = chart_date;
    }

    public String[] get_ChartDate(){
        return chart_date;
    }

    public void set_ShowChart(LinearLayout show_chart){
        this.show_chart = show_chart;
    }

    public LinearLayout get_ShowChart(){
        return show_chart;
    }

}
