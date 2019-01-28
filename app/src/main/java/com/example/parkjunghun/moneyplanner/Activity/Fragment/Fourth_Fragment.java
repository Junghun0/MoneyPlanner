package com.example.parkjunghun.moneyplanner.Activity.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.parkjunghun.moneyplanner.Activity.Activity.LockScreenActivity;
import com.example.parkjunghun.moneyplanner.Activity.Activity.StartDaySettingActivity;
import com.example.parkjunghun.moneyplanner.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

public class Fourth_Fragment extends Fragment {

    @BindView(R.id.password_checkbox)
    CheckBox checkBox;
    @BindView(R.id.register_button)
    Button register;
    @BindView(R.id.change_button)
    Button change;
    @BindView(R.id.setting)
    LinearLayout setting;
    @BindView(R.id.start_day)
    TextView start_Day;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fourth_fragment_layout, container, false);
        ButterKnife.bind(this, view);
        sharedPreferences = view.getContext().getSharedPreferences("data", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        start_Day.setText(sharedPreferences.getString("day_select","일요일"));

        if(sharedPreferences.getString("password",null) == null){
            checkBox.setChecked(false);
        } else {
            checkBox.setChecked(true);
            change.setEnabled(true);
            change.setClickable(true);
        }
        return view;
    }

    @OnClick(R.id.password_checkbox)
    public void check(){
        if(checkBox.isChecked()){
            register.setEnabled(true);
            register.setClickable(true);
        } else {
            if(sharedPreferences.getString("password",null) == null){
                register.setEnabled(false);
                register.setClickable(false);
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("비밀번호 등록 해제");
                builder.setMessage("정말 삭제 하시겠습니까? (해제하면 새로운 비밀번호를 등록해야 합니다.) ");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        register.setEnabled(false);
                        register.setClickable(false);
                        change.setEnabled(false);
                        change.setClickable(false);
                        editor.remove("password");
                        editor.commit();
                        checkBox.setChecked(false);
                    }
                });
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkBox.setChecked(true);
                    }
                });
                builder.show();
            }
        }
    }

    @Subscribe
    public void testEvent(String event) {
        if(event.equals("등록")){
            Log.e("okok","요기");
            register.setEnabled(false);
            register.setClickable(false);
            change.setEnabled(true);
            change.setClickable(true);
        }
    }

    @OnClick(R.id.register_button)
    public void register(){
        Intent intent = new Intent(getActivity().getApplicationContext(),LockScreenActivity.class);
        intent.putExtra("push","register");
        startActivity(intent);
    }

    @OnClick(R.id.change_button)
    public void change(){
        Intent intent = new Intent(getActivity().getApplicationContext(),LockScreenActivity.class);
        intent.putExtra("push","change");
        startActivity(intent);
    }

    @OnClick(R.id.setting)
    public void setting(){
        Intent intent = new Intent(getActivity().getApplicationContext(),StartDaySettingActivity.class);
        startActivityForResult(intent,3000);
        //startActivity(intent);
        Log.e("okok","여기 찍었다.");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 3000){
            start_Day.setText(data.getStringExtra("day"));
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
