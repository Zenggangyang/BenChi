package com.soowin.staremblem.ui.index.activity;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.soowin.staremblem.R;
import com.soowin.staremblem.ui.index.bean.UserInfoBean;
import com.soowin.staremblem.ui.index.mModel.MeModel;
import com.soowin.staremblem.ui.login.activity.LoginActivity;
import com.soowin.staremblem.utils.BaseActivity;
import com.soowin.staremblem.utils.PublicApplication;
import com.soowin.staremblem.utils.ScreenManager;
import com.soowin.staremblem.utils.StrUtils;
import com.soowin.staremblem.utils.Utlis;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 我的信息页面
 */
public class MeInfoActivity extends BaseActivity implements View.OnClickListener {
    UserInfoBean.DataBean data1 = new UserInfoBean.DataBean();
    private TextView tvTitle;
    private ImageView ivBack;

    private TextView tvName;
    private TextView tvPhone;
    private TextView tvSex;
    private TextView tvConectKefu;

    //权限部分回掉
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 103;

    //异步请求控制器
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private PopupWindow mPopupWindow;

    private TextView tvStringInfoName;
    private TextView tvStringInfoSex;
    private TextView tvStringInfoPhone;
    private TextView tvStringInfoTishi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_info);
        ScreenManager.getScreenManager().pushActivity(this);//加入栈
        data1 = (UserInfoBean.DataBean) getIntent().getSerializableExtra("data");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (StrUtils.isEmpty(PublicApplication.loginInfo.getString("token", ""))) {
            startActivity(new Intent(MeInfoActivity.this, LoginActivity.class));
        } else {
            initView();
        }
    }

    private void initView() {
        initTitle();
        initContent();
        initStatisDisplay();
    }

    /*展示静态文本*/
    private void initStatisDisplay() {
        tvStringInfoName = findViewById(R.id.tv_string_meinfo_name);
        tvStringInfoName.setText(PublicApplication.resourceText.getString("app_transfer_form_name", getResources().getString(R.string.app_transfer_form_name)));
        tvStringInfoSex = findViewById(R.id.tv_string_meinfo_sex);
        tvStringInfoSex.setText(PublicApplication.resourceText.getString("app_meinfo_sex", getResources().getString(R.string.app_meinfo_sex)));
        tvStringInfoPhone = findViewById(R.id.tv_string_meinfo_phone);
        tvStringInfoPhone.setText(PublicApplication.resourceText.getString("app_meinfo_phone", getResources().getString(R.string.app_meinfo_phone)));
        tvStringInfoTishi = findViewById(R.id.tv_string_meinfo_tishi);
        tvStringInfoTishi.setText(PublicApplication.resourceText.getString("app_meinfo_tizhi", getResources().getString(R.string.app_meinfo_tizhi)));
    }

    private void initContent() {
        tvName = findViewById(R.id.tv_name);
        tvPhone = findViewById(R.id.tv_phone);
        tvSex = findViewById(R.id.tv_sex);
        tvConectKefu = findViewById(R.id.tv_connect_kefu);
        tvConectKefu.setText(PublicApplication.resourceText.getString("app_meinf_kefu", getResources().getString(R.string.app_meinf_kefu)));
        tvConectKefu.setOnClickListener(this);
        if (data1 != null) {
            UserInfoBean.DataBean dataBean = data1;
            switch (dataBean.getSex()) {
                case 1:
                    tvSex.setText(PublicApplication.resourceText.getString("app_man", getResources().getString(R.string.app_man)));
                    break;
                case 2:
                    tvSex.setText(PublicApplication.resourceText.getString("app_women", getResources().getString(R.string.app_women)));
                    break;
                case 3:
                    tvSex.setText(PublicApplication.resourceText.getString("app_unkown", getResources().getString(R.string.app_unkown)));
                    break;
            }

            tvPhone.setText(dataBean.getMobile());
            tvName.setText(dataBean.getRealName());
        }
    }

    private void initTitle() {
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(PublicApplication.resourceText.getString("app_meinfo", getResources().getString(R.string.app_meinfo)));
        ivBack = findViewById(R.id.iv_back);
        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_go_back",""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_go_back))
                .error(getResources().getDrawable(R.drawable.img_go_back))
                .into(ivBack);
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_connect_kefu:
                if (StrUtils.isEmpty(PublicApplication.loginInfo.getString("token", ""))) {
                    startActivity(new Intent(MeInfoActivity.this, LoginActivity.class));
                } else {
                    if (StrUtils.isEmpty(PublicApplication.loginInfo.getString("ServiceTel", "")))
                        getPersonalInformation();
                    else
                        showPhonePopupwindow();
                }
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
            case R.id.tv_cancel:
                if (mPopupWindow != null)
                    mPopupWindow.dismiss();
                break;
            case R.id.ll_bg:
                if (mPopupWindow != null)
                    mPopupWindow.dismiss();
                break;
        }
    }

    /**
     * 拨打服务电话
     */
    private void callService() {
        if (!StrUtils.isEmpty(PublicApplication.loginInfo.getString("ServiceTel", "")))
            Utlis.call(this, PublicApplication.loginInfo.getString("ServiceTel", ""));
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
                                showToast(data.get_metadata().getMessage(), 3);
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

    @Override
    public void onPause() {
        super.onPause();
        if (mCompositeDisposable != null)
            mCompositeDisposable.clear();
        dismissDialog();
    }
}
