package com.example.parkjunghun.moneyplanner.Activity.Fragment;

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
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    private boolean shouldShow = false;
    private String selectedDate;
    CalendarRecyclerviewAdapter adapter;
    private DetailMoneyInfo data;
        
    public static First_Fragment newInstance2(DetailMoneyInfo detailMoneyInfo) {
        Bundle args = new Bundle();
        args.putSerializable("data",detailMoneyInfo);
        First_Fragment fragment = new First_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i(TAG, "on activitycreated");

        adapter = new CalendarRecyclerviewAdapter(this, dataList);
        main_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        main_recyclerview.setAdapter(adapter);

        if (getArguments() != null) {
            //디비에 넣기
            Log.e("first","getarguments not null......");
            data = (DetailMoneyInfo)getArguments().getSerializable("data");
            DatabaseManager.getInstance().setMoneyInfo(data);
        }

        final List<String> mutableBookings = new ArrayList<>();
        final ArrayList<String> detailMoneyInfoArrayList = new ArrayList<>();
        loadEvents();
        compactCalendarView.invalidate();
        logEventsByMonth(compactCalendarView);

        compactCalendarView.setUseThreeLetterAbbreviation(false);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.setIsRtl(false);
        compactCalendarView.displayOtherMonthDays(false);

        toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {

            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> bookingsFromMap = compactCalendarView.getEvents(dateClicked);
                Log.d(TAG, "clicked date->" + dateFormatOnclick.format(dateClicked) + ",,dateclicked->" + dateClicked);

                if (bookingsFromMap != null) {
                    mutableBookings.clear();
                    detailMoneyInfoArrayList.clear();
                    for (Event booking : bookingsFromMap) {
                        mutableBookings.add((String) booking.getData());
                        detailMoneyInfoArrayList.add((String) booking.getData());
                    }
                }

                DatabaseManager.getInstance().getMoneyInfo(adapter, dateFormatOnclick.format(dateClicked));

                //+버튼 눌럿을때 선택된 날짜 던져줌
                selectedDate = dateFormatOnclick.format(dateClicked);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                EventBus.getDefault().post(new CalendarScrollEvent(Integer.valueOf(dateFormatForYear.format(firstDayOfNewMonth)),
                        Integer.valueOf(dateFormatForMonth.format(firstDayOfNewMonth))));
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment_layout, container, false);
        ButterKnife.bind(this, view);
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
        Log.i(TAG, "oncreateview");
        return view;
    }

    @OnClick(R.id.plus_btn)
    public void onClickplus() {
        if (selectedDate != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_view2, Add_First_Fragment.newInstance(selectedDate)).addToBackStack(null).commit();
            adapter.clearItem(dataList);
        } else {
            Toast.makeText(getActivity(), "날짜를 선택해주세요", Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void moveCalendar(Weekly_Update_Event event) {
        if (event.update.equals("true")) {
            compactCalendarView.scrollLeft();
        } else {
            compactCalendarView.scrollRight();
        }
    }

    private void loadEvents() {
        Log.i(TAG, "loadEvents method...");
        addEvents(-1, -1);
        //addEvents(Calendar.DECEMBER, -1);
        //addEvents(Calendar.AUGUST, -1);
    }

    private void loadEventsForYear(int year) {
        addEvents(Calendar.DECEMBER, year);
        addEvents(Calendar.AUGUST, year);
    }

    private void addEvents(int month, int year) {
        Log.i(TAG, "addEvents method...");
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = currentCalender.getTime();
        //여기서 달력에 event 추가되는 일
//        for (int i = 0; i < 6; i++) {
//            currentCalender.setTime(firstDayOfMonth);
//            if (month > -1) {
//                currentCalender.set(Calendar.MONTH, month);
//            }
//            if (year > -1) {
//                currentCalender.set(Calendar.ERA, GregorianCalendar.AD);
//                currentCalender.set(Calendar.YEAR, year);
//            }
//
//            currentCalender.add(Calendar.DATE, i);
//            setToMidnight(currentCalender);
//
//            long timeInMillis = currentCalender.getTimeInMillis();
//            List<Event> events = getEvent(timeInMillis, i, detailMoneyInfo);
//            compactCalendarView.addEvents(events);
//        }


            if(data != null) {

                Log.i(TAG, "addEvents method...");

                currentCalender.setTime(firstDayOfMonth);
                setToMidnight(currentCalender);

                String date = data.getSelectDate();
                String datearray[] = date.split("-");

                currentCalender.add(Calendar.DATE, Integer.parseInt(datearray[2]) - 1);
                long time = currentCalender.getTimeInMillis();

                List<Event> eventList = getEvent(time, Integer.parseInt(datearray[2]) - 1, data);
                compactCalendarView.addEvents(eventList);
            }

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

    private List<Event> getEvent(long timeInMillis, int day, DetailMoneyInfo detailMoneyInfo) {

        List<Event> addeventlist = new ArrayList<>();

        String date = detailMoneyInfo.getSelectDate();
        String datearray[] = date.split("-");

        Log.i(TAG, "!getEvent method..." + day + " , " + (Integer.parseInt(datearray[2]) - 1) + "size->" + addeventlist.size());

        Event event = new Event(Color.argb(200, 100, 68, 65), timeInMillis, detailMoneyInfo.getType());
        addeventlist.add(event);

        return addeventlist;
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
        Log.i(TAG, "onresume datalist size"+dataList.size());
        if (getArguments() != null) {
            DatabaseManager.getInstance().getMoneyInfo(adapter, data.getSelectDate());
        }

        if(getActivity().getIntent().getExtras() != null && getArguments() == null){
            getActivity().getIntent().getIntExtra("index",0);
            Log.e(TAG,"getintetn->>"+getActivity().getIntent().getIntExtra("index",0));
            DatabaseManager.getInstance().getMoneyInfo(adapter, getActivity().getIntent().getStringExtra("date"));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "ondestroy");
    }

}
