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
public class Add_Secondspend_Fragment extends Fragment {

    private static final String TAG = "AddspendFragment";

    InputMethodManager inputMethodManager;
    @BindView(R.id.second_spend_edittext)
    EditText second_spend_edittext;
    @BindView(R.id.second_spendlinear)
    LinearLayout second_spendlinear;


    @BindView(R.id.income_btn1)
    ImageButton income_btn1;
    @BindView(R.id.income_btn2)
    ImageButton income_btn2;
    @BindView(R.id.income_btn3)
    ImageButton income_btn3;
    @BindView(R.id.income_btn4)
    ImageButton income_btn4;
    @BindView(R.id.income_btn5)
    ImageButton income_btn5;
    @BindView(R.id.income_btn6)
    ImageButton income_btn6;
    @BindView(R.id.income_btn7)
    ImageButton income_btn7;
    @BindView(R.id.income_btn8)
    ImageButton income_btn8;
    @BindView(R.id.income_btn9)
    ImageButton income_btn9;
    @BindView(R.id.income_btn10)
    ImageButton income_btn10;
    @BindView(R.id.income_btn11)
    ImageButton income_btn11;
    @BindView(R.id.income_btn12)
    ImageButton income_btn12;
    @BindView(R.id.income_btn13)
    ImageButton income_btn13;
    @BindView(R.id.income_btn14)
    ImageButton income_btn14;
    @BindView(R.id.income_btn15)
    ImageButton income_btn15;
    @BindView(R.id.income_btn16)
    ImageButton income_btn16;



    private int usingmoney;
    private String selectDate;
    private String data_key;
    private DetailMoneyInfo data;
    public static Add_Secondspend_Fragment newInstance(String selectDate, int usingmoney) {

        Bundle args = new Bundle();
        args.putString("selectDate",selectDate);
        args.putInt("usingmoney",usingmoney);
        Add_Secondspend_Fragment fragment = new Add_Secondspend_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    public Add_Secondspend_Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_secondspend_fragment, container, false);
        ButterKnife.bind(this, view);

        Log.e("First_Fragment","add_secondspend_first oncreateview......");
        data_key = FirebaseDatabase.getInstance().getReference().push().getKey();

        if(getArguments() != null){
            selectDate = getArguments().getString("selectDate");
            usingmoney = getArguments().getInt("usingmoney");
        }

        second_spend_edittext.setFocusableInTouchMode(true);
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);

        Log.d(TAG, "" + selectDate + "," + usingmoney);
        return view;
    }

    @OnClick(R.id.second_spendlinear)
    public void hideKeyboard() {
        inputMethodManager.hideSoftInputFromWindow(second_spend_edittext.getWindowToken(), 0);
    }

//    @OnClick(R.id.second_spend_info)
//    public void onClickspendInfo() {
//        data = new DetailMoneyInfo(data_key,"지출",selectDate,usingmoney);
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.main_view2, First_Fragment.newInstance2(data)).commit();
//    }

//    @OnClick(R.id.spend_btn1)
//    public void onClickbtn1(){
//        data = new DetailMoneyInfo(data_key,"지출",selectDate,usingmoney,"img1");
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.main_view2, First_Fragment.newInstance2(data)).commit();
//
//    }
//
//    @OnClick(R.id.spend_btn2)
//    public void onClickbtn2(){
//        data = new DetailMoneyInfo(data_key,"지출",selectDate,usingmoney,"img2");
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.main_view2, First_Fragment.newInstance2(data)).commit();
//
//    }
//
//    @OnClick(R.id.spend_btn3)
//    public void onClickbtn3(){
//        data = new DetailMoneyInfo(data_key,"지출",selectDate,usingmoney,"img3");
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.main_view2, First_Fragment.newInstance2(data)).commit();
//
//    }
//
//    @OnClick(R.id.spend_btn4)
//    public void onClickbtn4(){
//        data = new DetailMoneyInfo(data_key,"지출",selectDate,usingmoney,"img4");
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.main_view2, First_Fragment.newInstance2(data)).commit();
//
//    }
@OnClick(R.id.income_btn1)
public void onClickbtn1(){
    data = new DetailMoneyInfo(data_key,"지출",selectDate,usingmoney,"img1");
    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.main_view2, First_Fragment.newInstance2(data)).commit();
}

    @OnClick(R.id.income_btn2)
    public void onClickbtn2(){
        data = new DetailMoneyInfo(data_key,"지출",selectDate,usingmoney,"img2");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_view2, First_Fragment.newInstance2(data)).commit();
    }

    @OnClick(R.id.income_btn3)
    public void onClickbtn3(){
        data = new DetailMoneyInfo(data_key,"지출",selectDate,usingmoney,"img3");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_view2, First_Fragment.newInstance2(data)).commit();

    }

    @OnClick(R.id.income_btn4)
    public void onClickbtn4(){
        data = new DetailMoneyInfo(data_key,"지출",selectDate,usingmoney,"img4");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_view2, First_Fragment.newInstance2(data)).commit();

    }
    @OnClick(R.id.income_btn5)
    public void onClickbtn5(){
        data = new DetailMoneyInfo(data_key,"지출",selectDate,usingmoney,"img5");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_view2, First_Fragment.newInstance2(data)).commit();

    }

    @OnClick(R.id.income_btn6)
    public void onClickbtn6(){
        data = new DetailMoneyInfo(data_key,"지출",selectDate,usingmoney,"img6");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_view2, First_Fragment.newInstance2(data)).commit();

    }

    @OnClick(R.id.income_btn7)
    public void onClickbtn7(){
        data = new DetailMoneyInfo(data_key,"지출",selectDate,usingmoney,"img7");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_view2, First_Fragment.newInstance2(data)).commit();

    }

    @OnClick(R.id.income_btn8)
    public void onClickbtn8(){
        data = new DetailMoneyInfo(data_key,"지출",selectDate,usingmoney,"img8");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_view2, First_Fragment.newInstance2(data)).commit();

    }

    @OnClick(R.id.income_btn9)
    public void onClickbtn9(){
        data = new DetailMoneyInfo(data_key,"지출",selectDate,usingmoney,"img9");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_view2, First_Fragment.newInstance2(data)).commit();

    }

    @OnClick(R.id.income_btn10)
    public void onClickbtn10(){
        data = new DetailMoneyInfo(data_key,"지출",selectDate,usingmoney,"img10");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_view2, First_Fragment.newInstance2(data)).commit();

    }

    @OnClick(R.id.income_btn11)
    public void onClickbtn11(){
        data = new DetailMoneyInfo(data_key,"지출",selectDate,usingmoney,"img11");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_view2, First_Fragment.newInstance2(data)).commit();

    }

    @OnClick(R.id.income_btn12)
    public void onClickbtn12(){
        data = new DetailMoneyInfo(data_key,"지출",selectDate,usingmoney,"img12");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_view2, First_Fragment.newInstance2(data)).commit();

    }

    @OnClick(R.id.income_btn13)
    public void onClickbtn13(){
        data = new DetailMoneyInfo(data_key,"지출",selectDate,usingmoney,"img13");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_view2, First_Fragment.newInstance2(data)).commit();

    }

    @OnClick(R.id.income_btn14)
    public void onClickbtn14(){
        data = new DetailMoneyInfo(data_key,"지출",selectDate,usingmoney,"img14");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_view2, First_Fragment.newInstance2(data)).commit();

    }

    @OnClick(R.id.income_btn15)
    public void onClickbtn15(){
        data = new DetailMoneyInfo(data_key,"지출",selectDate,usingmoney,"img15");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_view2, First_Fragment.newInstance2(data)).commit();

    }

    @OnClick(R.id.income_btn16)
    public void onClickbtn16(){
        data = new DetailMoneyInfo(data_key,"지출",selectDate,usingmoney,"img16");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_view2, First_Fragment.newInstance2(data)).commit();

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("First_Fragment","add_secondspend ondestroy......");
    }
}
