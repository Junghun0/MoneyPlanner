package com.example.parkjunghun.moneyplanner.Activity.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkjunghun.moneyplanner.Activity.Adapter.LockButtonAdapter;
import com.example.parkjunghun.moneyplanner.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LockScreenActivity extends AppCompatActivity {

    @BindView(R.id.password_gridview)
    GridView gridView;
    @BindView(R.id.password_1)
    TextView password_1;
    @BindView(R.id.password_2)
    TextView password_2;
    @BindView(R.id.password_3)
    TextView password_3;
    @BindView(R.id.password_4)
    TextView password_4;
    @BindView(R.id.password_comment)
    TextView comment;

    private String password[] = {"1","2","3","4","5","6","7","8","9","","0","←"};
    private String result = "";
    private String check = "";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        LockButtonAdapter lockButtonAdapter = new LockButtonAdapter(this,password);
        gridView.setAdapter(lockButtonAdapter);
        intent = getIntent();
        if(intent.getStringExtra("push").equals("register")){
            comment.setText("새로 등록할 비밀번호를 입력해 주세요.");
        } else {
            comment.setText("현재 비밀번호를 입력해 주세요.");
        }
        lockButtonAdapter.setOnMyItemCheckedChanged(new LockButtonAdapter.OnMyItemCheckedChanged() {
            @Override
            public void onITemCheckedChanged(String[] arr, int checked) {
                switch (checked){
                    case 0: case 1: case 2:
                    case 3: case 4: case 5:
                    case 6: case 7: case 8:
                    case 10:
                        password_register(arr,checked);
                        break;
                    case 9:
                        break;
                    case 11:
                        password_cancel();
                        break;
                }
            }
        });
    }

    public void password_cancel(){
        if(result.length() == 4){
            result = result.substring(0,3);
            password_4.setText("");
        } else if(result.length() == 3){
            result = result.substring(0,2);
            password_3.setText("");
        } else if(result.length() == 2){
            result = result.substring(0,1);
            password_2.setText("");
        } else if(result.length() == 1){
            result = "";
            password_1.setText("");
        }
    }

    public void password_register(String[] arr, int count){
        if(password_1.getText().equals("") && password_2.getText().equals("") && password_3.getText().equals("") && password_4.getText().equals("")){
            password_1.setText(arr[count]);
            result += arr[count];
        } else if(!password_1.getText().equals("") && password_2.getText().equals("") && password_3.getText().equals("") && password_4.getText().equals("")){
            password_2.setText(arr[count]);
            result += arr[count];
        } else if(!password_1.getText().equals("") && !password_2.getText().equals("") && password_3.getText().equals("") && password_4.getText().equals("")){
            password_3.setText(arr[count]);
            result += arr[count];
        } else if(!password_1.getText().equals("") && !password_2.getText().equals("") && !password_3.getText().equals("") && password_4.getText().equals("")){
            password_4.setText(arr[count]);
            result += arr[count];
            if(comment.getText().toString().startsWith("새로")){
                comment.setText("한번 더 입력해 주세요.");
                password_1.setText(""); password_2.setText("");
                password_3.setText(""); password_4.setText("");
                check = result;
                result = "";
            } else if(comment.getText().toString().startsWith("한번")){
                Log.e("okok","여기?" + check + result);
                if(result.equals(check)){
                    editor.putString("password",result);
                    editor.commit();
                    Toast.makeText(getApplicationContext(),"등록되었습니다.",Toast.LENGTH_LONG).show();
                    EventBus.getDefault().post(new String("등록"));
                    finish();
                } else {
                    password_1.setText(""); password_2.setText("");
                    password_3.setText(""); password_4.setText("");
                    comment.setText("일치하지 않습니다. 다시 새로 등록할 비밀번호를 입력해 주세요.");
                    result = "";
                }
            } else if(comment.getText().toString().startsWith("현재")){
                if(sharedPreferences.getString("password",null).equals(result)){
                    password_1.setText(""); password_2.setText("");
                    password_3.setText(""); password_4.setText("");
                    comment.setText("새로 등록할 비밀번호를 입력해 주세요.");
                    result = "";
                } else{
                    Toast.makeText(getApplicationContext(),"현재 비밀번호가 일치하지 않습니다.",Toast.LENGTH_LONG).show();
                    password_1.setText(""); password_2.setText("");
                    password_3.setText(""); password_4.setText("");
                    result = "";
                }
            }
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