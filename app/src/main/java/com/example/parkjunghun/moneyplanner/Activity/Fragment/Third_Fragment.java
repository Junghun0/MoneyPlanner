package com.example.parkjunghun.moneyplanner.Activity.Fragment;

import android.graphics.Color;

import java.util.Calendar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.parkjunghun.moneyplanner.Activity.Adapter.ScheduleRecyclerviewAdapter;
import com.example.parkjunghun.moneyplanner.Activity.Model.DetailMoneyInfo;
import com.example.parkjunghun.moneyplanner.Activity.Model.ScheduleViewItem;
import com.example.parkjunghun.moneyplanner.Activity.Model.Weekly_Update_Event;
import com.example.parkjunghun.moneyplanner.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class Third_Fragment extends Fragment {

    private HorizontalCalendar horizontalCalendar;
    private HorizontalCalendar.Builder builder;
    private ScheduleRecyclerviewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager layoutManager1;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private TextView textView;
    private ArrayList<ScheduleViewItem> viewItemArrayList;
    @BindView(R.id.IncomeMoney)
    TextView InMoney;
    @BindView(R.id.OutlayMoney)
    TextView OutMoney;
    @BindView(R.id.CashMoney)
    TextView Cash;
    @BindView(R.id.CardMoney)
    TextView Card;
    @BindView(R.id.InChange)
    TextView InChange;
    @BindView(R.id.OutChange)
    TextView OutChange;
    @BindView(R.id.InRecyclerview)
    RecyclerView InRecyclerView;
    @BindView(R.id.OutRecyclerview)
    RecyclerView OutRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //테스트
        init();
    }

    //그냥 넣어 본것
    public void init(){
        viewItemArrayList = new ArrayList<>();
        viewItemArrayList.add(new ScheduleViewItem(R.drawable.ic_mood_black_24dp,"asd","wdwdw",R.drawable.ic_loop_black_24dp));
        viewItemArrayList.add(new ScheduleViewItem(R.drawable.ic_mood_black_24dp,"0o0o0o0","okok",R.drawable.ic_loop_black_24dp));
        for(int i=0;i<30;i++)
            viewItemArrayList.add(new ScheduleViewItem(R.drawable.ic_loop_black_24dp,"0o0o0o0","okok",R.drawable.ic_loop_black_24dp));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.third_fragment_layout, container, false);
        ButterKnife.bind(this, view);

        textView = (TextView)getActivity().findViewById(R.id.current_month);
        Calendar start = Calendar.getInstance();
        start.add(Calendar.YEAR,-10);
        Calendar end = Calendar.getInstance();
        end.add(Calendar.YEAR,10);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        //파이어베이스로 현재 날짜의
        //Log.e("Third_Fragment",start.get(Calendar.MONTH)+1 + "");
        //Log.e("Third_Fragment",start.get(Calendar.DAY_OF_MONTH) + "");

        builder = new HorizontalCalendar.Builder(view,R.id.calendarView)
                .range(start,end)
                .datesNumberOnScreen(7)
                .configure()
                .formatTopText("EEE")
                .formatMiddleText("dd")
                .formatBottomText("●")
                .textSize(12f, 20f, 7f)
                .showTopText(true)
                .showBottomText(true)
                .textColor(Color.BLACK, Color.RED)
                .colorTextBottom(Color.WHITE,Color.WHITE)
                .end();

        //textView 날짜가 파이어베이스에 데이터가 있다면 색깔 바꿔줘야함!
        //builder.configure().colorTextBottom(Color.WHITE,Color.BLACK);
        //builder.configure().colorTextBottom(Color.WHITE,Color.BLACK);

        horizontalCalendar = builder.build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                //여기에서도 만약 선택된 날짜가 파이어베이스안에 데이터가 있다면 점 색깔 바꿔줘야함!
                if(date.get(Calendar.DAY_OF_MONTH) > 0){
                    textView.setText(date.get(Calendar.YEAR) + "년 " + Integer.toString(date.get(Calendar.MONTH)+1) + "월");
                    horizontalCalendar.getSelectedItemStyle().setColorBottomText(Color.RED);
                    horizontalCalendar.refresh();
                    //Log.e("Third_Fragment","여기 뭐임");
                }
            }
        });

//        InRecyclerView.setHasFixedSize(true);
        /*
        ViewGroup.LayoutParams layoutParams= InRecyclerView.getLayoutParams();
        layoutParams.height = layoutParams.width;
        InRecyclerView.setLayoutParams(layoutParams);
        */
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager1 = new LinearLayoutManager(getActivity());
        InRecyclerView.setLayoutManager(layoutManager);
        adapter = new ScheduleRecyclerviewAdapter(viewItemArrayList);
        InRecyclerView.setAdapter(adapter);
        OutRecyclerView.setLayoutManager(layoutManager1);
        OutRecyclerView.setAdapter(adapter);
        return view;
    }

    @OnClick(R.id.InChange)
    public void OnInChange(){
        Log.e("asd","여기 눌러");
    }

    @OnClick(R.id.OutChange)
    public void OnOutChange(){
        Log.e("asd","나도 여기");
    }

    //중요한 함수임!
    public void Date_Update(int year, int month, boolean check){
        if(check == true){
//            Log.e("Third_Fragment","여기들어옴");
        }else{
            Calendar startC = Calendar.getInstance();
            startC.set(Calendar.YEAR,year);
            if(month-1 != startC.get(Calendar.MONTH)){
                startC.set(Calendar.MONTH,month-1);
                startC.set(Calendar.DAY_OF_MONTH,1);
            }

 /*           Calendar endC = Calendar.getInstance();
            endC.set(Calendar.YEAR,year +10);
            Log.e("Third_Fragment",startC.get(Calendar.MONTH)+1 + " " + startC.get(Calendar.DAY_OF_MONTH) + " " + startC.get(Calendar.YEAR));
            Log.e("Third_Fragment",endC.get(Calendar.MONTH)+1 + " " + endC.get(Calendar.DAY_OF_MONTH) + " " + endC.get(Calendar.YEAR));
  */         horizontalCalendar.selectDate(startC,true);
            //horizontalCalendar.setRange(startC,endC);
            horizontalCalendar.refresh();
        }
    }

    @Subscribe
    public void testEvent(Weekly_Update_Event event) {
        //Log.e("Third_Fragment",event.year + " " + event.month);
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
