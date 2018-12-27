package com.example.parkjunghun.moneyplanner.Activity.Function;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class WeeklyXAxisValueFormatter implements IAxisValueFormatter {

    private String[] mXValues;

    public WeeklyXAxisValueFormatter(String[] mXValues) {
        this.mXValues = mXValues;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        return mXValues[(int) value];
    }
}