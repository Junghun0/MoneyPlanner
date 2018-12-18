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

public class Add_First_Fragment extends Fragment {

    @BindView(R.id.add_button1) Button add_button1;
    @BindView(R.id.add_button2) Button add_button2;
    @BindView(R.id.add_edit1) EditText add_edit1;

    public Add_First_Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_first_fragment_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.add_button1)
    public void incomeButton(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_view, new Add_Second_Fragment()).addToBackStack(null).commit();
    }

    @OnClick(R.id.add_button2)
    public void spendButton(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_view, new Add_Second_Fragment()).addToBackStack(null).commit();
    }
}

