package com.example.parkjunghun.moneyplanner.Activity.Activity;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import com.example.parkjunghun.moneyplanner.Activity.Model.Weekly_Update_Event;
import com.example.parkjunghun.moneyplanner.Activity.Util.DatabaseManager;
import com.example.parkjunghun.moneyplanner.R;

import org.greenrobot.eventbus.EventBus;

import java.io.InputStream;
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
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<String> imageList = new ArrayList<>();
    private InputMethodManager imm;
    private InputStream in;
    private Bitmap img;
    private Bitmap shared_bit1;
    private Bitmap shared_bit2;
    private String key1;
    private String key2;
    private String shared_capture1;
    private String shared_capture2;

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
            preferences = "수입 ";
        } else if (type.equals("outlay")) {
            receipt.setText("수출 영수증");
            preferences = "수출 ";
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

        Log.e("asd",shared_capture1 + " okok " + shared_capture2);

        if(shared_capture1 == null && shared_capture2 == null) {
            capture1.setVisibility(View.INVISIBLE);
            capture2.setVisibility(View.INVISIBLE);
        } else if(shared_capture1 != null && shared_capture2 == null){
            capture1.setVisibility(View.VISIBLE);
            shared_bit1 = StringToBitMap(shared_capture1);
            capture1.setImageBitmap(shared_bit1);
            capture2.setVisibility(View.INVISIBLE);
        } else if(shared_capture1 == null && shared_capture2 != null){
            capture1.setVisibility(View.INVISIBLE);
            capture2.setVisibility(View.VISIBLE);
            shared_bit2 = StringToBitMap(shared_capture2);
            capture2.setImageBitmap(shared_bit2);
        } else {
            capture1.setVisibility(View.VISIBLE);
            shared_bit1 = StringToBitMap(shared_capture1);
            capture1.setImageBitmap(shared_bit1);
            capture2.setVisibility(View.VISIBLE);
            shared_bit2 = StringToBitMap(shared_capture2);
            capture2.setImageBitmap(shared_bit2);
        }

        String day[] = days.split("-");
        month.setText(day[1] + "월");
    }

    @OnClick(R.id.cancel_button)
    public void CancelButton() {
        editor.putString(preferences,receipt_content.getText().toString());
        for(int i=0;i<arrayList.size();i++){
            if(arrayList.get(i).equals(key1)){
                Log.e("asd","okok");
                editor.putString(key1,imageList.get(i));
            }else{
                Log.e("asd","okok123");
                editor.putString(key2,imageList.get(i));
            }
        }
        /*for(int i=0;i<imageList.size();i++){
            editor.putString(arrayList.get(i),imageList.get(i));
        }*/
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
       // if(arrayList.size() != 2) {
            intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(intent, "다중 선택은 '포토'를 선택하세요.(최대 2장 선택 가능)"), 1);
/*        } else {
            Toast.makeText(getApplicationContext(),"2장 모두 사진을 등록하셨습니다. 편집하고 싶은 사진을 선택해주세요.",Toast.LENGTH_LONG).show();
        }*/
    }

    @OnClick(R.id.image_capture1)
    public void capture1(){
        Log.e("asd","이미지 클릭하였습니다.");
    }

    @OnClick(R.id.image_capture2)
    public void capture2(){
        Log.e("asd","이미지2 클릭하였습니다.");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            //사진 선택할때 개수 세줘야한다
            if (resultCode == RESULT_OK) {
                try {
                    if(data.getClipData() == null){
                        in = getContentResolver().openInputStream(data.getData());
                        img = BitmapFactory.decodeStream(in);
                        if(shared_capture1 == null)
                            imageList.add(0,BitMapToString(img));
                        else
                            imageList.add(1,BitMapToString(img));
                        selectImage(img);
                    } else{
                        ClipData clipdata = data.getClipData();
                        if(clipdata.getItemCount() > 2){
                            Toast.makeText(getApplicationContext(),"사진은 최대 2장까지 선택가능합니다.",Toast.LENGTH_LONG).show();
                            return;
                        } else if(clipdata.getItemCount() == 1){
                            in = getContentResolver().openInputStream(clipdata.getItemAt(0).getUri());
                            img = BitmapFactory.decodeStream(in);
                            if(shared_capture1 == null)
                                imageList.add(0,BitMapToString(img));
                            else
                                imageList.add(1,BitMapToString(img));
                            selectImage(img);
                        } else if(clipdata.getItemCount() == 2){
                            for(int i=0;i<clipdata.getItemCount(); i++){
                                in = getContentResolver().openInputStream(clipdata.getItemAt(i).getUri());
                                img = BitmapFactory.decodeStream(in);
                                in.close();
                                imageList.add(BitMapToString(img));
                                selectImage(img);
                            }
                        }
                    }
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    public void selectImage(Bitmap bItmap){
        if(arrayList.size() == 0){
            capture1.setImageBitmap(bItmap);
            capture1.setVisibility(View.VISIBLE);
            arrayList.add(0,key1);
        } else if(arrayList.size() == 1){
            if(arrayList.get(0).equals(key1)) {
                capture2.setImageBitmap(bItmap);
                capture2.setVisibility(View.VISIBLE);
                arrayList.add(1,key2);
            }else {
                capture1.setImageBitmap(bItmap);
                capture1.setVisibility(View.VISIBLE);
                arrayList.add(0,key1);
            }
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
        for(int i=0;i<arrayList.size();i++){
            if(arrayList.get(i).equals(key1)){
                editor.putString(key1,imageList.get(i));
            }else{
                editor.putString(key2,imageList.get(i));
            }
        }
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
