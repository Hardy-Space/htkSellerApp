package com.hl.hl_htk.Utils;

import android.content.Context;
import android.os.Bundle;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

/**
 * Created by Administrator on 2017/7/5.
 */

public class VoiceUtil {


    public static SpeechSynthesizer mTts;

    public static void initIat(Context context) {
        mTts = SpeechSynthesizer.createSynthesizer(context, null);
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaofeng");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
    }


    public static void setVoice(int tag) {
        //0 女声   1 男声
        String yuyin = "";
        if (tag == 0) {
            mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
            yuyin = "切换为女声提示音";
        } else {
            mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaofeng");//设置发音人
            yuyin = "切换为男声提示音";
        }
        mTts.startSpeaking(yuyin, mSynListener);
    }


    public static void startSpeak(Context context, String content) {

        initIat(context);
        mTts.startSpeaking(content, mSynListener);
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
