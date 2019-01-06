package com.example.parkjunghun.moneyplanner.Activity.Util;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.parkjunghun.moneyplanner.Activity.Adapter.CalendarRecyclerviewAdapter;
import com.example.parkjunghun.moneyplanner.Activity.Model.DetailMoneyInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

public class DatabaseManager {

    public static DatabaseManager instance = new DatabaseManager();

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usinginfo_databaseReference;
    private DatabaseReference calendar_eventReference;
    private String key;
    private String childKeyvalue;
    private ArrayList<DetailMoneyInfo> detailMoneyInfoList = new ArrayList<>();
    private ArrayList<String> testmanagerarray = new ArrayList<>();

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

    public void getCalendarEvent(String date, final ArrayList<String> eventData) {
        String[] childkey = date.split("-");
        this.testmanagerarray = eventData;

        usinginfo_databaseReference.child(key).child(childkey[0] + childkey[1]).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventData.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data != null) {
                        eventData.add(data.getKey());
                        Log.e("First_Fragment dbdb", "database event! at if->" + eventData.size());
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
