package com.soowin.staremblem.wxapi;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.soowin.staremblem.R;
import com.soowin.staremblem.utils.BaseActivity;
import com.soowin.staremblem.utils.PublicApplication;
import com.soowin.staremblem.utils.StrUtils;
import com.soowin.staremblem.utils.myView.CustomToast;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by hxt on 2017/11/28.
 * 微信支付返回值
 */

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    private final String TAG = WXPayEntryActivity.class.getCanonicalName();
    private IWXAPI api = WXAPIFactory.createWXAPI(this, null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "WXPayEntryActivity  onCreate: ");
        init();
    }

    private void init() {
        api = WXAPIFactory.createWXAPI(this, PublicApplication.WEIXIN_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.e(TAG, "onReq: ");
    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.e(TAG, "onPayFinish, errCode = " + baseResp.errCode + "");
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (baseResp.errCode == 0) {   // 0 成功  展示成功页面

//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("微信支付结果");
//                builder.setMessage("支付订单成功！");
//                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ToastUtil.customToastGravity(WXPayEntryActivity.this,"支付成功:");
//
//                    }
//                });
//                builder.show();
                finish();
                CustomToast.showSuccessToast(getApplicationContext(), "支付成功");

            } else if (baseResp.errCode == -1) {  // -1 错误  可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
                String str = baseResp.errStr;
                if (StrUtils.isEmpty(str))
                    str = "网络异常";
//                Toast.makeText(WXPayEntryActivity.this, "支付出错：" + str, Toast.LENGTH_SHORT)
//                        .show();
//                ToastUtil.customToastGravity(WXPayEntryActivity.this, "支付出错:" + str);
                CustomToast.showFailToast(getApplicationContext(), "支付出错:" + str);
                finish();

            } else if (baseResp.errCode == -2) {  // -2 用户取消    无需处理。发生场景：用户不支付了，点击取消，返回APP。
//                Toast.makeText(WXPayEntryActivity.this, "取消支付：" + baseResp.errStr, Toast.LENGTH_SHORT)
//                        .show();

                CustomToast.showFailYellowToast(getApplicationContext(), "取消支付");
                finish();
            }
        }
    }
}
