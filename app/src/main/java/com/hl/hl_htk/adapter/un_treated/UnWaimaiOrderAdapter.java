package com.hl.hl_htk.adapter.un_treated;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hl.hl_htk.R;
import com.hl.hl_htk.adapter.WmListAdapter;
import com.hl.hl_htk.entity.ProductListBean;
import com.hl.hl_htk.entity.TreatedWmEntity;
import com.hl.hl_htk.entity.UnWaiMaiEntity;
import com.hl.hl_htk.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/10.
 */

public class UnWaimaiOrderAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    List<UnWaiMaiEntity.DataBean> list;

    public UnWaimaiOrderAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.list = new ArrayList<>();
    }

    public void setData(List<UnWaiMaiEntity.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addData(List<UnWaiMaiEntity.DataBean> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {

            convertView = layoutInflater.inflate(R.layout.item_un_waimai, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.bindData(list, position, context);
        viewHolder.tvJiedan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnWaiMaiEntity.DataBean dataBean = list.get(position);
                btnClickListener.click(position, dataBean.getOrderState() , dataBean.getOrderNumber());
            }
        });

        return convertView;
    }


    public BtnClickListener btnClickListener;

    public void setBtnClickListener(BtnClickListener btnClickListener) {
        this.btnClickListener = btnClickListener;
    }

    public interface BtnClickListener {
        public void click(int position, int state , String orderNumber);
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
        @Bind(R.id.tv_jiedan)
        TextView tvJiedan;
        @Bind(R.id.tv_call)
        TextView tvCall;


        private void bindData(List<UnWaiMaiEntity.DataBean> list, int position, Context context) {

            UnWaiMaiEntity.DataBean dataBean = list.get(position);

            tvOrderNumber.setText("外卖单" + dataBean.getOrderNumber());
            tvTime.setText("下单时间：" + dataBean.getOrderTime());
            nameText.setText(dataBean.getReceiptName());
            phoneNumberText.setText(dataBean.getReceivingCall());
            addressText.setText(dataBean.getShippingAddress());
            tvMoney.setText("￥" + dataBean.getOrderAmount());
            //      arriveTimeText.setText(dataBean.getDeliveryTime() + dataBean.getShipperName() + "已送达" + "  " + dataBean.getDeliveryPhone());
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
                UnWaiMaiEntity.DataBean.ProductListBean productListBean1 = dataBean.getProductList().get(i);
                productListBean.add(new ProductListBean(productListBean1.getProductName(), productListBean1.getQuantity(),
                        productListBean1.getProductId(), productListBean1.getOrderId(), productListBean1.getPrice()));
            }

            adapter.setData(productListBean);


            switch (dataBean.getOrderState()) {
                case 1:
                    tvJiedan.setText("接单");
                    break;
                case 2:
                    tvJiedan.setText("开始配送");
                    break;
                case 3:
                    tvJiedan.setText("配送中");
                    break;
            }

        }

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
