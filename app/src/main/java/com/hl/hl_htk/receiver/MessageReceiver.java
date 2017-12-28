package com.hl.hl_htk.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.hl.hl_htk.Utils.MyApplication;
import com.hl.hl_htk.model.JPushMessageModel;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import org.greenrobot.eventbus.EventBus;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2015/11/10.
 */
public class MessageReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush_Business";
    private NotificationManager nm;


    private void voice(Context context, String content) {

        SpeechSynthesizer mTts;

        mTts = SpeechSynthesizer.createSynthesizer(context, null);
        if (MyApplication.get(context).getLoginState().getVoice() == 0) {
            mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        } else {
            mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaofeng");//设置发音人
        }

        mTts.setParameter(SpeechConstant.SPEED, "70");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端

        mTts.startSpeaking(content, mSynListener);
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        Bundle bundle = intent.getExtras();

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.i(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.i(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.i(TAG, "onReceive: " + "收到通知");

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.i(TAG, "[MyReceiver] 用户点击打开了通知");

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.i(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.i(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.i(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }

    }


    /**
     * 收到自定义消息后的操作
     */
    private void processCustomMessage(Context context, Bundle bundle) {
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        JPushMessageModel jPushMessageModel = new Gson().fromJson(message, JPushMessageModel.class);
        switch (jPushMessageModel.getOrderState()){
            case 1://新订单，未结单
                EventBus.getDefault().post(new NewOrderEvent(jPushMessageModel.getOrderNumber() , jPushMessageModel.getOrderId()));
                voice(context, "您有一条新订单");
                break;
            case 3://配送中
                // TODO: 2017/11/13 配送后发送配送信息，刷新已处理数据列表
                break;
            case 4://订单完成
                EventBus.getDefault().post(new OrderCompletedEvent(jPushMessageModel.getOrderNumber() , jPushMessageModel.getOrderId()));
                voice(context, "订单" + jPushMessageModel.getOrderNumber() + "已送达");
                break;
            case 5://订单取消
                EventBus.getDefault().post(new OrderCancelEvent(jPushMessageModel.getOrderNumber() , jPushMessageModel.getOrderId()));
                voice(context, "订单" + jPushMessageModel.getOrderNumber() + "已取消");
                break;
        }
    }

    //合成监听器
    private static SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
        }

        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        }

        //开始播放
        public void onSpeakBegin() {
        }

        //暂停播放
        public void onSpeakPaused() {
        }

        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        //恢复播放回调接口
        public void onSpeakResumed() {
        }

        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
        }

    };


}
