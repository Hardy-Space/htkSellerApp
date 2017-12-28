package com.hl.hl_htk.Utils;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by w.chen on 2014/10/11.
 */
public class AsynClient {

    private static final String TAG = "AsynClient";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        addHeader();
        client.get(url, params, responseHandler);
    }

    public static void post(String url, Context context, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        params.put("role", "S"); // 服务器区分是否手机端
        params.put("token", MyApplication.get(context).getLoginState().getToken());
        addHeader();
        client.setTimeout(20 * 1000);
        client.post(url, params, responseHandler);
    }

    public static RequestParams getRequestParams() {
        RequestParams params = new RequestParams();
        return params;
    }

    private static void addHeader() {
//        Staff user = DataManager.getInstance().getDefaultStaff();
//        if (user != null && !TextUtils.isEmpty(user.getLast_session_id())) {
//            // Log.e(TAG, user.lastSessionId);
//            client.addHeader("Session-ID", user.getLast_session_id());
//            CLog.i(TAG, "Session-ID = " + user.getLast_session_id());
//        }
//        params.put("Request-Time", new Date().getTime());
//        params.put("Auth-Key", "p!I5G8xTD?");
        client.addHeader("Request-From", "SaApp");
    }

    /*private static String getAbsoluteUrl(String relativeUrl) {
        return Const.BASE_URL + relativeUrl;
    }*/
}
