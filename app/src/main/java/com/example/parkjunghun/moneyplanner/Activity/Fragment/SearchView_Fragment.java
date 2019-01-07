package com.example.parkjunghun.moneyplanner.Activity.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.parkjunghun.moneyplanner.Activity.Component.CustomDialog;
import com.example.parkjunghun.moneyplanner.Activity.Component.CustomPaymentDialog;
import com.example.parkjunghun.moneyplanner.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchView_Fragment extends Fragment {
    @BindView(R.id.searchview_tablayout)
    TabLayout searchview_tablayout;
    @BindView(R.id.searchdate_txtview)
    TextView searchdate_txtview;
    @BindView(R.id.search_btn)
    Button search_btn;
    @BindView(R.id.search_pay_txtview)
    TextView search_pay_txtview;
    @BindView(R.id.search_setcategory_txtview)
    TextView search_category_txtview;
    @BindView(R.id.search_recyclerview)
    RecyclerView search_recyclerview;


    public SearchView_Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.searchview_layout, container, false);
        ButterKnife.bind(this, view);
        searchdate_txtview.setText("전체 날짜");

        searchview_tablayout.addTab(searchview_tablayout.newTab().setText("전체"));
        searchview_tablayout.addTab(searchview_tablayout.newTab().setText("이번 주"));
        searchview_tablayout.addTab(searchview_tablayout.newTab().setText("이번 달"));
        searchview_tablayout.addTab(searchview_tablayout.newTab().setText("사용자지정"));

        searchview_tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(searchview_tablayout.getSelectedTabPosition() == 0){
                    searchdate_txtview.setText("전체 날짜");
                }else if(searchview_tablayout.getSelectedTabPosition() == 1){
                    searchdate_txtview.setText("이번 주 날짜");
                }else if(searchview_tablayout.getSelectedTabPosition() == 2){
                    searchdate_txtview.setText("이번 달 날짜");
                }else if(searchview_tablayout.getSelectedTabPosition() == 3){
                    searchdate_txtview.setText("사용자 지정");
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @OnClick(R.id.search_setcategory_txtview)
    public void setCategoryOnclick(){
        CustomDialog dialog = new CustomDialog(getActivity());
        dialog.callMethod();
    }

    @OnClick(R.id.search_pay_txtview)
    public void setPaymentOnclick(){
        CustomPaymentDialog dialog = new CustomPaymentDialog(getActivity());
        dialog.callMethod();
    }

    @OnClick(R.id.search_btn)
    public void searchBtnOnclick(){

    }
}
