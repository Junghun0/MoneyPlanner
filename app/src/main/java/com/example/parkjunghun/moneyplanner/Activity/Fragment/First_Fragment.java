package com.example.parkjunghun.moneyplanner.Activity.Fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.parkjunghun.moneyplanner.Activity.Adapter.CalendarRecyclerviewAdapter;
import com.example.parkjunghun.moneyplanner.Activity.Model.CalendarScrollEvent;
import com.example.parkjunghun.moneyplanner.Activity.Model.DetailMoneyInfo;
import com.example.parkjunghun.moneyplanner.Activity.Model.Weekly_Update_Event;
import com.example.parkjunghun.moneyplanner.Activity.Util.DatabaseManager;
import com.example.parkjunghun.moneyplanner.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

public class First_Fragment extends Fragment {

    private static final String TAG = "First_Fragment";
    InputMethodManager inputMethodManager;
    @BindView(R.id.plus_btn)
    ImageButton plus_btn;
    @BindView(R.id.compactcalendar_view)
    CompactCalendarView compactCalendarView;
    @BindView(R.id.main_recyclerview)
    RecyclerView main_recyclerview;

    private ActionBar toolbar;
    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.getDefault());
    private SimpleDateFormat dateFormatOnclick = new SimpleDateFormat("yyyy-MM-d", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("M", Locale.getDefault());
    private SimpleDateFormat dateFormatForYear = new SimpleDateFormat("yyyy", Locale.getDefault());
    private ArrayList<DetailMoneyInfo> dataList = new ArrayList<>();

    private ArrayList<String> eventDateList = new ArrayList<>();
    private ArrayList<Event> calendarEventList = new ArrayList<>();

    private boolean shouldShow = false;
    private String selectedDate;
    private String day_select;
    private CalendarRecyclerviewAdapter adapter;
    private DetailMoneyInfo data;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static First_Fragment newInstance2(DetailMoneyInfo detailMoneyInfo) {
        Bundle args = new Bundle();
        args.putSerializable("data", detailMoneyInfo);
        First_Fragment fragment = new First_Fragment();
        fragment.setArguments(args);
        return fragment;
    }


    public void getDate(){
       DatabaseManager.getInstance().getCalendarEvent(dateFormatOnclick.format(new Date()) , eventDateList, compactCalendarView);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onactivitycreated");
        //getDate();
        adapter = new CalendarRecyclerviewAdapter(this, dataList);
        main_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        main_recyclerview.setAdapter(adapter);

        sharedPreferences = getContext().getSharedPreferences("data", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (getArguments() != null) {
            //디비에 넣기
            data = (DetailMoneyInfo) getArguments().getSerializable("data");
            DatabaseManager.getInstance().setMoneyInfo(data);
        }

        //툴바
        toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        //달력
        loadEvents(12, 2018);
        //compactCalendarView.invalidate();
        //logEventsByMonth(compactCalendarView);

        compactCalendarView.setUseThreeLetterAbbreviation(false);

        day_select = sharedPreferences.getString("day_select","일요일").substring(0,1);

        Days_Select(day_select);

        compactCalendarView.setIsRtl(false);
        compactCalendarView.displayOtherMonthDays(true);

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {

            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> bookingsFromMap = compactCalendarView.getEvents(dateClicked);
                Log.d(TAG, "clicked date-> " + dateFormatOnclick.format(dateClicked));

                DatabaseManager.getInstance().getMoneyInfo(adapter, dateFormatOnclick.format(dateClicked));
                //+버튼 눌럿을때 선택된 날짜 던져줌
                selectedDate = dateFormatOnclick.format(dateClicked);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                EventBus.getDefault().post(new CalendarScrollEvent(Integer.valueOf(dateFormatForYear.format(firstDayOfNewMonth)),
                        Integer.valueOf(dateFormatForMonth.format(firstDayOfNewMonth))));

                //DatabaseManager.getInstance().getCalendarEvent(dateFormatOnclick.format(firstDayOfNewMonth) , eventDateList);
                //loadEvents(Integer.valueOf(dateFormatForMonth.format(firstDayOfNewMonth)),Integer.valueOf(dateFormatForYear.format(firstDayOfNewMonth)));
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment_layout, container, false);
        ButterKnife.bind(this, view);
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
        Log.e(TAG, "oncreateview");
        return view;
    }

    @OnClick(R.id.plus_btn)
    public void onClickplus() {
        if (selectedDate != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.main_view2, Add_First_Fragment.newInstance(selectedDate)).addToBackStack(null).commit();
            adapter.clearItem(dataList);
        } else {
            Toast.makeText(getActivity(), "날짜를 선택해주세요", Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void moveCalendar(Weekly_Update_Event event) {
        if (event.update.equals("true")) {
            compactCalendarView.scrollLeft();
        } else if(event.update.equals("false")){
            compactCalendarView.scrollRight();
        } else if(event.update.equals("update")){
            loadEvents(event.month, event.year);
            String a = sharedPreferences.getString("day_select","일요일").substring(0,1);
            Days_Select(a);
        }
    }

    private void loadEvents(int month, int year) {
        Log.i(TAG, "loadEvents method... month-"+month+"year"+year);
        addEvents(month, year);
    }

    private void loadEventsForYear(int year) {
        addEvents(Calendar.DECEMBER, year);
        addEvents(Calendar.AUGUST, year);
    }

    private void addEvents(int month, int year) {
        Log.i(TAG, "addEvents method...");
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, month);
        Date firstDayOfMonth = currentCalender.getTime();
        if(eventDateList.size() > 0 ){
            for(String date : eventDateList){
                currentCalender.setTime(firstDayOfMonth);
                if (month > -1) {
                    currentCalender.set(Calendar.MONTH, month);
                }
                if (year > -1) {
                    currentCalender.set(Calendar.ERA, GregorianCalendar.AD);
                    currentCalender.set(Calendar.YEAR, year);
                }

                int eventdate = Integer.parseInt(date);
                setToMidnight(currentCalender);
                currentCalender.add(Calendar.DATE, eventdate);

                long timeMillis = currentCalender.getTimeInMillis();
                List<Event> eventList = getEvent(timeMillis, eventdate);
                compactCalendarView.addEvents(eventList);
            }
        }

    }

    private List<Event> getEvent(long timeInMillis, int day) {

        List<Event> addeventlist = new ArrayList<>();
        if(eventDateList.size() > 0){
            Log.e(TAG,"at get Event size"+eventDateList.size());
            for(int i = 0 ; i < eventDateList.size(); i++){
                addeventlist.add(new Event(Color.argb(200, 100, 68, 65), timeInMillis));
            }
        }
        return addeventlist;
    }

    private void logEventsByMonth(CompactCalendarView compactCalendarView) {
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        currentCalender.set(Calendar.MONTH, Calendar.AUGUST);
        List<String> dates = new ArrayList<>();
        for (Event e : compactCalendarView.getEventsForMonth(new Date())) {
            dates.add(dateFormatForDisplaying.format(e.getTimeInMillis()));
        }
        Log.i(TAG, "logEventsByMonth method...");
        Log.d(TAG, "Events for Aug with simple date formatter: " + dates);
        Log.d(TAG, "Events for Aug month using default local and timezone: " + compactCalendarView.getEventsForMonth(currentCalender.getTime()));
    }

    private void setToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "ondetach");
        try {
            EventBus.getDefault().unregister(this);
        } catch (Exception e) {
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onstart");
        try {
            EventBus.getDefault().register(this);
        } catch (Exception e) {
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getArguments() != null) {
            //+버튼으로 데이터 추가할때
            DatabaseManager.getInstance().getMoneyInfo(adapter, data.getSelectDate());
        }

        if (getActivity().getIntent().getExtras() != null && getArguments() == null) {
            //데이터 지웠을때
            DatabaseManager.getInstance().deleteMoneyInfo2(adapter, getActivity().getIntent().getStringExtra("key"),
                    getActivity().getIntent().getStringExtra("date"), getActivity().getIntent().getIntExtra("index", 0));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "ondestroy");
    }

    public void Days_Select(String st){
        switch (st){
            case "일": compactCalendarView.setFirstDayOfWeek(Calendar.SUNDAY);       break;
            case "월": compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);       break;
            case "화": compactCalendarView.setFirstDayOfWeek(Calendar.TUESDAY);      break;
            case "수": compactCalendarView.setFirstDayOfWeek(Calendar.WEDNESDAY);    break;
            case "목": compactCalendarView.setFirstDayOfWeek(Calendar.THURSDAY);     break;
            case "금": compactCalendarView.setFirstDayOfWeek(Calendar.FRIDAY);       break;
            case "토": compactCalendarView.setFirstDayOfWeek(Calendar.SATURDAY);     break;
        }
    }

}
