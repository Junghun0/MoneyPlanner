package com.example.parkjunghun.moneyplanner.Activity.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkjunghun.moneyplanner.Activity.Model.Weekly_Update_Event;
import com.example.parkjunghun.moneyplanner.Activity.Util.DatabaseManager;
import com.example.parkjunghun.moneyplanner.R;

import org.greenrobot.eventbus.EventBus;

import java.text.NumberFormat;

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
    @BindView(R.id.content_save)
    Button save;
    @BindView(R.id.content_cancel)
    Button cancel;
    @BindView(R.id.image_capture1)
    ImageView capture1;
    @BindView(R.id.image_capture2)
    ImageView capture2;

    private Intent intent;
    private String days;
    private String key;
    private int index;
    private boolean check = true;
    private String content_result;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String preferences;
    private NumberFormat numberFormat = NumberFormat.getInstance();
    private InputMethodManager imm;
    private String key1;
    private String key2;
    private String shared_capture1;
    private String shared_capture2;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_schedule);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        intent = getIntent();
        String type = intent.getStringExtra("type");
        key = intent.getStringExtra("key");
        days = intent.getStringExtra("date");
        index = intent.getIntExtra("index",0);
        if (type.equals("income")) {
            receipt.setText("수입 영수증");
            preferences = "수입 " +days + " ";
        } else if (type.equals("outlay")) {
            receipt.setText("수출 영수증");
            preferences = "수출 " +days + " ";
        }
        preferences += index;
        receipt_date.setText(days);
        String result = sharedPreferences.getString(preferences," ");
        receipt_content.setText(result);
        //여기부분 메모기능 구현할때 업데이트도 해줘야함!!
        receipt_money.setText(numberFormat.format(Integer.parseInt(intent.getStringExtra("using_money"))) + "원");
        key1 = preferences + " image_capture1";
        key2 = preferences + " image_capture2";
        shared_capture1 = sharedPreferences.getString(key1,null);
        shared_capture2 = sharedPreferences.getString(key2,null);

        if(shared_capture1 == null && shared_capture2 == null) {
            capture1.setVisibility(View.INVISIBLE);
            capture2.setVisibility(View.INVISIBLE);
        } else if(shared_capture1 != null && shared_capture2 == null){
            capture1.setVisibility(View.VISIBLE);
            capture1.setImageURI(Uri.parse(shared_capture1));
            capture2.setVisibility(View.INVISIBLE);
        } else if(shared_capture1 == null && shared_capture2 != null){
            capture1.setVisibility(View.INVISIBLE);
            capture2.setVisibility(View.VISIBLE);
            capture2.setImageURI(Uri.parse(shared_capture2));
        } else {
            capture1.setVisibility(View.VISIBLE);
            capture1.setImageURI(Uri.parse(shared_capture1));
            capture2.setVisibility(View.VISIBLE);
            capture2.setImageURI(Uri.parse(shared_capture2));
        }
        String day[] = days.split("-");
        month.setText(day[1] + "월");
    }

    @OnClick(R.id.cancel_button)
    public void CancelButton() {
        editor.putString(preferences,receipt_content.getText().toString());
        editor.commit();
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
                editor.remove(preferences);
                editor.remove(key1);
                editor.remove(key2);
                editor.commit();
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
        intent = new Intent();
        camera_in(intent,0);
    }

    public void camera_in(Intent intent,int num){
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(num == 0)
            startActivityForResult(intent, 1);
        else if(num == 1)
            startActivityForResult(intent, 2);
        else if(num == 2)
            startActivityForResult(intent, 3);
    }

    @OnClick(R.id.image_capture1)
    public void capture1(){
        intent = new Intent(getApplicationContext(),PopupActivity.class);
        intent.putExtra("key",shared_capture1);
        intent.putExtra("pre_key",key1);
        intent.putExtra("select","capture1");
        startActivityForResult(intent,1000);
    }

    @OnClick(R.id.image_capture2)
    public void capture2(){
        intent = new Intent(getApplicationContext(),PopupActivity.class);
        intent.putExtra("key",shared_capture2);
        intent.putExtra("pre_key",key2);
        intent.putExtra("select","capture2");
        startActivityForResult(intent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try {
                    if(data.getClipData() == null){
                        uri = data.getData();
                        setSharedPreferences(key1,key2,uri);
                        this.grantUriPermission(this.getPackageName(),uri,Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                        this.getContentResolver().takePersistableUriPermission(uri,takeFlags);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else if(requestCode == 2){
            try{
                uri = data.getData();
                capture1.setImageURI(uri);
                capture1.setVisibility(View.VISIBLE);
                editor.putString(key1, String.valueOf(uri));
                editor.commit();
                this.grantUriPermission(this.getPackageName(),uri,Intent.FLAG_GRANT_READ_URI_PERMISSION);
                final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                this.getContentResolver().takePersistableUriPermission(uri,takeFlags);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }else if(requestCode == 3){
            try{
                uri = data.getData();
                capture2.setImageURI(uri);
                capture2.setVisibility(View.VISIBLE);
                editor.putString(key2, String.valueOf(uri));
                editor.commit();
                this.grantUriPermission(this.getPackageName(),uri,Intent.FLAG_GRANT_READ_URI_PERMISSION);
                final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                this.getContentResolver().takePersistableUriPermission(uri,takeFlags);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(requestCode == 1000){
            String x = data.getStringExtra("response");
            Log.e("asd",x);
            if(x.equals("imageView1")){
                capture1.setImageDrawable(null);
                capture1.setVisibility(View.INVISIBLE);
            } else if(x.equals("imageView2")){
                capture2.setImageDrawable(null);
                capture2.setVisibility(View.INVISIBLE);
            } else if(x.equals("capture1 camera")){
                intent = new Intent();
                camera_in(intent,1);
            } else if(x.equals("capture2 camera")){
                intent = new Intent();
                camera_in(intent,2);
            } /*else if(x.equals("rotate")){
                capture1.setRotation(90);
            }*/
        }
    }

    public void setSharedPreferences(String key_capture1, String key_capture2, Uri img1){
        String a = sharedPreferences.getString(key_capture1,null);
        String b = sharedPreferences.getString(key_capture2,null);

        if(a != null && b != null) {
            Toast.makeText(getApplicationContext(),"사진은 최대 2장까지 선택가능합니다.",Toast.LENGTH_LONG).show();
            return;
        }
        else if((a == null && b == null) || (a==null && b!=null)) {
            capture1.setImageURI(img1);
            capture1.setVisibility(View.VISIBLE);
            editor.putString(key1, String.valueOf(img1));
            editor.commit();
        }
        else if(a!=null && b==null){
            capture2.setImageURI(img1);
            capture2.setVisibility(View.VISIBLE);
            editor.putString(key2, String.valueOf(img1));
            editor.commit();
        } else{
            Toast.makeText(getApplicationContext(),"사진은 최대 2장까지 선택가능합니다.",Toast.LENGTH_LONG).show();
            return;
        }
    }

    @OnClick(R.id.receipt_change)
    public void receipt_change() {
        if(check == true){
            receipt_content.setEnabled(true);
            receipt_content.setFocusableInTouchMode(true);
            receipt_content.requestFocus();
            save.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
            check = false;
        } else if(check == false){
            receipt_content.setEnabled(false);
            receipt_content.setFocusable(false);
            receipt_content.setFocusableInTouchMode(false);
            save.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            check = true;
        }
    }

    @OnClick(R.id.content_save)
    public void click(){
        content_result = receipt_content.getText().toString();
        receipt_content.setText(content_result);
        try {
            imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
        }
        receipt_content.setEnabled(false);
        receipt_content.setFocusable(false);
        receipt_content.setFocusableInTouchMode(false);
        save.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.content_cancel)
    public void cancel(){
        try {
            imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
        }
        receipt_content.setEnabled(false);
        receipt_content.setFocusable(false);
        receipt_content.setFocusableInTouchMode(false);
        save.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        editor.putString(preferences,receipt_content.getText().toString());
        editor.commit();
        super.onBackPressed();
    }

    @Override
    public void onStop() {
        super.onStop();
        try{
            EventBus.getDefault().unregister(this);
        }catch (Exception e){}
    }
}
