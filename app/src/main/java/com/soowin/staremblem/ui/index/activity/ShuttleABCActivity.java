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
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
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

import static com.soowin.staremblem.ui.index.activity.ShuttleABCActivity.TimePickerEnum.ReturnTime;

/**
 * 接送服务页
 */
public class ShuttleABCActivity extends BaseActivity implements View.OnClickListener, MyDatePicker.TimePickerInterface {
    private static final String TAG = ShuttleABCActivity.class.getSimpleName();
    private final int PickUpPointCode = 0; //启程出发地点
    private final int DestinationCode = 1; //启程目的地

    //布局
    private final int ReturnDestinationCode = 2; //返程目的地
    List<CarListBean.DataBean> CarListData = new ArrayList<>();
    /*卡券Data数据*/
    CouponListBean.DataBean couponData;
    TimePickerEnum pickerEnum;
    //标题
    private ImageView ivTopBack;
    private TextView tvTopTitle;

    //view
    private TextView tvPrice;
    private TextView tvSeries;
    private TextView tvModel;
    private LinearLayout llParameter01, llParameter02, llParameter03, llParameter04;
    private TextView tvParameterName01, tvParameterName02, tvParameterName03, tvParameterName04;
    private TextView tvParameterValue01, tvParameterValue02, tvParameterValue03, tvParameterValue04;
    private ImageView ivInfo;                //特色描述
    private GridView gvInfo;
    private TextView tvTransportTime;
    private ViewPager vpShangwu;
    private TextView tvPlace;
    private TextView tvPickUpTime;//上车时间-启程
    private LinearLayout llPickUpPoint;//出发地点-启程
    private TextView tvPickUpPoint;//出发地点-启程
    private LinearLayout llDestination;//目的地-启程
    private TextView tvDestination;//目的地-启程
    private LinearLayout llReturnTime;//上车时间-返程
    private TextView tvReturnTime;//上车时间-返程
    private LinearLayout llReturnPlace;//出发地点-返程
    private TextView tvReturnPlace;//出发地点-返程
    private LinearLayout llPickUpTime;//上车时间-启程
    private TextView tvReturnDestination;//目的地-返程
    private TextView tvLastStep, tvNextStep;//上一步下一步
    private EditText etName;
    private EditText etTel;
    private TagFlowLayout tflRemarks;
    private Context mContext;
    private TextView viewTel;                    //手机
    private View viewName;                   //姓名
    private String CardValue;
    private String CardType;
//
    private ImageView ivCarBg;
    private RelativeLayout rlCar;
    /*View的显示与隐藏*/
    private TextView tvViewPickUpTime;//上车红View
    private LinearLayout llPickUpTimes;//VIew
    private LinearLayout tvViewPickUpPoint;//出发地点红色View
    private LinearLayout llViewDestination;//目的地的View
    private LinearLayout llReturnTimeView;//返程 上车时间
    private LinearLayout llReturnPlaceView;//返程 出发地点
    private LinearLayout llReturnDestination;//目的地-返程
    private LinearLayout llRetrunDestinationView;//目的地-返程
    private LinearLayout llPassenger;//手机 姓名
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
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();//异步请求控制器
    private List<CarListBean.DataBean> typeData1 = new ArrayList<>();
    private List<CarListBean.DataBean> mData = new ArrayList<>();
    private int ProductID;
    private int CarModelID;
    private CarListBean.DataBean CurrentData = new CarListBean.DataBean();
    private CreateOrderBena createOrderData;
    private String CityCode;
    private String CityName;
    private PopupWindow mWindow;
    private View contentView;
    private MyGridView gvPopItem;
    private RemarksAdapter mRemarksAdapter;
    //备注
    private List<RemarksBean> allRemarkItemList = new ArrayList<>();
    private List<RemarksBean> seletedRemarkItemList = new ArrayList<>();
    private List<RemarksBean> lastSeletedRemarkItemList = new ArrayList<>();//记录要选择之前的状态
    private CarListAdapter carPagerAdapter;
    private String getAddress;
    private String reCarAddressFrom;
    private String reCarAddressTo;
    private String CarAddressFrom;
    private String CarAddressTo;
    private String PassengerName;
    private String PassengerTel;
    private String CardNo = "";//卡券的卡号
    private int serviceType = 7;
    private String RentDate = "";   //上车时间
    private String RentDatePickUp = "";
    private String Remark = "";   //备注
    private String reRentDate = "";
    private String reRentDateReturn = "";
    /*静态文字展示*/
    private TextView tvStringServiceName;
    private TextView tvStringTransferService;//
    private TextView tvStringTransferSelector;
    private TextView tvStringModelIntroduce;
    private TextView tvStringTransferTime;
    private TextView tvStringDepartureInformation;
    private TextView tvStringReturnInformation;
    private TextView tvStringRemarks;
    private TextView tvStringTransferPrompt;
    private TextView tvStringTransferPromptContent;
    private TextView tvStringTransferHotLine;
    private TextView tvStringHotLineC;
    private TextView tvStringHotLineNumber;
    private int aboardY = 0;//上车时间
    private int aboardM = 0;
    private int aboardD = 0;
    private int aboardH = 0;
    private int aboardm = 0;
    private int aboardS = 0;

    private ImageView ivIconDownLi1, ivIconDownLi2;
    private String CouponDate="";
    private TagAdapter tflAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuttle_abc);
        mContext = this;
        Bundle b = getIntent().getBundleExtra("data");
        createOrderData = (CreateOrderBena) b.getSerializable(ConfirmationOfOrderActivity.CREATE_ORDER_CODE);
        couponData = (CouponListBean.DataBean) b.getSerializable(CouponListActivity.COUPONDATA);
        serviceType = createOrderData.getServiceType();
        if (couponData != null) {
            CardNo = couponData.getCardNo();
            CardValue = String.valueOf(couponData.getCardValue());
            CardType = String.valueOf(couponData.getCardType());
            CouponDate=couponData.getValidDate();
        } else {
            CardNo = "";
            CardValue = "0";
            CardType = "0";
        }
        initView();
        getData();
        if (StrUtils.isEmpty(PublicApplication.loginInfo.getString("ServiceTel", ""))) {
            getPersonalInformation();
        } else {
            String serviceTel = PublicApplication.loginInfo.getString("ServiceTel", "");
            StringBuffer buf = new StringBuffer(serviceTel);
            String aString = "-";
            buf.insert(3, aString);
            buf.insert(7, aString);
            tvStringHotLineNumber.setText(buf);
        }
    }

    /*获取请求数据*/
    private void getData() {
        showDialog();
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = MeModel.carList(CardNo, createOrderData.getCityCode(), 7);
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
            resetRemarkData();
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
            Glide.with(this)
                    .load(data.get(postion).getCarBgImageUrl())
                    .asBitmap()
                    .fitCenter()
                    .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            int imgh = resource.getHeight();
                            int imgw = resource.getWidth();
                            int wdith = ShuttleABCActivity.this.wdith - Utlis.dipTopx(ShuttleABCActivity.this, 40);
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
        initTitle();
        initContent();
        initViewPager();
        initStaticTextDisplay();
    }

    /*展示静态文本*/
    private void initStaticTextDisplay() {
        tvStringServiceName = findViewById(R.id.tv_service_name);
        tvStringServiceName.setText(PublicApplication.resourceText.getString("app_index_service_04_c", getResources().getString(R.string.app_index_service_04_c)));
        tvStringTransferService = findViewById(R.id.tv_string_transfer_service);
        tvStringTransferService.setText(PublicApplication.resourceText.getString("app_shuttleABC_service_introduce", getResources().getString(R.string.app_shuttleABC_service_introduce)));
        tvStringTransferSelector = findViewById(R.id.tv_string_transfer_selector);
        tvStringTransferSelector.setText(PublicApplication.resourceText.getString("app_transfer_selecter", getResources().getString(R.string.app_transfer_selecter)));
        tvStringModelIntroduce = findViewById(R.id.tv_model_introduce);
        tvStringModelIntroduce.setText(PublicApplication.resourceText.getString("app_transfer_model_introduce", getResources().getString(R.string.app_transfer_model_introduce)));
        tvStringTransferTime = findViewById(R.id.tv_string_transfer_time);
        tvStringTransferTime.setText(PublicApplication.resourceText.getString("app_transfer_time", getResources().getString(R.string.app_transfer_time)));
        tvStringDepartureInformation = findViewById(R.id.tv_string_departure_information);
        tvStringDepartureInformation.setText(PublicApplication.resourceText.getString("app_shuttleABC_form_departure_information", getResources().getString(R.string.app_shuttleABC_form_departure_information)));
        tvStringReturnInformation = findViewById(R.id.tv_string_information);
        tvStringReturnInformation.setText(PublicApplication.resourceText.getString("app_shuttleABC_form_return_information", getResources().getString(R.string.app_shuttleABC_form_return_information)));
        tvStringRemarks = findViewById(R.id.tv_string_remarks);
        tvStringRemarks.setText(PublicApplication.resourceText.getString("app_transfer_form_remarks", getResources().getString(R.string.app_transfer_form_remarks)));
        tvStringTransferPrompt = findViewById(R.id.tv_string_transfer_prompt);
        tvStringTransferPrompt.setText(PublicApplication.resourceText.getString("app_transfer_prompt", getResources().getString(R.string.app_transfer_prompt)));
        tvStringTransferPromptContent = findViewById(R.id.tv_string_prompt_content);
        tvStringTransferPromptContent.setText(PublicApplication.resourceText.getString("app_transfer_prompt_content", getResources().getString(R.string.app_transfer_prompt_content)));
        tvStringTransferHotLine = findViewById(R.id.tv_string_transfer_hotline);
        tvStringTransferHotLine.setText(PublicApplication.resourceText.getString("app_transfer_hotline", getResources().getString(R.string.app_transfer_hotline)));
        tvStringHotLineC = findViewById(R.id.tv_string_hotline_c);
        tvStringHotLineC.setText(PublicApplication.resourceText.getString("app_transfer_hotline_c", getResources().getString(R.string.app_transfer_hotline_c)));
        tvStringHotLineNumber = findViewById(R.id.tv_string_hotline_number);
        tvStringHotLineNumber.setText(PublicApplication.loginInfo.getString("ServiceTel", ""));

    }

    private void initViewPager() {
        vpShangwu = findViewById(R.id.vp_shangwu);
        vpShangwu.setLayoutParams(new RelativeLayout.LayoutParams(wdith, wdith / 5 * 2));
        carPagerAdapter = new CarListAdapter(this, wdith);
        vpShangwu.setPageMargin(20);//设置page间间距，自行根据需求设置
        vpShangwu.setOffscreenPageLimit(3);//>=3
        vpShangwu.setAdapter(carPagerAdapter);
        //setPageTransformer 决定动画效果
        vpShangwu.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

    private void initContent() {
        contentView = LayoutInflater.from(mContext).inflate(R.layout.popuplayout, null);
        tflRemarks = findViewById(R.id.tfl_remarks);
        setTflAdapter();
        tflRemarks.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
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
        viewTel = findViewById(R.id.view_tel);
        tvViewPickUpTime = findViewById(R.id.tv_view_pick_up_time);
        tvViewPickUpPoint = findViewById(R.id.tv_view_pick_up_point);
        llViewDestination = findViewById(R.id.ll_view_destination);
        llReturnTimeView = findViewById(R.id.ll_return_time_view);
        llReturnPlaceView = findViewById(R.id.ll_return_place_view);
        etName.setFilters(new InputFilter[]{Utlis.typeFilter});
        viewName = findViewById(R.id.view_name);
        etName.addTextChangedListener(twName);
        etName.setFilters(new InputFilter[]{Utlis.typeFilter});
        etTel.addTextChangedListener(twTel);
        llPickUpTime = findViewById(R.id.ll_pick_up_time);
        llPickUpTime.setOnClickListener(this);
        llPickUpTimes = findViewById(R.id.ll_pick_up_times);
        llDestination = findViewById(R.id.ll_destination);

        llDestination.setOnClickListener(this);
        tvDestination = findViewById(R.id.tv_destination);
        tvPickUpPoint = findViewById(R.id.tv_pick_up_point);
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
        tvPlace = findViewById(R.id.tv_place);
        tvPlace.setText(createOrderData.getCityName());
        ivInfo = findViewById(R.id.iv_info);
        tvTransportTime = findViewById(R.id.tv_transport_time);
        tvPrice = findViewById(R.id.tv_price);
        llPickUpPoint = findViewById(R.id.ll_pick_up_point);
        llPickUpPoint.setOnClickListener(this);
        llReturnTime = findViewById(R.id.ll_return_time);
        llReturnTime.setOnClickListener(this);
        tvReturnPlace = findViewById(R.id.tv_return_place);
        tvReturnTime = findViewById(R.id.tv_return_time);
        llReturnPlace = findViewById(R.id.ll_return_place);
        llReturnPlace.setOnClickListener(this);
        llReturnDestination = findViewById(R.id.ll_return_destination);
        llReturnDestination.setOnClickListener(this);
        llRetrunDestinationView = findViewById(R.id.ll_retrun_destination_view);
        tvReturnDestination = findViewById(R.id.tv_return_destination);
        llPassenger = findViewById(R.id.ll_passenger);

        tvLastStep = findViewById(R.id.tv_last_step);
        tvLastStep.setOnClickListener(this);
        tvLastStep.setText(PublicApplication.resourceText.getString("app_transfer_back", getResources().getString(R.string.app_transfer_back)));
        tvNextStep = findViewById(R.id.tv_next_step);
        tvNextStep.setOnClickListener(this);
        tvNextStep.setText(PublicApplication.resourceText.getString("app_transfer_goto", getResources().getString(R.string.app_transfer_goto)));
        seletedRemarkItemList.add(new RemarksBean());

        ivIconDownLi1 = findViewById(R.id.iv_icon_down_li1);
        ivIconDownLi2 = findViewById(R.id.iv_icon_down_li2);
        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_icon_down_li",""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_icon_down_li))
                .error(getResources().getDrawable(R.drawable.img_icon_down_li))
                .into(ivIconDownLi1);
        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_icon_down_li",""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_icon_down_li))
                .error(getResources().getDrawable(R.drawable.img_icon_down_li))
                .into(ivIconDownLi2);

        rlCar.setLayoutParams(new LinearLayout.LayoutParams(this.wdith, this.wdith * 2 / 5));
        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_banner", ""))
                .asBitmap()
                .error(R.drawable.img_banner)
                .placeholder(R.drawable.img_banner)
                .into(ivCarBg);


    }

    private void initTitle() {
        ivTopBack = findViewById(R.id.iv_top_back);
        ivTopBack.setOnClickListener(this);
        ivTopBack.setVisibility(View.VISIBLE);
        tvTopTitle = findViewById(R.id.tv_top_title);
        tvTopTitle.setText(PublicApplication.resourceText.getString("app_index_service_04_c", getResources().getString(R.string.app_index_service_04_c)));

        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_go_back",""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_go_back))
                .error(getResources().getDrawable(R.drawable.img_go_back))
                .into(ivTopBack);

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
                if (!tvPickUpPoint.getText().toString().equals("出发地点"))
                    intent.putExtra(AddressActivity.ADDRESS, tvPickUpPoint.getText().toString());
                startActivityForResult(intent, PickUpPointCode);
                break;
            case R.id.ll_pick_up_time:
                pickerEnum = TimePickerEnum.FromTime;
                MyDatePicker myDatePicker = new MyDatePicker(mContext);
                String time2 = MyDateUtils.getTime();
                long minTime = Long.parseLong(time2) + (4 * 60 * 60);
                if (tvPickUpTime.getText().equals("上车时间")) {
                    myDatePicker.showDateAndTimePickerDialog(this.wdith, 0, 0, 0, 0, 0, minTime + "",findViewById(R.id.ll_pop));
                } else {
                    if(!StrUtils.isEmpty(CouponDate)) {
                        int yearMin = Integer.valueOf(CouponDate.substring(0, 4));
                        int monthMin = Integer.valueOf(CouponDate.substring(5, 7));
                        int dayMin = Integer.valueOf(CouponDate.substring(8, 10));
                        int hourMin = Integer.valueOf(CouponDate.substring(11, 13));
                        int minuteMin = Integer.valueOf(CouponDate.substring(14, 16));
                        int minuteSecond = Integer.valueOf(CouponDate.substring(17, 19));
                        String Str=String.valueOf(yearMin+"-"+monthMin+"-"+dayMin+" "+hourMin+":"+minuteMin+":"+minuteSecond);
                        String s = MyDateUtils.dataOne1(Str);
                        long maxTime = Long.parseLong(s);
                        myDatePicker.showDateAndTimePickerDialog(this.wdith, aboardY, aboardM, aboardD, aboardH, aboardm, minTime + "",maxTime+"",findViewById(R.id.ll_pop));
                    }else
                    {
                        myDatePicker.showDateAndTimePickerDialog(this.wdith, aboardY, aboardM, aboardD, aboardH, aboardm, minTime + "",findViewById(R.id.ll_pop));
                    }
                }
                break;
            case R.id.ll_destination:
                Intent intent2 = new Intent(this, AddressActivity.class);
                if (!tvDestination.getText().toString().equals("目的地"))
                    intent2.putExtra(AddressActivity.ADDRESS, tvDestination.getText().toString());
                startActivityForResult(intent2, DestinationCode);
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
            case R.id.ll_return_destination:
                Intent intent3 = new Intent(this, AddressActivity.class);
                if (!tvReturnDestination.getText().toString().equals("目的地"))
                    intent3.putExtra(AddressActivity.ADDRESS, tvReturnDestination.getText().toString());
                startActivityForResult(intent3, ReturnDestinationCode);
                break;
            case R.id.ll_return_time:
                /*获取当前系统时间*/
                pickerEnum = ReturnTime;
                showDatePicker();
                break;
            case R.id.tv_next_step://下一步
                nextStepCommit();
                break;
            case R.id.tv_last_step://上一步
                finish();

                break;
        }
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

    private void showDatePicker() {
        if (RentDatePickUp.length() == 0) {
            showToast("温馨提示：请先选择启程时间", 3);
            return;
        }
        int yearMin = Integer.valueOf(RentDatePickUp.substring(0, 4));
        int monthMin = Integer.valueOf(RentDatePickUp.substring(5, 7));
        int dayMin = Integer.valueOf(RentDatePickUp.substring(8, 10));
        int hourMin = Integer.valueOf(RentDatePickUp.substring(11, 13));

        int minuteMin = Integer.valueOf(RentDatePickUp.substring(14, 16));
        int minSecond = Integer.valueOf(RentDatePickUp.substring(17, 19));
        String time = MyDateUtils.dataOne1(RentDatePickUp);
        MyDatePicker myDatePicker = new MyDatePicker(ShuttleABCActivity.this);
        myDatePicker.showDateAndTimePickerDialog(wdith, yearMin, monthMin, dayMin, hourMin, minuteMin, time,findViewById(R.id.ll_pop));

    }

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


    private void setTflAdapter() {
        tflAdapter = new TagAdapter<RemarksBean>(seletedRemarkItemList){
            @Override
            public View getView(FlowLayout parent, int position, RemarksBean o) {
                if (position == 0){
                    LinearLayout iv = (LinearLayout) LayoutInflater.from(ShuttleABCActivity.this).inflate( R.layout.item_tfl_iv,
                            tflRemarks,false);
                    return iv ;
                }else{
                    //正常标签的布局
                    LinearLayout tv = (LinearLayout) LayoutInflater.from(ShuttleABCActivity.this).inflate( R.layout.item_tfl_tv,
                            tflRemarks,false);

                    TextView viewById =  tv.findViewById(R.id.tv_name);
                    viewById.setText(o.getContent());
                    return tv;
                }
            }
        };

        tflRemarks.setAdapter(tflAdapter);
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
    public void positiveListener(int Year, int Month, int Day, int Hour, int Mint) {
        aboardY = Year;
        aboardM = Month;
        aboardD = Day;
        aboardH = Hour;
        aboardm = Mint;

        switch (pickerEnum) {
            case FromTime:
                llPickUpTimes.setVisibility(View.GONE);
                tvPickUpTime.setText(String.valueOf(Year + "-" + Month + "-" + Day + " " + Hour + ":" + Mint));
                RentDatePickUp = Year + "-" + to2(Month + "") + "-" + to2(Day + "") + " " + to2(Hour + "") + ":" + to2(Mint + "") + ":00";
                llPickUpTimes.setVisibility(View.GONE);
                tvViewPickUpTime.setVisibility(View.GONE);
                RentDate = Year + "-" + to2(Month + "") + "-" + to2(Day + "") + " " + to2(Hour + "") + ":" + to2(Mint + "");

                break;
            case ReturnTime:
                reRentDateReturn = Year + "-" + to2(Month + "") + "-" + to2(Day + "") + " " + to2(Hour + "") + ":" + to2(Mint + "") + ":00";
                if (reRentDateReturn.equals(RentDatePickUp)) {
                    showToast("请选择启程时间之后的时间", 3);
                    llReturnTimeView.setVisibility(View.VISIBLE);
                    return;
                }
                llReturnTimeView.setVisibility(View.GONE);
                tvReturnTime.setText(String.valueOf(Year + "-" + Month + "-" + Day + " " + Hour + ":" + Mint));
                reRentDate = Year + "-" + to2(Month + "") + "-" + to2(Day + "") + " " + to2(Hour + "") + ":" + to2(Mint + "");
                break;
        }
    }

    private String to2(String str) {
        return str.length() == 1 ? 0 + str : str;
    }

    @Override
    public void negativeListener() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PickUpPointCode) {//上车地点
            if (data != null && resultCode == 0) {
                getAddress = data.getStringExtra("address");
                if (!TextUtils.isEmpty(tvDestination.getText())&& getAddress.equals(tvDestination.getText().toString())){
                    showToast(getResources().getString(R.string.app_address_error), 3);
                } else {
                    tvPickUpPoint.setText(getAddress);
                    tvViewPickUpPoint.setVisibility(View.GONE);
                }
            }
        } else if (requestCode == DestinationCode) {//目的地  启程
            if (data != null && resultCode == 0) {
                getAddress = data.getStringExtra("address");
                if (!TextUtils.isEmpty(tvPickUpPoint.getText())&& getAddress.equals(tvPickUpPoint.getText().toString())){
                    showToast(getResources().getString(R.string.app_address_error), 3);
                } else {
                    tvDestination.setText(getAddress);
                    tvReturnPlace.setText(getAddress);
                    llViewDestination.setVisibility(View.GONE);
                    llReturnPlaceView.setVisibility(View.GONE);
                }
            }
        } else if (requestCode == ReturnDestinationCode) {//目的地 返程
            if (data != null && resultCode == 0) {
                getAddress = data.getStringExtra("address");
                if (!TextUtils.isEmpty(tvDestination.getText())&& getAddress.equals(tvDestination.getText().toString())){
                    showToast(getResources().getString(R.string.app_address_error), 3);
                } else {
                    tvReturnDestination.setText(getAddress);
                    llRetrunDestinationView.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mCompositeDisposable != null)
            mCompositeDisposable.clear();
        dismissDialog();
    }

    /*需要判断ProdcutId  CarModelID CityCode RentDate  CarAddressFrom  CarAddressTo   PassengerName,PassengerTel*/
    private void nextStepCommit() {
        boolean isHttp = true;
        CarAddressFrom = tvPickUpPoint.getText().toString();
        CarAddressTo = tvDestination.getText().toString();
        PassengerName = etName.getText().toString();
        PassengerTel = etTel.getText().toString();
//        String pickUpDate = tvPickUpTime.getText().toString();
        reCarAddressFrom = tvReturnPlace.getText().toString();
        reCarAddressTo = tvReturnDestination.getText().toString();
        ProductID = Integer.parseInt(CurrentData.getProductID());
        CarModelID = Integer.parseInt(CurrentData.getCarModelID());
        CityCode = createOrderData.getCityCode();
        CityName = createOrderData.getCityName();

//        reRentDate = tvReturnTime.getText().toString();
        String fanchengTime = tvReturnTime.getText().toString();
        if (StrUtils.isEmpty(RentDate) || RentDate.equals("上车时间")) {
            llPickUpTimes.setVisibility(View.VISIBLE);
            tvViewPickUpTime.setVisibility(View.VISIBLE);
            isHttp = false;
        }

        if (StrUtils.isEmpty(CarAddressFrom) || CarAddressFrom.equals("出发地点")) {
            tvViewPickUpPoint.setVisibility(View.VISIBLE);
            isHttp = false;
        }
        if (StrUtils.isEmpty(CarAddressTo) || CarAddressTo.equals("目的地")) {
            llViewDestination.setVisibility(View.VISIBLE);
            isHttp = false;
        }
        /*返程的出发地点和 正常的目的地相同*/
        if (StrUtils.isEmpty(reCarAddressFrom) || reCarAddressFrom.equals("出发地点")) {
            llReturnPlaceView.setVisibility(View.VISIBLE);
            isHttp = false;
        }

        if (StrUtils.isEmpty(fanchengTime) || fanchengTime.equals("上车时间")) {
            llReturnTimeView.setVisibility(View.VISIBLE);
            isHttp = false;
        }
        if (StrUtils.isEmpty(reCarAddressTo) || reCarAddressTo.equals("目的地")) {
            llRetrunDestinationView.setVisibility(View.VISIBLE);
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
        if (isHttp)
            nextStepData();

    }
    JSONObject orderString;
    JSONObject msgOrderString = new JSONObject();//性别

    /*对象存放代金券  次卡  活动卡*/
    public JSONObject JsonObjectData(JSONObject JsonObject, int ProductID,int CarModelID,String CarCity,String CarCityThree,String CarBrand,String TimeBoard,String ServiceCar,String PassengerName,
                                     String PassengerMobile,String Remark,int CarTypeID,String PaymentType,String FlightNumber,String StartPlace,String Destination,String ReturnTimeBoard,
                                     String ReturnStartPlace,String ReturnDestination,String CardNo,String CardValue) {
        try {
            JsonObject.put("ProductID", ProductID+"");
            JsonObject.put("CarModelID",CarModelID+"");
            JsonObject.put("CarCity", CarCity);
            JsonObject.put("CarCityThree",CarCityThree);
            JsonObject.put("CarBrand",CarBrand);
            JsonObject.put("TimeBoard",TimeBoard);
            JsonObject.put("ServiceCar",ServiceCar);
            JsonObject.put("PassengerName",PassengerName);
            JsonObject.put("PassengerMobile",PassengerMobile);
            JsonObject.put("Remark",Remark);
            JsonObject.put("CarTypeID",CarTypeID);
            JsonObject.put("PaymentType",PaymentType);
            JsonObject.put("FlightNumber",FlightNumber);
            JsonObject.put("StartPlace",StartPlace);
            JsonObject.put("Destination",Destination);
            JsonObject.put("ReturnTimeBoard",ReturnTimeBoard);
            JsonObject.put("ReturnStartPlace",ReturnStartPlace);
            JsonObject.put("ReturnDestination",ReturnDestination);
            JsonObject.put("CardNo",CardNo);
            JsonObject.put("CardValue",CardValue+"");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return JsonObject;
    }

    private void nextStepData() {
        Log.e(TAG+"RentDate1",reRentDate);
        Log.e(TAG+"RentDate2",RentDate);


        showDialog();
        if (CardNo == null)
            CardNo = "";
        // TODO: 2018/4/19 name改为测试
        orderString = JsonObjectData(msgOrderString,ProductID,CarModelID, createOrderData.getCityName(),CityCode,CurrentData.getCarBrand(),RentDate,CurrentData.getCarModel(),PassengerName+"测试数据",
                PassengerTel,Remark, Integer.parseInt(CardType),"","",CarAddressFrom,CarAddressTo,reRentDate, reCarAddressFrom, reCarAddressTo,CardNo, CardValue);

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

                                Intent intent = new Intent();
                                Bundle mbundle = new Bundle();
                                mbundle.putSerializable(CouponListActivity.COUPONDATA, couponData);
                                intent.putExtra("data", mbundle);
                                intent.putExtra(ConfirmationOfOrderActivity.ORDERID, data.getData().getOrderID());
                                intent.putExtra(ConfirmationOfOrderActivity.FROM_PAGE, "1");
                                intent.setClass(ShuttleABCActivity.this, ConfirmationOfOrderActivity.class);
                                ScreenManager.getScreenManager().RemoveAllExceptedOne(MainActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case 500:

                                CustomToast.showFailYellowToast(ShuttleABCActivity.this, data.get_metadata().getMessage());
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

    enum TimePickerEnum {
        FromTime, ReturnTime;
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

}
