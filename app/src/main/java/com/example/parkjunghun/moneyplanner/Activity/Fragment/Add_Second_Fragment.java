package com.example.parkjunghun.moneyplanner.Activity.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.parkjunghun.moneyplanner.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Add_Second_Fragment extends Fragment {
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_second_fragment_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.second_add_info)
    public void onClickbutton(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().remove(Add_Second_Fragment.this).commit();
    }
}
