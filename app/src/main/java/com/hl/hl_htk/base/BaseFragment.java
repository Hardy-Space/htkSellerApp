package com.hl.hl_htk.base;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hl.hl_htk.R;
import com.hl.hl_htk.Utils.MyApplication;

/**
 * Created by Administrator on 2017/6/13.
 */

public  abstract class BaseFragment  extends android.support.v4.app.Fragment {

    public static final String TAG = "BaseFragment";

    protected boolean isVisible = false;
    protected MyApplication app;
    private AlertDialog mChangingDialog;
    private View view ;

    @Override
    public void onCreate( Bundle savedInstanceState) {

        app = MyApplication.get(BaseFragment.this.getActivity());
        super.onCreate(savedInstanceState);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            isVisible = true;
            lazyInitData();
        } else {
            isVisible = false;
        }
    }


    public abstract void lazyInitData();

    public void showMessage(String message) {

        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

    }

    /**
     * 更新数据的加载动画
     */
    public void showChangeDialog(final String msg){
        if (mChangingDialog == null)
            mChangingDialog = new AlertDialog.Builder(getContext()).create();

        if (view == null){
             view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_change_state, null);
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
