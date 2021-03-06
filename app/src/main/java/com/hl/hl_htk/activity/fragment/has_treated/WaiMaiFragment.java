package com.hl.hl_htk.activity.fragment.has_treated;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hl.hl_htk.R;
import com.hl.hl_htk.Utils.AsynClient;
import com.hl.hl_htk.Utils.GsonHttpResponseHandler;
import com.hl.hl_htk.Utils.MyHttpConfig;
import com.hl.hl_htk.Utils.UiFormat;
import com.hl.hl_htk.adapter.TreatedWmAdapter;
import com.hl.hl_htk.base.BaseFragment;
import com.hl.hl_htk.entity.TreatedWmEntity;
import com.loopj.android.http.RequestParams;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/6.
 */

public class WaiMaiFragment extends BaseFragment {


    private boolean isPrepared = false;
    private boolean isFirst = true;
    private View view = null;
    private PullToRefreshListView listView;
    private int page = 1;
    TreatedWmAdapter treatedWmAdapter;
    private TreatedWmEntity treatedWmEntity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_waimai, null);
            isPrepared = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    lazyInitData();
                }
            }, 200);
        }

        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void lazyInitData() {
        if (isPrepared && isVisible) {
            initWidget();
        }
    }


    private void initWidget() {
        listView = (PullToRefreshListView) view.findViewById(R.id.listView);
        treatedWmAdapter = new TreatedWmAdapter(getActivity());
        listView.setAdapter(treatedWmAdapter);
        getData(page);

        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                getData(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {


                if (treatedWmEntity != null && treatedWmEntity.getData() != null && treatedWmEntity.getData().size() < 8) {
                    Message message = new Message();
                    message.what = 1000;
                    handler.sendMessage(message);
                } else {
                    page++;
                    getData(page);
                }


            }
        });

    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1000) {
                complete();
            }

        }
    };


    private void getData(final int page) {

        RequestParams params = AsynClient.getRequestParams();
        params.put("pageNumber", page);
        params.put("mark", 0);
        AsynClient.post(MyHttpConfig.treatedWm, getActivity(), params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(MyHttpConfig.tag, rawJsonResponse);
                Gson gson = new Gson();
                treatedWmEntity = gson.fromJson(rawJsonResponse, TreatedWmEntity.class);

                if (treatedWmEntity.getCode() == 100) {

                    if (treatedWmEntity.getData() == null) return;

                    if (page == 1) {
                        treatedWmAdapter.setData(treatedWmEntity.getData());
                    } else {
                        treatedWmAdapter.addData(treatedWmEntity.getData());
                    }
                }

                complete();
            }
        });

    }


    private void complete() {
        if (listView != null) {
            listView.onRefreshComplete();
        }

    }


}
