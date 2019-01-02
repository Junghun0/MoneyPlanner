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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.NumberFormat;
import java.util.ArrayList;

import devs.mulham.horizontalcalendar.HorizontalCalendar;

public class DatabaseManager {

    public static DatabaseManager instance = new DatabaseManager();

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usinginfo_databaseReference;
    private String key;
    private String childKeyvalue;
    private ArrayList<DetailMoneyInfo> detailMoneyInfoList = new ArrayList<>();
    private ArrayList<DetailMoneyInfo> InMoneyList = new ArrayList<>();
    private ArrayList<DetailMoneyInfo> OutMoneyList = new ArrayList<>();
    private ScheduleRecyclerviewAdapter InAdapter;
    private ScheduleRecyclerviewAdapter OutAdapter;
    private HorizontalCalendar horizontalCalendar1;
    private NumberFormat numberFormat;
    private TextView Income;
    private TextView Outlay;

    public static DatabaseManager getInstance() {
        return instance;
    }

    private DatabaseManager() {
        key = FirebaseInstanceId.getInstance().getToken();
        firebaseDatabase = FirebaseDatabase.getInstance();
        usinginfo_databaseReference = firebaseDatabase.getReference("using_info");
    }

    //db에 데이터 넣기
    public void setMoneyInfo(DetailMoneyInfo detailMoneyInfo) {
        String[] childkey = detailMoneyInfo.getSelectDate().split("-");
        Log.e("database","데이터삽입 불림..");
        usinginfo_databaseReference.child(key).child(childkey[0] + childkey[1]).child(childkey[2]).push().setValue(detailMoneyInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.e("database","데이터삽입 성공..");
            }
        });
    }

    //db 데이터 가져오기
    public void getMoneyInfo(final CalendarRecyclerviewAdapter adapter, String date) {
        String[] childkey = date.split("-");
        Log.e("First Fragment manager", "clicked date->" + childkey[0] + "," + childkey[1] + "," + childkey[2]);

        detailMoneyInfoList.clear();
        adapter.notifyDataSetChanged();
        usinginfo_databaseReference.child(key).child(childkey[0] + childkey[1]).child(childkey[2]).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("database first intent123", "데이터변경사항잇음");
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    DetailMoneyInfo detailMoneyInfo = data.getValue(DetailMoneyInfo.class);
                    if (detailMoneyInfo != null) {
                        detailMoneyInfoList.add(detailMoneyInfo);
                        adapter.setItem(detailMoneyInfoList);
                        adapter.notifyDataSetChanged();
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
                Log.e("database","데이터삭제 불림..");
                usinginfo_databaseReference.child(key).child(childkey[0] + childkey[1]).child(childkey[2]).child(childKeyvalue).removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Log.d("database", "" + childKeyvalue);
    }

    //데이터중에 key값을 받아서 해당하는 데이터 삭제
    public void deleteMoneyInfo2(final CalendarRecyclerviewAdapter adapter, final String keyvalue, String date, final int position) {
        final String[] childkey = date.split("-");

        usinginfo_databaseReference.child(key).child(childkey[0] + childkey[1]).child(childkey[2]).orderByChild("key").equalTo(keyvalue).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot keyvalue : dataSnapshot.getChildren()) {
                    childKeyvalue = keyvalue.getKey();
                }
                Log.e("database","데이터삭제 불림..");
                usinginfo_databaseReference.child(key).child(childkey[0] + childkey[1]).child(childkey[2]).child(childKeyvalue).removeValue();
                adapter.notifyItemRemoved(position);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Log.d("database", "" + childKeyvalue);
    }

    //일간 탭 날짜 선택시 DB 데이터 읽기
    public void getScheduleMoneyInfo(ScheduleRecyclerviewAdapter Inadapter, ScheduleRecyclerviewAdapter Outadapter, String data, HorizontalCalendar horizontalCalendar, View view) {
        InMoneyList.clear(); OutMoneyList.clear();
        InAdapter = Inadapter; OutAdapter = Outadapter;
        InAdapter.notifyDataSetChanged(); OutAdapter.notifyDataSetChanged();
        horizontalCalendar1 = horizontalCalendar;
        Income = view.findViewById(R.id.IncomeMoney);
        Outlay = view.findViewById(R.id.OutlayMoney);
        String[] days = data.split(" ");
        numberFormat = NumberFormat.getInstance();
            usinginfo_databaseReference.child(key).child(days[0]).child(days[1]).addValueEventListener(new ValueEventListener() {
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

                    if(InMoneyList.size() == 0 && OutMoneyList.size() == 0){
                        horizontalCalendar1.getConfig().setFormatBottomText(" ");
                        horizontalCalendar1.getSelectedItemStyle().setColorBottomText(Color.WHITE);
                    } else {
                        horizontalCalendar1.getConfig().setFormatBottomText("●");
                        horizontalCalendar1.getSelectedItemStyle().setColorBottomText(Color.RED);
                    }
                    horizontalCalendar1.refresh();
                    InAdapter.setItem(InMoneyList);
                    InAdapter.notifyDataSetChanged();
                    OutAdapter.setItem(OutMoneyList);
                    OutAdapter.notifyDataSetChanged();
                    Income.setText(numberFormat.format(InSum) + "원");
                    Outlay.setText(numberFormat.format(OutSum) + "원");
                    /*horizontalCalendar.refresh();
                    scheduleRecyclerviewAdapter.setItem(detailMoneyInfoList);
                    scheduleRecyclerviewAdapter.notifyDataSetChanged();*/
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
    }
}
