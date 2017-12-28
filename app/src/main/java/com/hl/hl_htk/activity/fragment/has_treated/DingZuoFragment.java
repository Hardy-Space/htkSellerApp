package com.hl.hl_htk.activity.fragment.has_treated;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hl.hl_htk.R;
import com.hl.hl_htk.base.BaseFragment;

/**
 * Created by Administrator on 2017/6/14.
 */

public class DingZuoFragment extends BaseFragment {

    private boolean isPrepared = false;
    private boolean isFirst = true;
    private View view = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {

            view = inflater.inflate(R.layout.fragment_today_order, null);

            isPrepared = true;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    lazyInitData();
                }
            }, 200);
        }

        return view;
    }

    @Override
    public void lazyInitData() {
        if (isPrepared&& isVisible) {



        }
    }


}
