package com.example.parkjunghun.moneyplanner.Activity.Adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

import com.example.parkjunghun.moneyplanner.Activity.Activity.DetailScheduleActivity;
import com.example.parkjunghun.moneyplanner.Activity.Fragment.Third_Fragment;
import com.example.parkjunghun.moneyplanner.Activity.Model.DetailMoneyInfo;
import com.example.parkjunghun.moneyplanner.R;

public class ScheduleRecyclerviewAdapter extends RecyclerView.Adapter<ScheduleRecyclerviewAdapter.ItemViewHolder>{

    private Third_Fragment context;
    private ArrayList<DetailMoneyInfo> arrayList;
    private NumberFormat numberFormat = NumberFormat.getInstance();
    private ItemViewHolder itemViewHolder1;
    private Intent intent;
    private Bitmap sendBitmap;
    private String date;

    private boolean isCheck = true;

    public ScheduleRecyclerviewAdapter(Third_Fragment third_fragment,ArrayList<DetailMoneyInfo> arrayList){
        this.context = third_fragment;
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
            intent = new Intent(context.getContext(), DetailScheduleActivity.class);
            /*sendBitmap = BitmapFactory.decodeResource(context.getContext().getResources(),R.drawable.ic_loop_black_24dp);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            sendBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            intent.putExtra("image",byteArray);*/
            //이미지도 추가해야함
            if(schedule_money.getCurrentTextColor() == Color.GREEN)
                intent.putExtra("type","income");
            else
                intent.putExtra("type","outlay");
            intent.putExtra("date",date);
            intent.putExtra("name",schedule_name.getText().toString());
            intent.putExtra("using_money",schedule_money.getText().toString());
            context.startActivity(intent);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        itemViewHolder1 = itemViewHolder;
        DetailMoneyInfo detailMoneyInfo = arrayList.get(i);
        date = detailMoneyInfo.getSelectDate();
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
        if(isCheck == true) {
            itemViewHolder1.schedule_change.setVisibility(View.INVISIBLE);
        }
        else {
            itemViewHolder1.schedule_change.setVisibility(View.VISIBLE);
        }

        itemViewHolder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {
            intent = new Intent(itemViewHolder1.itemView.getContext(),DetailScheduleActivity.class);
            /*sendBitmap = ((BitmapDrawable)itemViewHolder1.schedule_change.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            sendBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            intent.putExtra("image",byteArray);*/
                if(itemViewHolder1.schedule_money.getCurrentTextColor() == Color.GREEN)
                    intent.putExtra("type","income");
                else
                    intent.putExtra("type","outlay");
            intent.putExtra("name",itemViewHolder1.schedule_name.getText().toString());
            intent.putExtra("using_money",itemViewHolder1.schedule_money.getText().toString());
            intent.putExtra("date",date);
            itemViewHolder1.itemView.getContext().startActivity(intent);

        }
    });
    }

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

    public void isShow(int check){
        if(check % 2 == 1){
            isCheck = false;
        } else{
            isCheck = true;
        }
    }
}