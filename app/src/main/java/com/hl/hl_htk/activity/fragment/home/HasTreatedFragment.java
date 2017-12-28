package com.hl.hl_htk.activity.fragment.home;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hl.hl_htk.R;
import com.hl.hl_htk.Utils.AsynClient;
import com.hl.hl_htk.Utils.GsonHttpResponseHandler;
import com.hl.hl_htk.Utils.MyHttpConfig;
import com.hl.hl_htk.Utils.UiFormat;
import com.hl.hl_htk.adapter.TreatedAdapter;
import com.hl.hl_htk.adapter.UnTreatedAdapter;
import com.hl.hl_htk.base.BaseFragment;
import com.hl.hl_htk.dialog.CallDialog;
import com.hl.hl_htk.entity.CommonEntity;
import com.hl.hl_htk.entity.TreatedWmEntity;
import com.hl.hl_htk.receiver.OrderCancelEvent;
import com.hl.hl_htk.receiver.OrderCompletedEvent;
import com.loopj.android.http.RequestParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/13.
 */

public class HasTreatedFragment extends BaseFragment {

    @Bind(R.id.rv_has_treated)
    RecyclerView rvHasTreated;
    @Bind(R.id.refresh_has_treated)
    SmartRefreshLayout refreshHasTreated;

    private View view = null;
    private View emptyView;
    private View errorView;
    private int pageNumber = 1;
    private TreatedAdapter treatedAdapter;

    private Gson gson = new Gson();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_hastreated, container, false);
        }
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initWidget();
        return view;
    }

    @Override
    public void lazyInitData() {
    }

    private void initWidget() {
        emptyView = LayoutInflater.from(getContext()).inflate(R.layout.empty_view , null);
        errorView = LayoutInflater.from(getContext()).inflate(R.layout.error_view , null);

        rvHasTreated.setLayoutManager(new LinearLayoutManager(getContext()));
        treatedAdapter = new TreatedAdapter(R.layout.item_un_waimai , null);
        rvHasTreated.setAdapter(treatedAdapter);

        treatedAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                switch (view.getId()){
                    case R.id.tv_jiedan:
                        new AlertDialog.Builder(getContext())
                                .setMessage("确认配送？")
                                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        received(treatedAdapter.getData().get(position).getOrderNumber() , position);
                                    }
                                })
                                .setNegativeButton("取消" , null)
                                .show();
                        break;
                    case R.id.tv_refuse:
                        new AlertDialog.Builder(getContext())
                                .setMessage("确认取消订单？")
                                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        cancelOrder(treatedAdapter.getData().get(position).getOrderNumber() , position);
                                    }
                                })
                                .setNegativeButton("取消" , null)
                                .show();
                        break;
                    case R.id.tv_call:
                        CallDialog callDialog = new CallDialog(getActivity(), treatedAdapter.getData().get(position).getReceivingCall(), "联系用户");
                        callDialog.show();
                        break;
                }
            }
        });

        refreshHasTreated.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageNumber++;
                getData();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNumber = 1;
                getData();
            }
        });

        refreshHasTreated.autoRefresh();
    }

    private void getData() {
        RequestParams params = AsynClient.getRequestParams();
        params.put("pageNumber", pageNumber);
        params.put("mark", 0);
        AsynClient.post(MyHttpConfig.treatedWm, getActivity(), params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
                refreshHasTreated.finishRefresh();
                refreshHasTreated.finishLoadmore();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG, rawJsonResponse);
                refreshHasTreated.finishRefresh();
                refreshHasTreated.finishLoadmore();
                Gson gson = new Gson();
                TreatedWmEntity treatedWmEntity = gson.fromJson(rawJsonResponse, TreatedWmEntity.class);
                if (treatedWmEntity.getCode() == 100){
                    List<TreatedWmEntity.DataBean> data = treatedWmEntity.getData();
                    if (pageNumber == 1){
                        if (data == null || data.size() == 0){
                            treatedAdapter.setNewData(null);
                            treatedAdapter.setEmptyView(emptyView);
                            refreshHasTreated.setEnableLoadmore(false);
                        }else if (data.size() < 8){
                            treatedAdapter.setNewData(data);
                            refreshHasTreated.setEnableLoadmore(false);
                        }else {
                            treatedAdapter.setNewData(data);
                            refreshHasTreated.setEnableLoadmore(true);
                        }
                    }else {
                        if (data == null || data.size() == 0){
                            refreshHasTreated.setEnableLoadmore(false);
                        }else if (data.size() < 8){
                            treatedAdapter.addData(data);
                            refreshHasTreated.setEnableLoadmore(false);
                        }else {
                            treatedAdapter.addData(data);
                            refreshHasTreated.setEnableLoadmore(true);
                        }
                    }
                }else {
                    showMessage(treatedWmEntity.getMessage());
                    treatedAdapter.setEmptyView(errorView);
                }
            }
        });

    }

    private void received(String orderNumber , final int position){
        showChangeDialog("确认收货");
        RequestParams params = new RequestParams();
        params.put("orderNumber" , orderNumber);
        AsynClient.post(MyHttpConfig.confirmReceipt, getContext(), params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                Log.i(TAG, "onFailure: " + rawJsonData);
                hideChangeDialog();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG, "onSuccess: " + rawJsonResponse);
                hideChangeDialog();
                CommonEntity commonEntity = gson.fromJson(rawJsonResponse, CommonEntity.class);
                if (commonEntity.getCode() == 100){
////                    treatedAdapter.remove(position);
//                    rvHasTreated.getChildAt(position).g
//                    getData()；
                    ((TextView)treatedAdapter.getViewByPosition(rvHasTreated ,position, R.id.tv_jiedan)).setText("配送中");
//                    ((TextView)treatedAdapter.getViewByPosition(position,R.id.tv_jiedan)).setText("配送中");
                    showMessage(commonEntity.getMessage());
                }else {
                    showMessage(commonEntity.getMessage());
                }
            }
        });
    }

    private void cancelOrder(String orderNumber , final int position){
        showChangeDialog("取消订单");
        RequestParams params = new RequestParams();
        params.put("orderNumber" , orderNumber);
        AsynClient.post(MyHttpConfig.cancelOrder, getContext(), params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                Log.i(TAG, "onFailure: " + rawJsonData);
                hideChangeDialog();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG, "onSuccess: " + rawJsonResponse);
                hideChangeDialog();
                CommonEntity commonEntity = gson.fromJson(rawJsonResponse, CommonEntity.class);
                if (commonEntity.getCode() == 100){
                    treatedAdapter.remove(position);
                    showMessage(commonEntity.getMessage());
                }else {
                    showMessage(commonEntity.getMessage());
                }
            }
        });
    }

    @Subscribe
    public void onEventMainThread(OrderCompletedEvent event){
        pageNumber = 1;
        getData();
    }

    @Subscribe
    public void onEventMainThread(OrderCancelEvent event){
        pageNumber = 1;
        getData();
    }

    // TODO: 2017/11/13 增加配送中刷新

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

}
