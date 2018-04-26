package com.soowin.staremblem.ui.login.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.soowin.staremblem.R;
import com.soowin.staremblem.ui.index.activity.MainActivity;
import com.soowin.staremblem.ui.index.bean.BaseBean;
import com.soowin.staremblem.ui.login.bean.LoginBean;
import com.soowin.staremblem.ui.login.mModel.LoginModel;
import com.soowin.staremblem.utils.BaseActivity;
import com.soowin.staremblem.utils.PublicApplication;
import com.soowin.staremblem.utils.ScreenManager;
import com.soowin.staremblem.utils.StrUtils;
import com.soowin.staremblem.utils.Utlis;

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
 * 完善信息页面
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    //标题栏
    private TextView tvTopTitle;
    private ImageView ivTopBack;

    private EditText etRegisterName;

    private LinearLayout llRegisterMale,llRegisterFemale;
    private RadioButton rbRegisterMale,rbRegisterFemale;
    private TextView btnRegisterLogin;
    private ImageView ivIconPhone;

    private TextView  tvNameView;
    private boolean isSuccess;
    private String mobile;
    private String sex = "3";
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();//异步请求控制器
    private TextWatcher twName = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0) {
                tvNameView.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    //静态文字
    private TextView tvLoginStart,tvLoginStartEnglish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ScreenManager.getScreenManager().pushActivity(this);
        initView();
        initData();
    }

    private void initData() {
        mobile = getIntent().getStringExtra("phone");

    }

    private void initView() {
        initTitle();
        initContent();
    }

    private void initContent() {
        llRegisterMale = findViewById(R.id.ll_register_male);
        llRegisterFemale = findViewById(R.id.ll_register_female);
        etRegisterName = findViewById(R.id.et_register_name);
        rbRegisterMale = findViewById(R.id.rb_register_male);
        rbRegisterFemale = findViewById(R.id.rb_register_female);

        tvNameView =  findViewById(R.id.tv_name_view);
        etRegisterName.addTextChangedListener(twName);
        etRegisterName.setFilters(new InputFilter[]{Utlis.typeFilter});
        llRegisterMale.setOnClickListener(this);
        llRegisterFemale.setOnClickListener(this);
        btnRegisterLogin = findViewById(R.id.btn_register_login);
        btnRegisterLogin.setOnClickListener(this);

        //静态文字
        tvLoginStart = findViewById(R.id.tv_login_start);
        tvLoginStartEnglish = findViewById(R.id.tv_login_start_english);
        tvLoginStart.setText(PublicApplication.resourceText.getString("app_login_start", getResources().getString(R.string.app_login_start)));
        tvLoginStartEnglish.setText(PublicApplication.resourceText.getString("app_login_start_english", getResources().getString(R.string.app_login_start_english)));

        ivIconPhone = findViewById(R.id.iv_icon_phone);
        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_nav_n_03",""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_nav_n_03))
                .error(getResources().getDrawable(R.drawable.img_nav_n_03))
                .into(ivIconPhone);

    }

    private void initTitle() {
        tvTopTitle = findViewById(R.id.tv_title);
        ivTopBack = findViewById(R.id.iv_back);
        ivTopBack.setVisibility(View.VISIBLE);
        tvTopTitle.setText(PublicApplication.resourceText.getString("app_complete_information", getResources().getString(R.string.app_complete_information)));
        ivTopBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_top_back:
//                if (StrUtils.isEmpty(PublicApplication.loginInfo.getString("token", ""))) {
//                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
//                }
                finish();
                PublicApplication.loginInfo.edit().putString("token", "").apply();
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                break;
            case R.id.ll_register_male:
                rbRegisterMale.setChecked(true);
                rbRegisterFemale.setChecked(false);
                sex = "1";
                break;
            case R.id.ll_register_female:
                rbRegisterMale.setChecked(false);
                rbRegisterFemale.setChecked(true);
                sex = "2";
                break;
            case R.id.btn_register_login:
                loginInfo();
                break;
        }

    }

    /**
     * 用户完善登陆信息
     */
    private void loginInfo() {
        final String name = etRegisterName.getText().toString();
        if (TextUtils.isEmpty(name)){
            tvNameView.setVisibility(View.VISIBLE);
            return;
        }

//发送登录的请求
        showDialog();
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = null;
                result = LoginModel.loginInfo(mobile,name, sex,"");
                if (result != null)
                    emitter.onNext(result);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<String, BaseBean>() {
                    @Override
                    public BaseBean apply(String s) throws Exception {
                        Gson gson = new Gson();
                        BaseBean dataBean = gson.fromJson(s,
                                BaseBean.class);
                        return dataBean;
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
                    public void onNext(BaseBean dataBean) {
                        switch (dataBean.get_metadata().getCode()){
                            case 200:
                                isSuccess = true;
                                finish();
                                break;
                            case 500:
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

//    @Override
//    public void finish() {
//        super.finish();
//        if (!isSuccess){
//            //清除token,跳转到主页面
//            PublicApplication.loginInfo.edit().putString("token", "").apply();
//            startActivity(new Intent(RegisterActivity.this,MainActivity.class));
//        }
//    }

    @Override
    public void onPause() {
        super.onPause();
        if (mCompositeDisposable != null)
            mCompositeDisposable.clear();
        dismissDialog();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            PublicApplication.loginInfo.edit().putString("token", "").apply();
            startActivity(new Intent(RegisterActivity.this,MainActivity.class));
        }
        return false;
    }

}
