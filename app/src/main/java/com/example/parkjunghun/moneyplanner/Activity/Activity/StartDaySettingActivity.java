package com.example.parkjunghun.moneyplanner.Activity.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.parkjunghun.moneyplanner.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartDaySettingActivity extends Activity {

    @BindView(R.id.day_button_select)
    Button select;
    @BindView(R.id.day_button_cancel)
    Button cancel;
    @BindView(R.id.day_radioGroup)
    RadioGroup day_radioGroup;
    @BindView(R.id.radio0)
    RadioButton radio0;
    @BindView(R.id.radio1)
    RadioButton radio1;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_start_day_setting);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String a = sharedPreferences.getString("day_select","일요일").substring(0,1);

        switch (a) {
            case "일": day_radioGroup.check(R.id.radio0);    break;
            case "월": day_radioGroup.check(R.id.radio1);    break;
            case "화": day_radioGroup.check(R.id.radio2);    break;
            case "수": day_radioGroup.check(R.id.radio3);    break;
            case "목": day_radioGroup.check(R.id.radio4);    break;
            case "금": day_radioGroup.check(R.id.radio5);    break;
            case "토": day_radioGroup.check(R.id.radio6);    break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @OnClick(R.id.day_button_cancel)
    public void cancel_Click(){
        finish();
    }

    @OnClick(R.id.day_button_select)
    public void select_Click(){
        int rg = day_radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton)findViewById(rg);
        editor.putString("day_select",radioButton.getText().toString());
        editor.commit();
        Intent intent = new Intent();
        intent.putExtra("day",radioButton.getText().toString());
        setResult(RESULT_OK,intent);
        finish();
    }
}
