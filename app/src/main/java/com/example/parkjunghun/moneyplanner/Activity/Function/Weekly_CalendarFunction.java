package com.example.parkjunghun.moneyplanner.Activity.Function;

import android.graphics.Color;
import android.util.Log;

import com.example.parkjunghun.moneyplanner.Activity.Adapter.WeeklyViewAdapter;
import com.example.parkjunghun.moneyplanner.Activity.Model.DetailMoneyInfo;
import com.example.parkjunghun.moneyplanner.Activity.Util.DatabaseManager;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class Weekly_CalendarFunction {

    Calendar cal = Calendar.getInstance();
    Calendar bcal = Calendar.getInstance();
    Calendar acal = Calendar.getInstance();

    private DetailMoneyInfo data;
    private int usingmoney;
    private String selectDate;

    String date[];
    String db_date[];

    // 리스트뷰에 월별로 일주일씩 나누는 함수 그리고 리스트뷰에 ADD
    public void sep_Calendar(WeeklyViewAdapter weekly_Adapter, int year, int month) {
        String from, until;
        int day = 1, count = 1;
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        int firstDay = cal.get(Calendar.DAY_OF_WEEK);

        bcal.set(Calendar.YEAR, year);
        bcal.set(Calendar.MONTH, month - 2);
        if (bcal.get(Calendar.MONTH) == -1) {
            bcal.set(Calendar.YEAR, year - 1);
            bcal.set(Calendar.MONTH, 11);
        }

        acal.set(Calendar.YEAR, year);
        acal.set(Calendar.MONTH, month + 2);
        if(acal.get(Calendar.MONTH) == 13){
            acal.set(Calendar.YEAR, year +1);
            acal.set(Calendar.MONTH, 0);
        }

        int max_Day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int before_Max_Day = bcal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int db_before_Max_Day = bcal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int after_Day = 1;
        int db_after_Day = 1;

        while (max_Day-- > 0) {
            date = new String[7];
            db_date = new String[7];
            if (cal.get(Calendar.DAY_OF_WEEK) == 7) {
                if (cal.get(Calendar.DAY_OF_MONTH) == 1) {
                    int k = day;
                    int db_k = day;
                    date[6] = (cal.get(Calendar.MONTH) + 1) + "." + k;
                    db_date[6] = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + db_k;
                    for (int i = 5; i >= 0; i--) {
                        date[i] = (bcal.get(Calendar.MONTH) + 1) + "." + before_Max_Day--;
                        db_date[i] = bcal.get(Calendar.YEAR) + "-" + (bcal.get(Calendar.MONTH) + 1) + "-" + db_before_Max_Day--;
                        DatabaseManager.getInstance().getMoneyChart(db_date[i]);
                    }

                    from = (cal.get(Calendar.MONTH) + 1) + "월" + cal.get(Calendar.DAY_OF_MONTH) + "일";
                    weekly_Adapter.addItem(from + "",
                            cal.get(Calendar.WEEK_OF_MONTH) + "째주", 40000 + "원", date, db_date);
                    day = count + 1;
                } else {
                    int k = day;
                    int db_k = day;
                    if (k == 1) {
                        switch (firstDay) {
                            case 1:
                                for (int i = 0; i < date.length; i++) {
                                    date[i] = (cal.get(Calendar.MONTH) + 1) + "." + k++;
                                    db_date[i] = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + db_k++;
                                }
                                break;
                            case 2:
                                date[0] = (bcal.get(Calendar.MONTH) + 1) + "." + before_Max_Day--;
                                db_date[0] = bcal.get(Calendar.YEAR) + "-" + (bcal.get(Calendar.MONTH) + 1) + "-" + db_before_Max_Day--;
                                for (int i = 1; i < date.length; i++) {
                                    date[i] = (cal.get(Calendar.MONTH) + 1) + "." + k++;
                                    db_date[i] = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + db_k++;
                                }
                                break;
                            case 3:
                                for (int i = 1; i >= 0; i--) {
                                    date[i] = (bcal.get(Calendar.MONTH) + 1) + "." + before_Max_Day--;
                                    db_date[i] = bcal.get(Calendar.YEAR) + "-" + (bcal.get(Calendar.MONTH) + 1) + "-" + db_before_Max_Day--;
                                }
                                for (int i = 2; i < date.length; i++) {
                                    date[i] = (cal.get(Calendar.MONTH) + 1) + "." + k++;
                                    db_date[i] = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + db_k++;
                                }
                                break;
                            case 4:
                                for (int i = 2; i >= 0; i--) {
                                    date[i] = (bcal.get(Calendar.MONTH) + 1) + "." + before_Max_Day--;
                                    db_date[i] = bcal.get(Calendar.YEAR) + "-" + (bcal.get(Calendar.MONTH) + 1) + "-" + db_before_Max_Day--;
                                }
                                for (int i = 3; i < date.length; i++) {
                                    date[i] = (cal.get(Calendar.MONTH) + 1) + "." + k++;
                                    db_date[i] = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + db_k++;
                                }
                                break;
                            case 5:
                                for (int i = 3; i >= 0; i--) {
                                    date[i] = (bcal.get(Calendar.MONTH) + 1) + "." + before_Max_Day--;
                                    db_date[i] = bcal.get(Calendar.YEAR) + "-" + (bcal.get(Calendar.MONTH) + 1) + "-" + db_before_Max_Day--;
                                }
                                for (int i = 4; i < date.length; i++) {
                                    date[i] = (cal.get(Calendar.MONTH) + 1) + "." + k++;
                                    db_date[i] = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + db_k++;
                                }
                                break;
                            case 6:
                                for (int i = 4; i >= 0; i--) {
                                    date[i] = (bcal.get(Calendar.MONTH) + 1) + "." + before_Max_Day--;
                                    db_date[i] = bcal.get(Calendar.YEAR) + "-" + (bcal.get(Calendar.MONTH) + 1) + "-" + db_before_Max_Day--;
                                }
                                for (int i = 5; i < date.length; i++) {
                                    date[i] = (cal.get(Calendar.MONTH) + 1) + "." + k++;
                                    db_date[i] = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + db_k++;
                                }
                                break;
                            default:
                                break;
                        }
                    } else {
                        for (int i = 0; i < date.length; i++) {
                            date[i] = (cal.get(Calendar.MONTH) + 1) + "." + k++;
                            db_date[i] = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + db_k++;
                        }
                    }
                    from = (cal.get(Calendar.MONTH) + 1) + "월" + day + "일";
                    until = (cal.get(Calendar.MONTH) + 1) + "월" + cal.get(Calendar.DAY_OF_MONTH) + "일";
                    weekly_Adapter.addItem(from + " - " + until,
                            cal.get(Calendar.WEEK_OF_MONTH) + "째주", 40000 + "원", date,db_date);
                    day = count + 1;
                }
            } else if (cal.get(Calendar.DAY_OF_MONTH) == cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
                    int k = day;
                    int db_k = day;
                    for (int i = 0; i < date.length; i++) {
                        if (k > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                            if ((cal.get(Calendar.MONTH) + 2) == 13) {
                                date[i] = (acal.get(Calendar.MONTH)+1) + "." + after_Day++;
                                db_date[i] = acal.get(Calendar.YEAR) + "-" + (acal.get(Calendar.MONTH)+1) + "-" + db_after_Day++;
                                k++;
                            } else {
                                date[i] = (cal.get(Calendar.MONTH) + 2) + "." + after_Day++;
                                db_date[i] = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+2) + "-" + db_after_Day++;
                                k++;
                            }
                        } else {
                            date[i] = (cal.get(Calendar.MONTH) + 1) + "." + k++;
                            db_date[i] = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + db_k++;
                        }
                    }
                    from = (cal.get(Calendar.MONTH) + 1) + "월" + day + "일";
                    weekly_Adapter.addItem(from + "",
                            cal.get(Calendar.WEEK_OF_MONTH) + "째주", 40000 + "원", date,db_date);
                    break;
                } else {
                    int k = day;
                    int db_k = day;
                    for (int i = 0; i < date.length; i++) {
                        if (k > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                            if ((cal.get(Calendar.MONTH) + 2) == 13) {
                                date[i] = (acal.get(Calendar.MONTH)+1) + "." + after_Day++;
                                db_date[i] = acal.get(Calendar.YEAR) + "-" + (acal.get(Calendar.MONTH)+1) + "-" + db_after_Day++;
                                k++;
                            } else {
                                date[i] = (cal.get(Calendar.MONTH) + 2) + "." + after_Day++;
                                db_date[i] = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+2) + "-" + db_after_Day++;
                                k++;
                            }
                        } else {
                            date[i] = (cal.get(Calendar.MONTH) + 1) + "." + k++;
                            db_date[i] = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + db_k++;
                        }
                    }
                    from = (cal.get(Calendar.MONTH) + 1) + "월" + day + "일";
                    until = (cal.get(Calendar.MONTH) + 1) + "월" + cal.get(Calendar.DAY_OF_MONTH) + "일";
                    weekly_Adapter.addItem(from + " - " + until,
                            cal.get(Calendar.WEEK_OF_MONTH) + "째주", 40000 + "원", date,db_date);
                    break;
                }

            }
            cal.set(Calendar.DAY_OF_MONTH, ++count);
        }
    }


    // LINE-CHART 만드는 함수
    public void make_LineChart(LineChart lineChart, String date[], String db_date[]) {
        ArrayList<Entry> entries = new ArrayList<>();

        entries.add(new Entry(0, 1));

        Log.e("getMoneyChart","dddddddd");

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
        xAxis.setValueFormatter(new WeeklyXAxisValueFormatter(date));

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
    }
}
