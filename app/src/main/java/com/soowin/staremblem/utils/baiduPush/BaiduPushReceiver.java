package com.soowin.staremblem.utils.baiduPush;

import android.content.Context;
import android.util.Log;

import com.baidu.android.pushservice.PushMessageReceiver;
import com.soowin.staremblem.utils.PublicApplication;

import java.util.List;

/**
 * Created by hxt on 2018/3/13 0013.
 * 百度推送
 */

public class BaiduPushReceiver extends PushMessageReceiver {
    @Override
    public void onBind(Context context, int errorCode, String appid, String userId, String channelId, String requestId) {
// 2018/3/13 0013 定向推送 将channelId上传到服务器绑定
        PublicApplication.loginInfo.edit().putString("channelId", channelId).apply();
        Log.e(TAG, "onBind: errorCode=" + errorCode + "-appid=" + appid + "-userId=" + userId + "-channelId=" + channelId + "-requestId=" + requestId);
    }

    @Override
    public void onUnbind(Context context, int i, String s) {

    }

    @Override
    public void onSetTags(Context context, int i, List<String> list, List<String> list1, String s) {

    }

    @Override
    public void onDelTags(Context context, int i, List<String> list, List<String> list1, String s) {

    }

    @Override
    public void onListTags(Context context, int i, List<String> list, String s) {

    }

    @Override
    public void onMessage(Context context, String s, String s1) {

    }

    @Override
    public void onNotificationClicked(Context context, String s, String s1, String s2) {

    }

    @Override
    public void onNotificationArrived(Context context, String s, String s1, String s2) {

    }
}
