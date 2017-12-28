package com.hl.hl_htk.adapter.un_treated;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hl.hl_htk.R;
import com.hl.hl_htk.entity.TreatedTgEntity;
import com.hl.hl_htk.entity.UnTuanGouEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/10.
 */

public class UnTuanGouAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    List<UnTuanGouEntity.DataBean> list;


    public UnTuanGouAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.list = new ArrayList<>();
    }

    public void setData(List<UnTuanGouEntity.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addData(List<UnTuanGouEntity.DataBean> list) {
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
            convertView = layoutInflater.inflate(R.layout.item_un_tuangou, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.bindData(list, position);
        return convertView;
    }




    static class ViewHolder {
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
        @Bind(R.id.tv_jiedan)
        TextView tvJiedan;
        @Bind(R.id.tv_call)
        TextView tvCall;

        private void bindData(List<UnTuanGouEntity.DataBean> list, int position) {

            UnTuanGouEntity.DataBean dataBean = list.get(position);
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

          /*  if (dataBean.getOrderState() == 10) {
                useState = "未使用";
            } else if (dataBean.getOrderState() == 11) {
                useState = "使用";
            }
            tvUseState.setText(useState);*/
        }


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
