package com.example.parkjunghun.moneyplanner.Activity.Adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

import com.example.parkjunghun.moneyplanner.Activity.Model.DetailMoneyInfo;
import com.example.parkjunghun.moneyplanner.Activity.Model.ScheduleViewItem;
import com.example.parkjunghun.moneyplanner.R;

public class ScheduleRecyclerviewAdapter extends RecyclerView.Adapter<ScheduleRecyclerviewAdapter.ItemViewHolder>{

    private ArrayList<DetailMoneyInfo> arrayList;
    private NumberFormat numberFormat = NumberFormat.getInstance();
    private ItemViewHolder itemViewHolder1;

    public ScheduleRecyclerviewAdapter(ArrayList<DetailMoneyInfo> arrayList){
        this.arrayList = arrayList;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView schedule_image;
        private TextView schedule_name;
        private TextView schedule_money;
        private ImageView schedule_change;

    ItemViewHolder(View itemView){
        super(itemView);
        schedule_image = (ImageView)itemView.findViewById(R.id.schedule_image);
        schedule_name = (TextView)itemView.findViewById(R.id.schedule_name);
        schedule_money = (TextView)itemView.findViewById(R.id.schedule_money);
        schedule_change = (ImageView)itemView.findViewById(R.id.schedule_change);
        schedule_change.setOnClickListener(this);
    }

        @Override
        public void onClick(View v) {
            Log.e("asd","요기!");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        itemViewHolder1 = itemViewHolder;
        DetailMoneyInfo detailMoneyInfo = arrayList.get(i);
        //itemViewHolder.schedule_image.setImageResource(detailMoneyInfo.getUsingMoney());
        itemViewHolder1.schedule_name.setText(detailMoneyInfo.getSelectDate());
        itemViewHolder1.schedule_change.setImageResource(R.drawable.ic_loop_black_24dp);
        if(detailMoneyInfo.getType().equals("수입")){
            itemViewHolder1.schedule_money.setTextColor(Color.GREEN);
            itemViewHolder1.schedule_change.setColorFilter(Color.GREEN);
        }else if(detailMoneyInfo.getType().equals("지출")){
            itemViewHolder1.schedule_money.setTextColor(Color.RED);
            itemViewHolder1.schedule_change.setColorFilter(Color.RED);
        }
        itemViewHolder1.schedule_money.setText(numberFormat.format(detailMoneyInfo.getUsingMoney()) + "원");
        itemViewHolder1.schedule_change.setVisibility(View.INVISIBLE);
    }

    /*public void isShow(boolean asd){
        itemViewHolder1.schedule_change.setVisibility(View.VISIBLE);
    }*/

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.schedule_recyclerview,viewGroup,false);
        return new ItemViewHolder(view);
    }

    public void setItem(ArrayList<DetailMoneyInfo> dataList) {
        this.arrayList = dataList;
        notifyDataSetChanged();
    }
}
