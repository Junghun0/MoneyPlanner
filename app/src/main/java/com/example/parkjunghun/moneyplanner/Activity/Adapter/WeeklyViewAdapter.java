package com.example.parkjunghun.moneyplanner.Activity.Adapter;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.parkjunghun.moneyplanner.Activity.Model.WeeklyViewItem;
import com.example.parkjunghun.moneyplanner.Activity.Function.Weekly_CalendarFunction;
import com.example.parkjunghun.moneyplanner.R;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WeeklyViewAdapter extends BaseAdapter {

    @BindView(R.id.weekly_date)
    TextView tv_weekly_date;
    @BindView(R.id.week_number)
    TextView tv_week_number;
    @BindView(R.id.weekly_money)
    TextView tv_week_money;

    Weekly_CalendarFunction function = new Weekly_CalendarFunction();
    WeeklyViewItem items;
    ArrayList<WeeklyViewItem> weekly_items = new ArrayList<>();

    @Override
    public int getCount() {
        return weekly_items.size();
    }

    @Override
    public WeeklyViewAdapter getItem(int i) {
        return weekly_items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.weekly_customview, viewGroup, false);
        }

        ButterKnife.bind(this, view);

        items = (WeeklyViewItem) getItem(i);

        tv_weekly_date.setText(items.getWeekely_date());
        tv_week_number.setText(items.getWeek_number());
        tv_week_money.setText(items.getWeekly_money());

        return view;
    }

    public void show_Chart(int i, LineChart lineChart){
        items = (WeeklyViewItem) getItem(i);
        function.make_LineChart(lineChart, items.get_ChartDate());

    }

    public void addItem(String weekly_date, String week_number, String weekly_money, String[] chart_date) {
        WeeklyViewItem mitems = new WeeklyViewItem();

        mitems.setWeek_number(week_number);
        mitems.setWeekely_date(weekly_date);
        mitems.setWeekly_money(weekly_money);
        mitems.set_ChartDate(chart_date);

        weekly_items.add(mitems);
    }

    public void removeAll() {
        weekly_items.clear();
    }

}
