package com.example.parkjunghun.moneyplanner.Activity.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkjunghun.moneyplanner.Activity.Util.DatabaseManager;
import com.example.parkjunghun.moneyplanner.R;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailScheduleActivity extends AppCompatActivity {

    @BindView(R.id.receipt)
    TextView receipt;
    @BindView(R.id.cancel_button)
    ImageButton c_button;
    @BindView(R.id.trash_button)
    ImageButton t_button;
    @BindView(R.id.days_month)
    TextView month;
    private Bitmap image;
    private Intent intent;
    private String days;
    private DatabaseManager databaseManager;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail_schedule);
            ButterKnife.bind(this);
            databaseManager = DatabaseManager.getInstance();
            intent = getIntent();
            String type = intent.getStringExtra("type");
            days = intent.getStringExtra("date");
            if(type.equals("income")){
                receipt.setText("수입 영수증");
            } else if(type.equals("outlay")){
                receipt.setText("수출 영수증");
            }

            String day[] = days.split("-");
            month.setText(day[1] + "월");

            /*Intent intent = getIntent();
            byte[] arr = getIntent().getByteArrayExtra("image");
            Log.e("asd",arr.length + " ");
            image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
            imageView.setImageBitmap(image);*/
    }

    @OnClick(R.id.cancel_button)
    public void CancelButton(){
         finish();
    }

    @OnClick(R.id.trash_button)
    public void TrashButton(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("일정 삭제");
        builder.setMessage("정말 삭제 하시겠습니까?");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"asd",Toast.LENGTH_LONG).show();
                //여기다 DB 삭제 코드 넣으면 댐
                /*Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/
                finish();
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"2s",Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

/*    @Override
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

    }*/

}
