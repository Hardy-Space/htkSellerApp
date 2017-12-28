package com.hl.hl_htk.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.hl.hl_htk.R;
import com.hl.hl_htk.Utils.MyApplication;

/**
 * Created by Administrator on 2017/6/13.
 */

public class BaseActivity extends FragmentActivity {

    public static final String TAG = "BaseActivity";

    public View view1;

    protected MyApplication app;

    private AlertDialog mChangingDialog;
    private View view ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        app = MyApplication.get(this);
        super.onCreate(savedInstanceState);
        view1 = (View) findViewById(R.id.view1);
        settopbar(this, view1);
    }

    //将 上面的状态栏隐掉
    public static void settopbar(Activity activity, View view) {
        int sysVersion = Integer.parseInt(Build.VERSION.SDK);
        if (sysVersion >= 19) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else {
            view.setVisibility(View.GONE);
        }
    }


    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    /**
     * 更新数据的加载动画
     */
    public void showChangeDialog(final String msg){
        if (mChangingDialog == null)
            mChangingDialog = new AlertDialog.Builder(this).create();

        if (view == null){
            view = LayoutInflater.from(this).inflate(R.layout.dialog_change_state, null);
        }
        mChangingDialog.show();
        TextView tv = view.findViewById(R.id.dialog_change_tv);
        tv.setText(msg);
        mChangingDialog.setContentView(view);
    }

    public void hideChangeDialog(){
        if (mChangingDialog != null)
            mChangingDialog.dismiss();
    }


}
