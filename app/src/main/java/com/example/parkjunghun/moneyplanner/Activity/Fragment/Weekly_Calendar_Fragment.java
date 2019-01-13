package com.example.parkjunghun.moneyplanner.Activity.Fragment;

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

    Calendar mCal;
    WeeklyViewAdapter weekly_Adapter;
    Weekly_CalendarFunction function;

    int clickposition = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.weekly_fragment, container, false);
        ButterKnife.bind(this, view);

        mCal = Calendar.getInstance();
        function = new Weekly_CalendarFunction();
        weekly_Adapter = new WeeklyViewAdapter();

        function.sep_Calendar(weekly_Adapter, mCal.get(Calendar.YEAR), mCal.get(Calendar.MONTH) + 1);

        //리스트뷰 SET
        weekly_ListView.setAdapter(weekly_Adapter);
        //리스트뷰 아이템 클릭시
        weekly_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lineChart = (LineChart) view.findViewById(R.id.chart);

                if(clickposition > -1){
                    int viewposition = clickposition - weekly_ListView.getFirstVisiblePosition();
                    if(viewposition > -1 && viewposition < weekly_ListView.getChildCount()) {
                        View perviousView = weekly_ListView.getChildAt(viewposition);
                        LinearLayout chart_Layout = (LinearLayout) perviousView.findViewById(R.id.chart_layout);
                        chart_Layout.setVisibility(View.GONE);
                    }
                }
                LinearLayout chart_Layout  = (LinearLayout)view.findViewById(R.id.chart_layout);
                chart_Layout.setVisibility(View.VISIBLE);
                weekly_Adapter.show_Chart(i, lineChart);

                clickposition = i;
            }

        });

        return view;
    }

    //메인 엑티비티 날짜 옆으로 눌렀을때
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
