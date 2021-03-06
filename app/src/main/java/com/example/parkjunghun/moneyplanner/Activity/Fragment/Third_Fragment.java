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
import com.example.parkjunghun.moneyplanner.Activity.Model.Weekly_Update_Event;
import com.example.parkjunghun.moneyplanner.Activity.Util.DatabaseManager;
import com.example.parkjunghun.moneyplanner.R;

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
    private ScheduleRecyclerviewAdapter Inadapter;
    private ScheduleRecyclerviewAdapter Outadapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager layoutManager1;
    private TextView textView;
    private ArrayList<DetailMoneyInfo> InviewItemArrayList  = new ArrayList<>();
    private ArrayList<DetailMoneyInfo> OutviewItemArrayList  = new ArrayList<>();
    private DatabaseManager databaseManager;
    private int Incheck = 0;
    private int Outcheck = 0;
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
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.third_fragment_layout, container, false);
        ButterKnife.bind(this, view);
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
                .formatBottomText(" ")
                .textSize(12f, 20f, 7f)
                .showTopText(true)
                .showBottomText(true)
                .textColor(Color.BLACK, Color.RED)
                .colorTextBottom(Color.WHITE,Color.WHITE)
                .end();
        horizontalCalendar = builder.build();
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager1 = new LinearLayoutManager(getActivity());
        InRecyclerView.setLayoutManager(layoutManager);
        Inadapter = new ScheduleRecyclerviewAdapter(this,InviewItemArrayList);
        Outadapter = new ScheduleRecyclerviewAdapter(this,OutviewItemArrayList);
        InRecyclerView.setAdapter(Inadapter);
        OutRecyclerView.setLayoutManager(layoutManager1);
        OutRecyclerView.setAdapter(Outadapter);
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                if(date.get(Calendar.DAY_OF_MONTH) > 0){
                    String data = Integer.toString(date.get(Calendar.YEAR)) + Integer.toString(date.get(Calendar.MONTH)+1) + " " + Integer.toString(date.get(Calendar.DAY_OF_MONTH));
                    DatabaseManager.getInstance().getScheduleMoneyInfo(Inadapter,Outadapter,data,horizontalCalendar,view);
                    textView.setText(date.get(Calendar.YEAR) + "년 " + Integer.toString(date.get(Calendar.MONTH)+1) + "월");
                }
            }
        });
        return view;
    }

    @OnClick(R.id.InChange)
    public void OnInChange(){
        Incheck++;
        Inadapter.isShow(Incheck);
        Inadapter.notifyDataSetChanged();
        if(Incheck == 10)
            Incheck = 0;
    }

    @OnClick(R.id.OutChange)
    public void OnOutChange(){
        Outcheck++;
        Outadapter.isShow(Outcheck);
        Outadapter.notifyDataSetChanged();
        if(Outcheck == 10)
            Outcheck = 0;
    }

    //중요한 함수임!
    public void Date_Update(int year, int month, boolean check,String index1){
        Log.e("okok",index1);
        if(check == true){
            String in[] = index1.split(" ");
            String sum = Integer.toString(year) + "-" + Integer.toString(month);
            // in[0] -> 수출, 수입
            // in[1] -> DB
            // in[2] -> index
            // in[3] -> key
            if(in[0].equals("수입")){
                DatabaseManager.getInstance().deleteScheduleMoneyInfo(Inadapter,in[0],Integer.parseInt(in[2]),in[3],sum,view);
            } else if(in[0].equals("수출")){
                DatabaseManager.getInstance().deleteScheduleMoneyInfo(Outadapter,in[0],Integer.parseInt(in[2]),in[3],sum,view);
            }
        }else{
            Calendar startC = Calendar.getInstance();
            startC.set(Calendar.YEAR,year);
            startC.set(Calendar.MONTH,month-1);
            startC.set(Calendar.DAY_OF_MONTH,30);
            /*Outadapter.isShow(2);
            Inadapter.isShow(2);
            Inadapter.notifyDataSetChanged();
            Outadapter.notifyDataSetChanged();*/
            /*if(month-1 != startC.get(Calendar.MONTH)){
                startC.set(Calendar.MONTH,month-1);
                startC.set(Calendar.DAY_OF_MONTH,1);
            } else*/
            horizontalCalendar.selectDate(startC,true);
            //horizontalCalendar.setRange(startC,endC);
            horizontalCalendar.refresh();
        }
    }

    @Subscribe
    public void testEvent(Weekly_Update_Event event) {
        if(event.update.startsWith("수입") || event.update.startsWith("수출")){
            Date_Update(event.year, event.month, true,event.update);
        }else {
            Date_Update(event.year, event.month, false,"default");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
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
    }
}
