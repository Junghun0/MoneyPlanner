package com.example.parkjunghun.moneyplanner.Activity.Fragment;

import android.graphics.Color;

import java.util.Calendar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.parkjunghun.moneyplanner.Activity.Model.Weekly_Update;
import com.example.parkjunghun.moneyplanner.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class Third_Fragment extends Fragment {

    private HorizontalCalendar horizontalCalendar;
    private HorizontalCalendar.Builder builder;
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.third_fragment_layout, container, false);
        textView = (TextView)getActivity().findViewById(R.id.current_month);
        Calendar start = Calendar.getInstance();
        start.add(Calendar.YEAR,-10);
        Calendar end = Calendar.getInstance();
        end.add(Calendar.YEAR,10);

        builder = new HorizontalCalendar.Builder(view,R.id.calendarView)
                .range(start,end)
                .datesNumberOnScreen(7)
                .configure()
                .formatTopText("EEE")
                .formatMiddleText("dd")
                .formatBottomText("●")
                .textSize(10f, 20f, 7f)
                .showTopText(true)
                .showBottomText(true)
                .textColor(Color.BLACK, Color.RED)
                .end()
                    /*.addEvents(new CalendarEventsPredicate() {
                        @Override
                        public List<CalendarEvent> events(Calendar date) {
                            List<CalendarEvent> events = new ArrayList<>();
                            Random rnd = new Random();
                            int count = rnd.nextInt(2);
                            for (int i = 0; i <= count; i++){
                                events.add(new CalendarEvent(Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)), "event"));
                            }
                            return events;
                        }
                    })*/;

        horizontalCalendar = builder.build();
        //달력 만들어주는 시작월과 끝나는월 입력 후 붙이기
             /*   horizontalCalendar = new HorizontalCalendar.Builder(view, R.id.calendarView)
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
                .addEvents(new CalendarEventsPredicate() {
                        @Override
                        public List<CalendarEvent> events(Calendar date) {

                            return null;
                        }
                })
                //화면이 구성될때 표시되는 날짜!!
                //.defaultSelectedDate(start)
                .build();*/

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                textView = (TextView)getActivity().findViewById(R.id.current_month);
                if(date.get(Calendar.DAY_OF_MONTH) > 0){
                    textView.setText(date.get(Calendar.YEAR) + "년 " + Integer.toString(date.get(Calendar.MONTH)+1) + "월");
                    Log.e("Third_Fragment","여기 뭐임");
                /*builder.addEvents(new CalendarEventsPredicate() {
                    @Override
                    public List<CalendarEvent> events(Calendar date) {
                        List<CalendarEvent> events = new ArrayList<>();
                        Random rnd = new Random();
                        int count = rnd.nextInt(6);
                        for (int i = 0; i <= count; i++){
                            events.add(new CalendarEvent(Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)), "event"));
                        }
                        return events;
                    }
                });*/
                    //EventBus.getDefault().post(new CalendarScrollEvent(date.get(Calendar.YEAR),date.get(Calendar.MONTH)+1));
                }
            }
        });
        return view;
    }

    public void Date_Update(int year, int month, boolean check){
        if(check == true){
            Log.e("Third_Fragment","여기들어옴");
        }else{
            Calendar startC = Calendar.getInstance();
            startC.set(Calendar.YEAR,year);
            startC.set(Calendar.MONTH,month-1);
            startC.set(Calendar.DAY_OF_MONTH,1);
//                Calendar endC = Calendar.getInstance();
//                endC.set(Calendar.YEAR,year);
//                endC.set(Calendar.MONTH,month);
            horizontalCalendar.selectDate(startC,false);
            Log.e("Third_Fragment",startC.get(Calendar.MONTH) + " " + startC.get(Calendar.DAY_OF_MONTH));
//                horizontalCalendar.setRange(startC,endC);
            horizontalCalendar.refresh();
            Log.e("Third_Fragment","여기들어옴1111");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("Third_Fragment","okokokokokokok");
        super.onCreate(savedInstanceState);
    }

    @Subscribe
    public void testEvent(Weekly_Update event) {
        Log.e("Third_Fragment",event.year + " " + event.month);
        Date_Update(event.year, event.month, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        try{
            EventBus.getDefault().register(this);
        }catch (Exception e){}
    }

    @Override
    public void onStop() {
        super.onStop();
        try{
            EventBus.getDefault().unregister(this);
        }catch (Exception e){}
    }
}
