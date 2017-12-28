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
import com.hl.hl_htk.adapter.un_treated.UnAllOrderAdapter;
import com.hl.hl_htk.base.BaseFragment;
import com.hl.hl_htk.dialog.CallDialog;
import com.hl.hl_htk.entity.UnAllEntity;
import com.hl.hl_htk.model.CommonMsg;
import com.loopj.android.http.RequestParams;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/10.
 */

public class UnAllOrderFragment extends BaseFragment {
    private boolean isPrepared = false;
    private boolean isFirst = true;
    private View view = null;


    private PullToRefreshListView listView;
    private int page = 1;
    private UnAllEntity unAllEntity;
    UnAllOrderAdapter unAllOrderAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_un_all, null);
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
       //     isFirst = false;
            initWidget();
        }
    }


    private void initWidget() {
        listView = (PullToRefreshListView) view.findViewById(R.id.listView);
        unAllOrderAdapter = new UnAllOrderAdapter(getActivity());
        listView.setAdapter(unAllOrderAdapter);
        getData(page);

        unAllOrderAdapter.setAllBtnClickListener(new UnAllOrderAdapter.AllBtnClickListener() {
            @Override
            public void click(int state, String orderNumber) {
                if (state == 1) {
                    //接单
                    jiedan(orderNumber);
                } else if (state == 2) {
                    //配送
                    peisong(orderNumber);
                }
            }

            @Override
            public void call(String phoneNumber) {
                CallDialog callDialog = new CallDialog(getActivity(), phoneNumber, "联系用户");
                callDialog.show();
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

                if (unAllEntity != null && unAllEntity.getData() != null && unAllEntity.getData().size() < 8) {
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
        AsynClient.post(MyHttpConfig.unAllOrder, getActivity(), params, new GsonHttpResponseHandler() {
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
                unAllEntity = gson.fromJson(rawJsonResponse, UnAllEntity.class);

                if (unAllEntity.getCode() == 100) {

                    if (unAllEntity.getData() == null) return;
                    if (page == 1) {
                        unAllOrderAdapter.setData(unAllEntity.getData());
                    } else {

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




    public void peisong(String orderNumber) {
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
