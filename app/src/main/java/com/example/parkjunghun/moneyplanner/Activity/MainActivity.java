package com.example.parkjunghun.moneyplanner.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.parkjunghun.moneyplanner.Activity.Adapter.ViewPagerAdapter;
import com.example.parkjunghun.moneyplanner.Activity.Fragment.Third_Fragment;
import com.example.parkjunghun.moneyplanner.Activity.Model.CalendarEvent;
import com.example.parkjunghun.moneyplanner.Activity.Model.CalendarScrollEvent;
import com.example.parkjunghun.moneyplanner.Activity.Model.Weekly_Update_Event;
import com.example.parkjunghun.moneyplanner.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "First_Fragment";

    @BindView(R.id.main_Tablayout)
    TabLayout tabLayout;
    @BindView(R.id.main_ViewPager)
    ViewPager viewPager;
    @BindView(R.id.current_month)
    TextView currentMonth;
    @BindView(R.id.before_month)
    ImageButton left_button;
    @BindView(R.id.after_month)
    ImageButton right_button;
    @BindView(R.id.messages)
    ImageButton message_button;
    @BindView(R.id.search)
    ImageButton search_button;
    @BindView(R.id.account)
    ImageButton account_button;

    private ViewPagerAdapter viewPagerAdapter;
    private Third_Fragment third_fragment;
    Calendar cal;
    int month;
    int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        currentMonth.setText(year + "년 " + month +"월");

        third_fragment = new Third_Fragment();

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this,third_fragment);

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        //앱 키자마자 4개의 탭 미리 로딩해주는 함수
        viewPager.setOffscreenPageLimit(4);

        tabLayout.getTabAt(0).setText("간");
        tabLayout.getTabAt(1).setText("주간");
        tabLayout.getTabAt(2).setText("일간");
        tabLayout.getTabAt(3).setText("정");

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //viewPager 가 바뀔때 발생하는 리스너
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if(i == 2){
                    String days[] = currentMonth.getText().toString().split(" ");
                    //Log.e("Third_Fragment",days[0] + " " + days[1]);
                    String year[] = days[0].split("년");
                    String day[] = days[1].split("월");
                    //Log.e("Third_Fragment",year[0] + " ㅇ " + day[0]);
                    third_fragment.Date_Update(Integer.parseInt(year[0]),Integer.parseInt(day[0]),false);
                    //Log.e("Third_Fragment",currentMonth.getText().toString());
                    //여기다 이제 년 월 받아서 Date_update()함수 호출!;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        //별 쪽 버튼, 른버튼
        left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                --month;
                if (month == 0) {
                    --year;
                    month = 12;
                }
                if (year != cal.get(Calendar.YEAR)) currentMonth.setText(year + "년 " + month +"월");
                else currentMonth.setText(year + "년 " + month +"월");
                EventBus.getDefault().post(new Weekly_Update_Event("true",year,month));
                Log.d(TAG, "year<" + year + "month<" + month);
                //EventBus.getDefault().post(new CalendarEvent(false));
            }
        });

        right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ++month;
                if (month == 13) {
                    ++year;
                    month = 1;
                }
                if (year != cal.get(Calendar.YEAR)) currentMonth.setText(year + "년 " + month +"월");
                else currentMonth.setText(year + "년 " + month +"월");
                EventBus.getDefault().post(new Weekly_Update_Event("false",year,month));
                Log.d(TAG, "year" + year + "month" + month);
                //EventBus.getDefault().post(new CalendarEvent(true));
            }
        });
    }

    @Subscribe
    public void scrollEvent(CalendarScrollEvent event) {
        Log.d(TAG, "scroll event..." + event.getMonth());
        currentMonth.setText(event.getYear() + "년 " + event.getMonth()+"월" );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View actionbar = inflater.inflate(R.layout.custom_toolbar, null);

        actionBar.setCustomView(actionbar);

        Toolbar parent = (Toolbar) actionbar.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            EventBus.getDefault().register(this);
        } catch (Exception e) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            EventBus.getDefault().unregister(this);
        } catch (Exception e) {

        }

    }
}
