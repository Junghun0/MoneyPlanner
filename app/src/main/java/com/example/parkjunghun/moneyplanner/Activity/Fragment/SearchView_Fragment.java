package com.example.parkjunghun.moneyplanner.Activity.Fragment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.parkjunghun.moneyplanner.Activity.Adapter.SearchRecyclerViewAdapter;
import com.example.parkjunghun.moneyplanner.Activity.Component.CustomDialog;
import com.example.parkjunghun.moneyplanner.Activity.Component.CustomPaymentDialog;
import com.example.parkjunghun.moneyplanner.Activity.Model.DetailMoneyInfo;
import com.example.parkjunghun.moneyplanner.Activity.Util.DatabaseManager;
import com.example.parkjunghun.moneyplanner.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    @BindView(R.id.income_sumtxtview)
    TextView income_sumtxtview;
    @BindView(R.id.spend_sumtxtview)
    TextView spend_sumtxtview;
    @BindView(R.id.main_searchView)
    android.support.v7.widget.SearchView main_searchView;

    private SearchRecyclerViewAdapter searchRecyclerViewAdapter;
    private ArrayList<DetailMoneyInfo> searchData = new ArrayList<>();
    private View view;
    public static int tabState = 0;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d", Locale.getDefault());
    private String curdate;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.d", Locale.getDefault());
    private Calendar sunday = Calendar.getInstance();
    private Calendar firstdate = Calendar.getInstance();
    private Calendar lastdate = Calendar.getInstance();
    private Calendar saturday = Calendar.getInstance();

    InputMethodManager inputMethodManager;

    public SearchView_Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.searchview_layout, container, false);
        ButterKnife.bind(this, view);
        searchdate_txtview.setText("전체 날짜");

        searchview_tablayout.addTab(searchview_tablayout.newTab().setText("전체"));
        searchview_tablayout.addTab(searchview_tablayout.newTab().setText("이번 주"));
        searchview_tablayout.addTab(searchview_tablayout.newTab().setText("이번 달"));
        searchview_tablayout.addTab(searchview_tablayout.newTab().setText("사용자지정"));

        //이번주탭 날짜 구하기
        Date date = new Date();
        curdate = dateFormat.format(date);

        String week = String.valueOf(saturday.get(Calendar.WEEK_OF_MONTH));
        String month = String.valueOf(saturday.get(Calendar.MONTH));
        String year = String.valueOf(saturday.get(Calendar.YEAR));


        int w=Integer.parseInt(week);
        saturday.set(Calendar.WEEK_OF_MONTH,w);
        saturday.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);

        sunday.set(Calendar.WEEK_OF_MONTH,w);
        sunday.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);

        //이번달 탭 날짜 구하기
        firstdate.set(Calendar.YEAR, Integer.parseInt(year));
        firstdate.set(Calendar.MONTH, Integer.parseInt(month));
        firstdate.set(Calendar.DATE, firstdate.getActualMinimum(Calendar.DAY_OF_MONTH));

        lastdate.set(Calendar.YEAR, Integer.parseInt(year));
        lastdate.set(Calendar.MONTH, Integer.parseInt(month));
        lastdate.set(Calendar.DATE, lastdate.getActualMaximum(Calendar.DAY_OF_MONTH));


        searchview_tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(searchview_tablayout.getSelectedTabPosition() == 0){
                    tabState = 1;
                    searchdate_txtview.setText("전체 날짜");
                }else if(searchview_tablayout.getSelectedTabPosition() == 1){
                    tabState = 2;
                    searchdate_txtview.setText(formatter.format(saturday.getTime())+" - "+formatter.format(sunday.getTime()));
                }else if(searchview_tablayout.getSelectedTabPosition() == 2){
                    tabState = 3;
                    searchdate_txtview.setText(formatter.format(firstdate.getTime())+" - "+formatter.format(lastdate.getTime()));
                }else if(searchview_tablayout.getSelectedTabPosition() == 3){
                    tabState = 4;
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

        searchRecyclerViewAdapter = new SearchRecyclerViewAdapter(this, searchData);
        search_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        search_recyclerview.setAdapter(searchRecyclerViewAdapter);


        SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        main_searchView.onActionViewExpanded();
        main_searchView.setIconified(false);



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
    public void searchBtnOnclick() {
        if (tabState == 1 || tabState == 0) {
            DatabaseManager.getInstance().getSearchData(searchRecyclerViewAdapter, searchData, view, 1, curdate, formatter.format(saturday.getTime()), formatter.format(sunday.getTime()));
        }else if(tabState == 2){
            DatabaseManager.getInstance().getSearchData(searchRecyclerViewAdapter, searchData, view, 2, curdate, formatter.format(saturday.getTime()), formatter.format(sunday.getTime()));
        }else if(tabState == 3){
            DatabaseManager.getInstance().getSearchData(searchRecyclerViewAdapter, searchData, view, 3, curdate, formatter.format(saturday.getTime()), formatter.format(sunday.getTime()));
        }else if(tabState == 4){
            DatabaseManager.getInstance().getSearchData(searchRecyclerViewAdapter, searchData, view, 4, curdate, formatter.format(saturday.getTime()), formatter.format(sunday.getTime()));
        }
    }
}
