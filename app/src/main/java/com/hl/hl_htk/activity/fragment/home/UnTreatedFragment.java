package com.hl.hl_htk.activity.fragment.home;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hl.hl_htk.R;
import com.hl.hl_htk.Utils.AsynClient;
import com.hl.hl_htk.Utils.GsonHttpResponseHandler;
import com.hl.hl_htk.Utils.MyHttpConfig;
import com.hl.hl_htk.Utils.UiFormat;
import com.hl.hl_htk.adapter.UnTreatedAdapter;
import com.hl.hl_htk.base.BaseFragment;
import com.hl.hl_htk.dialog.CallDialog;
import com.hl.hl_htk.entity.UnWaiMaiEntity;
import com.hl.hl_htk.model.CommonMsg;
import com.hl.hl_htk.receiver.NewOrderEvent;
import com.hl.hl_htk.receiver.OrderCancelEvent;
import com.loopj.android.http.RequestParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/13.
 */

public class UnTreatedFragment extends BaseFragment  {

    @Bind(R.id.rv_untreated)
    RecyclerView rvUntreated;
    @Bind(R.id.refresh_untreated)
    SmartRefreshLayout refreshUntreated;

    private View view = null;

    private int pageNumber = 1;
    private UnTreatedAdapter unTreatedAdapter;
    private View emptyView;
    private View errorView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_untreated, container , false);
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

        rvUntreated.setLayoutManager(new LinearLayoutManager(getContext()));
        unTreatedAdapter = new UnTreatedAdapter(R.layout.item_un_waimai , null);
        rvUntreated.setAdapter(unTreatedAdapter);

        unTreatedAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                switch (view.getId()){
                    case R.id.tv_jiedan:
                        receive(unTreatedAdapter.getData().get(position).getOrderNumber() , position , 2);
                        break;
                    case R.id.tv_refuse:
                        new AlertDialog.Builder(getContext())
                                .setMessage("确认拒绝订单")
                                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        receive(unTreatedAdapter.getData().get(position).getOrderNumber() , position , 6);
                                    }
                                })
                                .setNegativeButton("取消" , null)
                                .show();
                        break;
                    case R.id.tv_call:
                        CallDialog callDialog = new CallDialog(getActivity(),
                                (unTreatedAdapter.getData().get(position).getOrderState() == 1)?
                                        unTreatedAdapter.getData().get(position).getReceivingCall():
                                        unTreatedAdapter.getData().get(position).getOrderNumber(), "联系用户");
                        callDialog.show();
                        break;
                }
            }
        });

        refreshUntreated.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
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

        refreshUntreated.autoRefresh();
    }

    private void getData() {
        RequestParams params = new RequestParams();
        params.put("pageNumber" , pageNumber);
        AsynClient.post(MyHttpConfig.unWaiMai, getContext(), params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                Log.i(TAG, "onFailure: " + rawJsonData);
                refreshUntreated.finishLoadmore();
                refreshUntreated.finishRefresh();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG, "onSuccess: " + rawJsonResponse);
                refreshUntreated.finishLoadmore();
                refreshUntreated.finishRefresh();
                Gson gson = new Gson();
                UnWaiMaiEntity unWaiMaiEntity = gson.fromJson(rawJsonResponse, UnWaiMaiEntity.class);
                if (unWaiMaiEntity.getCode() == 100){
                    if (pageNumber == 1){
                        if (unWaiMaiEntity.getData() == null || unWaiMaiEntity.getData().size() == 0){
                            unTreatedAdapter.setNewData(null);
                            unTreatedAdapter.setEmptyView(emptyView);
                            refreshUntreated.setEnableLoadmore(false);
                        }else if (unWaiMaiEntity.getData().size() < 8){
                            unTreatedAdapter.setNewData(unWaiMaiEntity.getData());
                            refreshUntreated.setEnableLoadmore(false);
                        }else {
                            unTreatedAdapter.setNewData(unWaiMaiEntity.getData());
                            refreshUntreated.setEnableLoadmore(true);
                        }
                    }else {
                        if (unWaiMaiEntity.getData() == null || unWaiMaiEntity.getData().size() == 0){
                            refreshUntreated.setEnableLoadmore(false);
                        }else if (unWaiMaiEntity.getData().size() < 8){
                            unTreatedAdapter.addData(unWaiMaiEntity.getData());
                            refreshUntreated.setEnableLoadmore(false);
                        }else {
                            unTreatedAdapter.addData(unWaiMaiEntity.getData());
                            refreshUntreated.setEnableLoadmore(true);
                        }
                    }
                }else {
                    unTreatedAdapter.setEmptyView(errorView);
                    showMessage(unWaiMaiEntity.getMessage());
                }
            }
        });
    }

    /**
     * 接单、拒单接口
     * @param orderNumber 订单号
     * @param position 订单position
     * @param state 操作类型 2接单，6拒单
     */
    public void receive(String orderNumber , final int position , int state) {
        showChangeDialog("加载中");
        RequestParams params = AsynClient.getRequestParams();
        params.put("orderNumber", orderNumber);
        params.put("orderStateId", state);
        AsynClient.post(MyHttpConfig.jiedan, getActivity(), params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
                hideChangeDialog();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(TAG , rawJsonResponse);
                hideChangeDialog();
                Gson gson = new Gson();
                CommonMsg commonMsg = gson.fromJson(rawJsonResponse, CommonMsg.class);

                if (commonMsg.getCode() == 100) {
                    unTreatedAdapter.remove(position);
                    showMessage(commonMsg.getMessage());
                }else {
                    showMessage(commonMsg.getMessage());
                }
            }
        });

    }

    @Subscribe
    public void onEventMainThread(NewOrderEvent event){
        pageNumber = 1;
        getData();
    }

    @Subscribe
    public void onEventMainThread(OrderCancelEvent event){
        pageNumber = 1;
        getData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

}
