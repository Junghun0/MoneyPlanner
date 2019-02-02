package com.example.parkjunghun.moneyplanner.Activity.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.parkjunghun.moneyplanner.Activity.Model.DetailMoneyInfo;
import com.example.parkjunghun.moneyplanner.R;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("ValidFragment")
public class Add_Secondincome_Fragment extends Fragment {

    private static final String TAG = "AddincomeFragment";
    InputMethodManager inputMethodManager;
    private int usingmoney;
    private String selectDate;
    @BindView(R.id.second_add_edittext)
    EditText second_add_edittext;

    @BindView(R.id.spend_btn1)
    ImageButton spend_btn1;
    @BindView(R.id.spend_btn2)
    ImageButton spend_btn2;
    @BindView(R.id.spend_btn3)
    ImageButton spend_btn3;
    @BindView(R.id.spend_btn4)
    ImageButton spend_btn4;

    @BindView(R.id.add_second_linear)
    LinearLayout add_second_linear;

    private DetailMoneyInfo data;
    private String data_key;

    public static Add_Secondincome_Fragment newInstance(String selectDate , int usingmoney) {

        Bundle args = new Bundle();
        args.putString("selectDate",selectDate);
        args.putInt("usingmoney",usingmoney);
        Add_Secondincome_Fragment fragment = new Add_Secondincome_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    public Add_Secondincome_Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_secondincome_fragment_layout, container, false);
        data_key = FirebaseDatabase.getInstance().getReference().push().getKey();
        ButterKnife.bind(this, view);
        if(getArguments() != null){
            usingmoney = getArguments().getInt("usingmoney");
            selectDate = getArguments().getString("selectDate");
        }

        second_add_edittext.setFocusableInTouchMode(true);
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);

        Log.d(TAG, "" + usingmoney + "," + selectDate);
        return view;
    }

    @OnClick(R.id.add_second_linear)
    public void hideKeyboard() {
        inputMethodManager.hideSoftInputFromWindow(second_add_edittext.getWindowToken(), 0);
    }

    @OnClick(R.id.spend_btn1)
    public void onClickbtn1(){
    data = new DetailMoneyInfo(data_key,"수입",selectDate,usingmoney,"img1");
    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.main_view2, First_Fragment.newInstance2(data)).commit();

}

    @OnClick(R.id.spend_btn2)
    public void onClickbtn2(){
        data = new DetailMoneyInfo(data_key,"수입",selectDate,usingmoney,"img2");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_view2, First_Fragment.newInstance2(data)).commit();

    }

    @OnClick(R.id.spend_btn3)
    public void onClickbtn3(){
        data = new DetailMoneyInfo(data_key,"수입",selectDate,usingmoney,"img3");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_view2, First_Fragment.newInstance2(data)).commit();

    }

    @OnClick(R.id.spend_btn4)
    public void onClickbtn4(){
        data = new DetailMoneyInfo(data_key,"수입",selectDate,usingmoney,"img4");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_view2, First_Fragment.newInstance2(data)).commit();

    }





    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("First_Fragment","add_secondincome ondestroy......");
    }
}
