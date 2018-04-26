package com.soowin.staremblem.pay;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.soowin.staremblem.utils.PublicApplication;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hxt on 2017/11/28.
 * 支付工厂类
 */

public class PayFactory {
    private static final String TAG = PayFactory.class.getSimpleName();
    String s = "支付失败";
    private PayListener mlistener;

    public static interface PayListener {
        void onPayListener(String s, boolean isOk);
    }

    public void AliPay(final String aliInfo, final Context context, final PayListener mlistener) {
        Observable.create(new ObservableOnSubscribe<Map<String, String>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Map<String, String>> e) throws Exception {
                PayTask alipay = new PayTask((Activity) context);
                Map<String, String> result = alipay.payV2(aliInfo, true);

                e.onNext(result);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Map<String, String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
//                        if (mCompositeDisposable != null)
//                            mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Map<String, String> data) {
                        int resultStatus = Integer.parseInt(data.get("resultStatus"));
                        switch (resultStatus) {
                            case 9000:
                                s = "订单支付成功";
                                mlistener.onPayListener(s, true);
                                break;
                            case 8000:
                                s = "正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态";
                                mlistener.onPayListener(s, false);
                                break;
                            case 4000:
                                s = "订单支付失败";
                                mlistener.onPayListener(s, false);
                                break;
                            case 5000:
                                s = "重复请求";
                                mlistener.onPayListener(s, false);
                                break;
                            case 6001:
                                s = "用户中途取消";
                                mlistener.onPayListener(s, false);
                                break;
                            case 6002:
                                s = "网络连接出错";
                                mlistener.onPayListener(s, false);
                                break;
                            case 6004:
                                s = "支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态";
                                mlistener.onPayListener(s, false);
                                break;
                            default:
                                break;
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mlistener.onPayListener(s, false);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /**
     * 微信部分
     */
    private IWXAPI api;

    /**
     * 微信部分
     */

    public void WxPay(Context context, WxPayBean mBean) {
        Log.e(TAG, "WxPay: PrepayBean");
        PublicApplication.WEIXIN_APP_ID = mBean.getAppid();
        // 商户APP工程中引入微信JAR包，调用API前，需要先向微信注册您的APPID
        api = WXAPIFactory.createWXAPI(context, PublicApplication.WEIXIN_APP_ID );  // 参数二是申请的APPID

        // 将该app注册到微信
        api.registerApp(PublicApplication.WEIXIN_APP_ID);  // 参数是申请的APPID

        PayReq request = new PayReq();
        request.appId = mBean.getAppid();
        request.partnerId = mBean.getPartnerid();
        request.prepayId = mBean.getPrepayid();
        request.packageValue = "Sign=WXPay";
        request.nonceStr = mBean.getNoncestr();
        request.timeStamp = mBean.getTimestamp() + "";
        request.sign = mBean.getSign();
        api.sendReq(request);  // 发起支付
//        Log.e(TAG, "WxPay: 发起支付" + api.sendReq(request));
    }
}
