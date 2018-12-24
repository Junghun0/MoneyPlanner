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
import android.widget.Button;
import android.widget.EditText;
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
    @BindView(R.id.second_spend_info)
    Button second_spend_info;
    @BindView(R.id.second_spendlinear)
    LinearLayout second_spendlinear;
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

    @OnClick(R.id.second_spend_info)
    public void onClickspendInfo() {
        data = new DetailMoneyInfo(data_key,"지출",selectDate,usingmoney);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_view2, First_Fragment.newInstance2(data)).commit();
    }
}
