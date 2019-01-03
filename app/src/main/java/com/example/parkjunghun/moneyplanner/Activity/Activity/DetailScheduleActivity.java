package com.example.parkjunghun.moneyplanner.Activity.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkjunghun.moneyplanner.Activity.Adapter.ScheduleRecyclerviewAdapter;
import com.example.parkjunghun.moneyplanner.Activity.MainActivity;
import com.example.parkjunghun.moneyplanner.Activity.Model.Weekly_Update_Event;
import com.example.parkjunghun.moneyplanner.Activity.Util.DatabaseManager;
import com.example.parkjunghun.moneyplanner.R;


import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailScheduleActivity extends AppCompatActivity {

    @BindView(R.id.receipt)
    TextView receipt;
    @BindView(R.id.receipt_image)
    ImageView receipt_image;
    @BindView(R.id.cancel_button)
    ImageButton c_button;
    @BindView(R.id.trash_button)
    ImageButton t_button;
    @BindView(R.id.receipt_change)
    ImageButton receipt_change;
    @BindView(R.id.receipt_camera)
    ImageButton receipt_camera;
    @BindView(R.id.days_month)
    TextView month;
    @BindView(R.id.receipt_date)
    TextView receipt_date;
    @BindView(R.id.receipt_money)
    TextView receipt_money;
    @BindView(R.id.receipt_name)
    TextView receipt_name;
    @BindView(R.id.receipt_content)
    EditText receipt_content;
    private Intent intent;
    private String days;
    private String key;
    private int index;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_schedule);
        ButterKnife.bind(this);
        intent = getIntent();
        String type = intent.getStringExtra("type");
        key = intent.getStringExtra("key");
        days = intent.getStringExtra("date");
        index = intent.getIntExtra("index",0);
        if (type.equals("income")) {
            receipt.setText("수입 영수증");
        } else if (type.equals("outlay")) {
            receipt.setText("수출 영수증");
        }
        receipt_date.setText(days);
        receipt_money.setText(intent.getStringExtra("using_money"));
        String day[] = days.split("-");
        month.setText(day[1] + "월");

    }

    @OnClick(R.id.cancel_button)
    public void CancelButton() {
        finish();
    }

    @OnClick(R.id.trash_button)
    public void TrashButton() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("일정 삭제");
        builder.setMessage("정말 삭제 하시겠습니까?");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseManager.getInstance().deleteScheduleMoneyInfo(days, key);
                String arr[] = days.split("-");
                String ret[] = receipt.getText().toString().split(" ");
                int year = Integer.parseInt(arr[0]+arr[1]);
                int month = Integer.parseInt(arr[2]);
                Log.e("asd",Integer.toString(year) + " " + Integer.toString(month));
                EventBus.getDefault().post(new Weekly_Update_Event("DB " +ret[0] + " " +index,year,month));
                finish();
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    @OnClick(R.id.receipt_camera)
    public void receipt_camera() {
        Log.e("asd", "카메라 클릭");
    }

    @OnClick(R.id.receipt_change)
    public void receipt_change() {
        Log.e("asd", "체인지 클릭");
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        try{
            EventBus.getDefault().unregister(this);
        }catch (Exception e){}
    }
}
