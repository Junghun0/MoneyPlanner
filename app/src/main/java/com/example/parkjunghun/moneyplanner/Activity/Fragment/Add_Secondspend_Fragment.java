package com.example.parkjunghun.moneyplanner.Activity.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.parkjunghun.moneyplanner.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("ValidFragment")
public class Add_Secondspend_Fragment extends Fragment {

    private static final String TAG = "AddspendFragment";

    InputMethodManager inputMethodManager;
    @BindView(R.id.second_spend_edittext)
    EditText second_spend_edittext;
    @BindView(R.id.second_spend_info)
    Button second_spend_info;
    @BindView(R.id.second_spendlinear)
    LinearLayout second_spendlinear;
    private int usingmoney;
    private String selectDate;

    public Add_Secondspend_Fragment(){}

    @SuppressLint("ValidFragment")
    public Add_Secondspend_Fragment(String selectDate, int usingmoney){
        this.selectDate = selectDate;
        this.usingmoney = usingmoney;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_secondspend_fragment, container ,false);
        ButterKnife.bind(this,view);

        second_spend_edittext.setFocusableInTouchMode(true);
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);

        Log.d(TAG,""+selectDate+","+usingmoney);
        return view;
    }

    @OnClick(R.id.second_spendlinear)
    public void hideKeyboard(){
        inputMethodManager.hideSoftInputFromWindow(second_spend_edittext.getWindowToken(),0);
    }

    @OnClick(R.id.second_spend_info)
    public void onClickspendInfo(){

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_view, First_Fragment.newInstance("지출",selectDate,usingmoney)).addToBackStack(null).commit();
        inputMethodManager.hideSoftInputFromWindow(second_spend_edittext.getWindowToken(),0);
    }
}
