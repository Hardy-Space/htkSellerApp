package com.hl.hl_htk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.hl.hl_htk.MainActivity;
import com.hl.hl_htk.R;
import com.hl.hl_htk.base.BaseActivity;

/**
 * Created by Administrator on 2017/7/6.
 */

public class StartActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_splash);
        super.onCreate(savedInstanceState);
        go();
    }


    private void go() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                if (TextUtils.isEmpty(app.getLoginState().getToken())) {
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(StartActivity.this, HomeActivity.class));
                }


                finish();

            }
        }).start();

    }

}
