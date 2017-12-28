package com.hl.hl_htk.activity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hl.hl_htk.R;
import com.hl.hl_htk.Utils.AsynClient;
import com.hl.hl_htk.Utils.GsonHttpResponseHandler;
import com.hl.hl_htk.Utils.MyHttpConfig;
import com.hl.hl_htk.Utils.UiFormat;
import com.hl.hl_htk.base.BaseActivity;
import com.hl.hl_htk.model.CommonMsg;
import com.hl.hl_htk.receiver.SMSReceiver;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/13.
 */

public class ForgetPassActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.left_text)
    TextView leftText;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.right_text)
    ImageView rightText;
    @Bind(R.id.account_et)
    EditText accountEt;
    @Bind(R.id.authCode_et)
    EditText authCodeEt;
    @Bind(R.id.getAuthCode_text)
    TextView getAuthCodeText;
    @Bind(R.id.newPass_text)
    EditText newPassText;
    @Bind(R.id.passWord2_et)
    EditText passWord2Et;
    @Bind(R.id.over_text)
    TextView overText;

    private String phoneNumber = ""; //手机号
    private String authCode = ""; //验证码
    private String passWord1 = ""; //密码
    private String passWord2 = ""; //密码

    private TimeCount time;
    private SMSReceiver smeReceiver;
    private IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_forget_pass);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        initData();
    }


    public void initData() {

        smeReceiver = new SMSReceiver(authCodeEt);
        filter = new IntentFilter(SMSReceiver.SMS_RECEIVED_ACTION);
        this.registerReceiver(smeReceiver, filter);

        titleText.setText("忘记密码");
        //  leftText.setBackground(getResources().getDrawable(R.drawable.ic_keyboard_arrow_left_white_24dp));
        getAuthCodeText.setOnClickListener(this);
        overText.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.getAuthCode_text:
                //获取验证码
                phoneNumber = accountEt.getText().toString().trim();
                if (phoneNumber.length() < 6 || phoneNumber.length() > 12) {
                    showMessage("请输入正确格式的手机号");
                    return;
                }
                //    getAuthCode(phoneNumber);

                authCodeEt.requestFocus(); //获取焦点
                authCodeEt.setText("");
                authCodeEt.setHint("等待自动填充");
                getAuthCode(phoneNumber);
                startTimeCount();

                break;
            case R.id.over_text:
                //完成
                phoneNumber = accountEt.getText().toString().trim();
                authCode = authCodeEt.getText().toString().trim();
                passWord1 = newPassText.getText().toString().trim();
                passWord2 = passWord2Et.getText().toString().trim();
                if (phoneNumber.length() != 11) {
                    showMessage("请输入正确格式的手机号");
                    return;
                } else if (TextUtils.isEmpty(authCode)) {
                    showMessage("请输入验证码");
                    return;
                } else if (passWord1.length() < 6 || passWord2.length() > 12) {
                    showMessage("请输入6-12位密码");
                    return;
                } else if (!passWord1.equals(passWord2)) {
                    showMessage("两次密码不一致");
                    return;
                }
                findPass(phoneNumber, authCode, passWord1);


                break;
            default:
                break;
        }

    }

    private void startTimeCount() {
        //获取验证码
        getAuthCodeText.setEnabled(false);
        long endTime = 60 * 1000;
        time = new TimeCount(endTime, 1000);
        time.start();
    }

    private void getAuthCode(String phoneNumber) {
        RequestParams params = AsynClient.getRequestParams();
        params.put("phone", Long.parseLong(phoneNumber));
        AsynClient.post(MyHttpConfig.getAuth, this, params, new GsonHttpResponseHandler() {
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
                CommonMsg cErrorMsg = UiFormat.getCommonMsg(rawJsonResponse);
                showMessage(cErrorMsg.getMessage());
            }
        });


    }


    private void findPass(String phoneNumber, String authCode, String passWord) {
        RequestParams params = AsynClient.getRequestParams();
        params.put("phone", phoneNumber);
        params.put("code", authCode);
        params.put("password", passWord);
        AsynClient.post(MyHttpConfig.forgetPass, this, params, new GsonHttpResponseHandler() {
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

                if (commonMsg.getCode() == 100) {
                    finish();
                }

            }
        });

    }


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            getAuthCodeText.setEnabled(true);
            getAuthCodeText.setText("重新验证");
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            getAuthCodeText.setEnabled(false);
            getAuthCodeText.setText(millisUntilFinished / 1000 + "s");
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (smeReceiver != null) {
            this.unregisterReceiver(smeReceiver);
        }
    }


}
