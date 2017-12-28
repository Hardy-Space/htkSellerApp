package com.hl.hl_htk.activity.fragment.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.hl.hl_htk.MainActivity;
import com.hl.hl_htk.R;
import com.hl.hl_htk.Utils.AsynClient;
import com.hl.hl_htk.Utils.GsonHttpResponseHandler;
import com.hl.hl_htk.Utils.MyHttpConfig;
import com.hl.hl_htk.Utils.UiFormat;
import com.hl.hl_htk.base.BaseFragment;
import com.hl.hl_htk.dialog.CallDialog;
import com.hl.hl_htk.dialog.ExitDialog;
import com.hl.hl_htk.dialog.OneLineDialog;
import com.hl.hl_htk.entity.UserInfoEntity;
import com.hl.hl_htk.model.CommonMsg;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.R.attr.breadCrumbShortTitle;
import static android.R.attr.id;

/**
 * Created by Administrator on 2017/6/13.
 */

public class SettingFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private boolean isPrepared = false;
    private boolean isFirst = true;
    private View view = null;


    @Bind(R.id.jt_text)
    TextView jtText;
    @Bind(R.id.voice_text)
    TextView voiceText;
    @Bind(R.id.voice_mark_rl)
    RelativeLayout voiceMarkRl;
    @Bind(R.id.zhendong_sc)
    SwitchCompat zhendongSc;
    @Bind(R.id.zhendong_rl)
    RelativeLayout zhendongRl;
    @Bind(R.id.jt1_text)
    TextView jt1Text;
    @Bind(R.id.call_text)
    TextView callText;
    @Bind(R.id.call_rl)
    RelativeLayout callRl;
    @Bind(R.id.jt2_text)
    TextView jt2Text;
    @Bind(R.id.call_service_text)
    TextView callServiceText;
    @Bind(R.id.call_service_rl)
    RelativeLayout callServiceRl;
    @Bind(R.id.jt3_text)
    TextView jt3Text;
    @Bind(R.id.feed_rl)
    RelativeLayout feedRl;
    @Bind(R.id.jt4_text)
    TextView jt4Text;
    @Bind(R.id.version_text)
    TextView versionText;
    @Bind(R.id.version_rl)
    RelativeLayout versionRl;
    @Bind(R.id.exit_text)
    TextView exitText;
    @Bind(R.id.head_sdv)
    SimpleDraweeView headSdv;
    @Bind(R.id.phoneNumber)
    TextView phoneNumber;
    @Bind(R.id.tv_state)
    TextView tvState;
    @Bind(R.id.rl_info)
    RelativeLayout rlInfo;
    @Bind(R.id.playing_sc)
    SwitchCompat playingSc;

    private boolean isPlaying = true;

    private String servicePhone = "";
    private OneLineDialog voiceDialog;
    List<String> list = new ArrayList<>();
    private CallDialog callDialog;

    private SpeechSynthesizer mTts;

    private void initIat() {
        mTts = SpeechSynthesizer.createSynthesizer(getActivity(), null);
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaofeng");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "70");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_setting, null);
            isPrepared = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    lazyInitData();
                }
            }, 200);
        }

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void lazyInitData() {

        if (isPrepared && isVisible) {
            //  isFirst = false;
            initWidget();
            getInfo();
            initIat();

        }

    }


    private void setVoice(int tag) {
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


    private void initWidget() {
        voiceMarkRl.setOnClickListener(this);
        callRl.setOnClickListener(this);
        callServiceRl.setOnClickListener(this);
        feedRl.setOnClickListener(this);
        versionRl.setOnClickListener(this);
        exitText.setOnClickListener(this);
        zhendongSc.setOnCheckedChangeListener(this);
        rlInfo.setOnClickListener(this);
        playingSc.setOnCheckedChangeListener(this);

        voiceDialog = new OneLineDialog(getActivity());
        list.add("男声提示音");
        list.add("女声提示音");


        voiceDialog.setOngetDataListener(new OneLineDialog.getDataListener() {
            @Override
            public void getData(String time, int viewId) {


                if ("女声提示音".equals(time)) {
                    setVoice(0);
                } else {
                    setVoice(1);
                }

                voiceText.setText(time);

            }
        }, 1);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.voice_mark_rl:
                voiceDialog.setData(list);
                voiceDialog.setShowOne("男声提示音");
                voiceDialog.show();
                break;
            case R.id.call_rl:
                callDialog = new CallDialog(getActivity(), "13792511691", "联系市场经理");
                callDialog.show();
                break;
            case R.id.call_service_rl:
                callDialog = new CallDialog(getActivity(), servicePhone, "联系客服");
                callDialog.show();
                break;
            case R.id.version_rl:

                break;
            case R.id.exit_text:
                showExitDialog();
                break;
            case R.id.rl_info:

                break;
            default:
                break;

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {

            case R.id.playing_sc:


                if (isPlaying) {
                    isPlaying = false;
                    break;
                }


                if (isChecked) {
                    palyingWork(1);
                } else {
                    palyingWork(0);
                }


                break;
            case R.id.zhendong_sc:
                if (isChecked) {
                    showMessage("打开了震动");
                } else {
                    showMessage("关闭了震动");
                }
                break;
            default:
                break;

        }

    }


    private void showExitDialog() {

        ExitDialog exitDialog = new ExitDialog(getActivity());
        exitDialog.show();
        Window window = exitDialog.getWindow();
        window.setWindowAnimations(R.style.dialog_anim);
        WindowManager.LayoutParams params = window.getAttributes();
        WindowManager wm = (WindowManager) getActivity().getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        params.width = wm.getDefaultDisplay().getWidth();
        params.y = wm.getDefaultDisplay().getHeight() / 2;
        window.setAttributes(params);
        TextView tvOk = (TextView) exitDialog.findViewById(R.id.tv_ok);

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                app.getLoginState().setToken("");
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

    }


    //合成监听器
    private SynthesizerListener mSynListener = new SynthesizerListener() {
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


    private void getInfo() {
        RequestParams params = AsynClient.getRequestParams();
        AsynClient.post(MyHttpConfig.userInfo, getActivity(), params, new GsonHttpResponseHandler() {
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
                UserInfoEntity userInfoEntity = gson.fromJson(rawJsonResponse, UserInfoEntity.class);

                if (userInfoEntity.getCode() == 100) {
                    if (userInfoEntity.getData() == null) return;
                    initView(userInfoEntity);

                }

            }
        });


    }


    private void initView(UserInfoEntity userInfoEntity) {
        UserInfoEntity.DataBean data = userInfoEntity.getData();

        headSdv.setImageURI(Uri.parse(data.getAvatarImg()));
        phoneNumber.setText(data.getManagerName());
        String state = "";
        if (data.getShopState() == 0) {
            state = "休息中";
            isPlaying = false;
            playingSc.setChecked(false);
        } else {
            state = "营业中";
            playingSc.setChecked(true);
        }

        tvState.setText(state);
        servicePhone = data.getCustomerServicePhone();
        callServiceText.setText(servicePhone);

    }


    private void palyingWork(final int shopStateId) {
        RequestParams params = AsynClient.getRequestParams();
        params.put("shopStateId", shopStateId);
        AsynClient.post(MyHttpConfig.playingWork, getActivity(), params, new GsonHttpResponseHandler() {
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
                    getInfo();
                } else {


                }

            }
        });
    }


}
