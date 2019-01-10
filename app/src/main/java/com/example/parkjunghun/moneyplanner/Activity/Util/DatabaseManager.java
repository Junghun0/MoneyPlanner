package com.example.parkjunghun.moneyplanner.Activity.Util;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.parkjunghun.moneyplanner.Activity.Adapter.CalendarRecyclerviewAdapter;
import com.example.parkjunghun.moneyplanner.Activity.Adapter.ScheduleRecyclerviewAdapter;
import com.example.parkjunghun.moneyplanner.Activity.Model.DetailMoneyInfo;
import com.example.parkjunghun.moneyplanner.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import devs.mulham.horizontalcalendar.HorizontalCalendar;

public class DatabaseManager {

    public static DatabaseManager instance = new DatabaseManager();

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usinginfo_databaseReference;
    private DatabaseReference calendar_eventReference;
    private String key;
    private String childKeyvalue;
    private String money;
    private ArrayList<DetailMoneyInfo> detailMoneyInfoList = new ArrayList<>();
    private ArrayList<DetailMoneyInfo> InMoneyList = new ArrayList<>();
    private ArrayList<DetailMoneyInfo> OutMoneyList = new ArrayList<>();
    private ScheduleRecyclerviewAdapter InAdapter;
    private ScheduleRecyclerviewAdapter OutAdapter;
    private HorizontalCalendar horizontalCalendar1;
    private NumberFormat numberFormat;
    private TextView Income;
    private TextView Outlay;
    private TextView textView;
    private String [] arr;
    private CompactCalendarView compactCalendarView;
    private int sum = 0;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-d hh:mm:ss");

    public static DatabaseManager getInstance() {
        return instance;
    }

    private DatabaseManager() {
        key = FirebaseInstanceId.getInstance().getToken();
        firebaseDatabase = FirebaseDatabase.getInstance();
        usinginfo_databaseReference = firebaseDatabase.getReference("using_info");
        calendar_eventReference = firebaseDatabase.getReference("using_info").child(key);
    }

    //db에 데이터 넣기
    public void setMoneyInfo(DetailMoneyInfo detailMoneyInfo) {
        String[] childkey = detailMoneyInfo.getSelectDate().split("-");
        Log.e("database", "데이터삽입 불림..");
        usinginfo_databaseReference.child(key).child(childkey[0] + childkey[1]).child(childkey[2]).push().setValue(detailMoneyInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.e("database", "데이터삽입 성공..");
            }
        });
    }

    //db 데이터 가져오기
    public void getMoneyInfo(final CalendarRecyclerviewAdapter adapter, String date) {
        String[] childkey = date.split("-");
        Log.e("First_Fragment manager", "clicked date->" + childkey[0] + "," + childkey[1] + "," + childkey[2]);

        detailMoneyInfoList.clear();
        adapter.notifyDataSetChanged();
        usinginfo_databaseReference.child(key).child(childkey[0] + childkey[1]).child(childkey[2]).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("First_Fragment intent database", "db 불러옴....");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    DetailMoneyInfo detailMoneyInfo = data.getValue(DetailMoneyInfo.class);
                    if (detailMoneyInfo != null) {
                        detailMoneyInfoList.add(detailMoneyInfo);
                        adapter.setItem(detailMoneyInfoList);
                        adapter.notifyDataSetChanged();
                        Log.i("First_Fragment intent database", "db 불러옴....size" + detailMoneyInfoList.size());
                    } else {
                        detailMoneyInfoList.clear();
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    //데이터중에 key값을 받아서 해당하는 데이터 삭제
    public void deleteMoneyInfo(String keyvalue, String date) {
        final String[] childkey = date.split("-");
        usinginfo_databaseReference.child(key).child(childkey[0] + childkey[1]).child(childkey[2]).orderByChild("key").equalTo(keyvalue).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot keyvalue : dataSnapshot.getChildren()) {
                    childKeyvalue = keyvalue.getKey();
                }
                Log.e("database", "데이터삭제 불림..");
                usinginfo_databaseReference.child(key).child(childkey[0] + childkey[1]).child(childkey[2]).child(childKeyvalue).removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Log.d("database", "" + childKeyvalue);
    }

    //데이터중에 key값을 찾아서 해당하는 데이터 삭제
    public void deleteMoneyInfo2(final CalendarRecyclerviewAdapter adapter, final String keyvalue, final String date, final int position) {
        final String[] childkey = date.split("-");

        usinginfo_databaseReference.child(key).child(childkey[0] + childkey[1]).child(childkey[2]).orderByChild("key").equalTo(keyvalue).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot keyvalue : dataSnapshot.getChildren()) {
                    childKeyvalue = keyvalue.getKey();
                }
                Log.e("first_fragment delete database", "데이터삭제 불림..");
                usinginfo_databaseReference.child(key).child(childkey[0] + childkey[1]).child(childkey[2]).child(childKeyvalue).removeValue();
                getMoneyInfo(adapter, date);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //일간 탭 날짜 선택시 DB 데이터 읽기
    public void getScheduleMoneyInfo(final ScheduleRecyclerviewAdapter Inadapter, final ScheduleRecyclerviewAdapter Outadapter, String data, HorizontalCalendar horizontalCalendar, View view) {
        InMoneyList.clear(); OutMoneyList.clear();
        horizontalCalendar1 = horizontalCalendar;
        Income = view.findViewById(R.id.IncomeMoney);
        Outlay = view.findViewById(R.id.OutlayMoney);
        String[] days = data.split(" ");
        numberFormat = NumberFormat.getInstance();
            usinginfo_databaseReference.child(key).child(days[0]).child(days[1]).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int InSum = 0;
                    int OutSum = 0;
                    for (DataSnapshot snot : dataSnapshot.getChildren()) {
                        DetailMoneyInfo detailMoneyInfo = snot.getValue(DetailMoneyInfo.class);
                        if(detailMoneyInfo.getType().equals("수입")){
                            InMoneyList.add(detailMoneyInfo);
                            InSum += detailMoneyInfo.getUsingMoney();
                        }
                        else if(detailMoneyInfo.getType().equals("지출")){
                            OutMoneyList.add(detailMoneyInfo);
                            OutSum += detailMoneyInfo.getUsingMoney();
                        }
                    }
                    Income.setText(numberFormat.format(InSum) + "원");
                    Outlay.setText(numberFormat.format(OutSum) + "원");
                    if(InMoneyList.size() == 0 && OutMoneyList.size() == 0){
                        horizontalCalendar1.getConfig().setFormatBottomText(" ");
                        horizontalCalendar1.getSelectedItemStyle().setColorBottomText(Color.WHITE);
                    } else {
                        horizontalCalendar1.getConfig().setFormatBottomText("●");
                        horizontalCalendar1.getSelectedItemStyle().setColorBottomText(Color.RED);
                    }
                    horizontalCalendar1.refresh();
                    Inadapter.setItem(InMoneyList);
                    Inadapter.notifyDataSetChanged();
                    Outadapter.setItem(OutMoneyList);
                    Outadapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
    }

    //삭제버튼 누를시 DB 삭제
    public void deleteScheduleMoneyInfo(final ScheduleRecyclerviewAdapter adapter, final String type, final int index1, String key1, String data,View view){
        //InMoneyList.clear(); OutMoneyList.clear();
        if(type.equals("수입")){
            textView = view.findViewById(R.id.IncomeMoney);
        } else if(type.equals("수출")){
            textView = view.findViewById(R.id.OutlayMoney);
        }
        arr = data.split("-");
        usinginfo_databaseReference.child(key).child(arr[0]).child(arr[1]).orderByChild("key").equalTo(key1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot keyvalue : dataSnapshot.getChildren()) {
                    DetailMoneyInfo detailMoneyInfo = keyvalue.getValue(DetailMoneyInfo.class);
                    money = Integer.toString(detailMoneyInfo.getUsingMoney());
                    childKeyvalue = keyvalue.getKey();
                }
                int sum = 0;
                usinginfo_databaseReference.child(key).child(arr[0]).child(arr[1]).child(childKeyvalue).removeValue();
                String result[] = textView.getText().toString().split("원");
                if(result[0].contains(",") || money.contains(",")){
                    String v = result[0].replace(",","");
                    String n = money.replace(",","");
                    sum = Integer.parseInt(v) - Integer.parseInt(n);
                } else{
                    sum = Integer.parseInt(result[0]) - Integer.parseInt(money);
                }
                textView.setText(numberFormat.format(sum) + "원");
                adapter.subItem(index1);
                adapter.isShow(2);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void getCalendarEvent(final String date, final ArrayList<String> eventData, CompactCalendarView calendarView) {
        String[] childkey = date.split("-");
        compactCalendarView = calendarView;
        usinginfo_databaseReference.child(key).child(childkey[0] + childkey[1]).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventData.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data != null) {
                        eventData.add(data.getKey());
                        Log.e("First_Fragment dbdb", "database event! at if->" + eventData.size());
                        long timeMillis = Calendar.getInstance().getTimeInMillis();
                        compactCalendarView.addEvent(new Event(Color.RED,timeMillis));
                        compactCalendarView.invalidate();
                        try {
                            Log.e("Frist_fragment dbdb", "여기불리냐" );
                            Date testdate = sdf.parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else {
                        eventData.clear();
                        Log.e("Frist_fragment dbdb", "" + eventData.size());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}