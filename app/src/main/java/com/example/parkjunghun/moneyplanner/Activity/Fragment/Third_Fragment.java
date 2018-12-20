package com.example.parkjunghun.moneyplanner.Activity.Fragment;

import android.graphics.Color;
import java.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkjunghun.moneyplanner.Activity.Model.Weekly_Update;
import com.example.parkjunghun.moneyplanner.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class Third_Fragment extends Fragment {

    private HorizontalCalendar horizontalCalendar;
    private TextView textView;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.third_fragment_layout, container, false);

            //textView = (TextView)getActivity().findViewById(R.id.current_month);
            Log.e("MainActivity",Calendar.getInstance().toString());
            Calendar start = Calendar.getInstance();
            start.add(Calendar.MONTH,-10);
            Calendar end = Calendar.getInstance();
            end.add(Calendar.MONTH,10);

        Log.e("MainActivity",start.toString());

        //달력 만들어주는 시작월과 끝나는월 입력 후 붙이기
        horizontalCalendar = new HorizontalCalendar.Builder(view, R.id.calendarView)
                .range(start,end)
                .datesNumberOnScreen(7)
                .configure()
                .formatMiddleText("EEE")
                .formatBottomText("dd")
                .textSize(0f, 8f, 16f)
                .showTopText(false)
                .showBottomText(true)
                .textColor(Color.BLACK, Color.RED)
                .end()
                //화면이 구성될때 표시되는 날짜!!
                //.defaultSelectedDate(start)
                .build();

//        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
//        @Override
//        public void onDateSelected(Calendar date, int position) {
//            textView = (TextView)getActivity().findViewById(R.id.current_month);
//            if(date.get(Calendar.DAY_OF_MONTH) > 0){
//                textView.setText(date.get(Calendar.YEAR) + "년 " + Integer.toString(date.get(Calendar.MONTH)+1) + "월");
//            }
//            Toast.makeText(getContext(), DateFormat.format("EEE, MMM d, yyyy", date) + " is selected!", Toast.LENGTH_SHORT).show();
//           Log.e("MainActivity",date.get(Calendar.MONTH)+1 + " " + date.get(Calendar.DAY_OF_MONTH) + " " + date.get(Calendar.YEAR));
//        }
//    });
        return view;
}

//    public void Date_Update(){
//        /*horizontalCalendar.getConfig().setFormatBottomText("dd")
//                .setFormatMiddleText("MMM")
//                .setShowBottomText(false);
//        horizontalCalendar.refresh();*/
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void testEvent(Weekly_Update event){
//        if(event.update.equals("true")) {
//            Date_Update();
//           Log.e("test_Event",event.update+" "+event.year+" "+event.month);
//        }
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        try{
//            EventBus.getDefault().register(this);
//        }catch (Exception e){}
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        try{
//            EventBus.getDefault().unregister(this);
//        }catch (Exception e){}
//    }
}