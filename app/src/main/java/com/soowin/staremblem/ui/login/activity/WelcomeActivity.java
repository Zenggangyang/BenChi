package com.soowin.staremblem.ui.login.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.soowin.staremblem.R;
import com.soowin.staremblem.ui.index.activity.MainActivity;

import com.soowin.staremblem.ui.index.bean.LoadPageHtmlBean;
import com.soowin.staremblem.ui.index.bean.SourceupdateBean;
import com.soowin.staremblem.ui.index.mModel.MeModel;
import com.soowin.staremblem.ui.login.bean.ResourceTextBean;
import com.soowin.staremblem.utils.BaseActivity;
import com.soowin.staremblem.utils.ImageUtils;
import com.soowin.staremblem.utils.PublicApplication;

import java.io.File;
import java.util.List;

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
 * 欢迎页面
 */
public class WelcomeActivity extends BaseActivity implements View.OnClickListener {
    private CountDownTimer cdtimer;
    private TextView tvTime;
    private ImageView ivWelcome;
    private TextView tvBatchNumber;

    //权限部分回掉
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 102;
    //异步请求控制器
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        init();
    }

    private void init() {
        PublicApplication.loginInfo.edit().putString("device-name", android.os.Build.MODEL).apply();
    }

    /**
     * 初始化权限
     */
    private void initPermissions() {
        /**android 6.0 权限申请**/
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //请求权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET}, MY_PERMISSIONS_REQUEST_CAMERA);
            //判断是否需要 向用户解释，为什么要申请该权限
//            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS);
        } else {
            initView();
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
                showToast(PublicApplication.resourceText.getString("app_welcome_miss_permission", getResources().getString(R.string.app_welcome_miss_permission)),
                        3);
            initView();
        }
    }

    private void initView() {
        tvTime = findViewById(R.id.tv_time);
        ivWelcome = findViewById(R.id.iv_welcome);
        tvBatchNumber = findViewById(R.id.tv_batch_number);
        tvTime.setText(PublicApplication.resourceText.getString("app_welcome_skip", getResources().getString(R.string.app_welcome_skip)));
        tvBatchNumber.setText(PublicApplication.resourceText.getString("app_welcome_batch_number", getResources().getString(R.string.app_welcome_batch_number)));

        getQidong();
    }

    /**
     * 获取欢迎页
     */
    private void getQidong() {
        ivWelcome.setLayoutParams(new LinearLayout.LayoutParams(this.wdith, 36 * this.wdith / 25));
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = MeModel.LoadPageHtml("kv");
                if (result != null)
                    emitter.onNext(result);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<String, LoadPageHtmlBean>() {
                    @Override
                    public LoadPageHtmlBean apply(String s) throws Exception {
                        Gson gson = new Gson();
                        return gson.fromJson(s, LoadPageHtmlBean.class);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoadPageHtmlBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (mCompositeDisposable != null)
                            mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(LoadPageHtmlBean data) {
                        switch (data.get_metadata().getCode()) {
                            case 200:
                                ivWelcome.setVisibility(View.VISIBLE);
                                Glide.with(WelcomeActivity.this)
                                        .load(data.getData().getPageHtmlUrl())
                                        .asBitmap()
                                        .error(R.drawable.img_welcome_ad)
                                        .fitCenter()
                                        .into(ivWelcome);
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getDataCheck();
                        showToast(PublicApplication.resourceText.getString("app_error_message", getResources().getString(R.string.app_error_message)), 3);
                    }

                    @Override
                    public void onComplete() {
                        getDataCheck();
                    }
                });
    }

    /**
     * 获取资源数据检测
     */
    private void getDataCheck() {
        getDataDownload();
        /*Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = MeModel.sourceupdate();
                if (result != null)
                    emitter.onNext(result);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<String, SourceupdateBean>() {
                    @Override
                    public SourceupdateBean apply(String s) throws Exception {
                        Gson gson = new Gson();
                        return gson.fromJson(s, SourceupdateBean.class);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SourceupdateBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (mCompositeDisposable != null)
                            mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(SourceupdateBean data) {
                        switch (data.get_metadata().getCode()) {
                            case 200:
                                if (data.getData().getIsupdate() == 1)
                                    getDataDownload();
                                else
                                    initGoto();
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        initGoto();
                    }

                    @Override
                    public void onComplete() {
                    }
                });*/
    }

    /**
     * 获取资源数据下载
     */
    private void getDataDownload() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = MeModel.sourcelist();
                if (result != null)
                    emitter.onNext(result);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<String, ResourceTextBean>() {
                    @Override
                    public ResourceTextBean apply(String s) throws Exception {
                        Gson gson = new Gson();
                        return gson.fromJson(s, ResourceTextBean.class);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResourceTextBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (mCompositeDisposable != null)
                            mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(ResourceTextBean data) {
                        switch (data.get_metadata().getCode()) {
                            case 200:
                                saveResource(data.getData());
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        initGoto();
                    }

                    @Override
                    public void onComplete() {
                        initGoto();
                    }
                });
    }

    /**
     * 保存资源
     *
     * @param data
     */
    private void saveResource(ResourceTextBean.DataBean data) {
        for (int i = 0; i < data.getTexts().getResourceList().size(); i++) {
            PublicApplication.resourceText.edit().putString(data.getTexts().getResourceList().get(i).getKey(), data.getTexts().getResourceList().get(i).getValue()).apply();
        }
        for (int i = 0; i < data.getImages().getResourceList().size(); i++) {
            PublicApplication.resourceText.edit().putString(data.getTexts().getResourceList().get(i).getKey(), data.getTexts().getResourceList().get(i).getValue()).apply();
        }
//        getImage(data.getImages().getResourceList());
    }

    /**
     * 读秒下一步
     */
    private void initGoto() {
        tvTime.setVisibility(View.VISIBLE);
        tvTime.setOnClickListener(this);

        cdtimer = new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvTime.setText(PublicApplication.resourceText.getString("app_welcome_skip", getResources().getString(R.string.app_welcome_skip)) + millisUntilFinished / 1000 + "s");
            }

            public void onFinish() {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        };
        cdtimer.start();

        File externalDir = Environment.getExternalStorageDirectory();
        //头像地址
        File path = new File(externalDir.getAbsolutePath() + "/StarEmblem/Images");
        if (!path.exists()) { // 判断目录是否存在
            if (path.mkdirs()) {
                Log.e("dbMethond", "mkdirs-->true");
            }// 创建目录
        }
        PublicApplication.pathAvatar = path + "/heard.jpg";
    }

    @Override
    protected void onResume() {
        super.onResume();
        initPermissions();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (cdtimer != null)
            cdtimer.cancel();
        if (mCompositeDisposable != null)
            mCompositeDisposable.clear();
    }

    @Override
    protected void onDestroy() {
        if (cdtimer != null) {
            cdtimer.cancel();
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_time:
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
                break;
        }
    }

    /**
     * 获取图片
     */
    public void getImage(final List<ResourceTextBean.DataBean.ResourceListBean> data) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                final String result = "";
                for (int i = 0; i < data.size(); i++) {
                    ImageUtils.donwloadImg(WelcomeActivity.this,
                            data.get(i).getValue(),
                            data.get(i).getKey(),
                            new ImageUtils.DonwloadImgLinener() {
                                @Override
                                public void onDonwloadImgLinener(boolean isOk) {

                                }
                            });
                }
                if (result != null)
                    emitter.onNext(result);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (mCompositeDisposable != null)
                            mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(String data) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        initGoto();
                    }

                    @Override
                    public void onComplete() {
                        initGoto();
                    }
                });
    }
}
