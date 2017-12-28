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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/8.
 */

public class WmListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    List<ProductListBean> list;


    public WmListAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.list = new ArrayList<>();
    }

    public void setData(List<ProductListBean> list) {
        this.list = list;
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
            convertView = layoutInflater.inflate(R.layout.item_order_detail, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.bindData(list, position);
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_itemName)
        TextView tvItemName;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.tv_num)
        TextView tvNum;


        private void bindData(List<ProductListBean> list, int position) {

            ProductListBean productListBean = list.get(position);

            tvItemName.setText(productListBean.getProductName());
            tvNum.setText("x" + productListBean.getQuantity());
            tvPrice.setText("￥" + productListBean.getPrice());

        }


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
