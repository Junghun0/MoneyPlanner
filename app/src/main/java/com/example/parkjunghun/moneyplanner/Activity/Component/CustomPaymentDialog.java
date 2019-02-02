package com.example.parkjunghun.moneyplanner.Activity.Component;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.parkjunghun.moneyplanner.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomPaymentDialog {

    @BindView(R.id.payment_ok_btn)
    Button payment_ok_btn;
    @BindView(R.id.payment_cancel_btn)
    Button payment_cancel_btn;
    @BindView(R.id.dialog_selected_checkcard)
    CheckBox chkbox_checkcard;
    @BindView(R.id.dialog_selected_cash)
    CheckBox chkbox_cash;
    @BindView(R.id.dialog_selected_card)
    CheckBox chkbox_card;
    @BindView(R.id.dialog_selected_all)
    CheckBox chkbox_all;

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
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT ;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT ;
        Window window = dialog.getWindow();
        window.setAttributes(lp);

        dialog.show();

        //확인버튼
        Button ok_btn = (Button)dialog.findViewById(R.id.payment_ok_btn);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //취소버튼
        Button cancel_btn = dialog.findViewById(R.id.payment_cancel_btn);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        
        //dialog checkbox
        final CheckBox chkbox_cash = dialog.findViewById(R.id.dialog_selected_cash);
        final CheckBox chkbox_card = dialog.findViewById(R.id.dialog_selected_card);
        final CheckBox chkbox_checkcard = dialog.findViewById(R.id.dialog_selected_checkcard);
        final CheckBox chkbox_all = dialog.findViewById(R.id.dialog_selected_all);
        
        chkbox_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(chkbox_all.isChecked()) {
                    chkbox_card.setChecked(true);
                    chkbox_cash.setChecked(true);
                    chkbox_checkcard.setChecked(true);
                }else{
                    chkbox_card.setChecked(false);
                    chkbox_cash.setChecked(false);
                    chkbox_checkcard.setChecked(false);
                }
            }
        });

        
        

    }
}
