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
public class Add_Secondincome_Fragment extends Fragment {

    private static final String TAG = "AddincomeFragment";
    InputMethodManager inputMethodManager;
    private int usingmoney;
    private String selectDate;
    @BindView(R.id.second_add_edittext)
    EditText second_add_edittext;
    @BindView(R.id.second_add_info)
    Button secondbutton1;
    @BindView(R.id.second_add_info2)
    Button secondbutton2;
    @BindView(R.id.second_add_info3)
    Button secondbutton3;
    @BindView(R.id.second_add_info4)
    Button secondbutton4;
    @BindView(R.id.add_second_linear)
    LinearLayout add_second_linear;

    public Add_Secondincome_Fragment(){}

    @SuppressLint("ValidFragment")
    public Add_Secondincome_Fragment(String selectDate, int usingmoney) {
        this.usingmoney = usingmoney;
        this.selectDate = selectDate;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_secondincome_fragment_layout, container, false);
        ButterKnife.bind(this, view);

        second_add_edittext.setFocusableInTouchMode(true);
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);

        Log.d(TAG,""+usingmoney+","+selectDate);
        return view;
    }

    @OnClick(R.id.add_second_linear)
    public void hideKeyboard(){
        inputMethodManager.hideSoftInputFromWindow(second_add_edittext.getWindowToken(),0);
    }

    @OnClick(R.id.second_add_info)
    public void onClickbutton(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_view, First_Fragment.newInstance("수입",selectDate,usingmoney)).addToBackStack(null).commit();
        inputMethodManager.hideSoftInputFromWindow(second_add_edittext.getWindowToken(),0);
    }
}
