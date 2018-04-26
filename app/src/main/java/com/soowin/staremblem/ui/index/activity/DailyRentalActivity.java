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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
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
import com.soowin.staremblem.utils.EditTextUtils;
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
 * 商务日租页
 */
public class DailyRentalActivity extends BaseActivity implements View.OnClickListener, MyDatePicker.TimePickerInterface, CompoundButton.OnCheckedChangeListener {
    private static final String TAG = DailyRentalActivity.class.getCanonicalName();
    /*卡券Data数据*/
    CouponListBean.DataBean couponData;
    CarListBean.DataBean CurrentData = new CarListBean.DataBean();//当前选中的Model车数据
    JSONObject orderString;
    JSONObject msgOrderString = new JSONObject();//性别
    //Title************************************************
    private TextView tvTitle;
    private ImageView ivBack;
    //content**********************************************
    private TextView tvServiceName;         //服务名称
    private TextView tvServiceIntroduce;    //服务介绍
    private TextView tvPrice;               //价格
    private TextView tvSeries;              //车辆系类
    private TextView tvModel;               //车辆型号
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
    //    private GridView gvInfo;                //特色描述
    private TextView tvTimeParameter;       //时限内容
    private ViewPager vpCar;                //滑动选择控件
    //静态文字
    private RadioButton rbType01;      //类型1
    private RadioButton rbType02;      //类型2
    private TextView tvBack;           //上一步
    private TextView tvGoto;           //下一步
    private ImageView ivCarBg;
    private RelativeLayout rlCar;
    //数据****************************************************************
    private String cardNo = "";
    private String cityCode = "";
    private String CardValue;
    private String CardType;
    private String CityName;
    private int serviceType = 0;
    private List<CarListBean.DataBean> typeData1 = new ArrayList<>();
    private List<CarListBean.DataBean> typeData2 = new ArrayList<>();
    private List<CarListBean.DataBean> mData = new ArrayList<>();
    //banner
    private CarListAdapter mAdapter;
    //订单生成所用数据
    private CreateOrderBena createOrderData;
    //异步请求控制器
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private TagFlowLayout tflDailyRentalRemarks;
    //====================================
    //PopupWindow
    private PopupWindow mWindow;
    private View contentView;
    private MyGridView gvPopItem;
    private RemarksAdapter mRemarksAdapter;
    //备注
    private List<RemarksBean> allRemarkItemList = new ArrayList<>();
    private List<RemarksBean> seletedRemarkItemList = new ArrayList<>();
    private List<RemarksBean> lastSeletedRemarkItemList = new ArrayList<>();//记录要选择之前的状态
    private Context mContext;
    /*上车时间*/
    private LinearLayout llDailyRentalFromTime;//上车时间
    private TextView tvDailyRentalFormTime;
    /*上车地点*/
    private LinearLayout llPickUoPoint;//上车地点
    private TextView tvPickUpPoint;//上车地点
    private LinearLayout llPickUopPointView;//上车地点提示的红色View
    /*姓名   手机号*/
    private LinearLayout llPassenger;       //顾客
    private TextView tvViewName;
    private TextView tvViewTel;
    TextWatcher twName = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0) {
                tvViewName.setVisibility(View.INVISIBLE);
                if (tvViewTel.getVisibility() == View.INVISIBLE)
                    llPassenger.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    TextWatcher twTel = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0) {
                tvViewTel.setVisibility(View.INVISIBLE);
                if (tvViewTel.getVisibility() == View.INVISIBLE)
                    llPassenger.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
    private String CouponDate = "";
    private TextView tvPlace;
    private TextView tvEmpty;
    private TextView tvViewTime;//上车时间
    /*服务类型*/
    private TextView tvViewPlace;//上车地点
    private EditText etDailyRentalName;//姓名
    private EditText etEtDailyRentalTel;//电话
    private String CardNo = "";//卡券的卡号
    private String CityCode = "";//城市编码
    private int ProductID;//产品ID   （汽车列表获取）
    private int CarModelID; //车型ID    汽车列表获取
    private String RentDate = "";   //上车时间
    private String CarAddressFrom = "";//上车地址
    private String PassengerName;//乘客姓名
    private String PassengerTel;//乘客电话
    private String Remark = "";   //备注
    private ImageView ivIconDownLi;
    //静态文字显示
    private TextView tvSelecter;//app_transfer_selecter
    private TextView tvModelIntroduce;//app_transfer_model_introduce
    private TextView tvTime;//app_transfer_time
    private TextView tvFormType;//app_transfer_form_type
    private TextView tvFormRemarks;//app_transfer_form_remarks
    private TextView tvStringPrompt;//app_transfer_prompt
    private TextView tvStringPromptContent;//app_transfer_prompt_content
    private TextView tvStringHotline;//app_transfer_hotline
    private TextView tvStringHotlineC;//app_transfer_hotline_c
    private TextView tvStringHotlineNumber;//app_transfer_hotline_number
    private int aboardY = 0;//上车时间
    private int aboardM = 0;
    private int aboardD = 0;
    private int aboardH = 0;
    private int aboardm = 0;
    private int aboardS = 0;
    private TagAdapter tflAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_rental);
        seletedRemarkItemList.add(new RemarksBean());

        mContext = this;
        initData();
        initView();
        getHttpData1();
        if (StrUtils.isEmpty(PublicApplication.loginInfo.getString("ServiceTel", ""))) {
            getPersonalInformation();
        } else {
            String serviceTel = PublicApplication.loginInfo.getString("ServiceTel", "");
            StringBuffer buf = new StringBuffer(serviceTel);
            String aString = "-";
            buf.insert(3, aString);
            buf.insert(7, aString);
            tvStringHotlineNumber.setText(buf);
        }

    }

    private void initData() {
        //初始化数据
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

        if (createOrderData != null) {
            serviceType = createOrderData.getServiceType();
            cityCode = createOrderData.getCityCode();
            CityName = createOrderData.getCityName();
        }

    }

    /**
     * 请求数据
     * 车列表
     */
    public void getHttpData1() {
        if (typeData1 == null || typeData1.size() < 1) {
            showDialog();
            Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                    String result = MeModel.carList(cardNo, cityCode, 3);
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
                                case 200:
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

                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onError(Throwable e) {
//                            getHttpData2();
                            dismissDialog();
                            setViewData(typeData1, 0);
                            mAdapter.setData(mData);
                            showToast(PublicApplication.resourceText.getString("app_error_message", getResources().getString(R.string.app_error_message)), 3);
                        }

                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onComplete() {
//                            getHttpData2();
                            setViewData(typeData1, 0);
                            mAdapter.setData(mData);
                            dismissDialog();
                        }
                    });
        }
    }

    /**
     * 请求数据
     * 车列表 第二种类型
     */
    private void getHttpData2() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = MeModel.carList(cardNo, cityCode, 6);
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

                    @Override
                    public void onNext(CarListBean data) {
                        switch (data.get_metadata().getCode()) {
                            case 200:
                                if (data.getData() != null && data.getData().size() > 0) {
                                    typeData2.clear();
                                    typeData2.addAll(data.getData());
                                }
                                break;
                            case 500:
                                showToast(data.get_metadata().getMessage(), 3);
                                break;
                        }
                    }

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onError(Throwable e) {
                        setViewData(typeData1, 0);
                        mAdapter.setData(mData);
                        dismissDialog();
                        showToast(PublicApplication.resourceText.getString("app_error_message", getResources().getString(R.string.app_error_message)), 3);
                    }

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onComplete() {
                        setViewData(typeData1, 0);
                        mAdapter.setData(mData);
                        dismissDialog();
                    }
                });
    }

    private void initView() {
        initTitle();
        initContent();

    }

    private void initContent() {
        contentView = LayoutInflater.from(mContext).inflate(R.layout.popuplayout, null);
        tflDailyRentalRemarks = findViewById(R.id.tfl_dailyRental_remarks);
        setTflAdapter();
//        tflDailyRentalRemarks.setHorizontalSpacing(20);
        tflDailyRentalRemarks.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
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
        llDailyRentalFromTime = findViewById(R.id.ll_dailyRental_form_time);
        llDailyRentalFromTime.setOnClickListener(this);
        contentView.findViewById(R.id.tv_pop_sure).setOnClickListener(this);
        contentView.findViewById(R.id.iv_pop_close).setOnClickListener(this);
        contentView.findViewById(R.id.ll_pop_outside).setOnClickListener(this);
        contentView.findViewById(R.id.ll_pop_inside).setOnClickListener(this);
        llPickUoPoint = findViewById(R.id.ll_pick_up_point);
        llPickUoPoint.setOnClickListener(this);
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

        tvServiceName = findViewById(R.id.tv_service_name);
        tvServiceIntroduce = findViewById(R.id.tv_service_introduce);
        tvPrice = findViewById(R.id.tv_price);
        tvSeries = findViewById(R.id.tv_series);
        tvModel = findViewById(R.id.tv_model);

        llPickUopPointView = findViewById(R.id.ll_pick_up_point_view);
        tvPickUpPoint = findViewById(R.id.tv_pick_up_point);
        etDailyRentalName = findViewById(R.id.et_dailyRental_name);
        llPassenger = findViewById(R.id.ll_passenger);
        tvViewName = findViewById(R.id.view_name);
        tvViewTel = findViewById(R.id.view_tel);
        tvViewTime = findViewById(R.id.tv_time_view);
        tvViewPlace = findViewById(R.id.tv_place_view);
        etDailyRentalName.addTextChangedListener(twName);
        etEtDailyRentalTel = findViewById(R.id.et_dailyRental_tel);
        etEtDailyRentalTel.addTextChangedListener(twTel);

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
//        gvInfo = findViewById(R.id.gv_info);
        tvTimeParameter = findViewById(R.id.tv_time_parameter);
        vpCar = findViewById(R.id.vp_car);

        tvDailyRentalFormTime = findViewById(R.id.tv_dailyRental_form_time);
        tvEmpty = findViewById(R.id.tv_empty);


        rbType01 = findViewById(R.id.rb_type_01);
        rbType02 = findViewById(R.id.rb_type_02);
        tvBack = findViewById(R.id.tv_back);
        tvGoto = findViewById(R.id.tv_goto);
//        flDailyRentalRemarks = findViewById(R.id.fl_dailyRental_remarks);
        rbType01.setOnCheckedChangeListener(this);
        rbType02.setOnCheckedChangeListener(this);
        tvBack.setOnClickListener(this);
        tvGoto.setOnClickListener(this);


        mAdapter = new CarListAdapter(DailyRentalActivity.this, DailyRentalActivity.this.wdith);
        vpCar.setAdapter(mAdapter);
        vpCar.setLayoutParams(new RelativeLayout.LayoutParams(this.wdith, this.wdith * 2 / 5));
        vpCar.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onPageSelected(int position) {
                if (serviceType == 3) {
                    setViewData(typeData1, position);
                } else {
                    setViewData(typeData2, position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (serviceType == 3)
            rbType01.setChecked(true);
        else
            rbType02.setChecked(true);
        //表单部分*****************************************************


        //静态文字部分
        tvServiceName.setText(PublicApplication.resourceText.getString("app_index_service_03_c", getResources().getString(R.string.app_index_service_03_c)));
        tvServiceIntroduce.setText(PublicApplication.resourceText.getString("app_dailyRental_service_introduce", getResources().getString(R.string.app_dailyRental_service_introduce)));
        tvSelecter = findViewById(R.id.tv_selecter);
        tvSelecter.setText(PublicApplication.resourceText.getString("app_transfer_selecter", getResources().getString(R.string.app_transfer_selecter)));
        tvSeries.setText(PublicApplication.resourceText.getString("app_transfer_series", getResources().getString(R.string.app_transfer_series)));
        tvModel.setText(PublicApplication.resourceText.getString("app_transfer_model", getResources().getString(R.string.app_transfer_model)));
        tvModelIntroduce = findViewById(R.id.tv_model_introduce);
        tvModelIntroduce.setText(PublicApplication.resourceText.getString("app_transfer_model_introduce", getResources().getString(R.string.app_transfer_model_introduce)));
        tvTime = findViewById(R.id.tv_time);
        tvTime.setText(PublicApplication.resourceText.getString("app_transfer_time", getResources().getString(R.string.app_transfer_time)));
        tvFormType = findViewById(R.id.tv_form_type);
        tvFormType.setText(PublicApplication.resourceText.getString("app_dailyRental_form_info", getResources().getString(R.string.app_dailyRental_form_info)));
        tvFormRemarks = findViewById(R.id.tv_form_remarks);
        tvFormRemarks.setText(PublicApplication.resourceText.getString("app_transfer_form_remarks", getResources().getString(R.string.app_transfer_form_remarks)));
        tvStringPrompt = findViewById(R.id.tv_String_prompt);
        tvStringPrompt.setText(PublicApplication.resourceText.getString("app_transfer_prompt", getResources().getString(R.string.app_transfer_prompt)));
        tvStringPromptContent = findViewById(R.id.tv_string_prompt_content);
        tvStringPromptContent.setText(PublicApplication.resourceText.getString("app_transfer_prompt_content", getResources().getString(R.string.app_transfer_prompt_content)));
        tvStringHotline = findViewById(R.id.tv_string_hotline);
        tvStringHotline.setText(PublicApplication.resourceText.getString("app_transfer_hotline", getResources().getString(R.string.app_transfer_hotline)));
        tvStringHotlineC = findViewById(R.id.tv_string_hotline_c);
        tvStringHotlineC.setText(PublicApplication.resourceText.getString("app_transfer_hotline_c", getResources().getString(R.string.app_transfer_hotline_c)));
        tvStringHotlineNumber = findViewById(R.id.tv_string_hotline_number);
//        tvStringHotlineNumber.setText(PublicApplication.resourceText.getString("app_transfer_hotline_number", getResources().getString(R.string.app_transfer_hotline_number)));
        tvStringHotlineNumber.setText(PublicApplication.loginInfo.getString("ServiceTel", ""));

        rbType01.setText(PublicApplication.resourceText.getString("app_order_daily_rental", getResources().getString(R.string.app_order_daily_rental)));
        rbType02.setText(PublicApplication.resourceText.getString("app_order_half_day_rent", getResources().getString(R.string.app_order_half_day_rent)));
        tvDailyRentalFormTime.setText(PublicApplication.resourceText.getString("app_transfer_form_time", getResources().getString(R.string.app_transfer_form_time)));
        tvPickUpPoint.setText(PublicApplication.resourceText.getString("app_transfer_form_place", getResources().getString(R.string.app_transfer_form_place)));
        tvBack.setText(PublicApplication.resourceText.getString("app_transfer_back", getResources().getString(R.string.app_transfer_back)));
        tvGoto.setText(PublicApplication.resourceText.getString("app_transfer_goto", getResources().getString(R.string.app_transfer_goto)));

        tvViewName.setText(PublicApplication.resourceText.getString("app_transfer_prompt_input", getResources().getString(R.string.app_transfer_prompt_input)));
        tvViewTel.setText(PublicApplication.resourceText.getString("app_transfer_prompt_input", getResources().getString(R.string.app_transfer_prompt_input)));
        tvViewTime.setText(PublicApplication.resourceText.getString("app_transfer_prompt_input", getResources().getString(R.string.app_transfer_prompt_input)));
        tvViewPlace.setText(PublicApplication.resourceText.getString("app_transfer_prompt_input", getResources().getString(R.string.app_transfer_prompt_input)));


        ivIconDownLi = findViewById(R.id.iv_icon_down_li);
        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_icon_down_li", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_icon_down_li))
                .error(getResources().getDrawable(R.drawable.img_icon_down_li))
                .into(ivIconDownLi);

        etDailyRentalName.setFilters(new InputFilter[]{Utlis.typeFilter});

        tvPlace = findViewById(R.id.tv_dailyRental_place);
        tvPlace.setText(createOrderData.getCityName());

        rlCar.setLayoutParams(new LinearLayout.LayoutParams(this.wdith, this.wdith * 2 / 5));
        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_banner", ""))
                .asBitmap()
                .error(R.drawable.img_banner)
                .placeholder(R.drawable.img_banner)
                .into(ivCarBg);
    }

    private void setTflAdapter() {
        tflAdapter = new TagAdapter<RemarksBean>(seletedRemarkItemList) {
            @Override
            public View getView(FlowLayout parent, int position, RemarksBean o) {
                if (position == 0) {
                    LinearLayout iv = (LinearLayout) LayoutInflater.from(DailyRentalActivity.this).inflate(R.layout.item_tfl_iv,
                            tflDailyRentalRemarks, false);
                    return iv;
                } else {
                    //正常标签的布局
                    LinearLayout tv = (LinearLayout) LayoutInflater.from(DailyRentalActivity.this).inflate(R.layout.item_tfl_tv,
                            tflDailyRentalRemarks, false);

                    TextView viewById = tv.findViewById(R.id.tv_name);
                    viewById.setText(o.getContent());
                    return tv;
                }
            }
        };

        tflDailyRentalRemarks.setAdapter(tflAdapter);
    }

    private void initTitle() {
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(PublicApplication.resourceText.getString("app_index_service_03_c", getResources().getString(R.string.app_index_service_03_c)));
        ivBack = findViewById(R.id.iv_back);
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(this);

        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_go_back", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_go_back))
                .error(getResources().getDrawable(R.drawable.img_go_back))
                .into(ivBack);

    }

    /**
     * 为界面加载初始数据
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setViewData(List<CarListBean.DataBean> data, int postion) {
        mData.clear();
        mData.addAll(data);
        if (mData != null && mData.size() > postion) {
            tvPrice.setText(StrUtils.getRmb()+ mData.get(postion).getPrice());
            tvSeries.setText("" + mData.get(postion).getCarBrand());
            tvModel.setText("" + mData.get(postion).getCarModel());
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
            Glide.with(this)
                    .load(data.get(postion).getCarBgImageUrl())
                    .asBitmap()
                    .fitCenter()
                    .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            int imgh = resource.getHeight();
                            int imgw = resource.getWidth();
                            int wdith = DailyRentalActivity.this.wdith - Utlis.dipTopx(DailyRentalActivity.this, 40);
                            int height = wdith * imgh / imgw;
                            ivInfo.setLayoutParams(new LinearLayout.LayoutParams(wdith, height));
                            ivInfo.setImageBitmap(resource);
                        }
                    });

            CurrentData = mData.get(postion);
            //            FromCarInfoAdapter mAdapter = new FromCarInfoAdapter(TransferActivity.this);
//                mAdapter.setData(mData.get(postion).get);
//                gvInfo.setAdapter();
            tvTimeParameter.setText(mData.get(postion).getCarDesc().getTimeLimit());
            allRemarkItemList.clear();
            resetRemarkData();
            String remarkList = mData.get(postion).getRemarkList();
            String[] arrRemarks = remarkList.split(";");
            for (int i = 0; i < arrRemarks.length; i++) {
                RemarksBean remarksBean = new RemarksBean();
                remarksBean.setContent(arrRemarks[i]);
                allRemarkItemList.add(remarksBean);
            }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                setClearData();
                break;
            case R.id.tv_back:
                finish();
                break;
            /*点击下一步*/
            case R.id.tv_goto:

                if (EditTextUtils.isEmpty(new EditText[]{etDailyRentalName, etEtDailyRentalTel}, new TextView[]{tvViewName, tvViewTel}, this) |
                        EditTextUtils.isEmpty2(new TextView[]{tvPickUpPoint}, new TextView[]{tvViewPlace}, new String[]{getResources().getString(R.string.app_transfer_form_place)}, this)) {
                    if (EditTextUtils.isEmpty2(new TextView[]{tvDailyRentalFormTime}, new TextView[]{tvViewTime}, new String[]{getResources().getString(R.string.app_transfer_form_time)}, this)) {
                        tvEmpty.setVisibility(View.VISIBLE);
                    }
                    return;
                }

                nextStepCommit();
                break;
            case R.id.tv_pop_sure:
                resetRemarkData();
                mWindow.dismiss();
                break;
            case R.id.ll_pop_outside:
                //点击的是popupwindow的外部
                mWindow.dismiss();
                break;
            case R.id.ll_pop_inside:
                //点击的是popupwindow的内部
                break;

            case R.id.iv_pop_close:
                restoreRemarkData();
                mWindow.dismiss();
                break;
            case R.id.ll_dailyRental_form_time:
                MyDatePicker myDatePicker = new MyDatePicker(mContext);
                String time2 = MyDateUtils.getTime();
                long minTime = Long.parseLong(time2) + (4 * 60 * 60);
                if (tvDailyRentalFormTime.getText().equals("上车时间")) {

                    myDatePicker.showDateAndTimePickerDialog(this.wdith, 0, 0, 0, 0, 0, minTime + "", findViewById(R.id.ll_pop));
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
                        myDatePicker.showDateAndTimePickerDialog(this.wdith, aboardY, aboardM, aboardD, aboardH, aboardm, minTime + "", maxTime + "", findViewById(R.id.ll_pop));
                    } else {
                        myDatePicker.showDateAndTimePickerDialog(this.wdith, aboardY, aboardM, aboardD, aboardH, aboardm, minTime + "", findViewById(R.id.ll_pop));
                    }
                }
                break;
            case R.id.ll_pick_up_point:/*点击上车地点*/
                Intent intent2 = new Intent(this, AddressActivity.class);
                if (!tvPickUpPoint.getText().toString().equals("上车地点"))
                    intent2.putExtra(AddressActivity.ADDRESS, tvPickUpPoint.getText().toString());
                startActivityForResult(intent2, 0);

                break;
        }
    }

    /*需要判断ProdcutId  CarModelID CityCode RentDate  CarAddressFrom  CarAddressTo   PassengerName,PassengerTel*/
    private void nextStepCommit() {
        CarAddressFrom = tvPickUpPoint.getText().toString();
        PassengerName = etDailyRentalName.getText().toString();
        PassengerTel = etEtDailyRentalTel.getText().toString();
        ProductID = Integer.parseInt(CurrentData.getProductID());
        CarModelID = Integer.parseInt(CurrentData.getCarModelID());
        CityCode = createOrderData.getCityCode();

        if (StrUtils.isEmpty(PassengerTel) || PassengerTel.equals("手机")) {
            llPassenger.setVisibility(View.VISIBLE);
            tvViewTel.setVisibility(View.VISIBLE);
            tvViewTel.setText(PublicApplication.resourceText.getString("app_transfer_prompt_input", getResources().getString(R.string.app_transfer_prompt_input)));
            return;
        }
        if (!RegularUtil.checkMobile(PassengerTel)){
            llPassenger.setVisibility(View.VISIBLE);
            tvViewTel.setVisibility(View.VISIBLE);
            tvViewTel.setText(PublicApplication.resourceText.getString("app_login_check_phone", getResources().getString(R.string.app_login_check_phone)));
            return;
        }

        nextStepData();

    }

    /*请求下一步的接口  生成订单*/
    private void nextStepData() {
        showDialog();
        if (CardNo == null)
            CardNo = "";
        // TODO: 2018/4/19 name改为测试
        orderString = jsonObjectData(msgOrderString, ProductID, CarModelID, createOrderData.getCityName(), CityCode, CurrentData.getCarBrand(), RentDate, CurrentData.getCarModel(), PassengerName + "测试数据",
                PassengerTel, Remark, Integer.parseInt(CardType), "", "", "", CarAddressFrom, "", "", "", CardNo, CardValue);

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
                                intent.setClass(DailyRentalActivity.this, ConfirmationOfOrderActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case 500:
                                CustomToast.showFailYellowToast(DailyRentalActivity.this, data.get_metadata().getMessage());
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

    /*对象存放代金券  次卡  活动卡*/
    public JSONObject jsonObjectData(JSONObject JsonObject, int ProductID, int CarModelID, String CarCity, String CarCityThree, String CarBrand, String TimeBoard, String ServiceCar, String PassengerName,
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

    /*地址的回掉*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (data != null) {
                if (resultCode == 0) {
                    String getAddress = data.getStringExtra(AddressActivity.ADDRESS);
                    tvPickUpPoint.setText(getAddress);
                    if (tvPickUpPoint.length() > 0 & !tvPickUpPoint.equals("上车地点")) {
                        llPickUopPointView.setVisibility(View.INVISIBLE);
                        tvViewPlace.setVisibility(View.GONE);
                    } else {
                        llPickUopPointView.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

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

    @Override
    public void onPause() {
        super.onPause();
        if (mCompositeDisposable != null)
            mCompositeDisposable.clear();
        dismissDialog();
    }

    @Override
    public void positiveListener(int Year, int Month, int Day, int Hour, int Mint) {
        aboardY = Year;
        aboardM = Month;
        aboardD = Day;
        aboardH = Hour;
        aboardm = Mint;
        tvDailyRentalFormTime.setText(String.valueOf(Year + "-" + Month + "-" + Day + " " + Hour + ":" + Mint));
        tvViewTime.setVisibility(View.GONE);
        tvEmpty.setVisibility(View.GONE);
        RentDate = Year + "-" + to2(Month + "") + "-" + to2(Day + "") + " " + to2(Hour + "") + ":" + to2(Mint + "");
    }

    private String to2(String str) {
        return str.length() == 1 ? 0 + str : str;
    }

    @Override
    public void negativeListener() {

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b)
            switch (compoundButton.getId()) {
                case R.id.rb_type_01:
                    serviceType = 3;
                    createOrderData.setServiceType(serviceType);
                    setViewData(typeData1, 0);
                    mAdapter = new CarListAdapter(DailyRentalActivity.this, DailyRentalActivity.this.wdith);
                    vpCar.setAdapter(mAdapter);
                    mAdapter.setData(mData);
                    break;
                case R.id.rb_type_02:
                    serviceType = 6;
                    createOrderData.setServiceType(serviceType);
                    setViewData(typeData2, 0);
                    mAdapter = new CarListAdapter(DailyRentalActivity.this, DailyRentalActivity.this.wdith);
                    vpCar.setAdapter(mAdapter);
                    mAdapter.setData(mData);
                    break;
            }
    }

    /*清空之前的CouponData*/
    private void setClearData() {
        EventBus.getDefault().post("ClearData");
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setClearData();
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
                        tvStringHotlineNumber.setText(buf);
                    }
                });
    }
}
