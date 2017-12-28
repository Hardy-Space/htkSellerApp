package com.hl.hl_htk.entity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/6/13.
 */

public class LoginStateEntity {


    private int voice  = 0; // 0 女声   1 男声
    private String token;


    Context mContext;
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;

    public LoginStateEntity(Context mContext) {
        this.mContext = mContext;
        mSharedPreferences = mContext.getSharedPreferences("saveinfo", Activity.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }


    public int getVoice() {
        voice = mSharedPreferences.getInt("voice", 0);
        return voice;
    }

    public void setVoice(int voice) {
        //    this.voice = voice;
        mEditor.putInt("voice", voice);
        mEditor.commit();
    }


    public String getToken() {
        token = mSharedPreferences.getString("token", "");
        return token;
    }

    public void setToken(String token) {
        //     this.token = token;
        mEditor.putString("token", token);
        mEditor.commit();
    }
}
