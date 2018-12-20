package com.example.parkjunghun.moneyplanner.Activity.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.parkjunghun.moneyplanner.Activity.Adapter.WeeklyViewAdapter;
import com.example.parkjunghun.moneyplanner.Activity.ETC.WeeklyXAxisValueFormatter;
import com.example.parkjunghun.moneyplanner.Activity.Model.Weekly_Update_Event;
import com.example.parkjunghun.moneyplanner.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;

public class Weekly_Calendar_Fragment extends Fragment {

    ListView weekly_ListView;
    TextView test;
    Calendar mCal;
    WeeklyViewAdapter weekly_Adapter;
    private LineChart lineChart;
    private LinearLayout chartLayout;
    private String date[][];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weekly_fragment, container, false);
        mCal = Calendar.getInstance();
        weekly_ListView = (ListView) view.findViewById(R.id.weekly_listview);

        weekly_Adapter = new WeeklyViewAdapter();
        sep_Calendar(weekly_Adapter, mCal.get(Calendar.YEAR), mCal.get(Calendar.MONTH) + 1);

        weekly_ListView.setAdapter(weekly_Adapter);
        weekly_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                chartLayout = (LinearLayout) view.findViewById(R.id.chart_layout);
                lineChart = (LineChart) view.findViewById(R.id.chart);
                Log.e("month", i + "");
                make_LineChart(lineChart, chartLayout, i);
            }
        });

        return view;
    }

    public void sep_Calendar(WeeklyViewAdapter weekly_Adapter, int year, int month) {
        String from, until;
        int day = 1, count = 1, i = 0;
        mCal.set(Calendar.YEAR, year);
        mCal.set(Calendar.MONTH, month - 1);
        date = new String[7][7];
        int max_Day = mCal.getActualMaximum(Calendar.DAY_OF_MONTH);
        mCal.set(Calendar.DAY_OF_MONTH, max_Day);

        mCal.set(Calendar.DAY_OF_MONTH, day);

        while (max_Day-- > 0) {
            if (mCal.get(Calendar.DAY_OF_WEEK) == 7) {
                if (mCal.get(Calendar.DAY_OF_MONTH) == 1) {
                    from = (mCal.get(Calendar.MONTH) + 1) + "월" + mCal.get(Calendar.DAY_OF_MONTH) + "일";
                    weekly_Adapter.addItem(from + "", mCal.get(Calendar.WEEK_OF_MONTH) + "째주", 40000 + "원");
                    date[i][0] = (mCal.get(Calendar.MONTH) + 1) + ".1";
                    ++i;
                    day = count + 1;
                    } else {
                        from = (mCal.get(Calendar.MONTH) + 1) + "월" + day + "일";
                        until = (mCal.get(Calendar.MONTH) + 1) + "월" + mCal.get(Calendar.DAY_OF_MONTH) + "일";
                        weekly_Adapter.addItem(from + " - " + until, mCal.get(Calendar.WEEK_OF_MONTH) + "째주", 40000 + "원");
                        for (int j = 0; j < 7 ; j++) {
                        date[i][j] = (mCal.get(Calendar.MONTH) + 1) + "." + (day++);
                    }
                    ++i;
                    day = count + 1;
                }
            } else if (mCal.get(Calendar.DAY_OF_MONTH) == mCal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                if (mCal.get(Calendar.DAY_OF_WEEK) == 1) {
                    from = (mCal.get(Calendar.MONTH) + 1) + "월" + day + "일";
                    weekly_Adapter.addItem(from + "", mCal.get(Calendar.WEEK_OF_MONTH) + "째주", 40000 + "원");
                    date[i][0] = (mCal.get(Calendar.MONTH) + 1) + "." + day;
                    ++i;
                    break;
                } else {
                    from = (mCal.get(Calendar.MONTH) + 1) + "월" + day + "일";
                    until = (mCal.get(Calendar.MONTH) + 1) + "월" + mCal.get(Calendar.DAY_OF_MONTH) + "일";
                    weekly_Adapter.addItem(from + " - " + until, mCal.get(Calendar.WEEK_OF_MONTH) + "째주", 40000 + "원");

                    for (int j = 0; j < 7; j++) {
                        if(day == mCal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                            date[i][j] = (mCal.get(Calendar.MONTH) + 1) + "." + day;
                            if(mCal.get(Calendar.MONTH) == 11) {
                                mCal.set(Calendar.YEAR, year + 1);
                                mCal.set(Calendar.MONTH, 0);
                            } else {
                                mCal.set(mCal.MONTH, mCal.get(Calendar.MONTH)+1);
                        }
                            day = 1;
                        } else {
                            date[i][j] = (mCal.get(Calendar.MONTH) + 1) + "." + (day++);
                        }
                    }
                    ++i;
                    break;
                }

            }
            mCal.set(Calendar.DAY_OF_MONTH, ++count);
        }


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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void weekly_Change_Event(Weekly_Update_Event event) {
        if (event.update.equals("true")) {
            Log.e("test_Event",event.update+" "+event.year+" "+event.month);
            weekly_Adapter.removeAll();
            sep_Calendar(weekly_Adapter, event.year, event.month);
            weekly_ListView.setAdapter(weekly_Adapter);
            weekly_Adapter.notifyDataSetChanged();
        }
    }

    private void make_LineChart(LineChart lineChart, LinearLayout chartLayout, int position) {

        if (chartLayout.getVisibility() == View.GONE) {
            chartLayout.setVisibility(View.VISIBLE);

            ArrayList<Entry> entries = new ArrayList<>();
            entries.add(new Entry(0, 1));
            entries.add(new Entry(1, 2));
            entries.add(new Entry(2, 0));
            entries.add(new Entry(3, 4));
            entries.add(new Entry(4, 3));
            entries.add(new Entry(5, 3));
            entries.add(new Entry(6, 3));

            LineDataSet lineDataSet = new LineDataSet(entries, "");

            lineDataSet.setLineWidth(2);
            lineDataSet.setCircleRadius(6);
            lineDataSet.setCircleColor(Color.parseColor("#FFA1B4DC"));
            lineDataSet.setCircleColorHole(Color.BLUE);
            lineDataSet.setColor(Color.parseColor("#FFA1B4DC"));
            lineDataSet.setDrawCircleHole(true);
            lineDataSet.setDrawCircles(true);
            lineDataSet.setDrawHorizontalHighlightIndicator(false);
            lineDataSet.setDrawHighlightIndicators(false);
            lineDataSet.setValueTextSize(12);

            LineData lineData = new LineData(lineDataSet);
            lineChart.setData(lineData);

            XAxis xAxis = lineChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextColor(Color.BLACK);
            xAxis.setDrawAxisLine(false);
            xAxis.setDrawGridLines(false);
            xAxis.setValueFormatter(new WeeklyXAxisValueFormatter(date[position]));

            YAxis yLAxis = lineChart.getAxisLeft();
            yLAxis.setDrawLabels(false);
            yLAxis.setDrawGridLines(false);
            yLAxis.setAxisLineColor(000000);

            YAxis yRAxis = lineChart.getAxisRight();
            yRAxis.setDrawLabels(false);
            yRAxis.setDrawAxisLine(false);
            yRAxis.setDrawGridLines(false);

            lineChart.getDescription().setEnabled(false);
            lineChart.getLegend().setEnabled(false);
            lineChart.setDoubleTapToZoomEnabled(false);
            lineChart.setDrawGridBackground(false);

            lineChart.animateY(2000, Easing.EasingOption.EaseInCubic);
            lineChart.invalidate();
        } else {
            chartLayout.setVisibility(View.GONE);
        }

    }

}
