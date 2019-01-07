package com.example.parkjunghun.moneyplanner.Activity.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.example.parkjunghun.moneyplanner.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PopupActivity extends Activity {

    @BindView(R.id.pop_edit)
    Button edit_button;
    @BindView(R.id.pop_change)
    Button change_button;
    @BindView(R.id.pop_delete)
    Button delete_button;
    @BindView(R.id.pop_cancel)
    Button cancel_button;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Intent intent;
    private String key;
    private String capture;
    private String pre_key;
    private Intent response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);
        ButterKnife.bind(this);
        intent = getIntent();
        key = intent.getStringExtra("key");
        pre_key = intent.getStringExtra("pre_key");
        capture = intent.getStringExtra("select");

        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @OnClick(R.id.pop_cancel)
    public void cancel_button1(){
        response = new Intent();
        response.putExtra("response","cancel");
        setResult(Activity.RESULT_OK,response);
        finish();
    }

    @OnClick(R.id.pop_delete)
    public void delete_button1(){
        response = new Intent();
        if(capture.equals("capture1")){
            response.putExtra("response","imageView1");
        }else if(capture.equals("capture2")){
            response.putExtra("response","imageView2");
        }
        editor.remove(pre_key);
        editor.commit();
        setResult(Activity.RESULT_OK,response);
        finish();
    }

    @OnClick(R.id.pop_change)
    public void change_button1(){
        response = new Intent();
        response.putExtra("response",capture + " camera");
        setResult(Activity.RESULT_OK,response);
        finish();
    }

/*    @OnClick(R.id.pop_edit)
    public void edit_button1(){
        response = new Intent();
        response.putExtra("response","rotate");
        setResult(Activity.RESULT_OK,response);
        finish();
    }*/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
