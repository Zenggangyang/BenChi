package com.soowin.staremblem.ui.index.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
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
import com.soowin.staremblem.ui.index.bean.FlinghtInforBean;
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
 * 接送机页
 */
public class TransferActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, MyDatePicker.TimePickerInterface {
    private static final String TAG = TransferActivity.class.getCanonicalName();
    List<FlinghtInforBean.DataBean> FlightData = new ArrayList<>();
    JSONObject orderString;
    JSONObject msgOrderString = new JSONObject();//性别
    boolean isHttp = true;
    boolean TimeRight = true;
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
    private TextView tvTimeParameter;       //时限内容
    private RelativeLayout rlCar;
    private ImageView ivCarBg;
    private ViewPager vpCar;                //滑动选择控件
    private LinearLayout llFormFlightDate;  //起飞日期
    private TextView tvFormFlightDate;      //起飞日期
    private EditText etFormFlightNumber;    //航班号
    private LinearLayout llFormTime;        //上车时间
    private TextView tvFormTime;            //上车时间
    private LinearLayout llFormDestination; //目的地
    private TextView tvFormDestination;     //目的地
    private EditText etFormName;            //姓名
    private EditText etFormPhone;           //手机
    private TagFlowLayout tflRemarks;           //备注的流布局
    private LinearLayout llPop;             //popWinddow负载页面
    //提示内容
    private TextView tvFormFlightDatePrompt;        //起飞日期提示
    private LinearLayout llFormFlightNumberPrompt;  //航班号提示
    private TextView tvFormFlightNumberPrompt;      //航班号提示
    private TextView tvFormTimePrompt;              //上车时间提示
    private TextView tvFormDestinationPrompt;       //目的地提示
    private LinearLayout llNamePrompt;              //姓名提示
    private TextView tvNamePrompt;              //姓名提示
    private TextView tvPhonePrompt;             //手机提示
    //静态文字
    private RadioButton rbType01;      //类型1
    private RadioButton rbType02;      //类型2
    private TextView tvFormType;       //服务类型
    private TextView tvTime;           //用车时限
    private TextView tvModelIntroduce; //车型信息
    private TextView tvSelecter;       //请选择车型
    private TextView tvFormRemarks;    //请选择备注
    private TextView tvPrompt;         //用车小贴士
    private TextView tvPromptContent;  //小贴士内容
    private TextView tvBack;           //上一步
    private TextView tvGoto;           //下一步
    private TextView tvHotline;        //Hotline
    private TextView tvHotlineC;       //服务热线
    private TextView tvHotlineNumber;  //热线电话
    //数据****************************************************************
    private String cardNo = "";
    private String CardValue = "";
    private String CarTypeID = "";
    private String cityCode = "";
    private int serviceType = 0;
    private List<CarListBean.DataBean> typeData1 = new ArrayList<>();
    private List<CarListBean.DataBean> typeData2 = new ArrayList<>();
    private List<CarListBean.DataBean> mData = new ArrayList<>();
    //banner
    private CarListAdapter mAdapter;
    //订单生成所用数据
    private CreateOrderBena createOrderData;
    //卡券Data数据
    private CouponListBean.DataBean couponData;
    //异步请求控制器
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    //备注部分
    private PopupWindow mPopupWindow;
    private View contentView;
    private MyGridView gvPopItem;
    //备注
    private List<RemarksBean> allRemarkItemList = new ArrayList<>();
    private List<RemarksBean> seletedRemarkItemList = new ArrayList<>();
    private List<RemarksBean> lastSeletedRemarkItemList = new ArrayList<>();//记录要选择之前的状态
    private RemarksAdapter mRemarksAdapter;
    private String Remark = "";//备注值
    //日期部分
    private String cityName = "";
    private int dateY = 0;//起飞日期
    private int dateM = 0;
    private int dateD = 0;
    private String flightNumber = "";//航班号
    private int aboardY = 0;//上车时间
    private int aboardM = 0;
    private int aboardD = 0;
    private int aboardH = 0;
    private int aboardm = 0;

    private ImageView ivQiFeiTime;
    private ImageView ivShangCheTime;
    private String CouponDate = "";
    private TagAdapter tflAdapter;

    private Boolean TimeTrue = false;
    private String RentDate = "";   //上车时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        ScreenManager.getScreenManager().pushActivity(this);//加入栈
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
            tvHotlineNumber.setText(buf);
        }
    }

    private void initData() {
        //初始化数据
        Bundle b = getIntent().getBundleExtra("data");
        createOrderData = (CreateOrderBena) b.getSerializable(ConfirmationOfOrderActivity.CREATE_ORDER_CODE);
        couponData = (CouponListBean.DataBean) b.getSerializable(CouponListActivity.COUPONDATA);
        if (createOrderData != null) {
            serviceType = createOrderData.getServiceType();
            cityCode = createOrderData.getCityCode();
            cityName = createOrderData.getCityName();
        }
        if (couponData != null) {
            cardNo = couponData.getCardNo();
            CardValue = String.valueOf(couponData.getCardValue());
            CarTypeID = String.valueOf(couponData.getCardType());
            CouponDate = couponData.getValidDate();
        } else {
            cardNo = "";
            CardValue = "0";
            CarTypeID = "0";
        }
    }

    private void initView() {
        initTitle();
        initContent();
    }

    private void initContent() {
        ivQiFeiTime = findViewById(R.id.iv_qifei_date);
        ivShangCheTime = findViewById(R.id.iv_shangche_time);
        rlCar = findViewById(R.id.rl_car);
        ivCarBg = findViewById(R.id.iv_car_bg);

        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_icon_down_li", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_icon_down_li))
                .error(getResources().getDrawable(R.drawable.img_icon_down_li))
                .into(ivQiFeiTime);
        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_icon_down_li", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_icon_down_li))
                .error(getResources().getDrawable(R.drawable.img_icon_down_li))
                .into(ivShangCheTime);
        tvServiceName = findViewById(R.id.tv_service_name);
        tvServiceIntroduce = findViewById(R.id.tv_service_introduce);
        tvPrice = findViewById(R.id.tv_price);
        tvSeries = findViewById(R.id.tv_series);
        tvModel = findViewById(R.id.tv_model);
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
        tvTimeParameter = findViewById(R.id.tv_time_parameter);
        vpCar = findViewById(R.id.vp_car);
        llFormFlightDate = findViewById(R.id.ll_form_flight_date);
        tvFormFlightDate = findViewById(R.id.tv_form_flight_date);
        etFormFlightNumber = findViewById(R.id.et_form_flight_number);
        llFormTime = findViewById(R.id.ll_form_time);
        tvFormTime = findViewById(R.id.tv_form_time);
        llFormDestination = findViewById(R.id.ll_form_destination);
        tvFormDestination = findViewById(R.id.tv_form_destination);
        etFormName = findViewById(R.id.et_form_name);
        etFormName.setFilters(new InputFilter[]{Utlis.typeFilter});
        etFormPhone = findViewById(R.id.et_form_phone);


        tvFormFlightDatePrompt = findViewById(R.id.tv_form_flight_date_prompt);
        llFormFlightNumberPrompt = findViewById(R.id.ll_form_flight_number_prompt);
        tvFormFlightNumberPrompt = findViewById(R.id.tv_form_flight_number_prompt);
        tvFormTimePrompt = findViewById(R.id.tv_form_time_prompt);
        tvFormDestinationPrompt = findViewById(R.id.tv_form_destination_prompt);
        llNamePrompt = findViewById(R.id.ll_name_prompt);
        tvNamePrompt = findViewById(R.id.tv_name_prompt);
        tvPhonePrompt = findViewById(R.id.tv_phone_prompt);

        rbType01 = findViewById(R.id.rb_type_01);
        rbType02 = findViewById(R.id.rb_type_02);
        tvBack = findViewById(R.id.tv_back);
        tvGoto = findViewById(R.id.tv_goto);
        tvFormType = findViewById(R.id.tv_form_type);
        tvTime = findViewById(R.id.tv_time);
        tvModelIntroduce = findViewById(R.id.tv_model_introduce);
        tvSelecter = findViewById(R.id.tv_selecter);
        tvFormRemarks = findViewById(R.id.tv_form_remarks);
        tvPrompt = findViewById(R.id.tv_prompt);
        tvPromptContent = findViewById(R.id.tv_prompt_content);
        tvHotline = findViewById(R.id.tv_hotline);
        tvHotlineC = findViewById(R.id.tv_hotline_c);
        tvHotlineNumber = findViewById(R.id.tv_hotline_number);

        //静态文字展示
        tvFormType.setText(PublicApplication.resourceText.getString("app_transfer_form_type", getResources().getString(R.string.app_transfer_form_type)));
        tvServiceName.setText(PublicApplication.resourceText.getString("app_transfer_service_name", getResources().getString(R.string.app_transfer_service_name)));
        tvServiceIntroduce.setText(PublicApplication.resourceText.getString("app_transfer_service_introduce", getResources().getString(R.string.app_transfer_service_introduce)));
        tvSelecter.setText(PublicApplication.resourceText.getString("app_transfer_selecter", getResources().getString(R.string.app_transfer_selecter)));
        tvModelIntroduce.setText(PublicApplication.resourceText.getString("app_transfer_model_introduce", getResources().getString(R.string.app_transfer_model_introduce)));
        tvTime.setText(PublicApplication.resourceText.getString("app_transfer_time", getResources().getString(R.string.app_transfer_time)));
        tvFormRemarks.setText(PublicApplication.resourceText.getString("app_transfer_form_remarks", getResources().getString(R.string.app_transfer_form_remarks)));
        tvPrompt.setText(PublicApplication.resourceText.getString("app_transfer_prompt", getResources().getString(R.string.app_transfer_prompt)));
        tvPromptContent.setText(PublicApplication.resourceText.getString("app_transfer_prompt_content", getResources().getString(R.string.app_transfer_prompt_content)));
        tvBack.setText(PublicApplication.resourceText.getString("app_transfer_back", getResources().getString(R.string.app_transfer_back)));
        tvGoto.setText(PublicApplication.resourceText.getString("app_transfer_goto", getResources().getString(R.string.app_transfer_goto)));
        tvHotline.setText(PublicApplication.resourceText.getString("app_transfer_hotline", getResources().getString(R.string.app_transfer_hotline)));
        tvHotlineC.setText(PublicApplication.resourceText.getString("app_transfer_hotline_c", getResources().getString(R.string.app_transfer_hotline_c)));
        rbType01.setOnCheckedChangeListener(this);
        rbType02.setOnCheckedChangeListener(this);
        tvBack.setOnClickListener(this);
        tvGoto.setOnClickListener(this);
        seletedRemarkItemList.add(new RemarksBean());

        rlCar.setLayoutParams(new LinearLayout.LayoutParams(this.wdith, this.wdith * 2 / 5));
        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_banner", ""))
                .asBitmap()
                .error(R.drawable.img_banner)
                .placeholder(R.drawable.img_banner)
                .into(ivCarBg);

        mAdapter = new CarListAdapter(TransferActivity.this, TransferActivity.this.wdith);
        vpCar.setAdapter(mAdapter);
        vpCar.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (serviceType == 1) {
                    setViewData(typeData1, position);
                } else {
                    setViewData(typeData2, position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (serviceType == 1)
            rbType01.setChecked(true);
        else
            rbType02.setChecked(true);
        //表单部分*****************************************************
        tflRemarks = findViewById(R.id.tfl_remarks);
        llPop = findViewById(R.id.ll_pop);

        llFormFlightDate.setOnClickListener(this);

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
        contentView = LayoutInflater.from(this).inflate(R.layout.popuplayout, null);
        contentView.findViewById(R.id.tv_pop_sure).setOnClickListener(this);
        contentView.findViewById(R.id.iv_pop_close).setOnClickListener(this);
        contentView.findViewById(R.id.ll_pop_outside).setOnClickListener(this);
        contentView.findViewById(R.id.ll_pop_inside).setOnClickListener(this);
        gvPopItem = contentView.findViewById(R.id.gv_pop_remark);
        mRemarksAdapter = new RemarksAdapter(this, allRemarkItemList, R.layout.item_remark);
        gvPopItem.setAdapter(mRemarksAdapter);
        gvPopItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean b = allRemarkItemList.get(position).isChecked();
                allRemarkItemList.get(position).setChecked(!b);
                mRemarksAdapter.notifyDataSetInvalidated();
            }
        });
        //航班号输入框部分
        etFormFlightNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                flightNumber = charSequence.toString();
                if (charSequence.length() > 0) {
                    getFlightNumber();
                    tvFormFlightNumberPrompt.setVisibility(View.INVISIBLE);
                    if (tvFormTimePrompt.getVisibility() == View.INVISIBLE)
                        llFormFlightNumberPrompt.setVisibility(View.GONE);
                    if (tvFormTimePrompt.getVisibility() == View.GONE)
                        llFormFlightNumberPrompt.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etFormFlightNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //在这里做请求操作
                    if (flightNumber.length() > 0) {
                        getFlightNumber();
                        tvFormFlightNumberPrompt.setVisibility(View.INVISIBLE);
                        if (tvFormTimePrompt.getVisibility() == View.INVISIBLE)
                            llFormFlightNumberPrompt.setVisibility(View.GONE);
                        if (tvFormTimePrompt.getVisibility() == View.GONE)
                            llFormFlightNumberPrompt.setVisibility(View.GONE);
                    }
                    return true;
                }
                return false;
            }
        });
        //上车时间
        llFormTime.setOnClickListener(this);
        //目的地
        llFormDestination.setOnClickListener(this);

        etFormName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    tvNamePrompt.setVisibility(View.INVISIBLE);
                    if (tvPhonePrompt.getVisibility() == View.INVISIBLE)
                        llNamePrompt.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etFormPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    tvPhonePrompt.setVisibility(View.INVISIBLE);
                    if (tvNamePrompt.getVisibility() == View.INVISIBLE)
                        llNamePrompt.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initTitle() {
        tvTitle = findViewById(R.id.tv_title);
        ivBack = findViewById(R.id.iv_back);
        tvTitle.setText(PublicApplication.resourceText.getString("app_transfer_service_name", getResources().getString(R.string.app_transfer_service_name)));
        //图片资源初始化
        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_go_back", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_go_back))
                .error(getResources().getDrawable(R.drawable.img_go_back))
                .into(ivBack);
        ivBack.setOnClickListener(this);
        ivBack.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                setClearData();
                break;
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_goto:
                createOrder();
                break;
            case R.id.ll_pop_outside:
                //点击的是popupwindow的外部
                mPopupWindow.dismiss();
                break;
            case R.id.ll_pop_inside:
                //点击的是popupwindow的内部
                break;
            case R.id.tv_pop_sure:
                resetRemarkData();
                mPopupWindow.dismiss();
                break;
            case R.id.iv_pop_close:
                restoreRemarkData();
                mPopupWindow.dismiss();
                break;
            case R.id.ll_form_flight_date://起飞日期
                showDateDialog();
                break;
            case R.id.ll_bg:
                if (mPopupWindow != null)
                    mPopupWindow.dismiss();
                break;
            case R.id.tv_cancel:
                if (mPopupWindow != null)
                    mPopupWindow.dismiss();
                break;
            case R.id.ll_form_time:
                MyDatePicker myDatePicker = new MyDatePicker(TransferActivity.this);
                String time2 = MyDateUtils.getTime();
                long minTime = Long.parseLong(time2) + (4 * 60 * 60);
                if (tvFormTime.getText().equals("上车时间")) {
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
            case R.id.ll_form_destination:
                Intent intent = new Intent(this, AddressActivity.class);
                if (!tvFormDestination.getText().toString().equals("目的地"))
                    intent.putExtra(AddressActivity.ADDRESS, tvFormDestination.getText().toString());
                startActivityForResult(intent, 0);
                break;

        }
    }

    /**
     * 创建订单
     * serviceType 服务类型（1 接机）  OrderSource (1 星徽尊享 2 app)  productID 产品ID carlist接口获取
     * carModelID 车型ID  CarCity 城市 CarCityThree 城市三字码  CarBrand 奔驰-车型 TimeBoard 用车时间
     * ServiceCar 车系  PassengerName 乘客姓名 PassengerMobile 乘客电话  PaymentType  支付方式
     * StartPlace 上车地址  Destination 下车地址  ReturnTimeBoard 返程上车时间  ReturnStartPlace 返程上车地址
     * ReturnDestination 返程下车地址  FlightNumber 航班号   CardNo 卡号  CardValue 卡值（卡的次数）
     * CarTypeID  卡类型id  Remark  备注
     */

    private void createOrder() {

        String date = tvFormFlightDate.getText().toString();
        final String number = etFormFlightNumber.getText().toString();
        final String time = tvFormTime.getText().toString();
        final String address = tvFormDestination.getText().toString();
        final String name = etFormName.getText().toString();
        final String phone = etFormPhone.getText().toString();
        if (StrUtils.isEmpty(date) || date.equals("起飞日期")) {
            tvFormFlightDatePrompt.setVisibility(View.VISIBLE);
            isHttp = false;
        }
        if (StrUtils.isEmpty(number) || number.equals("航班号")) {
            llFormFlightNumberPrompt.setVisibility(View.VISIBLE);
            tvFormFlightNumberPrompt.setVisibility(View.VISIBLE);
            isHttp = false;
        }
        if (StrUtils.isEmpty(time) || time.equals("上车时间")) {
            llFormFlightNumberPrompt.setVisibility(View.VISIBLE);
            tvFormTimePrompt.setVisibility(View.VISIBLE);
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
            }
        }
        if (StrUtils.isEmpty(address) || address.equals("目的地")) {
            tvFormDestinationPrompt.setVisibility(View.VISIBLE);
            isHttp = false;
        }
        if (StrUtils.isEmpty(name) || name.equals("姓名")) {
            llNamePrompt.setVisibility(View.VISIBLE);
            tvNamePrompt.setVisibility(View.VISIBLE);
            isHttp = false;
        }
        if (StrUtils.isEmpty(phone) || phone.equals("手机")) {
            llNamePrompt.setVisibility(View.VISIBLE);
            tvPhonePrompt.setVisibility(View.VISIBLE);
            tvPhonePrompt.setText(PublicApplication.resourceText.getString("app_transfer_prompt_input", getResources().getString(R.string.app_transfer_prompt_input)));
            isHttp = false;
        }
        if (!RegularUtil.checkMobile(phone)) {
            llNamePrompt.setVisibility(View.VISIBLE);
            tvPhonePrompt.setVisibility(View.VISIBLE);
            tvPhonePrompt.setText(PublicApplication.resourceText.getString("app_login_check_phone", getResources().getString(R.string.app_login_check_phone)));
            isHttp = false;
        }

        if (cardNo == null)
            cardNo = "";

     /*   接机上车地址=ArrPortName+ArrTerminal；
        送机目的地=DepPortName+DepTerminal； 用户输入地址*/
      /*  如果送机，目的地就是DepPortName+DepTerminal；上车地址是用户界面输入的*/
     /*但是接口我们要两个地址，上车地址和下车地址（目的地）都要有*/
        // TODO: 2018/4/19 name改为测试
        if (FlightData != null && FlightData.size() > 0 && !StrUtils.isEmpty(time)) {
            switch (serviceType) {

                case 1://接机
                    orderString = JsonObjectData(msgOrderString, createOrderData.getProductID(), createOrderData.getCarModelID(), createOrderData.getCityName(), createOrderData.getCityCode(), createOrderData.getCarBrand(), time, createOrderData.getServiceCar(), name + "测试数据",
                            phone, Remark, Integer.parseInt(CarTypeID), "", "" + number, FlightData.get(0).getArrPortName() + FlightData.get(0).getArrTerminal(), "", "" + address, "", "", "", cardNo, CardValue);
                    break;
                case 2://送机
                    orderString = JsonObjectData(msgOrderString, createOrderData.getProductID(), createOrderData.getCarModelID(), createOrderData.getCityName(), createOrderData.getCityCode(), createOrderData.getCarBrand(), time, createOrderData.getServiceCar(), name + "测试数据",
                            phone, Remark, Integer.parseInt(CarTypeID), "", "" + number, FlightData.get(0).getArrPortName() + FlightData.get(0).getArrTerminal(), "" + address, "", "", "", "", cardNo, CardValue);
                    break;
            }
        } else {
            isHttp = false;
        }
        if (isHttp) {
            if (!TimeTrue) {
                showToast("航班信息有误", 3);
            } else {
                showDialog();
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
                                        intent.setClass(TransferActivity.this, ConfirmationOfOrderActivity.class);
                                        startActivity(intent);
                                        finish();
                                        break;
                                    case 500:
                                        CustomToast.showFailYellowToast(TransferActivity.this, data.get_metadata().getMessage());
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
        } else {
            if (!TimeRight) {
                switch (serviceType) {
                    case 1://接机
                        CustomToast.showFailYellowToast(TransferActivity.this, PublicApplication.resourceText.getString("app_warm_prompt_airport_pick_up", getResources().getString(R.string.app_warm_prompt_airport_pick_up)));
                        break;
                    case 2://送机
                        CustomToast.showFailYellowToast(TransferActivity.this, PublicApplication.resourceText.getString("app_warm_prompt_airport_drop_off", getResources().getString(R.string.app_warm_prompt_airport_drop_off)));
                        break;
                }
            } else {
                CustomToast.showFailYellowToast(TransferActivity.this, "请填写正确的航班信息");
            }

        }
    }

    /*地址的回掉*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == 0) {
                tvFormDestination.setText(data.getStringExtra(AddressActivity.ADDRESS));
                tvFormDestinationPrompt.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 展示起飞日期选择器
     */
    @SuppressLint("WrongConstant")
    private void showDateDialog() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.item_date_select, null);
        mPopupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, true);

        final DatePicker dpDate = contentView.findViewById(R.id.dp_date);
        final TextView tvOk = contentView.findViewById(R.id.tv_ok);
        TextView tvCancel = contentView.findViewById(R.id.tv_cancel);
        LinearLayout llBg = contentView.findViewById(R.id.ll_bg);
        LinearLayout llFrame = contentView.findViewById(R.id.ll_frame);
        llBg.setOnClickListener(this);
        llFrame.setOnClickListener(this);
        tvCancel.setOnClickListener(this);

        long timeS = 0;
        long timeE = 0;
        timeS = System.currentTimeMillis() / 1000 - 1000;
        if (couponData != null && couponData.getUseDate() != null) {

            timeS = Long.parseLong(MyDateUtils.dataOne1(MyDateUtils.getDataStr(couponData.getUseDate()).substring(0, 19)));
            timeE = Long.parseLong(MyDateUtils.dataOne1(MyDateUtils.getDataStr(couponData.getValidDate()).substring(0, 19)));
            if (System.currentTimeMillis() / 1000 > timeS)
                timeS = System.currentTimeMillis() / 1000 - 1000;
            dpDate.setMaxDate(timeE * 1000);
        }
        if (timeS * 1000 < timeE * 1000) {
            dpDate.setMinDate(timeS * 1000);
        } else {
            Date date = new Date();
            long time = date.getTime();
            dpDate.setMinDate(time - 1000);

        }
        if (dateY != 0)
            dpDate.updateDate(dateY, dateM, dateD);

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateY = dpDate.getYear();
                dateM = dpDate.getMonth();
                dateD = dpDate.getDayOfMonth();
                tvFormFlightDate.setText(dateY + "-" + (dateM + 1) + "-" + dateD);
                tvFormFlightDatePrompt.setVisibility(View.GONE);
                getFlightNumber();
                mPopupWindow.dismiss();
            }
        });


        mPopupWindow.setTouchable(true);
        //如果不设置popupwindow的背景，无论是点击外部还是Back都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //显示PopupWindow
        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_transfer, null);
        mPopupWindow.setAnimationStyle(R.style.contextMenuAnim);
        mPopupWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
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

    /**
     * 备注选择部分
     */
    private void setTflAdapter() {
        tflAdapter = new TagAdapter<RemarksBean>(seletedRemarkItemList) {
            @Override
            public View getView(FlowLayout parent, int position, RemarksBean o) {
                if (position == 0) {
                    LinearLayout iv = (LinearLayout) LayoutInflater.from(TransferActivity.this).inflate(R.layout.item_tfl_iv,
                            tflRemarks, false);
                    return iv;
                } else {
                    //正常标签的布局
                    LinearLayout tv = (LinearLayout) LayoutInflater.from(TransferActivity.this).inflate(R.layout.item_tfl_tv,
                            tflRemarks, false);

                    TextView viewById = tv.findViewById(R.id.tv_name);
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
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(TransferActivity.this);
        }
        mPopupWindow.setContentView(contentView);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        contentView.setFocusable(true);
        contentView.setFocusableInTouchMode(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPopupWindow.setWidth(LinearLayoutCompat.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(LinearLayoutCompat.LayoutParams.MATCH_PARENT);
        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_transfer, null);
        mPopupWindow.setAnimationStyle(R.style.contextMenuAnim);
        rootview.getBackground().setAlpha(255);
        mPopupWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
        contentView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mPopupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
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
                    String result = MeModel.carList(cardNo, cityCode, 1);
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
                                        typeData1.clear();
                                        typeData1.addAll(data.getData());
                                        setViewData(typeData1, 0);
                                    }
                                    break;
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            showToast(PublicApplication.resourceText.getString("app_error_message", getResources().getString(R.string.app_error_message)), 3);
                            getHttpData2();
                        }

                        @Override
                        public void onComplete() {
                            getHttpData2();
                        }
                    });
        }
    }

    /*对象存放代金券  次卡  活动卡*/
    public JSONObject JsonObjectData(JSONObject JsonObject, int ProductID, int CarModelID, String CarCity, String CarCityThree, String CarBrand, String TimeBoard, String ServiceCar, String PassengerName,
                                     String PassengerMobile, String Remark, int CarTypeID, String PaymentType, String FlightNumber, String Address, String StartPlace, String Destination, String ReturnTimeBoard,
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
            JsonObject.put("Address", Address);
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

    /**
     * 为界面加载初始数据
     */
    private void setViewData(List<CarListBean.DataBean> data, int postion) {
        mData.clear();
        mData.addAll(data);
        if (mData != null && mData.size() > postion) {
            tvPrice.setText(StrUtils.getRmb() + mData.get(postion).getPrice());
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
                            int wdith = TransferActivity.this.wdith - Utlis.dipTopx(TransferActivity.this, 40);
                            int height = wdith * imgh / imgw;
                            ivInfo.setLayoutParams(new LinearLayout.LayoutParams(wdith, height));
                            ivInfo.setImageBitmap(resource);
                        }
                    });
            tvTimeParameter.setText(mData.get(postion).getCarDesc().getTimeLimit());
            String remarkList = mData.get(postion).getRemarkList();
            String[] arrRemarks = remarkList.split(";");
            allRemarkItemList.clear();
            for (int i = 0; i < arrRemarks.length; i++) {
                RemarksBean remarksBean = new RemarksBean();
                remarksBean.setContent(arrRemarks[i]);
                allRemarkItemList.add(remarksBean);
            }
            createOrderData.setProductID(Integer.parseInt(mData.get(postion).getProductID()));
            createOrderData.setCarModelID(Integer.parseInt(mData.get(postion).getCarModelID()));
            createOrderData.setCarBrand(mData.get(postion).getCarBrand());
            createOrderData.setServiceCar(mData.get(postion).getCarModel());
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
                String result = MeModel.carList(cardNo, cityCode, 2);
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
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        setViewData(typeData1, 0);
                        mAdapter.setData(mData);
                        dismissDialog();
                        showToast(PublicApplication.resourceText.getString("app_error_message", getResources().getString(R.string.app_error_message)), 3);
                    }

                    @Override
                    public void onComplete() {
                        setViewData(typeData1, 0);
                        mAdapter.setData(mData);
                        dismissDialog();

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
                        tvHotlineNumber.setText(buf);
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
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b)
            switch (compoundButton.getId()) {
                case R.id.rb_type_01:
                    serviceType = 1;
                    createOrderData.setServiceType(serviceType);
                    setViewData(typeData1, 0);
                    mAdapter = new CarListAdapter(TransferActivity.this, TransferActivity.this.wdith);
                    vpCar.setAdapter(mAdapter);
                    mAdapter.setData(mData);
                    break;
                case R.id.rb_type_02:
                    serviceType = 2;
                    createOrderData.setServiceType(serviceType);
                    setViewData(typeData2, 0);
                    mAdapter = new CarListAdapter(TransferActivity.this, TransferActivity.this.wdith);
                    vpCar.setAdapter(mAdapter);
                    mAdapter.setData(mData);
                    break;
            }
    }

    /**
     * 获取航班号
     */
    public void getFlightNumber() {


        if (StrUtils.isEmpty(flightNumber) || StrUtils.isEmpty(cityName) ||
                (dateY + dateM + dateD) < 2000) {
        } else {
            Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                    String result = MeModel.flightinfo(flightNumber, dateY + "-" + (dateM + 1) + "-" + dateD,
                            serviceType, cityName);

                    if (result != null)
                        emitter.onNext(result);
                    emitter.onComplete();
                }
            }).subscribeOn(Schedulers.io())
                    .map(new Function<String, FlinghtInforBean>() {
                        @Override
                        public FlinghtInforBean apply(String s) throws Exception {
                            Gson gson = new Gson();
                            return gson.fromJson(s, FlinghtInforBean.class);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<FlinghtInforBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            if (mCompositeDisposable != null)
                                mCompositeDisposable.add(d);
                        }

                        @Override
                        public void onNext(FlinghtInforBean data) {
                            switch (data.get_metadata().getCode()) {
                                case 200:

                                    if (data.getData() != null && data.getData().size() > 0) {
                                        FlightData = data.getData();
                                        TimeTrue = true;
                                        isHttp=true;
                                        String time1 = MyDateUtils.dataOne1(data.getData().get(0).getArrDateTime().replace("T", " ") + ":00");
                                        String time2 = MyDateUtils.getTime();
                                        switch (serviceType) {
                                            case 1://接机时间4个小时判断
                                                if (Long.parseLong(time1) - Long.parseLong(time2) > 4 * 60 * 60) {
                                                    tvFormTime.setText(data.getData().get(0).getArrDateTime().replace("T", " "));
                                                    aboardY = Integer.valueOf(data.getData().get(0).getArrDateTime().substring(0, 4));
                                                    aboardM = Integer.valueOf(data.getData().get(0).getArrDateTime().substring(5, 7));
                                                    aboardD = Integer.valueOf(data.getData().get(0).getArrDateTime().substring(8, 10));
                                                    aboardH = Integer.valueOf(data.getData().get(0).getArrDateTime().substring(11, 13));
                                                    aboardm = Integer.valueOf(data.getData().get(0).getArrDateTime().substring(14, 16));
                                                    tvFormTimePrompt.setVisibility(View.INVISIBLE);
                                                    if (tvFormFlightNumberPrompt.getVisibility() == View.INVISIBLE)
                                                        llFormFlightNumberPrompt.setVisibility(View.GONE);
                                                } else {
                                                    if (Long.parseLong(time1) <= Long.parseLong(time2)) {
                                                        CustomToast.showFailYellowToast(TransferActivity.this, PublicApplication.resourceText.getString("app_pick_up_time_is_not_correct", getResources().getString(R.string.app_pick_up_time_is_not_correct)));
                                                        isHttp = false;
                                                        TimeRight = false;
                                                    }
                                                }
                                                break;
                                            case 2://送机时间4个小时判断
                                                if (Long.parseLong(time1) - Long.parseLong(time2) > 4 * 60 * 60) {
                                                    TimeTrue = true;
                                                    tvFormTime.setText(data.getData().get(0).getDepDateTime().replace("T", " "));
                                                    aboardY = Integer.valueOf(data.getData().get(0).getDepDateTime().substring(0, 4));
                                                    aboardM = Integer.valueOf(data.getData().get(0).getDepDateTime().substring(5, 7));
                                                    aboardD = Integer.valueOf(data.getData().get(0).getDepDateTime().substring(8, 10));
                                                    aboardH = Integer.valueOf(data.getData().get(0).getDepDateTime().substring(11, 13));
                                                    aboardm = Integer.valueOf(data.getData().get(0).getDepDateTime().substring(14, 16));
                                                    tvFormTimePrompt.setVisibility(View.INVISIBLE);
                                                    if (tvFormFlightNumberPrompt.getVisibility() == View.INVISIBLE)
                                                        llFormFlightNumberPrompt.setVisibility(View.GONE);
                                                } else {
                                                    if (Long.parseLong(time1) <= Long.parseLong(time2)) {
                                                        CustomToast.showFailYellowToast(TransferActivity.this, PublicApplication.resourceText.getString("app_delivery_time_is_not_correct", getResources().getString(R.string.app_delivery_time_is_not_correct)));
                                                        isHttp = false;
                                                        TimeRight = false;
                                                    }

                                                }
                                                break;
                                        }

                                    }
                                    break;
                                case 500:
                                    Log.e(TAG, "onNext: " + data.get_metadata().getMessage());
//                                    showToast(data.get_metadata().getMessage(), 3);
                                    TimeTrue = false;
                                    break;
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            showToast(PublicApplication.resourceText.getString("app_error_message", getResources().getString(R.string.app_error_message)), 3);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    @Override
    public void positiveListener(int Year, int Month, int Day, int Hour, int Mint) {
        aboardY = Year;
        aboardM = Month;
        aboardD = Day;
        aboardH = Hour;
        aboardm = Mint;
//        tvFormTime.setText(Year + "-" + Month + "-" + Day + " " + Hour + ":" + Mint);
        RentDate = Year + "-" + to2(Month + "") + "-" + to2(Day + "") + " " + to2(Hour + "") + ":" + to2(Mint + "");
        tvFormTime.setText(RentDate);
        if (tvFormFlightNumberPrompt.getVisibility() == View.GONE)
            llFormFlightNumberPrompt.setVisibility(View.GONE);

    }

    private String to2(String str) {
        return str.length() == 1 ? 0 + str : str;
    }


    @Override
    public void negativeListener() {

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
