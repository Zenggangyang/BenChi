package com.soowin.staremblem.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.soowin.staremblem.R;
import com.soowin.staremblem.ui.index.activity.MainActivity;
import com.soowin.staremblem.utils.myView.CustomToast;
import com.soowin.staremblem.utils.myView.MyEditText;
import com.soowin.staremblem.utils.myView.MyRadioButton;
import com.soowin.staremblem.utils.myView.MyTextView;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Administrator on 2017/6/8.
 * 类的作用：基类
 * activity基类
 */
public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getCanonicalName();

    public static final String TOKEN_INVALID = "token_invalid";
    public static final String LACK_JURISDICTION = "lack_jurisdiction";
    public static final String SERVICE_ERROR = "service_error";
    public static final String APP_EXPIRED = "app_expired";

    private ProgressDialog dialog; // 正在加载进度条
    private static final String PACKAGE_URL_SCHEME = "package:"; // 方案
    //    private CustomProgressDialog dialog;
    public int wdith;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initFaceType();
        super.onCreate(savedInstanceState);
        ScreenManager.getScreenManager().pushActivity(this);//加入栈
        initDialog();
        initWindow();

    }

    private void initFaceType() {
        LayoutInflaterCompat.setFactory(LayoutInflater.from(this), new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                AppCompatDelegate delegate = getDelegate();
                View view = delegate.createView(parent, name, context, attrs);

                switch (name) {
                    case "TextView":
                        view = new MyTextView(context, attrs);
                        break;
                    case "RadioButton":
                        view = new MyRadioButton(context, attrs);
                        break;
                    case "EditText":
                        view = new MyEditText(context, attrs);
                        break;
                }
                return view;
            }
        });
    }

    private void initWindow() {
        WindowManager wm = getWindowManager();
        wdith = wm.getDefaultDisplay().getWidth();
    }


    @Override
    protected void onResume() {
        /**
         * 设置为竖屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public void initDialog() {
//        dialog = new CustomProgressDialog(this);
        dialog = new ProgressDialog(this);
//        dialog.createDialog(this);
        dialog.setTitle("请稍等片刻...");
        dialog.setMessage("正在努力加载...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }

    public void showDialog() {
//        if (dialog == null) {
//            dialog = CustomProgressDialog.createDialog(this, wdith, height);
//        }
        dialog.show();
    }

    public void dismissDialog() {
//        if (dialog != null) {
//            dialog.dismiss();
//            dialog = null;
//        }
        dialog.dismiss();
    }

    /**
     * 消息弹出框
     *
     * @param message     消息内容
     * @param messageType 消息类型 1、成功 2、失败 3、警告
     */
    public void showToast(String message, int messageType) {
//       ToastUtil.customToastGravity(this,str);
//        Toast mToast = null;
//        if (mToast == null) {
//            mToast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
//        } else {
//            mToast.setText(message);
//        }
//        mToast.setGravity(Gravity.CENTER, 0, 0);
//        mToast.show();
        switch (messageType) {
            case 1:
                CustomToast.showSuccessToast(getApplicationContext(), message);
                break;
            case 2:
                CustomToast.showFailToast(getApplicationContext(), message);
                break;
            case 3:
                CustomToast.showFailYellowToast(getApplicationContext(), message);
                break;

        }
    }

    // 显示缺失权限提示
    public void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("帮助");
        builder.setMessage("当前应用缺少必要权限。\n请点击\"设置\"-\"权限\"-打开所需权限。\n最后点击两次后退按钮，即可返回。");

        // 拒绝, 退出应用
        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
                startActivity(intent);
            }
        });
        builder.show();
    }



    /**
     * 接口返回值筛选
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void TokenInvalid(String massage) {
        switch (massage) {
            case TOKEN_INVALID://token失效跳转首页
                startActivity(new Intent(this, MainActivity.class));
                EventBus.getDefault().post(MainActivity.TOKEN_INVALID_CODE);
                ScreenManager.getScreenManager().RemoveAllExceptedOne(MainActivity.class);
                break;
            case LACK_JURISDICTION:
                showToast(getResources().getString(R.string.app_base_lack_jurisdiction), 3);
                break;
            case SERVICE_ERROR:
                showToast(getResources().getString(R.string.app_base_service_error), 3);
                break;
            case APP_EXPIRED:
                startActivity(new Intent(this, MainActivity.class));
                EventBus.getDefault().post(MainActivity.TOKEN_INVALID_CODE);
                ScreenManager.getScreenManager().RemoveAllExceptedOne(MainActivity.class);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
