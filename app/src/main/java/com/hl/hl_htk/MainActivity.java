package com.hl.hl_htk;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hl.hl_htk.Utils.AsynClient;
import com.hl.hl_htk.Utils.GsonHttpResponseHandler;
import com.hl.hl_htk.Utils.MyApplication;
import com.hl.hl_htk.Utils.MyHttpConfig;
import com.hl.hl_htk.Utils.PermissionUtils;
import com.hl.hl_htk.Utils.UiFormat;
import com.hl.hl_htk.activity.ForgetPassActivity;
import com.hl.hl_htk.activity.HomeActivity;
import com.hl.hl_htk.base.BaseActivity;
import com.hl.hl_htk.model.CommonMsg;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.view1)
    View view1;
    @Bind(R.id.left_text)
    TextView leftText;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.right_text)
    ImageView rightText;
    @Bind(R.id.account_et)
    EditText accountEt;
    @Bind(R.id.passWord_et)
    EditText passWordEt;
    @Bind(R.id.login_text)
    TextView loginText;
    @Bind(R.id.forgetpass_text)
    TextView forgetPassText;

    private static final int JPUSH_ONE = 10001;

    private String userName = "";
    private String passWord = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initData();
    }


    private void initData() {

        titleText.setText("登录");
        loginText.setOnClickListener(this);
        forgetPassText.setOnClickListener(this);


        if (Build.VERSION.SDK_INT >= 23) {
            PermissionUtils.requestMultiPermissions(this, mPermissionGrant);
        }


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.login_text:

                userName = accountEt.getText().toString().trim();
                passWord = passWordEt.getText().toString().trim();

                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(passWord)) {
                    showMessage("用户名或密码为空");
                    return;
                }

                loginText.setEnabled(false);
                login(userName, passWord);

                break;
            case R.id.forgetpass_text:
                startActivity(new Intent(MainActivity.this, ForgetPassActivity.class));
                break;
            default:
                break;
        }

    }


    private void login(String userName, String passWord) {
        showChangeDialog("登录");
        RequestParams params = AsynClient.getRequestParams();
        params.put("userName", userName);
        params.put("password", passWord);
        AsynClient.post(MyHttpConfig.login, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
                hideChangeDialog();
                loginText.setEnabled(true);
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i("TAG-->", rawJsonResponse);
                hideChangeDialog();
                Gson gson = new Gson();
                CommonMsg commonMsg = gson.fromJson(rawJsonResponse, CommonMsg.class);

                if (commonMsg.getCode() == 100) {

                    String alias = commonMsg.getData().toString().replaceAll("-", "");
                    alias = alias.trim();
                    JPushInterface.setAlias(MyApplication.getContext() , JPUSH_ONE  , alias);

                    app.getLoginState().setToken(commonMsg.getData().toString());
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    finish();
                } else {
                    loginText.setEnabled(true);
                    showMessage(commonMsg.getMessage());
                }

            }
        });


    }



    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_CALL_PHONE:
                    //  Toast.makeText(LoginActivity.this, "Result Permission Grant CODE_CALL_PHONE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_CAMERA:
                    //   Toast.makeText(LoginActivity.this, "Result Permission Grant CODE_CAMERA", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_ACCESS_FINE_LOCATION:
                    //  Toast.makeText(LoginActivity.this, "Result Permission Grant CODE_ACCESS_FINE_LOCATION", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_READ_EXTERNAL_STORAGE:
                    //  Toast.makeText(LoginActivity.this, "Result Permission Grant CODE_READ_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
                    //  Toast.makeText(LoginActivity.this, "Result Permission Grant CODE_WRITE_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
        }
    };



}
