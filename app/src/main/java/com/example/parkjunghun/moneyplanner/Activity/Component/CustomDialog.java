package com.example.parkjunghun.moneyplanner.Activity.Component;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.parkjunghun.moneyplanner.R;

public class CustomDialog {

    private Context context;

    public CustomDialog(Context context){
        this.context = context;
    }

    public void callMethod(){
        final Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        Window window = dialog.getWindow();
        window.setAttributes(lp);

        dialog.show();

        Button ok_btn = (Button)dialog.findViewById(R.id.dialog_ok_btn);
        Button cancel_btn = (Button)dialog.findViewById(R.id.dialog_cancel_btn);


        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }

}
