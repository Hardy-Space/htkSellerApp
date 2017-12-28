package com.hl.hl_htk.activity.fragment.untreated;

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
import com.hl.hl_htk.adapter.un_treated.UnWaimaiOrderAdapter;
import com.hl.hl_htk.base.BaseFragment;
import com.hl.hl_htk.entity.UnWaiMaiEntity;
import com.hl.hl_htk.model.CommonMsg;
import com.loopj.android.http.RequestParams;

/**
 * Created by Administrator on 2017/7/10.
 */

public class UnWaiMaiFragment extends BaseFragment {
    private boolean isPrepared = false;
    private boolean isFirst = true;
    private View view = null;


    private PullToRefreshListView listView;
    private int page = 1;
    private UnWaiMaiEntity unWaiMaiEntity;
    UnWaimaiOrderAdapter unWaimaiOrderAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_un_waimai, null);
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
        if (isPrepared && isVisible) {

            initWidget();
        }
    }


    private void initWidget() {
        listView = (PullToRefreshListView) view.findViewById(R.id.listView);
        unWaimaiOrderAdapter = new UnWaimaiOrderAdapter(getActivity());
        listView.setAdapter(unWaimaiOrderAdapter);
        getData(page);
        unWaimaiOrderAdapter.setBtnClickListener(new UnWaimaiOrderAdapter.BtnClickListener() {
            @Override
            public void click(int position, int state, String orderNumber) {
                if (state == 1) {
                    //接单
                    jiedan(orderNumber);
                } else if (state == 2) {
                    //配送
                    peisong(orderNumber);
                }
            }
        });

        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                getData(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (unWaiMaiEntity != null && unWaiMaiEntity.getData() != null && unWaiMaiEntity.getData().size() < 8) {
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


    private void getData(final int page) {
        RequestParams params = AsynClient.getRequestParams();
        params.put("pageNumber", page);
        AsynClient.post(MyHttpConfig.unWaiMai, getActivity(), params, new GsonHttpResponseHandler() {
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
                unWaiMaiEntity = gson.fromJson(rawJsonResponse, UnWaiMaiEntity.class);

                if (unWaiMaiEntity.getCode() == 100) {
                    if (unWaiMaiEntity.getData() == null) return;
                    if (page == 1) {
                        unWaimaiOrderAdapter.setData(unWaiMaiEntity.getData());
                    } else {
                        unWaimaiOrderAdapter.addData(unWaiMaiEntity.getData());
                    }

                }
                complete();
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


    private void complete() {
        if (listView != null) {
            listView.onRefreshComplete();
        }

    }


    public void jiedan(String orderNumber) {
        RequestParams params = AsynClient.getRequestParams();
        params.put("orderNumber", orderNumber);
        AsynClient.post(MyHttpConfig.jiedan, getActivity(), params, new GsonHttpResponseHandler() {
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
                CommonMsg commonMsg = gson.fromJson(rawJsonResponse, CommonMsg.class);

                if (commonMsg.getCode() == 100) {
                    getData(1);
                }

            }
        });

    }


    private void peisong(String orderNumber) {
        RequestParams params = AsynClient.getRequestParams();
        params.put("orderNumber", orderNumber);
        AsynClient.post(MyHttpConfig.peisong, getActivity(), params, new GsonHttpResponseHandler() {
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
                CommonMsg commonMsg = gson.fromJson(rawJsonResponse, CommonMsg.class);

                if (commonMsg.getCode() == 100) {
                    getData(1);
                }

            }
        });
    }

}
