package com.soowin.staremblem.ui.index.fragment;


import android.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.soowin.staremblem.R;
import com.soowin.staremblem.ui.index.activity.CityListActivity;
import com.soowin.staremblem.ui.index.activity.ConfirmationOfOrderActivity;
import com.soowin.staremblem.ui.index.activity.CouponListActivity;
import com.soowin.staremblem.ui.index.activity.MainActivity;


import com.soowin.staremblem.ui.index.bean.CouponListBean;

import com.soowin.staremblem.ui.index.activity.MessageListActivity;
import com.soowin.staremblem.ui.index.bean.CreateOrderBena;
import com.soowin.staremblem.ui.index.bean.MessageBean;
import com.soowin.staremblem.ui.index.mModel.MeModel;
import com.soowin.staremblem.utils.BaseActivity;
import com.soowin.staremblem.utils.PublicApplication;
import com.soowin.staremblem.utils.StrUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.lang.reflect.Field;
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
 * 主页
 * Created by hxt on 2018/3/10 0010.
 */

public class IndexFragment extends Fragment implements View.OnClickListener {
    private final String TAG = IndexFragment.class.getCanonicalName();

    private View view;
    MainActivity a;
    //title*****************************************************
    private TextView tvTitle;
    private ImageView ivMenu;
    //content**************************************************
    private RelativeLayout rlService01;
    private RelativeLayout rlService02;
    private RelativeLayout rlService03;
    private RelativeLayout rlService04;

    private ImageView ivBg;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();//异步请求控制器
    private Bundle bundle = new Bundle();
    //订单生成所用数据********************************************
    private CreateOrderBena createOrderData = new CreateOrderBena();
    CouponListBean.DataBean couponData = new CouponListBean.DataBean();
    private String CouponType;
    private ImageView ivIconAir, ivIconBusi, ivIconDaily, ivIconShuttle;
    private ImageView ivRightBack1, ivRightBack2, ivRightBack3, ivRightBack4;

    private View viewService01;
    private View viewService02;
    private View viewService03;
    private View viewService04;

    private View vMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = View.inflate(getActivity(), R.layout.fragment_index, null);
        }
        a = (MainActivity) getActivity();

        a.displayPage = 0;
        initView();
        return view;
    }

    private void initView() {
        initTitle();
        initContent();
    }

    private void initContent() {
        rlService01 = view.findViewById(R.id.rl_service_01);
        rlService02 = view.findViewById(R.id.rl_service_02);
        rlService03 = view.findViewById(R.id.rl_service_03);
        rlService04 = view.findViewById(R.id.rl_service_04);


        viewService01 = view.findViewById(R.id.view_index_service_01);
        viewService02 = view.findViewById(R.id.view_index_service_02);
        viewService03 = view.findViewById(R.id.view_index_service_03);
        viewService04 = view.findViewById(R.id.view_index_service_04);

        vMessage = view.findViewById(R.id.v_message);


        File externalDir = Environment.getExternalStorageDirectory();
        //头像地址
        PublicApplication.path = new File(externalDir.getAbsolutePath() + "/StarEmblem/Images");
        if (!PublicApplication.path.exists()) { // 判断目录是否存在
            if (PublicApplication.path.mkdirs()) {
                Log.e("path", "mkdirs-->true");
            }// 创建目录
        }
        //图片资源初始化
        ivBg = view.findViewById(R.id.iv_bg);
        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_index_bg.9", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_index_bg))
                .error(getResources().getDrawable(R.drawable.img_index_bg))
                .into(ivBg);


        ivIconAir = view.findViewById(R.id.iv_icon_air);
        ivIconBusi = view.findViewById(R.id.iv_icon_busi);
        ivIconDaily = view.findViewById(R.id.iv_icon_daily);
        ivIconShuttle = view.findViewById(R.id.iv_icon_shuttle);

        ivRightBack1 = view.findViewById(R.id.iv_right_back1);
        ivRightBack2 = view.findViewById(R.id.iv_right_back2);
        ivRightBack3 = view.findViewById(R.id.iv_right_back3);
        ivRightBack4 = view.findViewById(R.id.iv_right_back4);
        ImageView[] imageViews = new ImageView[]{ivRightBack1, ivRightBack2, ivRightBack3, ivRightBack4};

        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_icon_air", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_icon_air))
                .error(getResources().getDrawable(R.drawable.img_icon_air))
                .into(ivIconAir);

        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_icon_busi", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_icon_busi))
                .error(getResources().getDrawable(R.drawable.img_icon_busi))
                .into(ivIconBusi);

        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_icon_daily", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_icon_daily))
                .error(getResources().getDrawable(R.drawable.img_icon_daily))
                .into(ivIconDaily);

        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_icon_shuttle", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_icon_shuttle))
                .error(getResources().getDrawable(R.drawable.img_icon_shuttle))
                .into(ivIconShuttle);

        for (int i = 0; i < 4; i++) {
            Glide.with(this)
                    .load(PublicApplication.resourceText.getString("img_icon_b_arror_r", ""))
                    .asBitmap()
                    .placeholder(getResources().getDrawable(R.drawable.img_icon_b_arror_r))
                    .error(getResources().getDrawable(R.drawable.img_icon_b_arror_r))
                    .into(imageViews[i]);
        }

    }

    private void initTitle() {
        tvTitle = view.findViewById(R.id.tv_title);
        ivMenu = view.findViewById(R.id.iv_menu);

        tvTitle.setText(PublicApplication.resourceText.getString("app_name", getResources().getString(R.string.app_name)));
        ivMenu.setVisibility(View.VISIBLE);
        ivMenu.setOnClickListener(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!StrUtils.isEmpty(PublicApplication.loginInfo.getString("token", ""))) {//是否有登陆信息
            initData();
        }
        CouponType = a.getTypes();
        Bundle bundle = getArguments();
        if (bundle != null) {
            /*获取数据类别 是否传递卡券数据*/
            String type = CouponType;
            if (type != null) {
                viewService01.setVisibility(View.VISIBLE);
                viewService02.setVisibility(View.VISIBLE);
                viewService03.setVisibility(View.VISIBLE);
                viewService04.setVisibility(View.VISIBLE);

                rlService01.setOnClickListener(null);
                rlService02.setOnClickListener(null);
                rlService03.setOnClickListener(null);
                rlService04.setOnClickListener(null);
                switch (type) {
                    case "data":
                        Log.e(TAG, "onResume: data");
                        couponData = (CouponListBean.DataBean) bundle.getSerializable(CouponListActivity.COUPONDATA);
                        Log.e(TAG, "onResume: " + couponData.getCardNo());
                        List<Integer> serverType = couponData.getDescription().getServerType();
                        Log.e(TAG, "onResume:serverType   size " + serverType.size());
                        for (int i = 0; i < serverType.size(); i++) {
                            Integer integer = serverType.get(i);
                            switch (integer) {
                                case 1:
                                    rlService01.setOnClickListener(this);
                                    viewService01.setVisibility(View.GONE);
                                    break;
                                case 2:
                                    rlService01.setOnClickListener(this);
                                    viewService01.setVisibility(View.GONE);
                                    break;
                                case 5:
                                    rlService02.setOnClickListener(this);
                                    viewService02.setVisibility(View.GONE);
                                    break;
                                case 3:
                                    rlService03.setOnClickListener(this);
                                    viewService03.setVisibility(View.GONE);
                                    break;
                                case 6:
                                    rlService03.setOnClickListener(this);
                                    viewService03.setVisibility(View.GONE);
                                    break;
                                case 7:
                                    rlService04.setOnClickListener(this);
                                    viewService04.setVisibility(View.GONE);
                                    break;
                            }
                        }
                        break;
                    case "ClearData":
                    /*清空数据*/
                        couponData = new CouponListBean.DataBean();
                        Log.e(TAG, "onResume: ClearData 清空数据");
                        rlService01.setOnClickListener(this);
                        rlService02.setOnClickListener(this);
                        rlService03.setOnClickListener(this);
                        rlService04.setOnClickListener(this);
                        viewService01.setVisibility(View.GONE);
                        viewService02.setVisibility(View.GONE);
                        viewService03.setVisibility(View.GONE);
                        viewService04.setVisibility(View.GONE);

                        break;
                }
            }

        } else {
        /*初次进入bundle为null*/
            couponData = new CouponListBean.DataBean();
            Log.e(TAG, "onResume: bundle为null 清空数据");
            rlService01.setOnClickListener(this);
            rlService02.setOnClickListener(this);
            rlService03.setOnClickListener(this);
            rlService04.setOnClickListener(this);
            viewService01.setVisibility(View.GONE);
            viewService02.setVisibility(View.GONE);
            viewService03.setVisibility(View.GONE);
            viewService04.setVisibility(View.GONE);
        }
//        createOrderData = new CreateOrderBena();
    }

    /**
     * 获取消息列表
     */
    private void initData() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = MeModel.MessageList();

                if (result != null)
                    emitter.onNext(result);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<String, MessageBean>() {
                    @Override
                    public MessageBean apply(String s) throws Exception {
                        Gson gson = new Gson();
                        return gson.fromJson(s, MessageBean.class);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MessageBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (mCompositeDisposable != null)
                            mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(MessageBean baseBean) {
                        switch (baseBean.get_metadata().getCode()) {
                            case 200:
                                bundle.putSerializable("bean", baseBean);
                                boolean b = false;
                                for (int i = 0; i < baseBean.getData().size(); i++) {
                                    if (baseBean.getData().get(i).getState() == 0) {
                                        b = true;
                                    }
                                }
                                if (b)
                                    vMessage.setVisibility(View.VISIBLE);
                                else
                                    vMessage.setVisibility(View.GONE);
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 这段可以解决fragment嵌套fragment会崩溃的问题
     */
    @Override
    public void onDetach() {
        super.onDetach();
        try {
            //参数是固定写法
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.iv_menu:
                startActivity(new Intent(getActivity(), MessageListActivity.class).putExtras(bundle));
                break;
            case R.id.rl_service_01://接送机
                createOrderData.setServiceType(1);
                intent.setClass(getActivity(), CityListActivity.class);
                Bundle mbundle = new Bundle();
                /*判断CouponType  是否传递CouponData*/
                switch (CouponType) {
                    case "data":
                        mbundle.putSerializable(CouponListActivity.COUPONDATA, couponData);// 传入卡券
                        mbundle.putSerializable(ConfirmationOfOrderActivity.CREATE_ORDER_CODE, createOrderData);
                        break;
                    case "ClearData"://不传CouponData
                        /*传递订单数据Data*/
                        mbundle.putSerializable(ConfirmationOfOrderActivity.CREATE_ORDER_CODE, createOrderData);
                        break;
                }
                intent.putExtra("data", mbundle);
                startActivity(intent);
                break;
            case R.id.rl_service_02://商务接送
                createOrderData.setServiceType(5);
                Intent intent1 = new Intent(getActivity(), CityListActivity.class);
                Bundle mbundle1 = new Bundle();
                /*判断CouponType  是否传递CouponData*/
                switch (CouponType) {
                    case "data":
                        mbundle1.putSerializable(CouponListActivity.COUPONDATA, couponData);// 传入卡券
                        mbundle1.putSerializable(ConfirmationOfOrderActivity.CREATE_ORDER_CODE, createOrderData);
                        break;
                    case "ClearData"://不传CouponData
                        /*传递订单数据Data*/
                        mbundle1.putSerializable(ConfirmationOfOrderActivity.CREATE_ORDER_CODE, createOrderData);
                        break;
                }
                intent1.putExtra("data", mbundle1);
                startActivity(intent1);
                break;
            case R.id.rl_service_03://商务日租
                createOrderData.setServiceType(3);
                Intent intent2 = new Intent(getActivity(), CityListActivity.class);
                Bundle mbundle2 = new Bundle();
                  /*判断CouponType  是否传递CouponData*/
                switch (CouponType) {
                    case "data":
                        mbundle2.putSerializable(ConfirmationOfOrderActivity.CREATE_ORDER_CODE, createOrderData);
                        mbundle2.putSerializable(CouponListActivity.COUPONDATA, couponData);// 传入卡券
                        break;
                    case "ClearData"://不传CouponData
                        mbundle2.putSerializable(ConfirmationOfOrderActivity.CREATE_ORDER_CODE, createOrderData);
                        break;
                }
                intent2.putExtra("data", mbundle2);
                startActivity(intent2);
                break;
            case R.id.rl_service_04://接送服务
                createOrderData.setServiceType(7);
                Intent intent3 = new Intent(getActivity(), CityListActivity.class);
                Bundle mbundle3 = new Bundle();
                /*判断CouponType  是否传递CouponData*/
                switch (CouponType) {
                    case "data":
                        mbundle3.putSerializable(ConfirmationOfOrderActivity.CREATE_ORDER_CODE, createOrderData);
                        mbundle3.putSerializable(CouponListActivity.COUPONDATA, couponData);// 传入卡券
                        break;
                    case "ClearData"://不传CouponData
                        mbundle3.putSerializable(ConfirmationOfOrderActivity.CREATE_ORDER_CODE, createOrderData);
                        break;
                }
                intent3.putExtra("data", mbundle3);
                startActivity(intent3);
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mCompositeDisposable != null)
            mCompositeDisposable.clear();
    }
}
