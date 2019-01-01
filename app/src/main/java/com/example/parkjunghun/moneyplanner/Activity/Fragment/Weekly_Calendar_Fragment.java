package com.example.parkjunghun.moneyplanner.Activity.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.parkjunghun.moneyplanner.Activity.Adapter.WeeklyViewAdapter;
import com.example.parkjunghun.moneyplanner.Activity.Function.Weekly_CalendarFunction;
import com.example.parkjunghun.moneyplanner.Activity.Model.Weekly_Update_Event;
import com.example.parkjunghun.moneyplanner.R;
import com.github.mikephil.charting.charts.LineChart;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Weekly_Calendar_Fragment extends Fragment {

    @BindView(R.id.weekly_listview)
    ListView weekly_ListView;
    LineChart lineChart;
    LinearLayout chart_Layout;

    Calendar mCal;
    WeeklyViewAdapter weekly_Adapter;
    Weekly_CalendarFunction function;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.weekly_fragment, container, false);
        View customView = inflater.inflate(R.layout.weekly_customview, container, false);
        ButterKnife.bind(this, view);

        chart_Layout = (LinearLayout) customView.findViewById(R.id.chart_layout);
        mCal = Calendar.getInstance();
        function = new Weekly_CalendarFunction();
        weekly_Adapter = new WeeklyViewAdapter();

        function.sep_Calendar(weekly_Adapter, mCal.get(Calendar.YEAR), mCal.get(Calendar.MONTH) + 1);
        weekly_ListView.setAdapter(weekly_Adapter);
        weekly_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                chart_Layout = (LinearLayout) view.findViewById(R.id.chart_layout);
                lineChart = (LineChart) view.findViewById(R.id.chart);
                if (chart_Layout.getVisibility() == View.GONE) {
                    chart_Layout.setVisibility(View.VISIBLE);
                    weekly_Adapter.show_Chart(i, lineChart);
                } else {
                    chart_Layout.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void weekly_Change_Event(Weekly_Update_Event event) {
        weekly_Adapter.removeAll();

        function = new Weekly_CalendarFunction();
        function.sep_Calendar(weekly_Adapter, event.year, event.month);

        weekly_ListView.setAdapter(weekly_Adapter);
        weekly_Adapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            EventBus.getDefault().register(this);
        } catch (Exception e) {
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            EventBus.getDefault().unregister(this);
        } catch (Exception e) {
        }
    }

}
