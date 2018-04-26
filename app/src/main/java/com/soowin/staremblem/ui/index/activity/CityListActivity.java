package com.soowin.staremblem.ui.index.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.soowin.staremblem.R;
import com.soowin.staremblem.ui.index.adapter.CityListAdapter;
import com.soowin.staremblem.ui.index.bean.CouponListBean;
import com.soowin.staremblem.ui.index.bean.CreateOrderBena;
import com.soowin.staremblem.ui.index.mModel.MeModel;
import com.soowin.staremblem.ui.login.activity.LoginActivity;
import com.soowin.staremblem.ui.login.bean.CityListBean;
import com.soowin.staremblem.utils.BaseActivity;
import com.soowin.staremblem.utils.PublicApplication;
import com.soowin.staremblem.utils.ScreenManager;
import com.soowin.staremblem.utils.StrUtils;

import org.greenrobot.eventbus.EventBus;

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
 * 地址选择列表页
 */
public class CityListActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = CityListActivity.class.getCanonicalName();
    private int serviceType = 0;
    //content************************************************
    private String[] EList = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};
    //city信息************************************************
    private String cityCode = "";
    private String cityName = "";
    //city列表************************************************
    private ListView lvCity;
    private CityListAdapter mAdapter;
    private List<List<CityListBean.DataBean>> mData = new ArrayList<>();
    //Title**************************************************
    private ImageView ivBack;
    private TextView tvTitle;
    //异步请求控制器
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    //订单生成所用数据********************************************
    private CreateOrderBena createOrderData;
    /*卡券Data数据*/
    CouponListBean.DataBean couponData;
    private String CardNo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        initTitle();
    }

    private void initTitle() {
        ivBack = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(PublicApplication.resourceText.getString("app_city_title", getResources().getString(R.string.app_city_title)));
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(this);

        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_go_back", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_go_back))
                .error(getResources().getDrawable(R.drawable.img_go_back))
                .into(ivBack);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //初始化数据
        Bundle b = getIntent().getBundleExtra("data");
        createOrderData = (CreateOrderBena) b.getSerializable(ConfirmationOfOrderActivity.CREATE_ORDER_CODE);
        couponData = (CouponListBean.DataBean) b.getSerializable(CouponListActivity.COUPONDATA);
        if (createOrderData != null)
            serviceType = createOrderData.getServiceType();
        if (couponData != null) {
            CardNo = couponData.getCardNo();
        }
        if (StrUtils.isEmpty(PublicApplication.loginInfo.getString("token", ""))) {
            startActivity(new Intent(CityListActivity.this, LoginActivity.class));
        } else {
            initData();
            initView();
        }
    }

    private void initData() {
        if (mData.size() < 1)
            getData();
    }

    private void initView() {
        lvCity = findViewById(R.id.lv_city);

        mAdapter = new CityListAdapter(this, new CityListAdapter.OnCityClickLisener() {
            @Override
            public void onItemClick(CityListBean.DataBean data) {
                try {
                    if (data.getIsSelect().equals("1")) {
                        cityCode = data.getCode();
                        cityName = data.getName();
                        createOrderData.setCityCode(cityCode);
                        createOrderData.setCityName(cityName);

                        gotoNext();
                    } else {
                        showToast(getResources().getString(R.string.app_city_error_no), 3);
                    }
                } catch (NullPointerException e) {
                    showToast(getResources().getString(R.string.app_error_message), 3);
                }

            }
        });
        mAdapter.setData(mData);
        lvCity.setAdapter(mAdapter);
    }

    /**
     * 获取城市信息
     */
    public void getData() {
        showDialog();
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = MeModel.CityList(CardNo, serviceType + "");
                if (result != null)
                    emitter.onNext(result);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<String, CityListBean>() {
                    @Override
                    public CityListBean apply(String s) throws Exception {
                        Gson gson = new Gson();
                        return gson.fromJson(s, CityListBean.class);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CityListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (mCompositeDisposable != null)
                            mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(CityListBean data) {
                        switch (data.get_metadata().getCode()) {
                            case 200:
                                mData.clear();
                                for (int i = 0; i < 26; i++) {
                                    List<CityListBean.DataBean> itemData = new ArrayList<>();
                                    for (int j = 0; j < data.getData().size(); j++) {
                                        if (EList[i].equals(data.getData().get(j).getSign()))
                                            itemData.add(data.getData().get(j));

                                    }
                                    if (itemData.size() > 0)
                                        mData.add(itemData);
                                }
                                mAdapter.notifyDataSetChanged();
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                setClearData();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setClearData();
    }

    /*清空之前的CouponData*/
    private void setClearData() {

        EventBus.getDefault().post("ClearData");
        finish();
    }

    /**
     * 下一步
     */
    private void gotoNext() {
        if (StrUtils.isEmpty(cityCode))
            showToast(getResources().getString(R.string.app_city_error), 3);
        else {
            PublicApplication.loginInfo.edit().putString("RealName", createOrderData.getCityName()).apply();
            Intent intent = new Intent();
            Bundle mbundle = new Bundle();
            mbundle.putSerializable(ConfirmationOfOrderActivity.CREATE_ORDER_CODE, createOrderData);
            mbundle.putSerializable(CouponListActivity.COUPONDATA, couponData);
            intent.putExtra("data", mbundle);
            switch (serviceType) {
                case 1://接送机
                    intent.setClass(CityListActivity.this, TransferActivity.class);
                    break;
                case 2://接送机
                    intent.setClass(CityListActivity.this, TransferActivity.class);
                    break;
                case 3://商务日租
                    intent.setClass(CityListActivity.this, DailyRentalActivity.class);
                    break;
                case 5://商务接送
                    intent.setClass(CityListActivity.this, ShuttleABActivity.class);
                    break;
                case 6://商务日租
                    intent.setClass(CityListActivity.this, DailyRentalActivity.class);
                    break;
                case 7://接送服务
                    intent.setClass(CityListActivity.this, ShuttleABCActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mCompositeDisposable != null)
            mCompositeDisposable.clear();
        dismissDialog();
    }
}
