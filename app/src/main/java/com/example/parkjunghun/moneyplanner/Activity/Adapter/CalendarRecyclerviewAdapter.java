package com.example.parkjunghun.moneyplanner.Activity.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parkjunghun.moneyplanner.Activity.Fragment.First_Fragment;
import com.example.parkjunghun.moneyplanner.R;

import java.util.ArrayList;


public class CalendarRecyclerviewAdapter extends RecyclerView.Adapter<CalendarRecyclerviewAdapter.ViewHolder> {
    First_Fragment context;
    private ArrayList<String> arrayList;

    public CalendarRecyclerviewAdapter(First_Fragment first_fragment) {
        this.context = first_fragment;
        arrayList = new ArrayList<>();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView calendar_info_textview;
        private TextView calendar_dayinfo_textview;
        private ImageView calendar_info_imgview;
        private CardView calendar_cardview;

        ViewHolder(View itemView) {
            super(itemView);
            calendar_info_textview = (TextView) itemView.findViewById(R.id.calendar_info_textview);
            calendar_dayinfo_textview = (TextView) itemView.findViewById(R.id.calendar_dayinfo_textview);
            calendar_info_imgview = (ImageView) itemView.findViewById(R.id.calendar_info_imgview);
            calendar_cardview = (CardView) itemView.findViewById(R.id.calendar_cardview);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.calendar_recyclerview, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        if(arrayList.isEmpty()){

        }else{
            viewHolder.calendar_dayinfo_textview.setText(arrayList.get(i));
            viewHolder.calendar_info_textview.setText(arrayList.get(i));
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void addItem(String data) {
        //notifyItemInserted(arrayList.size()-1);
        arrayList.add(data);
        notifyItemInserted(arrayList.size() - 1);
        notifyDataSetChanged();
    }

    public void setItem(ArrayList<String> data) {
        this.arrayList = data;
        notifyDataSetChanged();
    }

    public void subItem(ArrayList<String> data) {
        this.arrayList = data;
        data.remove(data.size() - 1);
        notifyDataSetChanged();
    }

}
