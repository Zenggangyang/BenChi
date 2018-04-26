package com.soowin.staremblem.ui.index.activity;

import android.content.Intent;
import android.content.res.Resources;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.soowin.staremblem.R;
import com.soowin.staremblem.ui.index.adapter.CouponSelectListAdapter;
import com.soowin.staremblem.ui.index.bean.CouponListBean;
import com.soowin.staremblem.ui.index.mModel.MeModel;
import com.soowin.staremblem.ui.login.activity.LoginActivity;
import com.soowin.staremblem.utils.BaseActivity;
import com.soowin.staremblem.utils.PublicApplication;
import com.soowin.staremblem.utils.ScreenManager;
import com.soowin.staremblem.utils.StrUtils;

import java.io.Serializable;
import java.util.ArrayList;
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
 * 选择卡卷页
 */
public class SelectCouponActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = SelectCouponActivity.class.getSimpleName();
    /*title*/
    private TextView tvTitle;
    private ImageView ivBack;
    /*Content*/
    private RecyclerView rvSelectCoupon;

    private int width;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();//异步请求控制器

    //    未过期数据
    List<CouponListBean.DataBean> noOutDate = new ArrayList<>();
    CouponSelectListAdapter couponSelectListAdapter;
    private TextView tvCommit;
    List<CouponListBean.DataBean> data1 = new ArrayList<>();
    private String OrderId = "";
    private String CardNos = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_coupon);

        OrderId = getIntent().getStringExtra(CouponListActivity.ORDERID);
        CardNos = getIntent().getStringExtra("popos");
        Log.e(TAG, "onCreate: " + CardNos);
        if (StrUtils.isEmpty(PublicApplication.loginInfo.getString("token", ""))) {
            startActivity(new Intent(SelectCouponActivity.this, LoginActivity.class));
        } else {
            initWindow();
            initView();
        }

    }

    /*初始化View*/
    private void initView() {
        initTitle();
        initContent();
        getData();
    }

    /*初始化Content*/
    private void initContent() {
        rvSelectCoupon = findViewById(R.id.rv_select_coupon);
        couponSelectListAdapter = new CouponSelectListAdapter(this, width / 7 * 6);
        rvSelectCoupon.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvSelectCoupon.setAdapter(couponSelectListAdapter);
        tvCommit = findViewById(R.id.tv_Commit);
        tvCommit.setOnClickListener(this);
    }

    private void initTitle() {
        tvTitle = findViewById(R.id.tv_title);
        ivBack = findViewById(R.id.iv_back);
        //图片资源初始化
        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_go_back", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_go_back))
                .error(getResources().getDrawable(R.drawable.img_go_back))
                .into(ivBack);
        ivBack.setOnClickListener(this);
        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setText(PublicApplication.resourceText.getString("app_select_coupon", getResources().getString(R.string.app_select_coupon)));

    }

    private void initWindow() {
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
//        float density = dm.density;
        width = dm.widthPixels;
//        int height = dm.heightPixels;
//        wm = this.getWindowManager();
//        // 重设高度
//        width = wm.getDefaultDisplay().getWidth();

    }

    private void getData() {
        showDialog();
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = MeModel.MyCardList("0", OrderId);
                if (result != null)
                    emitter.onNext(result);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<String, CouponListBean>() {
                    @Override
                    public CouponListBean apply(String s) throws Exception {
                        Gson gson = new Gson();
                        return gson.fromJson(s, CouponListBean.class);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CouponListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (mCompositeDisposable != null)
                            mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(CouponListBean data) {
                        switch (data.get_metadata().getCode()) {
                            case 200:
                                noOutDate = data.getData();
                                if (data.getData().size() > 0) {
                                    data1 = new ArrayList<>();
                                    data1.addAll(data.getData());
                                    couponSelectListAdapter.setData(data1);

                                    if (CardNos != null && !StrUtils.isEmpty(CardNos)) {
                                        couponSelectListAdapter.setChecked(CardNos);
                                        couponSelectListAdapter.notifyDataSetChanged();
                                    } else {
                                       /* try {
                                            String popos = "onNext: " + getIntent().getStringExtra("popos");
                                            Log.e(TAG, popos);
                                            Log.e(TAG, "onNext: popos" + popos);
                                        } catch (Exception e) {
                                            Log.e(TAG, "onNext: 出现一场v" + e.getMessage());
                                        }*/
                                        Log.e(TAG, "onNext: cardNo出现异常" );
                                    }
                                } else {
                                    showToast("暂无数据", 3);
                                }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_Commit:

                Intent intent = new Intent();
                int position = couponSelectListAdapter.GetPosition();
                if (position >= 0) {
                    Log.e(TAG, "onClick:选中 " + position);
                    CouponListBean.DataBean dataBean = data1.get(position);
                    setResult(0, intent);
                    intent.putExtra("CouponData", (Serializable) dataBean);
                    intent.putExtra("popos", data1.get(position).getCardNo());
                } else {
                    /*没有选中 点击了确认  postion标记位为-1*/
                    setResult(0, intent);
                    intent.putExtra("popos", "");
                }
                finish();
                break;

        }
    }
}
