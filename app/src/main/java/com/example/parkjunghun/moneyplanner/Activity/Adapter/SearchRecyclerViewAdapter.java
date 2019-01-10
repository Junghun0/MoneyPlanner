package com.example.parkjunghun.moneyplanner.Activity.Adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parkjunghun.moneyplanner.Activity.Fragment.SearchView_Fragment;
import com.example.parkjunghun.moneyplanner.Activity.Model.DetailMoneyInfo;
import com.example.parkjunghun.moneyplanner.R;

import java.util.ArrayList;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.SearchViewHolder> {
    private SearchView_Fragment context;
    private ArrayList<DetailMoneyInfo> dataList;

    public SearchRecyclerViewAdapter(SearchView_Fragment context, ArrayList<DetailMoneyInfo> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.search_recyclerview, viewGroup, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, int i) {
        DetailMoneyInfo detailMoneyInfo = dataList.get(i);
        searchViewHolder.search_moneyinfo_txtview.setText(String.valueOf(detailMoneyInfo.getUsingMoney()));
        searchViewHolder.search_dayinfo_txtview.setText(detailMoneyInfo.getSelectDate());
        if(detailMoneyInfo.getType().equals("수입")){
            searchViewHolder.search_moneyinfo_txtview.setTextColor(Color.rgb(0,120,189));
        }else{
            searchViewHolder.search_moneyinfo_txtview.setTextColor(Color.rgb(223,110,80));
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView search_dayinfo_txtview;
        private TextView search_moneyinfo_txtview;
        private ImageView search_info_imgview;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            search_dayinfo_txtview = (TextView)itemView.findViewById(R.id.search_dayinfo_txtview);
            search_moneyinfo_txtview = (TextView)itemView.findViewById(R.id.search_moneyinfo_txtview);
            search_info_imgview = (ImageView)itemView.findViewById(R.id.search_info_imgview);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public void addItem(DetailMoneyInfo data) {
        dataList.add(data);
        notifyItemInserted(dataList.size() - 1);
        notifyDataSetChanged();
    }

    public void setItem(ArrayList<DetailMoneyInfo> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public void subItem(ArrayList<DetailMoneyInfo> dataList, int index) {
        this.dataList = dataList;
        dataList.remove(index);
        notifyItemRemoved(index);
        //data.remove(data.size() - 1);
        notifyDataSetChanged();
    }

    public void clearItem(){
        dataList.clear();
        notifyDataSetChanged();
    }
}
