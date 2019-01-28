package com.example.parkjunghun.moneyplanner.Activity.Component;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.parkjunghun.moneyplanner.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomPaymentDialog {

    @BindView(R.id.dialog_cancel_btn)
    Button dialog_cancel_btn;
    @BindView(R.id.dialog_ok_btn)
    Button dialog_ok_btn;
    @BindView(R.id.dialog_selected_all)
    CheckBox dialog_selected_all;
    @BindView(R.id.dialog_selected_card)
    CheckBox dialog_selected_card;
    @BindView(R.id.dialog_selected_cash)
    CheckBox dialog_selected_cash;
    @BindView(R.id.dialog_selected_checkcard)
    CheckBox dialog_selected_checkcard;

    private Dialog dialog;

    private Context context;

    public CustomPaymentDialog(Context context){
        this.context = context;
    }

    public void callMethod(){
        dialog = new Dialog(context);
        ButterKnife.bind(dialog);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_payment_dialog);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        Window window = dialog.getWindow();
        window.setAttributes(lp);

        dialog.show();

        //신용카드 선택
        dialog_selected_card.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    //체크되있을때

                }else{

                }
            }
        });

        //현금선택
        dialog_selected_cash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){

                }else{

                }
            }
        });

        //체크카드 선택
        dialog_selected_checkcard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){

                }else{

                }
            }
        });

        //모두 선택
        dialog_selected_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){

                }else{

                }
            }
        });
    }

    @OnClick(R.id.dialog_ok_btn)
    public void okOnClick(){
        dialog.dismiss();
    }

    @OnClick(R.id.dialog_cancel_btn)
    public void cancelOnClick(){
        dialog.dismiss();
    }
}
