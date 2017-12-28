package com.hl.hl_htk.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hl.hl_htk.R;

/**
 * Created by Administrator on 2017/6/13.
 */

public class ExitDialog extends AlertDialog {

    Context context;

    private TextView tvOk;
    private TextView tvCancel;

    public ExitDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_exit);
        tvOk = (TextView) findViewById(R.id.tv_ok);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);

     //   tvOk.setOnClickListener(new TvClickListener());
        tvCancel.setOnClickListener(new TvClickListener());
    }


    public class TvClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
              /*  case R.id.tv_ok:
                   // dismiss();
                    break;*/
                case R.id.tv_cancel:
                    dismiss();
                    break;
                default:
                    break;
            }
        }
    }


}
