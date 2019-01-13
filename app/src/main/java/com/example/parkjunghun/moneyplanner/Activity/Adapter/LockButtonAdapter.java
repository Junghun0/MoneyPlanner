package com.example.parkjunghun.moneyplanner.Activity.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class LockButtonAdapter extends BaseAdapter {

    Context context = null;
    String[] buttonNames = null;
    private OnMyItemCheckedChanged onMyItemCheckedChanged;

    public interface OnMyItemCheckedChanged{
        void onITemCheckedChanged(String[] arr,int checked);
    }

    public void setOnMyItemCheckedChanged(OnMyItemCheckedChanged onMyItemCheckedChanged){
        this.onMyItemCheckedChanged = onMyItemCheckedChanged;
    }

    public LockButtonAdapter(Context context, String[] buttonNames) {
        this.context = context;
        this.buttonNames = buttonNames;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Button button = null;

        if (null != convertView) {
            button = (Button)convertView;
        }
        else {
            button = new Button(context);
            button.setText(buttonNames[position]);
            button.setBackgroundColor(Color.WHITE);
            button.setTextSize(20);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onMyItemCheckedChanged != null){
                        onMyItemCheckedChanged.onITemCheckedChanged(buttonNames,position);
                    }
                }
            });
        }
        return button;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return (null != buttonNames) ? buttonNames.length : 0;
    }

    @Override
    public Object getItem(int position) {
        return buttonNames[position];
    }
}
