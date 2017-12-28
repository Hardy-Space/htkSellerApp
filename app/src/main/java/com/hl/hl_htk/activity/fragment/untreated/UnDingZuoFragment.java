package com.hl.hl_htk.activity.fragment.untreated;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hl.hl_htk.R;
import com.hl.hl_htk.base.BaseFragment;

/**
 * Created by Administrator on 2017/7/10.
 */

public class UnDingZuoFragment  extends BaseFragment {
    private boolean isPrepared = false;
    private boolean isFirst = true;
    private View view = null;


    private PullToRefreshListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_un_dingzuo, null);
            isPrepared = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    lazyInitData();
                }
            }, 200);
        }

        // ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void lazyInitData() {
        if (isPrepared  && isVisible) {
        //    isFirst = false;
            initWidget();
        }
    }


    private void initWidget() {
        listView = (PullToRefreshListView) view.findViewById(R.id.listView);
    }
}
