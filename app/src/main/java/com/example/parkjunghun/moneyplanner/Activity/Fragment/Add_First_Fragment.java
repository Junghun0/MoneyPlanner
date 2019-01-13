package com.example.parkjunghun.moneyplanner.Activity.Fragment;

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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.parkjunghun.moneyplanner.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Add_First_Fragment extends Fragment {

    private static final String TAG = "Add_First_Fragment";
    private String selectDate;
    private int usingMoney;
    @BindView(R.id.add_button1)
    Button add_button1;
    @BindView(R.id.add_button2)
    Button add_button2;
    @BindView(R.id.add_edit1)
    EditText add_edit1;
    @BindView(R.id.add_first_linear)
    LinearLayout add_first_linear;
    InputMethodManager inputMethodManager;

    public static Add_First_Fragment newInstance(String selectDate) {

        Bundle args = new Bundle();
        args.putString("selectDate",selectDate);
        Add_First_Fragment fragment = new Add_First_Fragment();
        fragment.setArguments(args);
        return fragment;
    }


    public Add_First_Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_first_fragment_layout, container, false);
        ButterKnife.bind(this, view);

        Log.e("First_Fragment","add_first oncreateview......");
        if(getArguments() != null){
            selectDate = getArguments().getString("selectDate");
        }

        add_edit1.setFocusableInTouchMode(true);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);

        return view;
    }

    @OnClick(R.id.add_first_linear)
    public void hideKeyboard() {
        inputMethodManager.hideSoftInputFromWindow(add_edit1.getWindowToken(), 0);
    }

    @OnClick(R.id.add_button1)
    public void incomeButton() {

        if (add_edit1.getText().toString().equals("")) {
            usingMoney = 0;
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_view2, Add_Secondincome_Fragment.newInstance(selectDate, usingMoney)).commit();
            inputMethodManager.hideSoftInputFromWindow(add_edit1.getWindowToken(), 0);
        } else {
            usingMoney = Integer.valueOf(add_edit1.getText().toString());
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_view2, Add_Secondincome_Fragment.newInstance(selectDate, usingMoney)).commit();
            inputMethodManager.hideSoftInputFromWindow(add_edit1.getWindowToken(), 0);
        }

    }

    @OnClick(R.id.add_button2)
    public void spendButton() {

        if (add_edit1.getText().toString().equals("")) {
            usingMoney = 0;
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_view2, Add_Secondspend_Fragment.newInstance(selectDate, usingMoney)).commit();
            inputMethodManager.hideSoftInputFromWindow(add_edit1.getWindowToken(), 0);
        } else {
            usingMoney = Integer.valueOf(add_edit1.getText().toString());
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_view2, Add_Secondspend_Fragment.newInstance(selectDate, usingMoney)).commit();
            inputMethodManager.hideSoftInputFromWindow(add_edit1.getWindowToken(), 0);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("First_Fragment","add_first ondestroy......");
    }
}

