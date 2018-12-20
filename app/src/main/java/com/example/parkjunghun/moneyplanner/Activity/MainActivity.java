package com.example.parkjunghun.moneyplanner.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.parkjunghun.moneyplanner.Activity.Adapter.ViewPagerAdapter;
import com.example.parkjunghun.moneyplanner.Activity.Model.Weekly_Update_Event;
import com.example.parkjunghun.moneyplanner.R;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_Tablayout)
    TabLayout tabLayout;
    @BindView(R.id.main_ViewPager)
    ViewPager viewPager;

    private TextView currentMonth;
    private ImageButton left_button;
    private ImageButton right_button;
    private ImageButton message_button;
    private ImageButton search_button;
    private ImageButton account_button;

    Calendar cal;
    int month;
    int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        left_button = (ImageButton)findViewById(R.id.before_month);
        right_button = (ImageButton)findViewById(R.id.after_month);

        //message_button = (ImageButton)findViewById(R.id.messages);
        //search_button = (ImageButton)findViewById(R.id.search);
        //account_button = (ImageButton)findViewById(R.id.account);

        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH)+1;
        currentMonth = findViewById(R.id.current_month);
        currentMonth.setText(year+"년 " + month + "월");

        ButterKnife.bind(this);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("월간");
        tabLayout.getTabAt(1).setText("주간");
        tabLayout.getTabAt(2).setText("일간");
        tabLayout.getTabAt(3).setText("설정");

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

// 월별 왼쪽 버튼, 오른쪽 버튼
        left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                --month;
                if(month == 0){--year; month = 12; }
                if(year != cal.get(Calendar.YEAR)) currentMonth.setText(year+"년 "+month+"월");
                else currentMonth.setText(year+"년 " + month + "월");
                EventBus.getDefault().post(new Weekly_Update_Event("true",year,month));
            }
        });

        right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ++month;
                if(month == 13){ ++year; month = 1; }
                if(year != cal.get(Calendar.YEAR)) currentMonth.setText(year+"년 "+month+"월");
                else currentMonth.setText(year+"년 " + month + "월");
                EventBus.getDefault().post(new Weekly_Update_Event("true",year,month));
            }
        });
    }
}