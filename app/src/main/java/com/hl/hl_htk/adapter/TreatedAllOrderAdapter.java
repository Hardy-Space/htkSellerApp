package com.hl.hl_htk.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hl.hl_htk.R;
import com.hl.hl_htk.entity.AllTreadOrder;
import com.hl.hl_htk.entity.ProductListBean;
import com.hl.hl_htk.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/14.
 */

public class TreatedAllOrderAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    List<AllTreadOrder.DataBean> list;

    private final static int TUANGOU = 1;
    private final static int WAIMAI = 0;
    private final static int DINGZUO = 2;


    public TreatedAllOrderAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.list = new ArrayList<>();
    }


    public void setData(List<AllTreadOrder.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addData(List<AllTreadOrder.DataBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        int mark = list.get(position).getMark();
        if (mark == 0) {
            return WAIMAI;
        } else if (mark == 1) {
            return TUANGOU;
        } else {
            return DINGZUO;
        }

    }


    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TgViewHolder tgViewHolder;
        WmViewHolder wmViewHolder;
        switch (getItemViewType(position)) {


            case TUANGOU:
                if (convertView == null) {
                    convertView = layoutInflater.inflate(R.layout.item_tuangou, null);
                    tgViewHolder = new TgViewHolder(convertView);
                    convertView.setTag(tgViewHolder);
                } else {
                    tgViewHolder = (TgViewHolder) convertView.getTag();
                }
                tgViewHolder.bindTgData(list, position);

                break;
            case WAIMAI:
                if (convertView == null) {
                    convertView = layoutInflater.inflate(R.layout.item_waimai, null);
                    wmViewHolder = new WmViewHolder(convertView);
                    convertView.setTag(wmViewHolder);
                } else {
                    wmViewHolder = (WmViewHolder) convertView.getTag();
                }
                wmViewHolder.bindWmData(list, position, context);

                break;
            case DINGZUO:
                if (convertView == null) {

                } else {

                }
                break;


        }


        return convertView;
    }


    static class TgViewHolder {
        @Bind(R.id.tv_orderNumber)
        TextView tvOrderNumber;
        @Bind(R.id.tv_data)
        TextView tvData;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.sdv_logo)
        SimpleDraweeView sdvLogo;
        @Bind(R.id.tv_taocan_name)
        TextView tvTaocanName;
        @Bind(R.id.tv_money)
        TextView tvMoney;
        @Bind(R.id.tv_useState)
        TextView tvUseState;


        private void bindTgData(List<AllTreadOrder.DataBean> list, int position) {
            AllTreadOrder.DataBean dataBean = list.get(position);
            tvOrderNumber.setText("团购订单" + dataBean.getOrderNumber());
            tvTime.setText("下单时间：" + dataBean.getOrderTime());
            sdvLogo.setImageURI(Uri.parse(dataBean.getLogoUrl()));
            tvTaocanName.setText(dataBean.getPackageName());
            tvMoney.setText("￥" + dataBean.getOrderAmount());
            String useState = "";

            String date = "";
            try {
                date = dataBean.getOrderTime().substring(8, 10);

            } catch (Exception e) {
                e.printStackTrace();
            }
            tvData.setText(date);

            if (dataBean.getOrderState() == 10) {
                useState = "未使用";
            } else if (dataBean.getOrderState() == 11) {
                useState = "使用";
            }
            tvUseState.setText(useState);


        }

        TgViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class WmViewHolder {
        @Bind(R.id.tv_orderNumber)
        TextView tvOrderNumber;
        @Bind(R.id.tv_data)
        TextView tvData;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.name_text)
        TextView nameText;
        @Bind(R.id.phoneNumber_text)
        TextView phoneNumberText;
        @Bind(R.id.address_text)
        TextView addressText;
        @Bind(R.id.distance_text)
        TextView distanceText;
        @Bind(R.id.tv_money)
        TextView tvMoney;
        @Bind(R.id.arrive_time_text)
        TextView arriveTimeText;
        @Bind(R.id.listView)
        MyListView listView;

        private void bindWmData(List<AllTreadOrder.DataBean> list, int position, Context context) {

            AllTreadOrder.DataBean dataBean = list.get(position);
            tvOrderNumber.setText("外卖单" + dataBean.getOrderNumber());
            tvTime.setText("下单时间：" + dataBean.getOrderTime());
            nameText.setText(dataBean.getReceiptName());
            phoneNumberText.setText(dataBean.getReceivingCall());
            addressText.setText(dataBean.getShippingAddress());
            tvMoney.setText("￥" + dataBean.getOrderAmount());
            arriveTimeText.setText(dataBean.getDeliveryTime() + dataBean.getShipperName() + "已送达" + "  " + dataBean.getDeliveryPhone());
            //distanceText.setText();
            String date = "";
            try {
                date = dataBean.getOrderTime().substring(8, 10);

            } catch (Exception e) {
                e.printStackTrace();
            }
            tvData.setText(date);
            WmListAdapter adapter = new WmListAdapter(context);
            listView.setAdapter(adapter);

            List<ProductListBean> productListBean = new ArrayList<>();
            for (int i = 0; i < dataBean.getProductList().size(); i++) {
                AllTreadOrder.DataBean.ProductListBean productListBean1 = dataBean.getProductList().get(i);
                productListBean.add(new ProductListBean(productListBean1.getProductName(), productListBean1.getQuantity(),
                        productListBean1.getProductId(), productListBean1.getOrderId(), productListBean1.getPrice()));
            }


               adapter.setData(productListBean);
        }


        WmViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
