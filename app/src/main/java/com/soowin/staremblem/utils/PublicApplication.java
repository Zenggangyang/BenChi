package com.soowin.staremblem.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.webkit.CookieSyncManager;

import com.google.gson.Gson;
import com.king.thread.nevercrash.NeverCrash;
import com.soowin.staremblem.http.HttpUrl;
import com.soowin.staremblem.ui.index.bean.BaseBean;
import com.soowin.staremblem.ui.index.bean.CarListBean;
import com.soowin.staremblem.ui.index.mModel.MeModel;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/6/8.
 */

public class PublicApplication extends Application {

    /**
     * 获取数据路径拼接接口
     **/
    public static HttpUrl urlData = new HttpUrl();
    /**
     * 头像地址
     */
    public static String pathAvatar = "";
    public static File path;
    /**
     * 登陆后返回的公共数据 --地址-用户-用户id -令牌 -加密解密密钥 等等
     **/
//    public static LoginBean loginMessage = new LoginBean();
    /**
     * 本地数据库 存储数据
     */
    public static SharedPreferences loginInfo;
    public static SharedPreferences resourceText;
    /**
     * 第三方id  TODO: 2018/3/13 0013 需客户提供apikey
     */
    public static String WEIXIN_APP_ID = "wxde139f4851f9f5b9";//微信
    public static String BAIDU_PUSH_ID = "r2dl4VLU73O516GcSeMHtHlh";//百度推送
    public static String UMENG_ID = "5a9f46d9f29d987f07000724";//友盟统计

    private static final String BUGLY_ID = "fdf29a3e62";
    /**
     * 字体
     */
    public static Typeface typefaceC = null;
    public static Typeface typefaceE = null;
    private static Context mContext;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        CookieSyncManager.createInstance(this);

        /*File externalDir = Environment.getExternalStorageDirectory();
        //头像地址
        File path = new File(externalDir.getAbsolutePath() + "/CarNest/Images");
        if (!path.exists()) { // 判断目录是否存在
            if (path.mkdirs()) {
                Log.e("path", "mkdirs-->true");
            }// 创建目录
        }
//        数据库路径
        databasesPath = externalDir.getAbsolutePath() + "/CarNest/DataBases";
        File datapath = new File(databasesPath);
        if (!datapath.exists()) { // 判断目录是否存在
            if (datapath.mkdirs()) {
                Log.e("datapath", "mkdirs-->true");
            }// 创建目录
        }
        pathAvatar = path + "/heard.jpg";*/
        initUMeng();
        initTypeFace();
        initDataBase();
        initBugCrash();
    }

    /**
     * 初始化bug监听
     */
    private void initBugCrash() {
        Bugly.init(this, BUGLY_ID, false);

        NeverCrash.init(new NeverCrash.CrashHandler() {
            @Override
            public void uncaughtException(Thread t, final Throwable e) {
                /*手动上传给腾讯Bugly日志*/
                CrashReport.postCatchedException(e);
                // 上传日志记录
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        String result = MeModel.LogInfo(e.getLocalizedMessage(), e.getMessage());
                        if (result != null)
                            emitter.onNext(result);
                        emitter.onComplete();
                    }
                }).subscribeOn(Schedulers.io())
                        .map(new Function<String, BaseBean>() {
                            @Override
                            public BaseBean apply(String s) throws Exception {
                                Gson gson = new Gson();
                                return gson.fromJson(s, BaseBean.class);
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<BaseBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                if (mCompositeDisposable != null)
                                    mCompositeDisposable.add(d);
                            }

                            @Override
                            public void onNext(BaseBean baseBean) {
                                switch (baseBean.get_metadata().getCode()) {

                                    case 200:

                                        break;
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initTypeFace() {
        typefaceC = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/hanyizhongsongjian.ttf");
        typefaceE = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Corporate_s.ttf");
    }

    /**
     * 初始化数据库
     */
    private void initDataBase() {
        loginInfo = getSharedPreferences("StarEmblem", 0);
        loginInfo.edit().putString("app-ver", "app1.0").apply();

        resourceText = getSharedPreferences("StarEmblemResourceText", 0);
    }

    /**
     * 初始化友盟统计
     */
    private void initUMeng() {
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.setCatchUncaughtExceptions(true);
        UMConfigure.init(this, UMENG_ID, "soowin",
                UMConfigure.DEVICE_TYPE_PHONE, null);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        //内存低的时候,调用垃圾回收器
        System.gc();
    }

}
