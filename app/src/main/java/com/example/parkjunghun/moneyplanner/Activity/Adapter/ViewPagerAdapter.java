package com.example.parkjunghun.moneyplanner.Activity.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.parkjunghun.moneyplanner.Activity.Fragment.First_Fragment;
import com.example.parkjunghun.moneyplanner.Activity.Fragment.Fourth_Fragment;
import com.example.parkjunghun.moneyplanner.Activity.Fragment.Second_Fragment;
import com.example.parkjunghun.moneyplanner.Activity.Fragment.Third_Fragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    Context context;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0 :
                return new First_Fragment();
            case 1:
                return new Second_Fragment();
            case 2:
                return new Third_Fragment();
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
