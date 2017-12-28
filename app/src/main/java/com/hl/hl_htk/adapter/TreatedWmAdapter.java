package com.hl.hl_htk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hl.hl_htk.R;
import com.hl.hl_htk.entity.AllTreadOrder;
import com.hl.hl_htk.entity.ProductListBean;
import com.hl.hl_htk.entity.TreatedWmEntity;
import com.hl.hl_htk.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/8.
 */

public class TreatedWmAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    List<TreatedWmEntity.DataBean> list;

    public TreatedWmAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
        this.layoutInflater = LayoutInflater.from(context);
    }


    public void setData(List<TreatedWmEntity.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addData(List<TreatedWmEntity.DataBean> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_waimai, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.bindData(list, position, context);
        return convertView;
    }

    static class ViewHolder {
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
        @Bind(R.id.listView)
        MyListView listView;
        @Bind(R.id.tv_money)
        TextView tvMoney;
        @Bind(R.id.arrive_time_text)
        TextView arriveTimeText;

        private void bindData(List<TreatedWmEntity.DataBean> list, int position, Context context) {
            TreatedWmEntity.DataBean dataBean = list.get(position);

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
                TreatedWmEntity.DataBean.ProductListBean productListBean1 = dataBean.getProductList().get(i);
                productListBean.add(new ProductListBean(productListBean1.getProductName(), productListBean1.getQuantity(),
                        productListBean1.getProductId(), productListBean1.getOrderId(), productListBean1.getPrice()));
            }

            adapter.setData(productListBean);

        }


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
