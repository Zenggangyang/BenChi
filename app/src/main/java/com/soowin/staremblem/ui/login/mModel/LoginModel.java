package com.soowin.staremblem.ui.login.mModel;

import com.orhanobut.logger.Logger;
import com.soowin.staremblem.http.HttpTool;
import com.soowin.staremblem.utils.PublicApplication;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/3/20.
 */

public class LoginModel {
    private static final String TAG = LoginModel.class.getCanonicalName();

    /**
     * 获取验证码
     */
    public static String getCode(String mobile) throws Exception {
        RequestBody body = new FormBody.Builder()
                .add("mobile", mobile)
                .build();
        String result = HttpTool.okPost(body, PublicApplication.urlData.mobilecode);
        Logger.e(TAG + "获取验证码" + result);
        return result;
    }

    /**
     * 登录
     */
    public static String login(String mobile, String code) throws Exception {
        RequestBody body = new FormBody.Builder()
                .add("mobile", mobile)
                .add("code", code)
                .build();
        String result = HttpTool.okPost(body, PublicApplication.urlData.login);
        Logger.e(TAG + "登录" + result);
        return result;
    }

    /**
     * 用户完善登录信息
     */
    public static String loginInfo(String mobile, String name, String sex, String headimgurl) throws Exception {
        RequestBody body = new FormBody.Builder()
                .add("mobile", mobile)
                .add("name", name)
                .add("sex", sex)
                .add("headimgurl", headimgurl)
                .build();
        String result = HttpTool.okPost(body, PublicApplication.urlData.loginInfo);
        Logger.e(TAG + "用户完善登录信息" + result);
        return result;
    }

}
