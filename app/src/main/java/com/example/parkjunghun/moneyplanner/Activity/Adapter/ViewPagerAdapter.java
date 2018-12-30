package com.example.parkjunghun.moneyplanner.Activity.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.parkjunghun.moneyplanner.Activity.Fragment.First_Fragment;
import com.example.parkjunghun.moneyplanner.Activity.Fragment.Fourth_Fragment;
import com.example.parkjunghun.moneyplanner.Activity.Fragment.Weekly_Calendar_Fragment;
import com.example.parkjunghun.moneyplanner.Activity.Fragment.Third_Fragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    Context context;
    private Third_Fragment third_fragment;

    public ViewPagerAdapter(FragmentManager fm, Context context,Third_Fragment third_fragment) {
        super(fm);
        this.context = context;
        this.third_fragment = third_fragment;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0 :
                return new First_Fragment();
            case 1:
                return new Weekly_Calendar_Fragment();
            case 2:
                return third_fragment;
            case 3:
                return new Fourth_Fragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
