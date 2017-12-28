package com.hl.hl_htk.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hl.hl_htk.R;
import com.hl.hl_htk.entity.ProductListBean;
import com.hl.hl_htk.entity.TreatedWmEntity;
import com.hl.hl_htk.entity.UnWaiMaiEntity;
import com.hl.hl_htk.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/9.
 */

public class TreatedAdapter extends BaseQuickAdapter<TreatedWmEntity.DataBean , BaseViewHolder> {

    public TreatedAdapter(@LayoutRes int layoutResId, @Nullable List<TreatedWmEntity.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TreatedWmEntity.DataBean item) {
        String date = "";
        try {
            date = item.getOrderTime().substring(8, 10);

        } catch (Exception e) {
            e.printStackTrace();
        }

        helper.setText(R.id.tv_orderNumber , "外卖单" + item.getOrderNumber())
                .setText(R.id.tv_data , date)
                .setText(R.id.tv_time , "下单时间：" + item.getOrderTime())
                .setText(R.id.name_text , item.getReceiptName())
                .setText(R.id.phoneNumber_text , item.getReceivingCall())
                .setText(R.id.address_text , item.getShippingAddress())
                .setText(R.id.tv_money , "￥" + item.getOrderAmount())
                .addOnClickListener(R.id.tv_jiedan)
                .addOnClickListener(R.id.tv_refuse)
                .addOnClickListener(R.id.tv_call);

        MyListView listView = helper.getView(R.id.listView);
        WmListAdapter adapter = new WmListAdapter(mContext);
        listView.setAdapter(adapter);
        List<ProductListBean> productListBean = new ArrayList<>();
        for (int i = 0; i < item.getProductList().size(); i++) {
            TreatedWmEntity.DataBean.ProductListBean productListBean1 = item.getProductList().get(i);
            productListBean.add(new ProductListBean(productListBean1.getProductName(), productListBean1.getQuantity(),
                    productListBean1.getProductId(), productListBean1.getOrderId(), productListBean1.getPrice()));
        }

        adapter.setData(productListBean);

        switch (item.getOrderState()){
            case 1:
                helper.setText(R.id.tv_jiedan , "配送");
                helper.setText(R.id.tv_refuse , "拒单");
                break;
            case 2:
                helper.setText(R.id.tv_jiedan , "配送中");
                helper.setText(R.id.tv_refuse , "拒单");
                break;
            case 3:
                helper.setText(R.id.tv_jiedan , "订单完成");
                helper.setText(R.id.tv_refuse , "取消订单");
                break;
        }
    }
}
