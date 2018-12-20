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
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;

import com.example.parkjunghun.moneyplanner.Activity.Adapter.CalendarRecyclerviewAdapter;
import com.example.parkjunghun.moneyplanner.Activity.Model.CalendarEvent;
import com.example.parkjunghun.moneyplanner.Activity.Model.CalendarScrollEvent;
import com.example.parkjunghun.moneyplanner.Activity.Model.DetailMoneyInfo;
import com.example.parkjunghun.moneyplanner.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
    private SimpleDateFormat dateFormatOnclick = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("M", Locale.getDefault());
    private SimpleDateFormat dateFormatForYear = new SimpleDateFormat("yyyy", Locale.getDefault());


    private boolean shouldShow = false;
    private String selectedDate;
    private DetailMoneyInfo detailMoneyInfo;

    public static First_Fragment newInstance(String type, String selectDate, int usingMoney) {
        
        Bundle args = new Bundle();
        args.putString("type",type);
        args.putString("selectDate",selectDate);
        args.putInt("usingMoney",usingMoney);
        First_Fragment fragment = new First_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment_layout, container, false);
        ButterKnife.bind(this, view);
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);

        detailMoneyInfo = new DetailMoneyInfo();

        if(getArguments() != null){
            String bundle1 = getArguments().getString("type");
            String bundle2 = getArguments().getString("selectDate");
            int bundle3 = getArguments().getInt("usingMoney");

            detailMoneyInfo = new DetailMoneyInfo("keyvalue",bundle1,bundle2,bundle3);
        }

        final List<String> mutableBookings = new ArrayList<>();
        final ArrayList<String> detailMoneyInfoArrayList = new ArrayList<>();
        loadEvents();
        compactCalendarView.invalidate();
        logEventsByMonth(compactCalendarView);

        final CalendarRecyclerviewAdapter adapter = new CalendarRecyclerviewAdapter(this);
        main_recyclerview.setAdapter(adapter);

        compactCalendarView.setUseThreeLetterAbbreviation(false);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.setIsRtl(false);
        compactCalendarView.displayOtherMonthDays(false);

        toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> bookingsFromMap = compactCalendarView.getEvents(dateClicked);

                Log.d(TAG, "clicked date->" + dateFormatOnclick.format(dateClicked)+"dateclicked->"+dateClicked);
                if (bookingsFromMap != null) {
                    mutableBookings.clear();
                    detailMoneyInfoArrayList.clear();
                    for (Event booking : bookingsFromMap) {
                        mutableBookings.add((String) booking.getData());
                        detailMoneyInfoArrayList.add((String)booking.getData());
                        adapter.setItem(detailMoneyInfoArrayList);
                    }
                    adapter.notifyDataSetChanged();
                }
                selectedDate = dateFormatOnclick.format(dateClicked);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                EventBus.getDefault().post(new CalendarScrollEvent(Integer.valueOf(dateFormatForYear.format(firstDayOfNewMonth)),
                        Integer.valueOf(dateFormatForMonth.format(firstDayOfNewMonth))));
            }
        });
        return view;
    }

    @OnClick(R.id.plus_btn)
    public void onClickplus(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_view, new Add_First_Fragment(selectedDate)).addToBackStack(null).commit();
    }

    @Subscribe
    public void moveCalendar(CalendarEvent event) {
        if (event.isChange()) {
            compactCalendarView.scrollRight();
        } else {
            compactCalendarView.scrollLeft();
        }
    }

    private void loadEvents() {
        Log.i(TAG,"loadEvents method...");
        addEvents(-1, -1);
        addEvents(Calendar.DECEMBER, -1);
        addEvents(Calendar.AUGUST, -1);
    }

    private void loadEventsForYear(int year) {
        addEvents(Calendar.DECEMBER, year);
        addEvents(Calendar.AUGUST, year);
    }

    private void addEvents(int month, int year) {
        Log.i(TAG,"addEvents method...");
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = currentCalender.getTime();
        //여기서 달력에 event 날짜 추가
        for (int i = 0; i < 6; i++) {
            currentCalender.setTime(firstDayOfMonth);
            if (month > -1) {
                currentCalender.set(Calendar.MONTH, month);
            }
            if (year > -1) {
                currentCalender.set(Calendar.ERA, GregorianCalendar.AD);
                currentCalender.set(Calendar.YEAR, year);
            }
            currentCalender.add(Calendar.DATE, i);
            setToMidnight(currentCalender);

            long timeInMillis = currentCalender.getTimeInMillis();
            List<Event> events = getEvent(timeInMillis, i, detailMoneyInfo);
            compactCalendarView.addEvents(events);

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
        Log.i(TAG,"logEventsByMonth method...");
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
        Log.i(TAG,"getEvents method...");
        if (day < 2) {
            return Arrays.asList(new Event(Color.argb(200,152,222,96),timeInMillis,"shibal.."+detailMoneyInfo.getUsingMoney()));
        } else if (day > 2 && day <= 4) {
            return Arrays.asList(
                    new Event(Color.argb(200, 200, 200, 200), timeInMillis, "Custom Event@@"),
                    new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis)),
                    new Event(Color.argb(255, 100, 68, 65), timeInMillis, "Event 2 at " + new Date(timeInMillis)));
        } else {
            return Arrays.asList(
                    new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis)),
                    new Event(Color.argb(255, 100, 68, 65), timeInMillis, "Event 2 at " + new Date(timeInMillis)),
                    new Event(Color.argb(255, 70, 68, 65), timeInMillis, "Event 3 at " + new Date(timeInMillis)));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            EventBus.getDefault().unregister(this);
        } catch (Exception e) {
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            EventBus.getDefault().register(this);
        } catch (Exception e) {
        }
    }
}
