package com.example.parkjunghun.moneyplanner.Activity.WC_Function;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

import com.example.parkjunghun.moneyplanner.Activity.Adapter.WeeklyViewAdapter;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Calendar;

public class Weekly_CalendarFunction {

    Calendar cal ;
    private String date[][];

    public Weekly_CalendarFunction(){
        cal = Calendar.getInstance();
    }

    public void sep_Calendar(WeeklyViewAdapter weekly_Adapter, int year, int month) {
        String from, until;
        int day = 1, count = 1, i = 0;
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        date = new String[7][7];
        int max_Day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, max_Day);

        cal.set(Calendar.DAY_OF_MONTH, day);

        while (max_Day-- > 0) {
            if (cal.get(Calendar.DAY_OF_WEEK) == 7) {
                if (cal.get(Calendar.DAY_OF_MONTH) == 1) {
                    from = (cal.get(Calendar.MONTH) + 1) + "월" + cal.get(Calendar.DAY_OF_MONTH) + "일";
                    weekly_Adapter.addItem(from + "", cal.get(Calendar.WEEK_OF_MONTH) + "째주", 40000 + "원");
                    date[i][0] = (cal.get(Calendar.MONTH) + 1) + ".1";
                    ++i;
                    day = count + 1;
                } else {
                    from = (cal.get(Calendar.MONTH) + 1) + "월" + day + "일";
                    until = (cal.get(Calendar.MONTH) + 1) + "월" + cal.get(Calendar.DAY_OF_MONTH) + "일";
                    weekly_Adapter.addItem(from + " - " + until, cal.get(Calendar.WEEK_OF_MONTH) + "째주", 40000 + "원");
                    for (int j = 0; j < 7 ; j++) {
                        date[i][j] = (cal.get(Calendar.MONTH) + 1) + "." + (day++);
                    }
                    ++i;
                    day = count + 1;
                }
            } else if (cal.get(Calendar.DAY_OF_MONTH) == cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
                    from = (cal.get(Calendar.MONTH) + 1) + "월" + day + "일";
                    weekly_Adapter.addItem(from + "", cal.get(Calendar.WEEK_OF_MONTH) + "째주", 40000 + "원");
                    date[i][0] = (cal.get(Calendar.MONTH) + 1) + "." + day;
                    ++i;
                    break;
                } else {
                    from = (cal.get(Calendar.MONTH) + 1) + "월" + day + "일";
                    until = (cal.get(Calendar.MONTH) + 1) + "월" + cal.get(Calendar.DAY_OF_MONTH) + "일";
                    weekly_Adapter.addItem(from + " - " + until, cal.get(Calendar.WEEK_OF_MONTH) + "째주", 40000 + "원");

                    for (int j = 0; j < 7; j++) {
                        if(day == cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                            date[i][j] = (cal.get(Calendar.MONTH) + 1) + "." + day;
                            if(cal.get(Calendar.MONTH) == 11) {
                                cal.set(Calendar.YEAR, year + 1);
                                cal.set(Calendar.MONTH, 0);
                            } else {
                                cal.set(cal.MONTH, cal.get(Calendar.MONTH)+1);
                            }
                            day = 1;
                        } else {
                            date[i][j] = (cal.get(Calendar.MONTH) + 1) + "." + (day++);
                        }
                    }
                    ++i;
                    break;
                }

            }
            cal.set(Calendar.DAY_OF_MONTH, ++count);
        }


    }

    public void make_LineChart(LineChart lineChart, LinearLayout chartLayout, int position) {

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
