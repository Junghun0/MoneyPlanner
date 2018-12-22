package com.example.parkjunghun.moneyplanner.Activity.WC_Function;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class WeeklyYAxisValueFormatter implements IAxisValueFormatter {

    private String[] mYValues;

    public WeeklyYAxisValueFormatter(String[] mYValues) {
        this.mYValues = mYValues;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        return mYValues[(int) value];
    }
}