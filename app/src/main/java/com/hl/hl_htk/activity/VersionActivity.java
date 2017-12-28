package com.hl.hl_htk.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hl.hl_htk.R;
import com.hl.hl_htk.Utils.AsynClient;
import com.hl.hl_htk.Utils.GsonHttpResponseHandler;
import com.hl.hl_htk.Utils.MyHttpConfig;
import com.hl.hl_htk.Utils.MyUtils;
import com.hl.hl_htk.Utils.UiFormat;
import com.hl.hl_htk.Utils.UpData;
import com.hl.hl_htk.base.BaseActivity;
import com.hl.hl_htk.dialog.UpdateDialog;
import com.hl.hl_htk.entity.UpDataEntity;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/20.
 * 版本更新
 */

public class VersionActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.view1)
    View view1;

    @Bind(R.id.iv_logo03)
    ImageView ivLogo03;
    @Bind(R.id.tv_version)
    TextView tvVersion;
    @Bind(R.id.tv_upData)
    TextView tvUpData;
    @Bind(R.id.left_text)
    TextView leftText;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.right_text)
    ImageView rightText;

    private UpdateDialog mUpdateDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_version);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initWidget();
    }

    private void initWidget() {
        titleText.setText(getResources().getText(R.string.versoion));
        tvVersion.setText(MyUtils.getVersion(VersionActivity.this));
        leftText.setOnClickListener(this);
        tvUpData.setOnClickListener(this);
        mUpdateDialog = new UpdateDialog(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.left_text:
                finish();
                break;
            case R.id.tv_upData:
                //  showMessage("当前已是最新版本");
                upData();
                break;
            default:
                break;
        }
    }


    private void upData() {

        RequestParams params = AsynClient.getRequestParams();
        params.put("appId", "20170804");
        params.put("versionNumber", MyUtils.getVersion(this));
        AsynClient.post(MyHttpConfig.upDataUrl, this, params, new GsonHttpResponseHandler() {
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
                UpDataEntity upDataEntity = gson.fromJson(rawJsonResponse, UpDataEntity.class);

                if (upDataEntity.getCode() == 100) {

                    if (upDataEntity.getData() == null) {
                        showMessage(upDataEntity.getMessage());
                    } else {
                        showDialog(upDataEntity.getData().getUploadLog(), upDataEntity.getData().getDownloadUrl());
                    }

                }

            }
        });

    }


    private void showDialog(String content, final String url) {

        mUpdateDialog.show();
        mUpdateDialog.setCancelable(false);
        TextView tv_content = (TextView) mUpdateDialog.findViewById(R.id.tv_content);
        TextView tv_cancel = (TextView) mUpdateDialog.findViewById(R.id.tv_cancel);
        TextView tv_ok = (TextView) mUpdateDialog.findViewById(R.id.tv_ok);

        tv_content.setText(content);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUpdateDialog.dismiss();
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUpdateDialog.dismiss();
                UpData upData = new UpData(VersionActivity.this, url);
                upData.checkUpdate();
            }
        });

    }


}
