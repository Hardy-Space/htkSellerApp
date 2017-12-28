package com.hl.hl_htk.Utils;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2017/6/19.
 */

public class MyUtils {


    //获取当前版本
    public static String getVersion(Context context) {
        String version = "";
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            version = info.versionName;

            //  return version;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return version;
    }



}