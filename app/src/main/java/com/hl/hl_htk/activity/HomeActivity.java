package com.hl.hl_htk.activity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.hl.hl_htk.R;
import com.hl.hl_htk.Utils.AsynClient;
import com.hl.hl_htk.Utils.GsonHttpResponseHandler;
import com.hl.hl_htk.Utils.MyHttpConfig;
import com.hl.hl_htk.Utils.UiFormat;
import com.hl.hl_htk.activity.fragment.home.HasTreatedFragment;
import com.hl.hl_htk.activity.fragment.home.SettingFragment;
import com.hl.hl_htk.activity.fragment.home.UnTreatedFragment;
import com.hl.hl_htk.adapter.MyPageAdapter;
import com.hl.hl_htk.base.BaseActivity;
import com.hl.hl_htk.base.BaseFragment;
import com.hl.hl_htk.model.CommonMsg;
import com.hl.hl_htk.widget.MyViewPager;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/13.
 */

public class HomeActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.view1)
    View view1;
    @Bind(R.id.left_text)
    TextView leftText;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.right_text)
    ImageView rightText;
    @Bind(R.id.myViewPager)
    MyViewPager myViewPager;
    @Bind(R.id.rb1_main)
    RadioButton rb1Main;
    @Bind(R.id.rb2_main)
    RadioButton rb2Main;
    @Bind(R.id.rb3_main)
    RadioButton rb3Main;
    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;

    private long exitTime = 0; //返回键触发时间

    UnTreatedFragment unTreatedFragment;
    HasTreatedFragment hasTreatedFragment;
    SettingFragment settingFragment;
    List<BaseFragment> fragmentList;
    MyPageAdapter myPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_home);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initWidget();
    }


    private void initWidget() {

        titleText.setText(getResources().getText(R.string.setting));

        unTreatedFragment = new UnTreatedFragment();
        hasTreatedFragment = new HasTreatedFragment();
        settingFragment = new SettingFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(unTreatedFragment);
        fragmentList.add(hasTreatedFragment);
        fragmentList.add(settingFragment);
        myPageAdapter = new MyPageAdapter(fragmentList, getSupportFragmentManager());

        myViewPager.setAdapter(myPageAdapter);
        myViewPager.setOffscreenPageLimit(2);
        myViewPager.setCurrentItem(2);


        rb1Main.setOnClickListener(this);
        rb2Main.setOnClickListener(this);
        rb3Main.setOnClickListener(this);
        rightText.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rb1_main:
                titleText.setText(getResources().getText(R.string.untreated));
                myViewPager.setCurrentItem(0);
                rightText.setVisibility(View.VISIBLE);
                break;
            case R.id.rb2_main:
                titleText.setText(getResources().getText(R.string.hastreated));
                myViewPager.setCurrentItem(1);
                rightText.setVisibility(View.GONE);
                break;
            case R.id.rb3_main:
                titleText.setText(getResources().getText(R.string.setting));
                myViewPager.setCurrentItem(2);
                rightText.setVisibility(View.GONE);
                break;
            case R.id.right_text:
                customScan();
                break;

        }

    }

    public void customScan() {
        new IntentIntegrator(this)
                .setOrientationLocked(false)
                .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                .initiateScan(); // 初始化扫描
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void exit() {

        if ((System.currentTimeMillis() - exitTime) > 2000) {
            showMessage("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                showMessage("内容为空");
            } else {
                String ScanResult = intentResult.getContents();
                //  showMessage("扫描成功:" + ScanResult);
                useTg(ScanResult);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void useTg(String orderNumber) {
        RequestParams params = AsynClient.getRequestParams();
        params.put("orderNumber", orderNumber);
        AsynClient.post(MyHttpConfig.useTg, this, params, new GsonHttpResponseHandler() {
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
                showMessage(commonMsg.getMessage());
                /*if(commonMsg.getCode() == 100){

                }*/

            }
        });

    }


}
