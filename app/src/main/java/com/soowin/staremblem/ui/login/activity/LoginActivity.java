package com.soowin.staremblem.ui.login.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.soowin.staremblem.R;
import com.soowin.staremblem.ui.index.activity.MainActivity;
import com.soowin.staremblem.ui.index.bean.PageHtmlBean;
import com.soowin.staremblem.ui.index.mModel.MeModel;
import com.soowin.staremblem.ui.login.bean.LoginBean;
import com.soowin.staremblem.ui.login.bean.SmsCodeBean;
import com.soowin.staremblem.ui.login.mModel.LoginModel;
import com.soowin.staremblem.utils.BaseActivity;
import com.soowin.staremblem.utils.PublicApplication;
import com.soowin.staremblem.utils.RegularUtil;
import com.soowin.staremblem.utils.ScreenManager;
import com.soowin.staremblem.utils.StrUtils;

import org.greenrobot.eventbus.EventBus;

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
 * 登陆页面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    //权限部分回掉
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 102;
    //标题栏
    private TextView tvTopTitle;
    private ImageView ivTopBack;
    //界面
    private EditText etLoginPhone, etLoginSmsCode;
    private TextView tvLoginGetCode;
    private TextView btnLoginLogin;
    private TextView tvLoginUserAgreement;
    private CheckBox cbLoginAgree;
    private CountDownTimer cdtimer;
    //红色提示文字
    private TextView tvLoginPhonehinttext, tvLoginSmshinttext;
    private String TAG = LoginActivity.class.getSimpleName();
    private String PageHtmlUrl = "";//用户协议网址
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();//异步请求控制器

    //静态文字
    private TextView tvLoginStart, tvLoginStartEnglish, tvLoginIAagree;
    private ImageView ivIconPhone, ivIconSms;
    private boolean flag;//是否显示用户协议dialog的标识


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ScreenManager.getScreenManager().pushActivity(this);
        PublicApplication.loginInfo.edit().putString("token", "").apply();
        initImei();
        initTitle();
        initView();
        getUserAgreMent();

    }


    private void initTitle() {
        tvTopTitle = findViewById(R.id.tv_top_title);
        ivTopBack = findViewById(R.id.iv_top_back);
        tvTopTitle.setText((PublicApplication.resourceText.getString("app_login_login", getResources().getString(R.string.app_login_login))));
        ivTopBack.setOnClickListener(this);

        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_go_back", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_go_back))
                .error(getResources().getDrawable(R.drawable.img_go_back))
                .into(ivTopBack);

    }

    /*用户协议*/
    private void getUserAgreMent() {
        if (flag) {
            showDialog();
        }
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = MeModel.LoadLoginHtml();

                if (result != null)
                    emitter.onNext(result);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<String, PageHtmlBean>() {
                    @Override
                    public PageHtmlBean apply(String s) throws Exception {
                        Gson gson = new Gson();
                        return gson.fromJson(s, PageHtmlBean.class);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PageHtmlBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (mCompositeDisposable != null)
                            mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(PageHtmlBean data) {
                        switch (data.get_metadata().getCode()) {
                            case 200:
                                PageHtmlUrl = data.getData().getPageHtmlUrl();
//                                showToast("成功", 1);
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (flag) {
                            dismissDialog();
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (flag) {
                            dismissDialog();
                            Intent intent = new Intent(LoginActivity.this, WebViewActivity.class);
                            intent.putExtra(WebViewActivity.URLS, PageHtmlUrl);
                            intent.putExtra(WebViewActivity.TITLE, PublicApplication.resourceText.getString("app_personal_center_user_agreement", getResources().getString(R.string.app_personal_center_user_agreement)));
                            startActivityForResult(intent, 1);
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            cbLoginAgree.setChecked(true);
        }
    }

    /*清空之前的CouponData*/
    private void setClearData() {
        EventBus.getDefault().post("ClearData");
    }

    private void initView() {
        etLoginPhone = findViewById(R.id.et_login_phone);
        etLoginSmsCode = findViewById(R.id.et_login_sms_code);
        tvLoginGetCode = findViewById(R.id.tv_login_get_code);
        tvLoginGetCode.setOnClickListener(this);
        btnLoginLogin = findViewById(R.id.btn_login_login);
        btnLoginLogin.setOnClickListener(this);
        tvLoginUserAgreement = findViewById(R.id.tv_login_user_agreement);
        tvLoginUserAgreement.setOnClickListener(this);
        cbLoginAgree = findViewById(R.id.cb_login_agree);

        tvLoginPhonehinttext = findViewById(R.id.tv_login_phonehinttext);
        tvLoginSmshinttext = findViewById(R.id.tv_login_smshinttext);

//        EditTextUtils.init(new EditText[]{etLoginPhone, etLoginSmsCode}, null);
        cdtimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvLoginGetCode.setText(PublicApplication.resourceText.getString("app_login_surplus", getResources().getString(R.string.app_login_surplus)) + (millisUntilFinished / 1000) + PublicApplication.resourceText.getString("app_login_sec", getResources().getString(R.string.app_login_sec)));
            }

            public void onFinish() {
                tvLoginGetCode.setText(PublicApplication.resourceText.getString("app_login_revalidation", getResources().getString(R.string.app_login_revalidation)));
                tvLoginGetCode.setClickable(true);
            }
        };
        TextWatcher twPhone = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    tvLoginPhonehinttext.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        etLoginPhone.addTextChangedListener(twPhone);
        TextWatcher twSms = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    tvLoginSmshinttext.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        etLoginSmsCode.addTextChangedListener(twSms);


        //静态文字
        tvLoginStart = findViewById(R.id.tv_login_start);
        tvLoginStartEnglish = findViewById(R.id.tv_login_start_english);
        tvLoginIAagree = findViewById(R.id.tv_login_i_agree);
        tvLoginStart.setText(PublicApplication.resourceText.getString("app_login_start", getResources().getString(R.string.app_login_start)));
        tvLoginStartEnglish.setText(PublicApplication.resourceText.getString("app_login_start_english", getResources().getString(R.string.app_login_start_english)));
        tvLoginIAagree.setText(PublicApplication.resourceText.getString("app_login_i_agree", getResources().getString(R.string.app_login_i_agree)));
        btnLoginLogin.setText(PublicApplication.resourceText.getString("app_login_login", getResources().getString(R.string.app_login_login)));
        tvLoginGetCode.setText(PublicApplication.resourceText.getString("app_login_get_sms_code", getResources().getString(R.string.app_login_get_sms_code)));
        tvLoginUserAgreement.setText(PublicApplication.resourceText.getString("app_login_user_agreement", getResources().getString(R.string.app_login_user_agreement)));

        ivIconPhone = findViewById(R.id.iv_icon_phone);
        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_icon_phone", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_icon_phone))
                .error(getResources().getDrawable(R.drawable.img_icon_phone))
                .into(ivIconPhone);

        ivIconSms = findViewById(R.id.iv_icon_sms);
        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_icon_sms", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_icon_sms))
                .error(getResources().getDrawable(R.drawable.img_icon_sms))
                .into(ivIconSms);

    }

    private void initImei() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            //请求权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_CAMERA);
            return;
        }
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        PublicApplication.loginInfo.edit().putString("device-id", tm.getDeviceId()).apply();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //权限申请结果
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
            for (int index = 0; index < permissions.length; index++) {
                if (grantResults[index] == PackageManager.PERMISSION_DENIED) {
                    /*用户拒绝了权限*/
                } else {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    PublicApplication.loginInfo.edit().putString("device-id", tm.getDeviceId()).apply();
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //获取验证码
            case R.id.tv_login_get_code:
                tvLoginGetCode.setClickable(false);
                getCode();
                break;
            //登录
            case R.id.btn_login_login:
                login();
                break;
            //用户协议
            case R.id.tv_login_user_agreement:
                if (TextUtils.isEmpty(PageHtmlUrl)) {
                    flag = true;
                    getUserAgreMent();
                } else {
                    Intent intent = new Intent(this, WebViewActivity.class);
                    intent.putExtra(WebViewActivity.URLS, PageHtmlUrl);
                    intent.putExtra(WebViewActivity.TITLE, PublicApplication.resourceText.getString("app_personal_center_user_agreement", getResources().getString(R.string.app_personal_center_user_agreement)));
                    startActivityForResult(intent, 1);
                }

                break;
            case R.id.iv_top_back:
                if (StrUtils.isEmpty(PublicApplication.loginInfo.getString("token", ""))) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
                finish();
                break;
        }
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (StrUtils.isEmpty(PublicApplication.loginInfo.getString("token", ""))) {
                ScreenManager.getScreenManager().RemoveAllExceptedOne(MainActivity.class);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
            finish();
        }
        return false;
    }

    /**
     * 发送验证码
     */
    private void getCode() {
        final String phone = etLoginPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            tvLoginPhonehinttext.setVisibility(View.VISIBLE);
            return;
        }
        if (!RegularUtil.checkMobile(phone)) {
            tvLoginPhonehinttext.setVisibility(View.VISIBLE);
            return;
        }
        //发送获取验证码的请求
        showDialog();
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = LoginModel.getCode(phone);
                Log.e(TAG, result);
                if (result != null)
                    emitter.onNext(result);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<String, SmsCodeBean>() {
                    @Override
                    public SmsCodeBean apply(String s) throws Exception {
                        Gson gson = new Gson();
                        SmsCodeBean dataBean = gson.fromJson(s,
                                SmsCodeBean.class);
                        return dataBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SmsCodeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (mCompositeDisposable != null)
                            mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(SmsCodeBean dataBean) {
                        if (dataBean.get_metadata().getCode() == 200) {
                            cdtimer.start();
                        } else {
                            tvLoginGetCode.setClickable(true);
                            showToast(dataBean.get_metadata().getMessage(), 3);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        tvLoginGetCode.setClickable(true);
                        dismissDialog();
                        showToast(PublicApplication.resourceText.getString("app_error_message", getResources().getString(R.string.app_error_message)), 3);
                    }

                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }
                });

    }

    /**
     * 登录
     */
    private void login() {
        boolean isHttp = true;
        final String phone = etLoginPhone.getText().toString();
        final String code = etLoginSmsCode.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            tvLoginPhonehinttext.setVisibility(View.VISIBLE);
            isHttp = false;
        }
        if (!RegularUtil.checkMobile(phone)) {
            tvLoginPhonehinttext.setVisibility(View.VISIBLE);
            tvLoginPhonehinttext.setText(PublicApplication.resourceText.getString("app_login_check_phone", getResources().getString(R.string.app_login_check_phone)));
            isHttp = false;
        }
        if (TextUtils.isEmpty(code)) {
            tvLoginSmshinttext.setVisibility(View.VISIBLE);
            isHttp = false;
        }
        if (!cbLoginAgree.isChecked()) {
            showToast(getResources().getString(R.string.app_login_please_check), 3);
            isHttp = false;
        }

        if (!isHttp) {
            return;
        }


//        if (TextUtils.isEmpty(phone)) {
//            showToast("请输入手机号码", 3);
//            return;
//        }
//        if (!RegularUtil.checkMobile(phone)) {
//            showToast("请输入正确的手机号码", 3);
//            return;
//        }
//        if (TextUtils.isEmpty(code)) {
//            showToast("请输入验证码", 3);
//            return;
//        }
//        if (!cbLoginAgree.isChecked()) {
//            showToast("请勾选用户协议", 3);
//            return;
//        }

        //发送登录的请求

        showDialog();
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = LoginModel.login(phone, code);
                if (result != null)
                    emitter.onNext(result);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<String, LoginBean>() {
                    @Override
                    public LoginBean apply(String s) throws Exception {
                        Gson gson = new Gson();
                        LoginBean dataBean = gson.fromJson(s,
                                LoginBean.class);
                        return dataBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (mCompositeDisposable != null)
                            mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(LoginBean dataBean) {

                        switch (dataBean.get_metadata().getCode()) {

                            case 200:
                                PublicApplication.loginInfo.edit().putString("token", dataBean.getData().getToken()).apply();
                                setClearData();
                                if (dataBean.getData().getIsNewUser().equals("1")) {
                                    //新用户
                                    finish();
                                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class).putExtra("phone", phone));
                                } else {
                                    //老用户
                                    finish();
                                }
                                break;
                            default:
                                showToast(dataBean.get_metadata().getMessage(),3);
                                break;
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast(PublicApplication.resourceText.getString("app_error_message", getResources().getString(R.string.app_error_message)), 3);
                        dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        dismissDialog();

                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mCompositeDisposable != null)
            mCompositeDisposable.clear();
        dismissDialog();
    }

}
