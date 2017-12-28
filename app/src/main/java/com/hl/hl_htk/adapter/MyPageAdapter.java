package com.hl.hl_htk.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hl.hl_htk.base.BaseFragment;

import java.util.List;


/**
 * 作者 龙威陶
 * 说明
 * 创建时间 2016/2/23.
 * 版本  1
 * 公司名称 百迅科技  www.bxv8.com baixukeji@163.com
 */
public class MyPageAdapter extends FragmentPagerAdapter {
      List<BaseFragment> list;


      public MyPageAdapter(List<BaseFragment> list, FragmentManager fragmentManager) {

            super(fragmentManager);
            this.list=list;
      }


    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }


}
