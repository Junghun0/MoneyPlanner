package com.example.parkjunghun.moneyplanner.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.parkjunghun.moneyplanner.Activity.Activity.LockScreenActivity;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(sharedPreferences.getString("password",null) == null){
            intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else {
            intent = new Intent(getApplicationContext(),LockScreenActivity.class);
            intent.putExtra("push","pass");
            startActivity(intent);
        }
        finish();
    }
}
