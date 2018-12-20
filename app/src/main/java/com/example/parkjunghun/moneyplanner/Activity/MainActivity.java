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
import com.example.parkjunghun.moneyplanner.Activity.Model.CalendarEvent;
import com.example.parkjunghun.moneyplanner.Activity.Model.CalendarScrollEvent;
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
        currentMonth.setText(year + "" + month + ");

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("간");
        tabLayout.getTabAt(1).setText("주간");
        tabLayout.getTabAt(2).setText("간");
        tabLayout.getTabAt(3).setText("정");

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //별 쪽 버튼, 른버튼
        left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                --month;
                if (month == 0) {
                    --year;
                    month = 12;
                }
                if (year != cal.get(Calendar.YEAR)) currentMonth.setText(year + "" + month + ");
                else currentMonth.setText(year + "" + month + ");
                EventBus.getDefault().post(new Weekly_Update_Event("true",year,month));
                Log.d(TAG, "year<" + year + "month<" + month);
                EventBus.getDefault().post(new CalendarEvent(false));
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
                if (year != cal.get(Calendar.YEAR)) currentMonth.setText(year + "" + month + ");
                else currentMonth.setText(year + "" + month + ");
                EventBus.getDefault().post(new Weekly_Update_Event("true",year,month));
                Log.d(TAG, "year" + year + "month" + month);
                EventBus.getDefault().post(new CalendarEvent(true));
            }
        });
    }

    @Subscribe
    public void scrollEvent(CalendarScrollEvent event) {
        Log.d(TAG, "scroll event..." + event.getMonth());
        currentMonth.setText(event.getYear() + "" + event.getMonth() + ");
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
