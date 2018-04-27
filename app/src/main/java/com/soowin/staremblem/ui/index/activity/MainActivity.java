package com.soowin.staremblem.ui.index.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.android.pushservice.PushSettings;
import com.google.gson.Gson;
import com.soowin.staremblem.R;
import com.soowin.staremblem.ui.index.bean.CouponListBean;
import com.soowin.staremblem.ui.index.bean.UserInfoBean;
import com.soowin.staremblem.ui.index.fragment.IndexFragment;
import com.soowin.staremblem.ui.index.mModel.MeModel;
import com.soowin.staremblem.ui.login.activity.LoginActivity;
import com.soowin.staremblem.ui.login.activity.WebViewActivity;
import com.soowin.staremblem.ui.login.fragment.MeFragment;
import com.soowin.staremblem.utils.BaseActivity;
import com.soowin.staremblem.utils.PublicApplication;
import com.soowin.staremblem.utils.ScreenManager;
import com.soowin.staremblem.utils.StrUtils;
import com.soowin.staremblem.utils.Utlis;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

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
 * 主页
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    public int displayPage = 0;//展示页面
    public static final Integer TOKEN_INVALID_CODE = 0;//token失效
    public static final Integer APP_EXPIRED_CODE = 1;//app过期

    private AlertDialog.Builder dialog;
    //fragment*************************************************
    private IndexFragment indexFragment;
    private MeFragment meFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    //按钮部分***************************************************
    private RadioButton rbIndex;
    private RadioButton rbMe;
    private TextView tvPhone;
    private PopupWindow mPopupWindow;
    public  PopupWindow  mShowRloginDialog;
    CouponListBean.DataBean CouponData = new CouponListBean.DataBean();
    private String ClearData = "";
    //权限部分回掉
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 103;
    //异步请求控制器
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private  View contentViews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScreenManager.getScreenManager().RemoveAllExceptedOne(MainActivity.class);
        ClearData = "ClearData";/*Activity创建的时候 优惠券 清空数据的信号*/
        EventBus.getDefault().register(this);
        initBaiduPush();
        initView();
        contentViews= LayoutInflater.from(this).inflate(R.layout.item_main_token_invalid, null);
        mShowRloginDialog = new PopupWindow(contentViews, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, true);
    }


    private void initBaiduPush() {
        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,
                PublicApplication.BAIDU_PUSH_ID);
        PushSettings.enableDebugMode(getApplicationContext(), true);//debug模式
    }

    @Override
    protected void onResume() {
        super.onResume();
        loginStatus(displayPage);
    }


    private void initView() {
        indexFragment = new IndexFragment();

        meFragment = new MeFragment();

        rbIndex = findViewById(R.id.rb_index);
        rbMe = findViewById(R.id.rb_me);
        tvPhone = findViewById(R.id.tv_phone);

        rbIndex.setOnClickListener(this);
        rbMe.setOnClickListener(this);
        tvPhone.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_index:
                loginStatus(0);
                break;
            case R.id.rb_me:
                loginStatus(1);
                break;
            case R.id.tv_phone:
                if (StrUtils.isEmpty(PublicApplication.loginInfo.getString("token", ""))) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                } else {
                    if (StrUtils.isEmpty(PublicApplication.loginInfo.getString("ServiceTel", "")))
                        getPersonalInformation();
                    else
                        showPhonePopupwindow();
                }
//                getPingjia();
                break;
            case R.id.iv_menu:
                startActivity(new Intent(MainActivity.this, MessageActivity.class));
                break;
            case R.id.tv_ok:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    //请求权限
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CAMERA);
                } else {
                    callService();
                }
                if (mPopupWindow != null)
                    mPopupWindow.dismiss();
                break;
            case R.id.tv_cancel:
                if (mPopupWindow != null)
                    mPopupWindow.dismiss();
                if (mShowRloginDialog!=null)
                    mShowRloginDialog.dismiss();
                break;
            case R.id.ll_bg:
                if (mPopupWindow != null)
                    mPopupWindow.dismiss();
                if (mShowRloginDialog!=null)
                    mShowRloginDialog.dismiss();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //权限申请结果
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
            boolean b = true;
            for (int index = 0; index < permissions.length; index++) {
                if (grantResults[index] == PackageManager.PERMISSION_DENIED) {
                    b = false;
                    /*用户拒绝了权限*/
                }
            }
            if (!b)
                showToast(getResources().getString(R.string.app_index_miss_phone),
                        3);
            else
                initView();
        }
    }

    /**
     * 拨打服务电话
     */
    private void callService() {
        if (!StrUtils.isEmpty(PublicApplication.loginInfo.getString("ServiceTel", "")))
            Utlis.call(this, PublicApplication.loginInfo.getString("ServiceTel", ""));
    }

    /**
     * 展示服务电话
     */
    private void showPhonePopupwindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.item_main_phone, null);
        mPopupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, true);

        contentView.findViewById(R.id.ll_bg).setOnClickListener(this);
        contentView.findViewById(R.id.tv_ok).setOnClickListener(this);
        contentView.findViewById(R.id.tv_cancel).setOnClickListener(this);
        contentView.findViewById(R.id.ll_frame).setOnClickListener(this);
        TextView tvPhone = contentView.findViewById(R.id.tv_phone);
        tvPhone.setText(PublicApplication.loginInfo.getString("ServiceTel", ""));

        mPopupWindow.setTouchable(true);
        //如果不设置popupwindow的背景，无论是点击外部还是Back都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
//        mPopupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //显示PopupWindow
        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        mPopupWindow.setAnimationStyle(R.style.contextMenuAnim);
//        rootview.getBackground().setAlpha(255);
        mPopupWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
    }

    /**
     * 添加fragment布局
     */
    private void addFragment(int fragment) {

        // 步骤一：添加一个FragmentTransaction的实例
        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();
        // 步骤二：用add()方法加上Fragment的对象rightFragment
        if (transaction != null) {
            switch (fragment) {
                case 0:
                    transaction.replace(R.id.fl_main, indexFragment);

                    break;
                case 1:
                    transaction.replace(R.id.fl_main, meFragment);
                    break;
            }
        }
        // 步骤三：调用commit()方法使得FragmentTransaction实例的改变生效
        assert transaction != null;
        transaction.commit();
    }

    public void loginStatus(int status) {
        displayPage = status;
        addFragment(status);
        switch (status) {
            case 0:
                rbIndex.setChecked(true);
                break;
            case 1:
                rbMe.setChecked(true);
                break;
        }
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(); //调用双击退出函数
        }
        return false;
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (!isExit) {
            isExit = true; // 准备退出
            Toast.makeText(this, PublicApplication.resourceText.getString("app_index_out", getResources().getString(R.string.app_index_out)), Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            ScreenManager.getScreenManager().RemoveAllExceptedOne(MainActivity.class);
            ScreenManager.getScreenManager().popActivity();
            System.exit(0);
        }
    }

    /**
     * 重复登陆
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RLogin(Integer type) {
        if (type == TOKEN_INVALID_CODE)
            ShowRloginDialog(PublicApplication.resourceText.getString("app_index_token", getResources().getString(R.string.app_index_token)));
        else if (type == APP_EXPIRED_CODE)
            ShowAppExpiredDialog(PublicApplication.resourceText.getString("app_base_app_expired", getResources().getString(R.string.app_base_app_expired)));
    }

    /**
     * 清空数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ClearData(String data) {
        Log.e(TAG, "ClearData: ");/*清空Data数据的信号*/
        ClearData = data;
    }


    /**/
    public String getTypes() {
        return ClearData;
    }

    /**
     * 获取卡券传递的数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GetCouponData(CouponListBean.DataBean dataBean) {
        if (dataBean != null) {
            displayPage = 0;
            CouponData = dataBean;
            Log.e(TAG, "GetCouponData: " + CouponData.getCardNo());
            if (CouponData != null) {
                ClearData = "data";
                Bundle bundle = new Bundle();
                bundle.putSerializable(CouponListActivity.COUPONDATA, CouponData);

                indexFragment.setArguments(bundle);
            }
        }
    }

    /**
     * token失效提示框
     */
    public void ShowRloginDialog(String message) {
        contentViews.findViewById(R.id.ll_bg).setOnClickListener(this);
        contentViews.findViewById(R.id.tv_cancel).setOnClickListener(this);
        contentViews.findViewById(R.id.ll_frame).setOnClickListener(this);
        contentViews.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                mShowRloginDialog.dismiss();
            }
        });
        TextView tv = contentViews.findViewById(R.id.tv_content);
        tv.setText(message);

        mShowRloginDialog.setTouchable(true);
        //如果不设置popupwindow的背景，无论是点击外部还是Back都无法dismiss弹框
        mShowRloginDialog.setBackgroundDrawable(new BitmapDrawable());
        mShowRloginDialog.setOutsideTouchable(true);
        mShowRloginDialog.setFocusable(true);
//        mPopupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        mShowRloginDialog.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //显示PopupWindow
        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        mShowRloginDialog.setAnimationStyle(R.style.contextMenuAnim);
//        rootview.getBackground().setAlpha(255);
        if (!mShowRloginDialog.isShowing())
            mShowRloginDialog.showAtLocation(rootview, Gravity.CENTER, 0, 0);
    }

    /**
     * app过期提示框
     */
    public void ShowAppExpiredDialog(String message) {
        dialog = new AlertDialog.Builder(this);
        dialog.setTitle(PublicApplication.resourceText.getString("app_main_tongzhi",getResources().getString(R.string.app_main_tongzhi)));
        dialog.setMessage(PublicApplication.resourceText.getString("app_main_shengji",getResources().getString(R.string.app_main_shengji)));
        dialog.setCancelable(false);
        dialog.show();
    }

    /*获取个人的用户信息  userinfo */
    private void getPersonalInformation() {
        showDialog();
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = MeModel.UserInfo();

                if (result != null)
                    emitter.onNext(result);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<String, UserInfoBean>() {
                    @Override
                    public UserInfoBean apply(String s) throws Exception {
                        Gson gson = new Gson();
                        return gson.fromJson(s, UserInfoBean.class);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserInfoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (mCompositeDisposable != null)
                            mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(UserInfoBean data) {
                        switch (data.get_metadata().getCode()) {
                            case 200:

                                UserInfoBean.DataBean dataBean = data.getData();
                                PublicApplication.pathAvatar = dataBean.getAvatar();
                                PublicApplication.loginInfo.edit().putString("UserID", String.valueOf(dataBean.getUserID())).apply();
                                PublicApplication.loginInfo.edit().putString("Mobile", dataBean.getMobile()).apply();
                                PublicApplication.loginInfo.edit().putString("RealName", dataBean.getRealName()).apply();
                                PublicApplication.loginInfo.edit().putString("NickName", dataBean.getNickName()).apply();
                                PublicApplication.loginInfo.edit().putString("avatar", dataBean.getAvatar()).apply();
                                PublicApplication.loginInfo.edit().putString("Sex", String.valueOf(dataBean.getSex())).apply();
                                PublicApplication.loginInfo.edit().putString("Balance", String.valueOf(dataBean.getBalance())).apply();
                                PublicApplication.loginInfo.edit().putString("OpenID", String.valueOf(dataBean.getOpenID())).apply();
                                PublicApplication.loginInfo.edit().putString("ServiceTel", String.valueOf(dataBean.getServiceTel())).apply();
                                break;
                            case 500:

                              showToast(data.get_metadata().getMessage(),3);
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                        showToast(PublicApplication.resourceText.getString("app_error_message", getResources().getString(R.string.app_error_message)), 3);
                    }

                    @Override
                    public void onComplete() {
                        dismissDialog();
                        showPhonePopupwindow();
                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mCompositeDisposable != null)
            mCompositeDisposable.clear();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPopupWindow != null)
            mPopupWindow.dismiss();
        dismissDialog();
    }

    /**
     * 获取评价网页
     */
    public void getPingjia() {
        //https://api.benz.wx.fractalist.com.cn/v1/order/OrderEvaluationIndex?OrderId=订单号
        Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
        intent.putExtra(WebViewActivity.URLS, "https://api.benz.wx.fractalist.com.cn/v1/order/OrderEvaluationIndex?OrderId=93854");
        startActivity(intent);
       /* showDialog();
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                RequestBody body1 = new FormBody.Builder()
                        .add("test", "ceshicanshu_汉字_123123")
                        .build();
                String result = HttpTool.okPostTest(body1, "http://39.105.50.123:8080/HolleWorld/getData");
                Log.e(TAG, "subscribe: " + result);
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
                    public void onNext(BaseBean data) {
                        showToast(data.get_metadata().getMessage(), 3);
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }
                });*/
    }

}
/*tv.setMovementMethod(ScrollingMovementMethod.getInstance());//滚动
        tv.setText(Html.fromHtml("<html><head><title>TextView使用HTML</title></head><body><p><strong>强调</strong></p><p><em>斜体</em></p>"
                + "<p><a href=\"http://www.dreamdu.com/xhtml/\">超链接HTML入门</a>学习HTML!</p><p><font color=\"#aabb00\">颜色1"
                + "</p><p><font color=\"#00bbaa\">颜色2</p><h1>标题1</h1><h3>标题2</h3><h6>标题3</h6><p>大于>小于< </p><p>" +
                "下面是网络图片</p><img src=\"http://avatar.csdn.net/0/3/8/2_zhang957411207.jpg\"/></body></html>"));
*/
