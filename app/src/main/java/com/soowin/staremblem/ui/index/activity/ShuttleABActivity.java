package com.soowin.staremblem.ui.index.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.soowin.staremblem.R;
import com.soowin.staremblem.ui.index.adapter.CarListAdapter;
import com.soowin.staremblem.ui.index.adapter.RemarksAdapter;
import com.soowin.staremblem.ui.index.bean.CarListBean;
import com.soowin.staremblem.ui.index.bean.CouponListBean;
import com.soowin.staremblem.ui.index.bean.CreateOrderBean;
import com.soowin.staremblem.ui.index.bean.CreateOrderBena;
import com.soowin.staremblem.ui.index.bean.RemarksBean;
import com.soowin.staremblem.ui.index.bean.UserInfoBean;
import com.soowin.staremblem.ui.index.mModel.MeModel;
import com.soowin.staremblem.utils.BaseActivity;
import com.soowin.staremblem.utils.DataUtils;
import com.soowin.staremblem.utils.MyDateUtils;
import com.soowin.staremblem.utils.PublicApplication;
import com.soowin.staremblem.utils.RegularUtil;
import com.soowin.staremblem.utils.ScreenManager;
import com.soowin.staremblem.utils.StrUtils;
import com.soowin.staremblem.utils.Utlis;
import com.soowin.staremblem.utils.myView.CustomToast;
import com.soowin.staremblem.utils.myView.MyDatePicker;
import com.soowin.staremblem.utils.myView.MyGridView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
 * 商务接送页
 */
public class ShuttleABActivity extends BaseActivity implements View.OnClickListener, MyDatePicker.TimePickerInterface {
    private static final String TAG = ShuttleABActivity.class.getSimpleName();
    /*服务类型*/
    private int serviceType = 5;
    private String CardNo = "";//卡券的卡号
    private String CityCode = "";//城市编码
    private int ProductID;//产品ID   （汽车列表获取）
    private int CarModelID; //车型ID    汽车列表获取
    private String RentDate = "";   //上车时间
    private String CarAddressFrom = "";//上车地址
    private String CarAddressTo = "";//下车地址
    private String PassengerName;//乘客姓名
    private String PassengerTel;//乘客电话
    private String Remark = "";   //备注

    private String CardValue = "";
    private String CardType = "";
    /*title*/
    private ImageView ivTopBack;//返回按钮
    private TextView tvTopTitle;//标题

    private ImageView ivCarBg;
    private RelativeLayout rlCar;


    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();//异步请求控制器

    /*ViewPager*/
    private ViewPager vpMvp;
    private CarListAdapter carPagerAdapter;

    List<CarListBean.DataBean> CarListData = new ArrayList<>();
    private List<CarListBean.DataBean> typeData1 = new ArrayList<>();
    private List<CarListBean.DataBean> mData = new ArrayList<>();
    CarListBean.DataBean CurrentData = new CarListBean.DataBean();
    //界面信息**************************************************
    private WindowManager wm;
    private int width;

    /*车型 车的类别*/
    private TextView tvSeries;              //车的类别
    private TextView tvModel;               //车辆型号
    private EditText etTel;                 //手机
    private EditText etName;                //姓名
    private TextView tvCity;                //城市
    /*出发地点*/
    private LinearLayout llPickUp;          //出发地点
    private TextView tvTransportTime;       //用车时限
    private TextView tvPrice;              //总价格 接口获取
    private TextView tvPickUpPoint;        //出发地点
    private TextView tvPickUpTime;         //上车时间
    private String getAddress;             //地址回掉回来的地址
    private LinearLayout llDestination;    //目的地
    private TextView tvDestination;        //目的地
    private LinearLayout llPickUpPoint;    //上车地点

    private Context mContext;
    private LinearLayout llParameter01;     //车辆参数
    private TextView tvParameterName01;     //参数名称
    private TextView tvParameterValue01;    //参数值
    private LinearLayout llParameter02;     //车辆参数
    private TextView tvParameterName02;     //参数名称
    private TextView tvParameterValue02;    //参数值
    private LinearLayout llParameter03;     //车辆参数
    private TextView tvParameterName03;     //参数名称
    private TextView tvParameterValue03;    //参数值
    private LinearLayout llParameter04;     //车辆参数
    private TextView tvParameterName04;     //参数名称
    private TextView tvParameterValue04;    //参数值
    private ImageView ivInfo;                //特色描述
    private TextView tvLastStep;            //上一步
    private TextView tvNextStep;            //下一步

    /*备注*/
    private TagFlowLayout tflShuttleRemarks;//备注的流布局
    private PopupWindow mWindow;
    private View contentView;
    private MyGridView gvPopItem;
    private RemarksAdapter mRemarksAdapter;
    //备注
    private List<RemarksBean> allRemarkItemList = new ArrayList<>();
    private List<RemarksBean> seletedRemarkItemList = new ArrayList<>();
    private List<RemarksBean> lastSeletedRemarkItemList = new ArrayList<>();//记录要选择之前的状态
    //订单生成所用数据********************************************
    private CreateOrderBena createOrderData;
    /*卡券Data数据*/
    CouponListBean.DataBean couponData;


    /*显示和隐藏*/

    private TextView tvPickUpTimeView;      //上车时间选中的时候 把llpuckUpGone掉   没有选中上车时间View显示
    private LinearLayout llPickUpTimeView;    //上车地点
    private LinearLayout tvPickUpPointView;     //上车地点 显示与隐藏
    private LinearLayout tvDestinationView;     //目的地显示与隐藏
    private LinearLayout llPassenger;       //顾客
    private TextView viewTel;                    //手机
    private View viewName;                   //姓名

    /*静态显示文本*/
    private TextView tvStringServiceName;
    private TextView tvStringNameTishi;
    private TextView tvStringSelector;
    private TextView tvModelIntroduce;
    private TextView tvStringLimitTime;
    private TextView tvStringSendMessage;
    private TextView tvStringRemark;
    private TextView tvStringPrompt;
    private TextView tvStringPormptContent;

    private TextView tvStringHotLine;
    private TextView tvStringHotLineC;
    private TextView tvStringHotLineNumber;

    private int aboardY = 0;//上车时间
    private int aboardM = 0;
    private int aboardD = 0;
    private int aboardH = 0;
    private int aboardm = 0;
    private int aboardS = 0;

    private ImageView ivPickuP;

    private String CouponDate = "";
    private TagAdapter tflAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuttle);
        mContext = this;
        Bundle b = getIntent().getBundleExtra("data");
        createOrderData = (CreateOrderBena) b.getSerializable(ConfirmationOfOrderActivity.CREATE_ORDER_CODE);
        couponData = (CouponListBean.DataBean) b.getSerializable(CouponListActivity.COUPONDATA);
        if (couponData != null) {
            CardNo = couponData.getCardNo();
            CardValue = String.valueOf(couponData.getCardValue());
            CardType = String.valueOf(couponData.getCardType());
            CouponDate = couponData.getValidDate();
        } else {
            CardNo = "";
            CardValue = "0";
            CardType = "0";
        }
        initView();
        getData();
        if (StrUtils.isEmpty(PublicApplication.loginInfo.getString("ServiceTel", "")))
            getPersonalInformation();
        else {

            String serviceTel = PublicApplication.loginInfo.getString("ServiceTel", "");
            StringBuffer buf = new StringBuffer(serviceTel);
            String aString = "-";
            buf.insert(3, aString);
            buf.insert(7, aString);
            tvStringHotLineNumber.setText(buf);
        }
    }

    /*静态展示文本*/
    private void initStaticTextDisplay() {
        tvStringServiceName = findViewById(R.id.tv_service_name);
        tvStringServiceName.setText(PublicApplication.resourceText.getString("app_index_service_02_c", getResources().getString(R.string.app_index_service_02_c)));
        tvStringNameTishi = findViewById(R.id.tv_string_name_tishi);
        tvStringNameTishi.setText(PublicApplication.resourceText.getString("app_shuttleAB_service_introduce", getResources().getString(R.string.app_shuttleAB_service_introduce)));
        tvStringSelector = findViewById(R.id.tv_selecter);
        tvStringSelector.setText(PublicApplication.resourceText.getString("app_transfer_selecter", getResources().getString(R.string.app_transfer_selecter)));
        tvModelIntroduce = findViewById(R.id.tv_model_introduce);
        tvModelIntroduce.setText(PublicApplication.resourceText.getString("app_transfer_model_introduce", getResources().getString(R.string.app_transfer_model_introduce)));
        tvStringLimitTime = findViewById(R.id.tv_string_limit_time);
        tvStringLimitTime.setText(PublicApplication.resourceText.getString("app_transfer_time", getResources().getString(R.string.app_transfer_time)));
        tvStringSendMessage = findViewById(R.id.tv_string_send_message);
        tvStringSendMessage.setText(PublicApplication.resourceText.getString("app_shuttleAB_form_send_information", getResources().getString(R.string.app_shuttleAB_form_send_information)));
        tvStringRemark = findViewById(R.id.tv_string_remark);
        tvStringRemark.setText(PublicApplication.resourceText.getString("app_transfer_form_remarks", getResources().getString(R.string.app_transfer_form_remarks)));
        tvStringPrompt = findViewById(R.id.tv_String_prompt);
        tvStringPrompt.setText(PublicApplication.resourceText.getString("app_transfer_prompt", getResources().getString(R.string.app_transfer_prompt)));

        tvStringPormptContent = findViewById(R.id.tv_string_prompt_content);
        tvStringPormptContent.setText(PublicApplication.resourceText.getString("app_transfer_prompt_content", getResources().getString(R.string.app_transfer_prompt_content)));

        tvStringHotLine = findViewById(R.id.tv_string_hotline);
        tvStringHotLine.setText(PublicApplication.resourceText.getString("app_transfer_hotline", getResources().getString(R.string.app_transfer_hotline)));
        tvStringHotLineC = findViewById(R.id.tv_string_hotline_c);
        tvStringHotLineC.setText(PublicApplication.resourceText.getString("app_transfer_hotline_c", getResources().getString(R.string.app_transfer_hotline_c)));
        tvStringHotLineNumber = findViewById(R.id.tv_string_hotline_number);

        ivPickuP = findViewById(R.id.iv_pick_up);
        //图片资源初始化
        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_go_back",""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_go_back))
                .error(getResources().getDrawable(R.drawable.img_go_back))
                .into(ivTopBack);
        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_icon_down_li",""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_icon_down_li))
                .error(getResources().getDrawable(R.drawable.img_icon_down_li))
                .into(ivPickuP);
    }


    /**
     * 初始化界面信息
     */
    private void initWindow() {
        wm = getWindowManager();
        // 重设高度
        width = wm.getDefaultDisplay().getWidth();
    }

    /*获取请求数据*/
    private void getData() {
        showDialog();
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = MeModel.carList(CardNo, createOrderData.getCityCode(), createOrderData.getServiceType());
                if (result != null)
                    emitter.onNext(result);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<String, CarListBean>() {
                    @Override
                    public CarListBean apply(String s) throws Exception {
                        Gson gson = new Gson();
                        return gson.fromJson(s, CarListBean.class);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CarListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (mCompositeDisposable != null)
                            mCompositeDisposable.add(d);
                    }

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onNext(CarListBean data) {
                        switch (data.get_metadata().getCode()) {
                            case 200://请求成功
                                CarListData = data.getData();
                                if (data.getData() != null && data.getData().size() > 0) {
                                    typeData1.clear();
                                    typeData1.addAll(data.getData());
                                    setViewData(typeData1, 0);
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
                        carPagerAdapter.setData(mData);
                        showToast(PublicApplication.resourceText.getString("app_error_message", getResources().getString(R.string.app_error_message)), 3);
                    }

                    @Override
                    public void onComplete() {
                        dismissDialog();
                        carPagerAdapter.setData(mData);
                    }
                });
    }

    /*获取个人的用户信息  userinfo */
    private void getPersonalInformation() {
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
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast(PublicApplication.resourceText.getString("app_error_message", getResources().getString(R.string.app_error_message)), 3);
                    }

                    @Override
                    public void onComplete() {
                        String app_transfer_hotline_number = PublicApplication.loginInfo.getString("ServiceTel", getResources().getString(R.string.app_transfer_hotline_number));
                        StringBuffer buf = new StringBuffer(app_transfer_hotline_number);
                        String aString = "-";
                        buf.insert(3, aString);
                        buf.insert(7, aString);
                        tvStringHotLineNumber.setText(buf);

                    }
                });
    }

    /**
     * 为界面加载初始数据
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setViewData(List<CarListBean.DataBean> data, int postion) {
        mData.clear();
        mData.addAll(data);

        if (mData != null && mData.size() > postion) {
            tvPrice.setText(StrUtils.getRmb() + mData.get(postion).getPrice());
            tvSeries.setText("" + mData.get(postion).getCarBrand());
            tvModel.setText("" + mData.get(postion).getCarModel());
            allRemarkItemList.clear();
            resetRemarkData();/*重新设置备注信息*/
            String remarkList = mData.get(postion).getRemarkList();
            String[] arrRemarks = remarkList.split(";");
            for (int i = 0; i < arrRemarks.length; i++) {
                RemarksBean remarksBean = new RemarksBean();
                remarksBean.setContent(arrRemarks[i]);
                allRemarkItemList.add(remarksBean);
            }
            mRemarksAdapter.notifyDataSetInvalidated();
            if (mData.get(postion).getCarDesc().getDescList().size() == 3) {
                llParameter01.setVisibility(View.VISIBLE);
                tvParameterName01.setText(mData.get(postion).getCarDesc().getDescList().get(0).getName());
                tvParameterValue01.setText(mData.get(postion).getCarDesc().getDescList().get(0).getValue());
                llParameter02.setVisibility(View.VISIBLE);
                tvParameterName02.setText(mData.get(postion).getCarDesc().getDescList().get(1).getName());
                tvParameterValue02.setText(mData.get(postion).getCarDesc().getDescList().get(1).getValue());
                llParameter03.setVisibility(View.VISIBLE);
                tvParameterName03.setText(mData.get(postion).getCarDesc().getDescList().get(2).getName());
                tvParameterValue03.setText(mData.get(postion).getCarDesc().getDescList().get(2).getValue());
                llParameter04.setVisibility(View.GONE);
            } else if (mData.get(postion).getCarDesc().getDescList().size() > 3) {
                llParameter01.setVisibility(View.VISIBLE);
                tvParameterName01.setText(mData.get(postion).getCarDesc().getDescList().get(0).getName());
                tvParameterValue01.setText(mData.get(postion).getCarDesc().getDescList().get(0).getValue());
                llParameter02.setVisibility(View.VISIBLE);
                tvParameterName02.setText(mData.get(postion).getCarDesc().getDescList().get(1).getName());
                tvParameterValue02.setText(mData.get(postion).getCarDesc().getDescList().get(1).getValue());
                llParameter03.setVisibility(View.VISIBLE);
                tvParameterName03.setText(mData.get(postion).getCarDesc().getDescList().get(2).getName());
                tvParameterValue03.setText(mData.get(postion).getCarDesc().getDescList().get(2).getValue());
                llParameter04.setVisibility(View.VISIBLE);
                tvParameterName04.setText(mData.get(postion).getCarDesc().getDescList().get(3).getName());
                tvParameterValue04.setText(mData.get(postion).getCarDesc().getDescList().get(3).getValue());
            }
//            FromCarInfoAdapter mAdapter = new FromCarInfoAdapter(TransferActivity.this);
//                mAdapter.setData(mData.get(postion).get);
//                gvInfo.setAdapter();
            Glide.with(this)
                    .load(data.get(postion).getCarBgImageUrl())
                    .asBitmap()
                    .fitCenter()
                    .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            int imgh = resource.getHeight();
                            int imgw = resource.getWidth();
                            int wdith = ShuttleABActivity.this.wdith - Utlis.dipTopx(ShuttleABActivity.this, 40);
                            int height = wdith * imgh / imgw;
                            ivInfo.setLayoutParams(new LinearLayout.LayoutParams(wdith, height));
                            ivInfo.setImageBitmap(resource);
                        }
                    });
            tvTransportTime.setText(mData.get(postion).getCarDesc().getTimeLimit());
            CurrentData = mData.get(postion);
        }
    }


    /*初始化View*/
    private void initView() {
        initWindow();
        initTitle();
        initContent();
        initViewPager();
        initStaticTextDisplay();

    }


    /*添加按钮*/
    private void setTflAdapter() {
        tflAdapter = new TagAdapter<RemarksBean>(seletedRemarkItemList) {
            @Override
            public View getView(FlowLayout parent, int position, RemarksBean o) {
                if (position == 0) {
                    LinearLayout iv = (LinearLayout) LayoutInflater.from(ShuttleABActivity.this).inflate(R.layout.item_tfl_iv,
                            tflShuttleRemarks, false);
                    return iv;
                } else {
                    //正常标签的布局
                    LinearLayout tv = (LinearLayout) LayoutInflater.from(ShuttleABActivity.this).inflate(R.layout.item_tfl_tv,
                            tflShuttleRemarks, false);

                    TextView viewById = tv.findViewById(R.id.tv_name);
                    viewById.setText(o.getContent());
                    return tv;
                }
            }
        };

        tflShuttleRemarks.setAdapter(tflAdapter);
    }

    private void initContent() {

        tvCity = findViewById(R.id.tv_city);
        tvCity.setText(createOrderData.getCityName());
//        llDailyRental = findViewById(R.id.ll_sbuttleRental);
        contentView = LayoutInflater.from(mContext).inflate(R.layout.popuplayout, null);
        tflShuttleRemarks = findViewById(R.id.tfl_shuttle_remarks);
        setTflAdapter();
        tflShuttleRemarks.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, com.zhy.view.flowlayout.FlowLayout parent) {
                if (position == 0) {
                    initPopupWindow();
                }
                return false;
            }
        });
        ivCarBg = findViewById(R.id.iv_car_bg);
        rlCar = findViewById(R.id.rl_car);
        contentView.findViewById(R.id.tv_pop_sure).setOnClickListener(this);
        contentView.findViewById(R.id.iv_pop_close).setOnClickListener(this);
        contentView.findViewById(R.id.ll_pop_outside).setOnClickListener(this);
        contentView.findViewById(R.id.ll_pop_inside).setOnClickListener(this);
        gvPopItem = contentView.findViewById(R.id.gv_pop_remark);
        mRemarksAdapter = new RemarksAdapter(mContext, allRemarkItemList, R.layout.item_remark);
        gvPopItem.setAdapter(mRemarksAdapter);
        gvPopItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean b = allRemarkItemList.get(position).isChecked();
                allRemarkItemList.get(position).setChecked(!b);
                mRemarksAdapter.notifyDataSetInvalidated();
            }
        });
        tvSeries = findViewById(R.id.tv_series);
        tvModel = findViewById(R.id.tv_model);
        tvPickUpTime = findViewById(R.id.tv_pick_up_time);
        etName = findViewById(R.id.et_name);
        etTel = findViewById(R.id.et_tel);
        etName.setFilters(new InputFilter[]{Utlis.typeFilter});
        ResetView();
        llPickUp = findViewById(R.id.ll_pick_up_time);
        llDestination = findViewById(R.id.ll_destination);
        tvDestination = findViewById(R.id.tv_destination);
        tvPickUpPoint = findViewById(R.id.tv_pick_up_point);
        llDestination.setOnClickListener(this);
        llPickUp.setOnClickListener(this);
        etName.addTextChangedListener(twName);
        etTel.addTextChangedListener(twTel);
        llParameter01 = findViewById(R.id.ll_parameter_01);
        tvParameterName01 = findViewById(R.id.tv_parameter_name_01);
        tvParameterValue01 = findViewById(R.id.tv_parameter_value_01);
        llParameter02 = findViewById(R.id.ll_parameter_02);
        tvParameterName02 = findViewById(R.id.tv_parameter_name_02);
        tvParameterValue02 = findViewById(R.id.tv_parameter_value_02);
        llParameter03 = findViewById(R.id.ll_parameter_03);
        tvParameterName03 = findViewById(R.id.tv_parameter_name_03);

        tvParameterValue03 = findViewById(R.id.tv_parameter_value_03);
        llParameter04 = findViewById(R.id.ll_parameter_04);
        tvParameterName04 = findViewById(R.id.tv_parameter_name_04);

        tvParameterValue04 = findViewById(R.id.tv_parameter_value_04);
        ivInfo = findViewById(R.id.iv_info);
        tvTransportTime = findViewById(R.id.tv_transport_time);
        tvPrice = findViewById(R.id.tv_price);
        llPickUpPoint = findViewById(R.id.ll_pick_up_point);
        llPickUpPoint.setOnClickListener(this);
        tvLastStep = findViewById(R.id.tv_last_step);
        tvLastStep.setText(PublicApplication.resourceText.getString("app_transfer_back", getResources().getString(R.string.app_transfer_back)));
        tvLastStep.setOnClickListener(this);
        tvNextStep = findViewById(R.id.tv_next_step);
        tvNextStep.setOnClickListener(this);
        tvNextStep.setText(PublicApplication.resourceText.getString("app_transfer_goto", getResources().getString(R.string.app_transfer_goto)));
        seletedRemarkItemList.add(new RemarksBean());

        rlCar.setLayoutParams(new LinearLayout.LayoutParams(this.wdith, this.wdith * 2 / 5));
        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_banner", ""))
                .asBitmap()
                .error(R.drawable.img_banner)
                .placeholder(R.drawable.img_banner)
                .into(ivCarBg);

    }

    private void ResetView() {
        viewTel = findViewById(R.id.view_tel);
        viewName = findViewById(R.id.view_name);
        tvPickUpTimeView = findViewById(R.id.tv_pick_up_time_view);
        llPickUpTimeView = findViewById(R.id.ll_pick_up);
        tvPickUpPointView = findViewById(R.id.tv_pick_up_point_view);
        tvDestinationView = findViewById(R.id.tv_destination_view);
        llPassenger = findViewById(R.id.ll_passenger);

    }

    TextWatcher twTel = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0) {
                viewTel.setVisibility(View.INVISIBLE);
                if (viewName.getVisibility() == View.INVISIBLE)
                    llPassenger.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    TextWatcher twName = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0) {
                viewName.setVisibility(View.INVISIBLE);
                if (viewTel.getVisibility() == View.INVISIBLE)
                    llPassenger.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {


        }
    };

    private void initViewPager() {
        vpMvp = findViewById(R.id.vp_shangwu);
        vpMvp.setLayoutParams(new RelativeLayout.LayoutParams(wdith, wdith / 5 * 2));
        carPagerAdapter = new CarListAdapter(this, width);
        vpMvp.setPageMargin(20);//设置page间间距，自行根据需求设置
        vpMvp.setOffscreenPageLimit(3);//>=3
        vpMvp.setAdapter(carPagerAdapter);
        //setPageTransformer 决定动画效果
        vpMvp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onPageSelected(int position) {
             /*ViewPager切换 换掉数据*/
                setViewData(typeData1, position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initPopupWindow() {
        lastSeletedRemarkItemList.clear();
        lastSeletedRemarkItemList.addAll(seletedRemarkItemList);
        if (mWindow == null) {
            mWindow = new PopupWindow(mContext);
        }
        mWindow.setContentView(contentView);
        mWindow.setOutsideTouchable(true);
        mWindow.setTouchable(true);
        mWindow.setFocusable(true);
        contentView.setFocusable(true);
        contentView.setFocusableInTouchMode(true);
        mWindow.setBackgroundDrawable(new BitmapDrawable());
        mWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mWindow.setWidth(LinearLayoutCompat.LayoutParams.MATCH_PARENT);
        mWindow.setHeight(LinearLayoutCompat.LayoutParams.MATCH_PARENT);
        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_transfer, null);
        mWindow.setAnimationStyle(R.style.contextMenuAnim);
        rootview.getBackground().setAlpha(255);
        mWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
        contentView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
    }

    private void initTitle() {
        ivTopBack = findViewById(R.id.iv_top_back);
        ivTopBack.setOnClickListener(this);
        ivTopBack.setVisibility(View.VISIBLE);
        tvTopTitle = findViewById(R.id.tv_top_title);
        tvTopTitle.setText(PublicApplication.resourceText.getString("app_index_service_02_c", getResources().getString(R.string.app_index_service_02_c)));

    }

    private void restoreRemarkData() {
        for (int i = 0; i < allRemarkItemList.size(); i++) {
            allRemarkItemList.get(i).setChecked(false);
            for (int j = 0; j < lastSeletedRemarkItemList.size(); j++) {
                if (allRemarkItemList.get(i).getContent().equals(lastSeletedRemarkItemList.get(j).getContent())) {
                    allRemarkItemList.get(i).setChecked(true);
                }
            }
        }
        mRemarksAdapter.notifyDataSetChanged();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_top_back:
                setClearData();

                break;
            case R.id.ll_pick_up_point:
                Intent intent = new Intent(this, AddressActivity.class);
                if (!tvPickUpPoint.getText().toString().equals("上车地点"))
                    intent.putExtra(AddressActivity.ADDRESS, tvPickUpPoint.getText().toString());
                startActivityForResult(intent, 0);
                break;
            case R.id.ll_pick_up_time:

                MyDatePicker myDatePicker = new MyDatePicker(ShuttleABActivity.this);
                String time2 = MyDateUtils.getTime();
                long minTime = Long.parseLong(time2) + (4 * 60 * 60);
                if (tvPickUpTime.getText().equals("上车时间")) {
                    myDatePicker.showDateAndTimePickerDialog(this.wdith, 0, 0, 0, 0, 0, minTime + "",findViewById(R.id.ll_pop));
                } else {
                    if (!StrUtils.isEmpty(CouponDate)) {
                        int yearMin = Integer.valueOf(CouponDate.substring(0, 4));
                        int monthMin = Integer.valueOf(CouponDate.substring(5, 7));
                        int dayMin = Integer.valueOf(CouponDate.substring(8, 10));
                        int hourMin = Integer.valueOf(CouponDate.substring(11, 13));
                        int minuteMin = Integer.valueOf(CouponDate.substring(14, 16));
                        int minuteSecond = Integer.valueOf(CouponDate.substring(17, 19));
                        String Str = String.valueOf(yearMin + "-" + monthMin + "-" + dayMin + " " + hourMin + ":" + minuteMin + ":" + minuteSecond);
                        String s = MyDateUtils.dataOne1(Str);
                        long maxTime = Long.parseLong(s);
                        myDatePicker.showDateAndTimePickerDialog(this.wdith, aboardY, aboardM, aboardD, aboardH, aboardm, minTime + "", maxTime + "",findViewById(R.id.ll_pop));
                    } else {
                        myDatePicker.showDateAndTimePickerDialog(this.wdith, aboardY, aboardM, aboardD, aboardH, aboardm, minTime + "",findViewById(R.id.ll_pop));
                    }
                }
                break;
            case R.id.ll_destination:
                Intent intent2 = new Intent(this, AddressActivity.class);
                if (!tvDestination.getText().toString().equals("目的地"))
                    intent2.putExtra(AddressActivity.ADDRESS, tvDestination.getText().toString());
                startActivityForResult(intent2, 1);
                break;
            case R.id.ll_pop_outside:
                //点击的是popupwindow的外部
                mWindow.dismiss();
                break;
            case R.id.ll_pop_inside:
                //点击的是popupwindow的内部
                break;
            case R.id.tv_pop_sure:
                /*设置备注*/
                resetRemarkData();
                mWindow.dismiss();
                break;
            case R.id.iv_pop_close:
                restoreRemarkData();
                mWindow.dismiss();
                break;
            case R.id.tv_next_step://下一步

                nextStepCommit();
                break;
            case R.id.tv_last_step://上一步
                finish();

                break;
        }
    }
    /*记录备注 设置备注*/

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void resetRemarkData() {
        Remark = "";
        seletedRemarkItemList.clear();
        seletedRemarkItemList.add(new RemarksBean());
        for (int i = 0; i < allRemarkItemList.size(); i++) {
            if (allRemarkItemList.get(i).isChecked()) {
                seletedRemarkItemList.add(allRemarkItemList.get(i));
                Remark = Remark + allRemarkItemList.get(i).getContent() + ";";

            }
        }
        tflAdapter.notifyDataChanged();
    }

    /*地址的回掉*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (data != null) {
                if (resultCode == 0) {
                    getAddress = data.getStringExtra(AddressActivity.ADDRESS);
                    tvPickUpPoint.setText(getAddress);
                    tvPickUpPointView.setVisibility(View.GONE);
                }
            }
        } else if (requestCode == 1) {
            if (data != null) {
                if (resultCode == 0) {
                    /*目的地*/
                    getAddress = data.getStringExtra(AddressActivity.ADDRESS);
                    if (tvPickUpPoint.getText().toString().equals(getAddress)) {
                        showToast(getResources().getString(R.string.app_address_error), 3);
                    } else {
                        tvDestination.setText(getAddress);
                        tvDestinationView.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    @Override
    public void positiveListener(int Year, int Month, int Day, int Hour, int Mint) {

        aboardY = Year;
        aboardM = Month;
        aboardD = Day;
        aboardH = Hour;
        aboardm = Mint;
        tvPickUpTime.setText(Year + "-" + Month + "-" + Day + " " + Hour + ":" + Mint);
        RentDate  = Year + "-" + to2(Month + "") + "-" + to2(Day + "") + " " + to2(Hour + "") + ":" + to2(Mint + "");
        llPickUpTimeView.setVisibility(View.GONE);

    }

    private String to2(String str) {
        return str.length() == 1 ? 0 + str : str;
    }

    /*创建订单*/
    /*需要判断ProdcutId  CarModelID CityCode RentDate  CarAddressFrom  CarAddressTo   PassengerName,PassengerTel*/
    private void nextStepCommit() {
        ProductID = Integer.parseInt(CurrentData.getProductID());
        CarModelID = Integer.parseInt(CurrentData.getCarModelID());
        CityCode = createOrderData.getCityCode();


        boolean isHttp = true;
//        RentDate = tvPickUpTime.getText().toString();//上车时间
        CarAddressFrom = tvPickUpPoint.getText().toString();//上车地点
        CarAddressTo = tvDestination.getText().toString();//目的地
        PassengerName = etName.getText().toString();//姓名
        PassengerTel = etTel.getText().toString();//手机

        if (StrUtils.isEmpty(RentDate) || RentDate.equals("上车时间")) {
            llPickUpTimeView.setVisibility(View.VISIBLE);
            tvPickUpTimeView.setVisibility(View.VISIBLE);
            isHttp = false;
        } else {

                /*获取系统当前时间*/
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date curDate = new Date(System.currentTimeMillis());
            String format = formatter.format(curDate);//系统当前时间

            String straboardM = "";
            String straboardD = "";
            String straboardH = "";
            String straboardMs = "";
            if (aboardM < 10)
                straboardM = "0" + aboardM;
            else
                straboardM = "" + aboardM;
            if (aboardD < 10)
                straboardD = "0" + aboardD;
            else
                straboardD = "" + aboardD;
            if (aboardH < 10)
                straboardH = "0" + aboardH;
            else
                straboardH = "" + aboardH;
            if (aboardm < 10)
                straboardMs = "0" + aboardm;
            else
                straboardMs = "" + aboardm;
            String Date = "";
            Date = String.valueOf(aboardY + "-" + straboardM + "-" + straboardD + " " + straboardH + ":" + straboardMs);
            final int remainHour = (int) (Double.parseDouble(DataUtils.getTimeDifferenceHour(Date, format)));
            if (remainHour < 0) {
                isHttp = false;
                showToast(PublicApplication.loginInfo.getString("app_transfer_prompt_content", getResources().getString(R.string.app_transfer_prompt_content)), 3);
            } else {
                isHttp = true;
            }
        }
        if (StrUtils.isEmpty(CarAddressFrom) || CarAddressFrom.equals("上车地点")) {
            tvPickUpPointView.setVisibility(View.VISIBLE);
            isHttp = false;
        }
        if (StrUtils.isEmpty(CarAddressTo) || CarAddressTo.equals("目的地")) {
            tvDestinationView.setVisibility(View.VISIBLE);
            isHttp = false;
        }
        if (StrUtils.isEmpty(CarAddressTo) || CarAddressTo.equals("目的地")) {
            tvDestinationView.setVisibility(View.VISIBLE);
            isHttp = false;
        }
        if (StrUtils.isEmpty(PassengerName) || PassengerName.equals("姓名")) {
            llPassenger.setVisibility(View.VISIBLE);
            viewName.setVisibility(View.VISIBLE);
            isHttp = false;
        }
        if (StrUtils.isEmpty(PassengerTel) || PassengerTel.equals("手机")) {
            llPassenger.setVisibility(View.VISIBLE);
            viewTel.setVisibility(View.VISIBLE);
            viewTel.setText(PublicApplication.resourceText.getString("app_transfer_prompt_input", getResources().getString(R.string.app_transfer_prompt_input)));
            isHttp = false;
        }
        if (!RegularUtil.checkMobile(PassengerTel)){
            llPassenger.setVisibility(View.VISIBLE);
            viewTel.setVisibility(View.VISIBLE);
            viewTel.setText(PublicApplication.resourceText.getString("app_login_check_phone", getResources().getString(R.string.app_login_check_phone)));
            isHttp = false;
        }
        if (isHttp) {
            nextStepData();
        }

    }

    /*对象存放代金券  次卡  活动卡*/
    public JSONObject JsonObjectData(JSONObject JsonObject, int ProductID, int CarModelID, String CarCity, String CarCityThree, String CarBrand, String TimeBoard, String ServiceCar, String PassengerName,
                                     String PassengerMobile, String Remark, int CarTypeID, String PaymentType, String FlightNumber, String StartPlace, String Destination, String ReturnTimeBoard,
                                     String ReturnStartPlace, String ReturnDestination, String CardNo, String CardValue) {
        try {
            JsonObject.put("ProductID", ProductID + "");
            JsonObject.put("CarModelID", CarModelID + "");
            JsonObject.put("CarCity", CarCity);
            JsonObject.put("CarCityThree", CarCityThree);
            JsonObject.put("CarBrand", CarBrand);
            JsonObject.put("TimeBoard", TimeBoard);
            JsonObject.put("ServiceCar", ServiceCar);
            JsonObject.put("PassengerName", PassengerName);
            JsonObject.put("PassengerMobile", PassengerMobile);
            JsonObject.put("Remark", Remark);
            JsonObject.put("CarTypeID", CarTypeID);
            JsonObject.put("PaymentType", PaymentType);
            JsonObject.put("FlightNumber", FlightNumber);
            JsonObject.put("StartPlace", StartPlace);
            JsonObject.put("Destination", Destination);
            JsonObject.put("ReturnTimeBoard", ReturnTimeBoard);
            JsonObject.put("ReturnStartPlace", ReturnStartPlace);
            JsonObject.put("ReturnDestination", ReturnDestination);
            JsonObject.put("CardNo", CardNo);
            JsonObject.put("CardValue", CardValue + "");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return JsonObject;
    }

    JSONObject orderString;
    JSONObject msgOrderString = new JSONObject();//性别

    /**
     * serviceType 服务类型（1 接机）  OrderSource (1 星徽尊享 2 app)  productID 产品ID carlist接口获取
     * carModelID 车型ID  CarCity 城市 CarCityThree 城市三字码  CarBrand 奔驰-车型 TimeBoard 用车时间
     * ServiceCar 车系  PassengerName 乘客姓名 PassengerMobile 乘客电话  PaymentType  支付方式
     * StartPlace 上车地址  Destination 下车地址  ReturnTimeBoard 返程上车时间  ReturnStartPlace 返程上车地址
     * ReturnDestination 返程下车地址  FlightNumber 航班号   CardNo 卡号  CardValue 卡值（卡的次数）
     * CarTypeID  卡类型id  Remark  备注
     *
     */
    /*请求下一步的接口  生成订单*/
    private void nextStepData() {

        showDialog();
        if (CardNo == null)
            CardNo = "";
        // TODO: 2018/4/19 name改为测试
        orderString = JsonObjectData(msgOrderString, ProductID, CarModelID, createOrderData.getCityName(), CityCode, CurrentData.getCarBrand(), RentDate, CurrentData.getCarModel(), PassengerName+"测试数据",
                PassengerTel, Remark, Integer.parseInt(CardType), "", "", CarAddressFrom, CarAddressTo, "", "", "", CardNo, CardValue);
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = MeModel.CreateOrder(serviceType, 2, orderString);
                if (result != null)
                    emitter.onNext(result);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<String, CreateOrderBean>() {
                    @Override
                    public CreateOrderBean apply(String s) throws Exception {
                        Gson gson = new Gson();
                        return gson.fromJson(s, CreateOrderBean.class);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CreateOrderBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (mCompositeDisposable != null)
                            mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(CreateOrderBean data) {
                        switch (data.get_metadata().getCode()) {
                            case 200://请求成功
                                ScreenManager.getScreenManager().RemoveAllExceptedOne(MainActivity.class);
                                Intent intent = new Intent();
                                Bundle mbundle = new Bundle();
                                mbundle.putSerializable(CouponListActivity.COUPONDATA, couponData);
                                intent.putExtra("data", mbundle);
                                intent.putExtra(ConfirmationOfOrderActivity.ORDERID, data.getData().getOrderID());
                                intent.putExtra(ConfirmationOfOrderActivity.FROM_PAGE, "1");
                                intent.setClass(ShuttleABActivity.this, ConfirmationOfOrderActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case 500:

                                CustomToast.showFailYellowToast(ShuttleABActivity.this, data.get_metadata().getMessage());
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
    public void negativeListener() {

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mCompositeDisposable != null)
            mCompositeDisposable.clear();
        dismissDialog();
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
}
