package com.example.parkjunghun.moneyplanner.Activity.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.parkjunghun.moneyplanner.Activity.Model.ScheduleViewItem;
import com.example.parkjunghun.moneyplanner.R;

public class ScheduleRecyclerviewAdapter extends RecyclerView.Adapter<ScheduleRecyclerviewAdapter.ItemViewHolder>{

    private ArrayList<ScheduleViewItem> arrayList;

    public ScheduleRecyclerviewAdapter(ArrayList<ScheduleViewItem> arrayList){

        this.arrayList = arrayList;

    }

    static class ItemViewHolder extends RecyclerView.ViewHolder{

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
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.schedule_image.setImageResource(arrayList.get(i).getImage());
        itemViewHolder.schedule_name.setText(arrayList.get(i).getName());
        itemViewHolder.schedule_money.setText(arrayList.get(i).getMoney());
        itemViewHolder.schedule_change.setImageResource(arrayList.get(i).getChange_image());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.schedule_recyclerview,viewGroup,false);
        return new ItemViewHolder(view);
    }
}
