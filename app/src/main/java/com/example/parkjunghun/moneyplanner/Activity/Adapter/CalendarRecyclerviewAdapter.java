package com.example.parkjunghun.moneyplanner.Activity.Adapter;

import android.content.Intent;
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

import com.example.parkjunghun.moneyplanner.Activity.DetailActivity;
import com.example.parkjunghun.moneyplanner.Activity.Fragment.First_Fragment;
import com.example.parkjunghun.moneyplanner.Activity.Model.DetailMoneyInfo;
import com.example.parkjunghun.moneyplanner.R;

import java.util.ArrayList;


public class CalendarRecyclerviewAdapter extends RecyclerView.Adapter<CalendarRecyclerviewAdapter.ViewHolder> {
    private First_Fragment context;
    private ArrayList<DetailMoneyInfo> dataList;

    public CalendarRecyclerviewAdapter(First_Fragment context, ArrayList<DetailMoneyInfo> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView calendar_info_textview;
        private TextView calendar_dayinfo_textview;
        private ImageView calendar_info_imgview;
        private TextView calendar_img_info;
        private CardView calendar_cardview;
        private ViewHolder mviewholder;

        public ViewHolder(View itemView) {
            super(itemView);
            calendar_info_textview = (TextView) itemView.findViewById(R.id.calendar_info_textview);
            calendar_dayinfo_textview = (TextView) itemView.findViewById(R.id.calendar_dayinfo_textview);
            calendar_info_imgview = (ImageView) itemView.findViewById(R.id.calendar_info_imgview);
            calendar_img_info = (TextView) itemView.findViewById(R.id.calendar_img_info);
            calendar_cardview = (CardView) itemView.findViewById(R.id.calendar_cardview);
            calendar_cardview.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context.getContext(), DetailActivity.class);
            intent.putExtra("data", dataList.get(getLayoutPosition()));
            intent.putExtra("index", getLayoutPosition());
            Log.e("database first_frag","recycler onclick->"+getLayoutPosition());
            context.startActivity(intent);
        }
    }

    @NonNull
    @Override
    public CalendarRecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.calendar_recyclerview, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        DetailMoneyInfo detailMoneyInfo = dataList.get(i);
        viewHolder.calendar_dayinfo_textview.setText(detailMoneyInfo.getSelectDate());
        viewHolder.calendar_info_textview.setText(String.valueOf(detailMoneyInfo.getUsingMoney()+" 원"));
        if(detailMoneyInfo.getType().equals("수입")){

            viewHolder.calendar_info_textview.setTextColor(Color.rgb(0,120,189));

            if(detailMoneyInfo.getImgUrl() != null){
                if(detailMoneyInfo.getImgUrl().equals("img1")){
                    viewHolder.calendar_info_imgview.setImageResource(R.drawable.btn1);
                    viewHolder.calendar_img_info.setText("월급");
                }else if(detailMoneyInfo.getImgUrl().equals("img2")){
                    viewHolder.calendar_info_imgview.setImageResource(R.drawable.btn2);
                    viewHolder.calendar_img_info.setText("용돈");
                }else if(detailMoneyInfo.getImgUrl().equals("img3")){
                    viewHolder.calendar_info_imgview.setImageResource(R.drawable.btn3);
                    viewHolder.calendar_img_info.setText("이월");
                }else if(detailMoneyInfo.getImgUrl().equals("img4")){
                    viewHolder.calendar_info_imgview.setImageResource(R.drawable.btn4);
                    viewHolder.calendar_img_info.setText("기타");
                }
            }

        }else{
            viewHolder.calendar_info_textview.setTextColor(Color.rgb(223,110,80));

            if(detailMoneyInfo.getImgUrl() != null){

                if(detailMoneyInfo.getImgUrl().equals("img1")){
                    viewHolder.calendar_info_imgview.setImageResource(R.drawable.btn1);
                    viewHolder.calendar_img_info.setText("교통");
                }else if(detailMoneyInfo.getImgUrl().equals("img2")){
                    viewHolder.calendar_info_imgview.setImageResource(R.drawable.btn2);
                    viewHolder.calendar_img_info.setText("쇼핑");
                }else if(detailMoneyInfo.getImgUrl().equals("img3")){
                    viewHolder.calendar_info_imgview.setImageResource(R.drawable.btn3);
                    viewHolder.calendar_img_info.setText("교육");
                }else if(detailMoneyInfo.getImgUrl().equals("img4")){
                    viewHolder.calendar_info_imgview.setImageResource(R.drawable.btn4);
                    viewHolder.calendar_img_info.setText("저축");
                }else if(detailMoneyInfo.getImgUrl().equals("img5")){
                    viewHolder.calendar_info_imgview.setImageResource(R.drawable.btn5);
                    viewHolder.calendar_img_info.setText("통신");
                }else if(detailMoneyInfo.getImgUrl().equals("img6")){
                    viewHolder.calendar_info_imgview.setImageResource(R.drawable.btn6);
                    viewHolder.calendar_img_info.setText("식비");
                }else if(detailMoneyInfo.getImgUrl().equals("img7")){
                    viewHolder.calendar_info_imgview.setImageResource(R.drawable.btn7);
                    viewHolder.calendar_img_info.setText("의료");
                }else if(detailMoneyInfo.getImgUrl().equals("img8")){
                    viewHolder.calendar_info_imgview.setImageResource(R.drawable.btn8);
                    viewHolder.calendar_img_info.setText("생활비");
                }else if(detailMoneyInfo.getImgUrl().equals("img9")){
                    viewHolder.calendar_info_imgview.setImageResource(R.drawable.btn9);
                    viewHolder.calendar_img_info.setText("미용");
                }else if(detailMoneyInfo.getImgUrl().equals("img10")){
                    viewHolder.calendar_info_imgview.setImageResource(R.drawable.btn10);
                    viewHolder.calendar_img_info.setText("여행");
                }else if(detailMoneyInfo.getImgUrl().equals("img11")){
                    viewHolder.calendar_info_imgview.setImageResource(R.drawable.btn11);
                    viewHolder.calendar_img_info.setText("문화");
                }else if(detailMoneyInfo.getImgUrl().equals("img12")){
                    viewHolder.calendar_info_imgview.setImageResource(R.drawable.btn12);
                    viewHolder.calendar_img_info.setText("주거");
                }else if(detailMoneyInfo.getImgUrl().equals("img13")){
                    viewHolder.calendar_info_imgview.setImageResource(R.drawable.btn13);
                    viewHolder.calendar_img_info.setText("세금");
                }else if(detailMoneyInfo.getImgUrl().equals("img14")){
                    viewHolder.calendar_info_imgview.setImageResource(R.drawable.btn14);
                    viewHolder.calendar_img_info.setText("취미");
                }else if(detailMoneyInfo.getImgUrl().equals("img15")){
                    viewHolder.calendar_info_imgview.setImageResource(R.drawable.btn15);
                    viewHolder.calendar_img_info.setText("보험");
                }else if(detailMoneyInfo.getImgUrl().equals("img16")){
                    viewHolder.calendar_info_imgview.setImageResource(R.drawable.btn16);
                    viewHolder.calendar_img_info.setText("월급");
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
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

    public void clearItem(ArrayList<DetailMoneyInfo> dataList){
        this.dataList = dataList;
        Log.e("database","adapter->clearItem");
        dataList.clear();
        notifyDataSetChanged();
    }
}
