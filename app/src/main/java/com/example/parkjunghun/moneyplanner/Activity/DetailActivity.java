package com.example.parkjunghun.moneyplanner.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkjunghun.moneyplanner.Activity.Adapter.CalendarRecyclerviewAdapter;
import com.example.parkjunghun.moneyplanner.Activity.Fragment.First_Fragment;
import com.example.parkjunghun.moneyplanner.Activity.Model.DetailMoneyInfo;
import com.example.parkjunghun.moneyplanner.Activity.Util.DatabaseManager;
import com.example.parkjunghun.moneyplanner.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends AppCompatActivity {

    private DetailMoneyInfo moneyInfo;
    private int recycler_index;
    @BindView(R.id.getintent1)
    TextView getintent1;
    @BindView(R.id.getintent2)
    TextView getintent2;
    @BindView(R.id.detail_moneyinfo)
    TextView detail_moneyinfo;
    @BindView(R.id.delete_button)
    Button delete_button;

    CalendarRecyclerviewAdapter adapter;
    First_Fragment context;
    private ArrayList<DetailMoneyInfo> dataList = new ArrayList<>();
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        moneyInfo = (DetailMoneyInfo) getIntent().getSerializableExtra("data");
        recycler_index = getIntent().getIntExtra("index", 0);
        getintent1.setText(String.valueOf(moneyInfo.getSelectDate()));
        getintent2.setText(String.valueOf(moneyInfo.getUsingMoney()));
        detail_moneyinfo.setText(String.valueOf(moneyInfo.getType()));
        adapter = new CalendarRecyclerviewAdapter(context, dataList);
        date = moneyInfo.getSelectDate();
    }

    @OnClick(R.id.delete_button)
    public void onClickDelete() {
        //DatabaseManager.getInstance().deleteMoneyInfo(moneyInfo.getKey(),moneyInfo.getSelectDate());
        Log.e("database","Detailac->"+recycler_index+"getkey->"+moneyInfo.getKey());
        DatabaseManager.getInstance().deleteMoneyInfo2(adapter, moneyInfo.getKey(),moneyInfo.getSelectDate(), recycler_index);
        Toast.makeText(this, "데이터가삭제되었습니다.", Toast.LENGTH_SHORT).show();
        adapter.notifyItemRemoved(recycler_index);
        Intent intent2 = new Intent(this, MainActivity.class);
        intent2.putExtra("index",recycler_index);
        intent2.putExtra("key",moneyInfo.getKey());
        intent2.putExtra("date",date);
        startActivity(intent2);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("first","detail ac ondestory");
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
        Log.e("first","detail ac onstop");
    }

}
