package com.soowin.staremblem.ui.index.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;

import android.os.Bundle;
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
import com.soowin.staremblem.ui.index.bean.BaseBean;
import com.soowin.staremblem.ui.index.bean.CouponListBean;
import com.soowin.staremblem.ui.index.bean.OrderDetailBean;
import com.soowin.staremblem.ui.index.bean.PriceDetails;
import com.soowin.staremblem.ui.index.bean.UserInfoBean;
import com.soowin.staremblem.ui.index.mModel.MeModel;

import com.soowin.staremblem.utils.BaseActivity;
import com.soowin.staremblem.utils.DataUtils;
import com.soowin.staremblem.utils.PublicApplication;
import com.soowin.staremblem.utils.ScreenManager;
import com.soowin.staremblem.utils.StrUtils;

import com.soowin.staremblem.utils.myView.CustomToast;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

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
 * 确认订单页
 */
public class ConfirmationOfOrderActivity extends BaseActivity implements View.OnClickListener {
    //入参***************************************************************
    public static final String SERVICE_TYPE_CODE = "service_type_code";//服务类型（1、接机 2、送机 3、日租 5、商务 6、半日租 7、接送套餐）
    public static final String CREATE_ORDER_CODE = "create_order_data";//创见订单所用数据
    private static final String TAG = ConfirmationOfOrderActivity.class.getSimpleName();
    /*初始化title*/
    private TextView tvTitle;
    private ImageView ivBack;
    public static final String ORDERID = "orderId";//需要传递OrderId来请求数据
    private String OrderId = "";//订单id
    private String fromPage = "1";
    public static final String FROM_PAGE = "from_page";
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();//异步请求控制器
    /*初始化View*/
    private TextView tvServiceType;//服务类型
    private TextView tvCardDesc;//请选择卡券：
    private TextView tvPayWay;//支付方式
    private TextView tvPrice;//支付金额
    private int HuoDongType = 0;
    private PopupWindow mPopupWindow;
    LinearLayout llPayWay;//支付方式
    LinearLayout llSelectCoupon;

    private Boolean isShow = true;//是否展示支付方式
    private int PayType = 1;//1 是微信 3是余额

    private Double payMoney;//支付金额或者余额金额
    private Double HuoDongMoney;//活动的钱数

    UserInfoBean.DataBean UserInfoData = new UserInfoBean.DataBean();
    OrderDetailBean.DataBean dataBean = new OrderDetailBean.DataBean();
    CouponListBean.DataBean couponData = new CouponListBean.DataBean();


    /*显示与隐藏覆盖*/
    private ImageView ivSelectCoupon;
    private ImageView ivUnMoney;
    private ImageView ivPayWay;


    private String CardNo = "";//传递卡券的记录的位置  初次进入传""

    private TextView tvCommitPay;//确认支付
    /*静态文字展示*/
    private TextView tvServiceTypes;
    private TextView tvStringServiceCar;
    private TextView tvStringGuestMessage;
    private TextView tvStringServiceSpare;
    private TextView tvSelected;//已选择
    private TextView tvStringUnPayMoney;
    private TextView tvStringPayWay;

    private ImageView ivCouponDownIcon;
    private ImageView ivDownIcon;

    private LinearLayout llDailyRental;//商务日租
    private LinearLayout llTransfer;//接机服务
    private LinearLayout llShuttleABC;//接送套餐
    private LinearLayout llShuttleABA;//商务接送
    /*公用部分*/
    private TextView tvCarType;//车型
    private TextView tvGuestInformation;//贵宾信息
    private TextView tvServiceSpare;//备品
    /*商务日租*/
    private TextView tvStringPickUpDailyRental;//上车时间
    private TextView tvPickUpDailyRental;
    private TextView tvStringCarCityDailyRental;//用车城市
    private TextView tvCarCityDailyRental;
    private TextView tvStringCarSiteDailyRental;//上车地点
    private TextView tvCarSiteDailyRental;
    /*接机服务 接机时间*/  /*送机服务 送机时间*/
    private TextView tvStringFlightNumberTransfer;//航班号
    private TextView tvFlightNumberTransfer;
    private TextView tvStringPickUpTimeTransfer;//接机时间
    private TextView tvPickUpTimeTransfer;
    private TextView tvStringDestinationTransfer;//目的地
    private TextView tvDestinationTransfer;
    /*接送套餐*/
    private TextView tvStringSetOutShuttleabc;//上车时间 启程
    private TextView tvSetOutShuttleabc;
    private TextView tvStringOrderCarShuttleabc;//用车时间
    private TextView tvOrderCarShuttleabc;
    private TextView tvStringOrderCarSiteShuttleabc;//上车地点
    private TextView tvOrderCarSiteShuttleabc;
    private TextView tvStringConfirmDestinationShuttleabc;//目的地
    private TextView tvConfirmDestinationShuttleabc;
    private TextView tvStringBackTrackingShuttleabc;//上车时间 返程
    private TextView tvBackTrackingShuttleabc;
    private TextView tvStringBackConfirmDestinationShuttleabc;//目的地
    private TextView tvBackDestinationShuttleabc;
    /*商务接送*/
    private TextView tvStringPickUpShuttleaba;//上车时间
    private TextView tvPickUpShuttleaba;
    private TextView tvStringCarCityShuttleaba;//用车城市
    private TextView tvCarCityShuttleaba;
    private TextView tvStringCarSiteShuttleaba;//上车地点
    private TextView tvCarSiteShuttleaba;
    private TextView tvStringOrderDestinationaba;//目的地
    private TextView tvOrderDestinationaba;
    //    未过期数据
    List<CouponListBean.DataBean> noOutDate = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_of_order);

        //初始化数据
        Bundle b = getIntent().getBundleExtra("data");
        couponData = (CouponListBean.DataBean) b.getSerializable(CouponListActivity.COUPONDATA);
        OrderId = getIntent().getStringExtra(ORDERID);
        fromPage = getIntent().getStringExtra(FROM_PAGE);
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mCompositeDisposable != null)
            mCompositeDisposable.clear();
        dismissDialog();
    }

    /*请求订单详情的数据*/
    private void initData() {
        OrderDetail(OrderId);
        /*请求连接用户信息*/
        getPersonalInformation();
    }

    /*初始化View*/
    private void initView() {
        initTitle();
        initContent();
        initStaticTextDisplay();//静态文字展示
    }

    private void initStaticTextDisplay() {
        tvSelected = findViewById(R.id.tv_selected);
        ivCouponDownIcon = findViewById(R.id.iv_coupon_down_icon);
        ivDownIcon = findViewById(R.id.iv_down_icon);
        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_order_down", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_order_down))
                .error(getResources().getDrawable(R.drawable.img_order_down))
                .into(ivCouponDownIcon);

        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_order_down", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_order_down))
                .error(getResources().getDrawable(R.drawable.img_order_down))
                .into(ivDownIcon);
        tvServiceTypes = findViewById(R.id.tv_service_type);//接机服务
        tvServiceTypes.setText(PublicApplication.resourceText.getString("app_service_type_right", getResources().getString(R.string.app_service_type_right)));
        tvStringServiceCar = findViewById(R.id.tv_string_service_car);//服务车型
        tvStringServiceCar.setText(PublicApplication.resourceText.getString("app_service_car", getResources().getString(R.string.app_service_car)));
        /*商务日租*/
        tvStringPickUpDailyRental = findViewById(R.id.tv_string_pick_up_daily_rental);
        tvStringPickUpDailyRental.setText(PublicApplication.resourceText.getString("app_string_pick_up", getResources().getString(R.string.app_string_pick_up)));
        tvStringCarCityDailyRental = findViewById(R.id.tv_string_car_city_daily_rental);
        tvStringCarCityDailyRental.setText(PublicApplication.resourceText.getString("app_order_car_City", getResources().getString(R.string.app_order_car_City)));
        tvStringCarSiteDailyRental = findViewById(R.id.tv_string_car_site_daily_rental);
        tvStringCarSiteDailyRental.setText(PublicApplication.resourceText.getString("app_order_car_site", getResources().getString(R.string.app_order_car_site)));
        tvPickUpDailyRental = findViewById(R.id.tv_pick_up_daily_rental);
        tvCarCityDailyRental = findViewById(R.id.tv_car_city_daily_rental);
        tvCarSiteDailyRental = findViewById(R.id.tv_car_site_daily_rental);
        /*接机服务*/
        tvStringFlightNumberTransfer = findViewById(R.id.tv_string_flightnumber_transfer);
        tvStringFlightNumberTransfer.setText(PublicApplication.resourceText.getString("app_airport_message", getResources().getString(R.string.app_airport_message)));
        tvStringPickUpTimeTransfer = findViewById(R.id.tv_string_pick_up_time_transfer);
        tvStringDestinationTransfer = findViewById(R.id.tv_string_destination_transfer);
        tvStringDestinationTransfer.setText(PublicApplication.resourceText.getString("app_confirm_order_destination", getResources().getString(R.string.app_confirm_order_destination)));
        tvFlightNumberTransfer = findViewById(R.id.tv_flight_number_transfer);
        tvPickUpTimeTransfer = findViewById(R.id.tv_pick_up_time_transfer);
        tvDestinationTransfer = findViewById(R.id.tv_destination_transfer);

        /*接送套餐*/
        tvStringSetOutShuttleabc = findViewById(R.id.tv_string_set_out_shuttleabc);
        tvStringSetOutShuttleabc.setText(PublicApplication.resourceText.getString("app_string_set_out", getResources().getString(R.string.app_string_set_out)));
        tvStringOrderCarShuttleabc = findViewById(R.id.tv_string_order_car_shuttleabc);
        tvStringOrderCarShuttleabc.setText(PublicApplication.resourceText.getString("app_order_car_City", getResources().getString(R.string.app_order_car_City)));
        tvStringOrderCarSiteShuttleabc = findViewById(R.id.tv_string_order_car_site_shuttleabc);
        tvStringOrderCarSiteShuttleabc.setText(PublicApplication.resourceText.getString("app_order_car_site", getResources().getString(R.string.app_order_car_site)));
        tvStringConfirmDestinationShuttleabc = findViewById(R.id.tv_string_confirm_destination_shuttleabc);
        tvStringConfirmDestinationShuttleabc.setText(PublicApplication.resourceText.getString("app_confirm_order_destination", getResources().getString(R.string.app_confirm_order_destination)));
        tvStringBackTrackingShuttleabc = findViewById(R.id.tv_string_back_tracking_shuttleabc);
        tvStringBackTrackingShuttleabc.setText(PublicApplication.resourceText.getString("app_string_back_tracking", getResources().getString(R.string.app_string_back_tracking)));
        tvStringBackConfirmDestinationShuttleabc = findViewById(R.id.tv_back_confirm_destination_shuttleabc);
        tvStringBackConfirmDestinationShuttleabc.setText(PublicApplication.resourceText.getString("app_confirm_order_destination", getResources().getString(R.string.app_confirm_order_destination)));
        tvSetOutShuttleabc = findViewById(R.id.tv_set_out_shuttleabc);
        tvOrderCarShuttleabc = findViewById(R.id.tv_order_car_shuttleabc);
        tvOrderCarSiteShuttleabc = findViewById(R.id.tv_order_car_site_shuttleabc);
        tvConfirmDestinationShuttleabc = findViewById(R.id.tv_confirm_destination_shuttleabc);
        tvBackTrackingShuttleabc = findViewById(R.id.tv_back_tracking_shuttleabc);
        tvBackDestinationShuttleabc = findViewById(R.id.tv_back_destination_shuttleabc);
        /*商务接送*/
        tvStringPickUpShuttleaba = findViewById(R.id.tv_string_pick_up_shuttle_aba);
        tvStringPickUpShuttleaba.setText(PublicApplication.resourceText.getString("app_string_pick_up", getResources().getString(R.string.app_string_pick_up)));
        tvStringCarCityShuttleaba = findViewById(R.id.tv_string_car_city_shuttle_aba);
        tvStringCarCityShuttleaba.setText(PublicApplication.resourceText.getString("app_order_car_City", getResources().getString(R.string.app_order_car_City)));
        tvStringCarSiteShuttleaba = findViewById(R.id.tv_string_car_site_shuttle_aba);
        tvStringCarSiteShuttleaba.setText(PublicApplication.resourceText.getString("app_order_car_site", getResources().getString(R.string.app_order_car_site)));
        tvStringOrderDestinationaba = findViewById(R.id.tv_string_order_destination_aba);
        tvStringOrderDestinationaba.setText(PublicApplication.resourceText.getString("app_confirm_order_destination", getResources().getString(R.string.app_confirm_order_destination)));

        tvPickUpShuttleaba = findViewById(R.id.tv_pick_up_shuttle_aba);
        tvCarCityShuttleaba = findViewById(R.id.tv_car_city_shuttle_aba);
        tvCarSiteShuttleaba = findViewById(R.id.tv_car_site_shuttle_aba);
        tvOrderDestinationaba = findViewById(R.id.tv_order_destination_aba);
        tvStringGuestMessage = findViewById(R.id.tv_string_guest_message);//贵宾信息
        tvStringGuestMessage.setText(PublicApplication.resourceText.getString("app_airport_guibin", getResources().getString(R.string.app_airport_guibin)));
        tvStringServiceSpare = findViewById(R.id.tv_string_service_spare);//服务以及备品
        tvStringServiceSpare.setText(PublicApplication.resourceText.getString("app_airport_beipin", getResources().getString(R.string.app_airport_beipin)));
        tvStringUnPayMoney = findViewById(R.id.tv_string_unpay_money);//待付款
        tvStringUnPayMoney.setText(PublicApplication.resourceText.getString("app_order_amount_to_pay", getResources().getString(R.string.app_order_amount_to_pay)));
        tvStringPayWay = findViewById(R.id.tv_string_pay_way);//支付方式
        tvStringPayWay.setText(PublicApplication.resourceText.getString("app_order_mode_of_payment", getResources().getString(R.string.app_order_mode_of_payment)));
        /*确认支付*/
        tvCommitPay.setText(PublicApplication.resourceText.getString("app_order_confirm_payment", getResources().getString(R.string.app_order_confirm_payment)));

    }


    private void initContent() {

        llDailyRental = findViewById(R.id.ll_Daily_Rental);
        llTransfer = findViewById(R.id.ll_transfer);
        llShuttleABC = findViewById(R.id.ll_shuttle_abc);
        llShuttleABA = findViewById(R.id.ll_shuttle_aba);

        tvServiceType = findViewById(R.id.tv_service_type);
        tvCarType = findViewById(R.id.tv_order_car_type);
        tvGuestInformation = findViewById(R.id.tv_guest_message);
        tvServiceSpare = findViewById(R.id.tv_service_spare);
        tvCardDesc = findViewById(R.id.tv_card_desc);
        llPayWay = findViewById(R.id.ll_pay_way);
        llPayWay.setOnClickListener(this);
        tvPayWay = findViewById(R.id.tv_pay_way);
        tvPrice = findViewById(R.id.tv_price);
        llSelectCoupon = findViewById(R.id.ll_select_coupon);
        llSelectCoupon.setOnClickListener(this);

        ivSelectCoupon = findViewById(R.id.iv_select_coupon);
        ivUnMoney = findViewById(R.id.iv_un_pay);
        ivPayWay = findViewById(R.id.iv_pay_way);


        tvCommitPay = findViewById(R.id.tv_commit_pay);
        tvCommitPay.setOnClickListener(this);
    }

    private void initTitle() {
        tvTitle = findViewById(R.id.tv_title);
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_go_back", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_go_back))
                .error(getResources().getDrawable(R.drawable.img_go_back))
                .into(ivBack);
        tvTitle.setText(PublicApplication.resourceText.getString("app_order_confirmation", getResources().getString(R.string.app_order_confirmation)));
        ivBack.setVisibility(View.VISIBLE);
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

                                UserInfoData = data.getData();

                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        CustomToast.showFailToast(ConfirmationOfOrderActivity.this, getResources().getString(R.string.app_error_message));
                        ConfirmationOfOrderActivity.this.finish();
                    }

                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }
                });
    }

    /*订单详情*/
    public void OrderDetail(final String OrderId) {
        showDialog();
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = MeModel.OrderDetail(OrderId);

                if (result != null)
                    emitter.onNext(result);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<String, OrderDetailBean>() {
                    @Override
                    public OrderDetailBean apply(String s) throws Exception {
                        Gson gson = new Gson();
                        return gson.fromJson(s, OrderDetailBean.class);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderDetailBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (mCompositeDisposable != null)
                            mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(OrderDetailBean baseBean) {
                        switch (baseBean.get_metadata().getCode()) {
                            case 200:
                                OrderDetailBean.DataBean data = baseBean.getData();
                                dataBean = data;
                                  /*1-7  没有4*/
                                switch (dataBean.getCarProduct().getServiceType()) {
                                    case 1://接机
                                        tvServiceType.setText(PublicApplication.resourceText.getString("app_transfer_form_type_name_01", getResources().getString(R.string.app_transfer_form_type_name_01)));
                                        llDailyRental.setVisibility(View.GONE);
                                        llTransfer.setVisibility(View.VISIBLE);
                                        llShuttleABA.setVisibility(View.GONE);
                                        llShuttleABC.setVisibility(View.GONE);
                                        tvStringPickUpTimeTransfer.setText(PublicApplication.resourceText.getString("app_order_jieji_time", getResources().getString(R.string.app_order_jieji_time)));
                                        tvFlightNumberTransfer.setText(" " + dataBean.getCarProduct().getFlightNumber());
                                        tvPickUpTimeTransfer.setText(" " + dataBean.getCarProduct().getTimeBoard());
                                        tvDestinationTransfer.setText("  " + dataBean.getCarProduct().getDestination());
                                        break;
                                    case 2://送机
                                        tvServiceType.setText(PublicApplication.resourceText.getString("app_transfer_form_type_name_02", getResources().getString(R.string.app_transfer_form_type_name_02)));
                                        llDailyRental.setVisibility(View.GONE);
                                        llTransfer.setVisibility(View.VISIBLE);
                                        llShuttleABA.setVisibility(View.GONE);
                                        llShuttleABC.setVisibility(View.GONE);
                                        tvStringPickUpTimeTransfer.setText(PublicApplication.resourceText.getString("app_order_songji_time", getResources().getString(R.string.app_order_songji_time)));
                                        tvFlightNumberTransfer.setText(" " + dataBean.getCarProduct().getFlightNumber());
                                        tvPickUpTimeTransfer.setText(" " + dataBean.getCarProduct().getTimeBoard());
                                        tvDestinationTransfer.setText("  " + dataBean.getCarProduct().getAddress());
                                        break;
                                    case 3://日租
                                        tvServiceType.setText(PublicApplication.resourceText.getString("app_order_daily_rental", getResources().getString(R.string.app_order_daily_rental)));
                                        llDailyRental.setVisibility(View.VISIBLE);
                                        llTransfer.setVisibility(View.GONE);
                                        llShuttleABA.setVisibility(View.GONE);
                                        llShuttleABC.setVisibility(View.GONE);
                                        tvPickUpDailyRental.setText(" " + dataBean.getCarProduct().getTimeBoard());
                                        tvCarCityDailyRental.setText(" " + dataBean.getCarProduct().getCarCity());
                                        tvCarSiteDailyRental.setText(" " + dataBean.getCarProduct().getDestination());
                                        break;
                                    case 5://商务
                                        tvServiceType.setText(PublicApplication.resourceText.getString("app_order_business", getResources().getString(R.string.app_order_business)));
                                        llDailyRental.setVisibility(View.GONE);
                                        llTransfer.setVisibility(View.GONE);
                                        llShuttleABA.setVisibility(View.VISIBLE);
                                        llShuttleABC.setVisibility(View.GONE);
                                        tvPickUpShuttleaba.setText(" " + dataBean.getCarProduct().getTimeBoard());
                                        tvCarCityShuttleaba.setText(" " + dataBean.getCarProduct().getCarCity());
                                        tvCarSiteShuttleaba.setText(" " + dataBean.getCarProduct().getStartPlace());
                                        tvOrderDestinationaba.setText("  " + dataBean.getCarProduct().getDestination());

                                        break;
                                    case 6://半日租
                                        tvServiceType.setText(PublicApplication.resourceText.getString("app_order_half_day_rent", getResources().getString(R.string.app_order_half_day_rent)));
                                        break;
                                    case 7://接送套餐
                                        tvServiceType.setText(PublicApplication.resourceText.getString("app_order_transport_package", getResources().getString(R.string.app_order_transport_package)));
                                        llDailyRental.setVisibility(View.GONE);
                                        llTransfer.setVisibility(View.GONE);
                                        llShuttleABA.setVisibility(View.GONE);
                                        llShuttleABC.setVisibility(View.VISIBLE);
                                        tvSetOutShuttleabc.setText(" " + dataBean.getCarProduct().getTimeBoard());
                                        tvOrderCarShuttleabc.setText(" " + dataBean.getCarProduct().getCarCity());
                                        tvOrderCarSiteShuttleabc.setText(" " + dataBean.getCarProduct().getStartPlace());
                                        tvConfirmDestinationShuttleabc.setText("  " + dataBean.getCarProduct().getDestination());
                                        tvBackTrackingShuttleabc.setText(" " + dataBean.getCarProduct().getReturnTimeBoard());
                                        tvBackDestinationShuttleabc.setText(" " + dataBean.getCarProduct().getReturnDestination());
                                        break;
                                }
                                tvCarType.setText(" " + dataBean.getCarProduct().getCarBrand());//服务车型
                                String take_mobile = dataBean.getCarProduct().getPassengerMobile();
                                StringBuilder replacePhone = new StringBuilder(take_mobile);
                                replacePhone.replace(3, 7, "****");
                                replacePhone.substring(7, 11);

                                tvGuestInformation.setText(" " + dataBean.getCarProduct().getPassengerName() + " " + replacePhone);//贵宾信息

                                if (dataBean.getCarProduct().getRemark() != null && !StrUtils.isEmpty(dataBean.getCarProduct().getRemark())) {
                                    tvServiceSpare.setText(dataBean.getCarProduct().getRemark().substring(0, dataBean.getCarProduct().getRemark().length() - 1));
                                } else {
                                    tvServiceSpare.setText("");
                                }
                                tvPrice.setText(" " + StrUtils.getRmb() + dataBean.getCarProduct().getPrice());
//                                tvServiceSpare.setText(" " + dataBean.getCarProduct().getRemark());//服务以及备品
                                if (!StrUtils.isEmpty(dataBean.getCarProduct().getCardNo())) {
                                    tvCardDesc.setText(" " + dataBean.getCarProduct().getCardNo());
                                    CardNo = dataBean.getCarProduct().getCardNo();
                                    // TODO: 2018/4/24 后台返回的value值有问题
                                    double UnPayMoney = Double.parseDouble(String.valueOf(dataBean.getCarProduct().getPrice()));
                                    double VoucherValue = dataBean.getCarProduct().getCardValue();//代金券的钱数
                                    HuoDongMoney = (UnPayMoney - VoucherValue);
                                    tvPrice.setText(" " + StrUtils.getRmb() + HuoDongMoney);
                                    tvSelected.setText(PublicApplication.resourceText.getString("app_order_coupon_selected", getResources().getString(R.string.app_order_coupon_selected)));
                                } else {
                                    tvSelected.setText(PublicApplication.resourceText.getString("app_order_select_coupon", getResources().getString(R.string.app_order_select_coupon)));
                                    tvCardDesc.setText(" " + dataBean.getCardDesc());
                                }
                                if (!StrUtils.isEmpty(dataBean.getCarProduct().getCardNo()) && dataBean.getCarProduct().getCardNo() != null) {
                                    setCouponResult(dataBean);
                                }
                                /*有卡券*/
                                if (couponData != null) {
                                    if (!StrUtils.isEmpty(CardNo) && CardNo != null) {
                                        setResults(couponData);
                                    }
                                }
                                break;
                            case 500:
                                showToast(baseBean.get_metadata().getMessage(), 3);
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                        CustomToast.showFailToast(ConfirmationOfOrderActivity.this, getResources().getString(R.string.app_error_message));
                    }

                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }
                });
    }
    /*请求订单详情 根据接口设置卡券的数据*/
    private void setCouponResult(OrderDetailBean.DataBean dataBean) {
        if (dataBean != null) {
            switch (dataBean.getCarProduct().getCarType()) {
                case 1://活动卡
                    ivUnMoney.setVisibility(View.VISIBLE);
                    ivPayWay.setVisibility(View.VISIBLE);
                    ivSelectCoupon.setVisibility(View.GONE);
                    llPayWay.setOnClickListener(null);
                    tvCardDesc.setText(dataBean.getCarProduct().getCardNo());
                    tvSelected.setText(PublicApplication.resourceText.getString("app_order_coupon_selected", getResources().getString(R.string.app_order_coupon_selected)));
                    payMoney = 0.00;
                    HuoDongMoney = 0.00;
                    tvPrice.setText(" " + StrUtils.getRmb() + payMoney);
                    PayType = 5;
                    isShow = false;
                    HuoDongType = 7;
                    break;

                case 3://次卡

                    ivUnMoney.setVisibility(View.VISIBLE);
                    ivPayWay.setVisibility(View.VISIBLE);
                    ivSelectCoupon.setVisibility(View.GONE);
                    llPayWay.setOnClickListener(null);
                    tvCardDesc.setText(dataBean.getCarProduct().getCardNo());
                    tvSelected.setText(PublicApplication.resourceText.getString("app_order_coupon_selected", getResources().getString(R.string.app_order_coupon_selected)));
                    payMoney = 0.00;
                    HuoDongMoney = 0.00;
                    tvPrice.setText(" " + StrUtils.getRmb() + payMoney);
                    PayType = 5;//既不是微信也不是余额支付
                    isShow = false;
                    HuoDongType = 7;
                    break;
                case 4://代金券  有两种支付方式 1 代金券 2 代金券+微信 钱

                    double VoucherValue = dataBean.getCarProduct().getCardValue();//代金券的钱数
                    double UnPayMoney = Double.parseDouble(String.valueOf(dataBean.getCarProduct().getPrice()));
                    if (VoucherValue >= UnPayMoney) {//代金券金额大于未支付价格        /*1  代金券*/
                        tvPrice.setText(" " + StrUtils.getRmb() + "0");
                        ivPayWay.setVisibility(View.VISIBLE);
                        ivSelectCoupon.setVisibility(View.GONE);
                        ivUnMoney.setVisibility(View.VISIBLE);
                        llPayWay.setOnClickListener(null);
                        payMoney = 0.00;
                        HuoDongMoney = 0.00;
                        PayType = 0;
                        isShow = false;
                    } else {     /*2  代金券+微信的钱   或 代金券+余额的钱*/
                        tvPrice.setText(" " + StrUtils.getRmb() + (UnPayMoney - VoucherValue));
                        HuoDongMoney = (UnPayMoney - VoucherValue);
                        payMoney = UnPayMoney;
                        ivUnMoney.setVisibility(View.GONE);
                        ivSelectCoupon.setVisibility(View.GONE);
                        ivPayWay.setVisibility(View.GONE);
                        llPayWay.setOnClickListener(this);
                        isShow = true;

                    }
                    tvSelected.setText(PublicApplication.resourceText.getString("app_order_coupon_selected", getResources().getString(R.string.app_order_coupon_selected)));
                    tvCardDesc.setText(dataBean.getCarProduct().getCardNo());
                    HuoDongType = 6;
                    break;
            }
        }
    }

    /*订单详情的Bean类*/
    private void setResults(CouponListBean.DataBean couponData) {
        if (couponData != null) {
            switch (couponData.getType()) {
                case 1://活动卡
                    ivUnMoney.setVisibility(View.VISIBLE);
                    ivPayWay.setVisibility(View.VISIBLE);
                    ivSelectCoupon.setVisibility(View.GONE);
                    llPayWay.setOnClickListener(null);
                    tvCardDesc.setText(couponData.getCardNo());
                    tvSelected.setText(PublicApplication.resourceText.getString("app_order_coupon_selected", getResources().getString(R.string.app_order_coupon_selected)));
                    payMoney = 0.00;
                    HuoDongMoney = 0.00;
                    tvPrice.setText(" " + StrUtils.getRmb() + payMoney);
                    PayType = 5;
                    isShow = false;
                    HuoDongType = 7;
                    break;

                case 3://次卡

                    ivUnMoney.setVisibility(View.VISIBLE);
                    ivPayWay.setVisibility(View.VISIBLE);
                    ivSelectCoupon.setVisibility(View.GONE);
                    llPayWay.setOnClickListener(null);
                    tvCardDesc.setText(couponData.getCardNo());
                    tvSelected.setText(PublicApplication.resourceText.getString("app_order_coupon_selected", getResources().getString(R.string.app_order_coupon_selected)));
                    payMoney = 0.00;
                    HuoDongMoney = 0.00;
                    tvPrice.setText(" " + StrUtils.getRmb() + payMoney);
                    PayType = 5;//既不是微信也不是余额支付
                    isShow = false;
                    HuoDongType = 7;
                    break;
                case 4://代金券  有两种支付方式 1 代金券 2 代金券+微信 钱

                    double VoucherValue = couponData.getCardValue();//代金券的钱数
                    double UnPayMoney = Double.parseDouble(String.valueOf(dataBean.getCarProduct().getPrice()));
                    if (VoucherValue >= UnPayMoney) {//代金券金额大于未支付价格        /*1  代金券*/
                        tvPrice.setText(" " + StrUtils.getRmb() + "0");
                        ivPayWay.setVisibility(View.VISIBLE);
                        ivSelectCoupon.setVisibility(View.GONE);
                        ivUnMoney.setVisibility(View.VISIBLE);
                        llPayWay.setOnClickListener(null);
                        payMoney = 0.00;
                        HuoDongMoney = 0.00;
                        PayType = 0;
                        isShow = false;
                    } else {     /*2  代金券+微信的钱   或 代金券+余额的钱*/
                        tvPrice.setText(" " + StrUtils.getRmb() + (UnPayMoney - VoucherValue));
                        HuoDongMoney = (UnPayMoney - VoucherValue);
                        payMoney = UnPayMoney;
                        ivUnMoney.setVisibility(View.GONE);
                        ivSelectCoupon.setVisibility(View.GONE);
                        ivPayWay.setVisibility(View.GONE);
                        llPayWay.setOnClickListener(this);
                        isShow = true;

                    }
                    tvSelected.setText(PublicApplication.resourceText.getString("app_order_coupon_selected", getResources().getString(R.string.app_order_coupon_selected)));
                    tvCardDesc.setText(couponData.getCardNo());
                    HuoDongType = 6;
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_pay_way:
                if (isShow)
                    ShowPopUpWindow();
                break;
            case R.id.ll_select_coupon://跳转到选择卡券页面
                Intent intent = new Intent(this, SelectCouponActivity.class);
            /*初次传空*/
                intent.putExtra("popos", CardNo);
                intent.putExtra(CouponListActivity.ORDERID, OrderId);
                startActivityForResult(intent, 0);
                break;
            case R.id.tv_commit_pay:
                String timeBoard = dataBean.getCarProduct().getTimeBoard().replace("T", " ");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date curDate = new Date(System.currentTimeMillis());

                String format = formatter.format(curDate);//系统当前时间

                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                int yearMin = 0;
                int monthMin = 0;
                int dayMin = 0;
                int hourMin = 0;
                int minuteMin = 0;
                try {
                    Date date = sf.parse(timeBoard);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    yearMin = calendar.get(Calendar.YEAR);
                    monthMin = calendar.get(Calendar.MONTH) + 1;
                    dayMin = calendar.get(Calendar.DAY_OF_MONTH);
                    hourMin = calendar.get(Calendar.HOUR_OF_DAY);
                    minuteMin = calendar.get(Calendar.MINUTE);

                } catch (ParseException e) {
                    e.printStackTrace();

                }
                String straboardM = "";
                String straboardD = "";
                String straboardH = "";
                String straboardMs = "";


                if (monthMin < 10)
                    straboardM = "0" + monthMin;
                else
                    straboardM = "" + monthMin;
                if (dayMin < 10)
                    straboardD = "0" + dayMin;
                else
                    straboardD = "" + dayMin;
                if (hourMin < 10)
                    straboardH = "0" + hourMin;
                else
                    straboardH = "" + hourMin;
                if (minuteMin < 10)
                    straboardMs = "0" + minuteMin;
                else
                    straboardMs = "" + minuteMin;
                String Date = "";
                Date = String.valueOf(yearMin + "-" + straboardM + "-" + straboardD + " " + straboardH + ":" + straboardMs);
                final int remainHour = (int) (Double.parseDouble(DataUtils.getTimeDifferenceHour(Date, format)));
                if (remainHour <= 0) {
                    showToast(PublicApplication.loginInfo.getString("app_transfer_prompt_content", getResources().getString(R.string.app_transfer_prompt_content)), 3);
                } else {
                    /*大于4个小时才能下单*/
                    if (remainHour > 4) {
                        CommitPay();
                    } else {
                        /*判断相同的天数 小时相同 分钟不同的情况*/
                        if (remainHour == 4) {
                            /*下订单的分钟*/
//                            straboardMs /**/
                            if (Integer.parseInt(straboardMs) > curDate.getMinutes()) {
                                CommitPay();
                            } else {
                                showToast(PublicApplication.loginInfo.getString("app_transfer_prompt_content", getResources().getString(R.string.app_transfer_prompt_content)), 3);
                            }

                        } else if (remainHour < 4) {
                            showToast(PublicApplication.loginInfo.getString("app_transfer_prompt_content", getResources().getString(R.string.app_transfer_prompt_content)), 3);

                        }
                    }
                }

                break;
            case R.id.iv_back:

                setClearData();
                break;
        }
    }


    /*清空之前的CouponData*/
    private void setClearData() {
        switch (Integer.parseInt(fromPage)) {
            case 1:
                startActivity(new Intent(this, MainActivity.class));
                EventBus.getDefault().post("ClearData");
                ScreenManager.getScreenManager().RemoveAllExceptedOne(MainActivity.class);
                break;
            case 2:
                finish();

                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setClearData();
    }

    /*卡券回掉*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (data != null) {
                if (resultCode == 0) {
                    couponData = (CouponListBean.DataBean) data.getSerializableExtra("CouponData");
                    if (couponData != null) {
                        switch (couponData.getType()) {
                            case 1://活动卡
                                ivUnMoney.setVisibility(View.VISIBLE);
                                ivPayWay.setVisibility(View.VISIBLE);
                                ivSelectCoupon.setVisibility(View.GONE);
                                llPayWay.setOnClickListener(null);
                                payMoney = 0.00;
                                HuoDongMoney = 0.00;
                                tvPrice.setText(" " + StrUtils.getRmb() + payMoney);
                                PayType = 5;
                                isShow = false;
                                HuoDongType = 7;
                                break;
                      /*      case 2://余额卡
                                isShow = true;
                                break;*/
                            case 3://次卡
                                ivUnMoney.setVisibility(View.VISIBLE);
                                ivPayWay.setVisibility(View.VISIBLE);
                                ivSelectCoupon.setVisibility(View.GONE);
                                llPayWay.setOnClickListener(null);
                                tvCardDesc.setText(couponData.getCardNo());
                                tvSelected.setText(PublicApplication.resourceText.getString("app_order_coupon_selected", getResources().getString(R.string.app_order_coupon_selected)));
                                payMoney = 0.00;
                                HuoDongMoney = 0.00;
                                tvPrice.setText(" " + StrUtils.getRmb() + payMoney);
                                PayType = 5;//既不是微信也不是余额支付
                                isShow = false;
                                HuoDongType = 7;
                                break;
                            case 4://代金券  有两种支付方式 1 代金券 2 代金券+微信 余额

                                double VoucherValue = couponData.getCardValue();//代金券的钱数
                                double UnPayMoney = Double.parseDouble(String.valueOf(dataBean.getCarProduct().getPrice()));
                                if (VoucherValue >= UnPayMoney) {//代金券金额大于未支付价格        /*1  代金券*/
                                    tvPrice.setText(" " + StrUtils.getRmb() + "0");
                                    ivPayWay.setVisibility(View.VISIBLE);
                                    ivSelectCoupon.setVisibility(View.GONE);
                                    ivUnMoney.setVisibility(View.VISIBLE);
                                    llPayWay.setOnClickListener(null);
                                    payMoney = 0.00;
                                    HuoDongMoney = 0.00;
                                    PayType = 0;
                                    isShow = false;
                                } else {     /*2  代金券+微信的钱   或 代金券+余额的钱*/
                                    tvPrice.setText(" " + StrUtils.getRmb() + (UnPayMoney - VoucherValue));
                                    HuoDongMoney = (UnPayMoney - VoucherValue);
                                    payMoney = UnPayMoney;
                                    ivUnMoney.setVisibility(View.GONE);
                                    ivSelectCoupon.setVisibility(View.GONE);
                                    ivPayWay.setVisibility(View.GONE);
                                    llPayWay.setOnClickListener(this);
                                    isShow = true;

                                }
                                HuoDongType = 6;
                                break;

                        }
                        tvSelected.setText(PublicApplication.resourceText.getString("app_order_coupon_selected", getResources().getString(R.string.app_order_coupon_selected)));
                        CardNo = data.getStringExtra("popos");
                        tvCardDesc.setText(" " + CardNo);
                    } else {
                        tvSelected.setText(PublicApplication.resourceText.getString("app_order_select_coupon", getResources().getString(R.string.app_order_select_coupon)));
                        tvCardDesc.setText(" " + dataBean.getCardDesc());
                        ivPayWay.setVisibility(View.GONE);
                        tvPrice.setText(" " + StrUtils.getRmb() + dataBean.getCarProduct().getPrice());
                        ivUnMoney.setVisibility(View.GONE);
                        llPayWay.setOnClickListener(this);
                        CardNo = "";
                        isShow = true;

                    }
                }
            }
        }
    }

    //    /*对象存放代金券  次卡  活动卡*/


    /*提交支付*/
    private void CommitPay() {

        showDialog();
        String s = "";
        switch (HuoDongType) {
            case 6://代金券
                PriceDetails priceDetailOne = new PriceDetails();
                priceDetailOne.setType(PayType);
                priceDetailOne.setPrice(String.valueOf(HuoDongMoney));
                Gson gson = new Gson();
                List<PriceDetails> data = new ArrayList<>();

                PriceDetails priceDetailTwo = new PriceDetails();
                priceDetailTwo.setType(6);
                priceDetailTwo.setPrice(String.valueOf(dataBean.getCarProduct().getPrice() - HuoDongMoney));

                data.add(priceDetailOne);
                data.add(priceDetailTwo);
                s = gson.toJson(data);
                break;
            case 7://活动卡 次卡
                s = "";
                break;
            default://其他支付
                s = "";
                break;
        }

        final String finalS = s;
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = "";
                switch (HuoDongType) {
                    case 6://代金券  混合支付
                        result = MeModel.OrderPay(OrderId, String.valueOf(HuoDongMoney), CardNo, String.valueOf(PayType), finalS);
                        break;
                    case 7://次卡  单独支付
                        result = MeModel.OrderPay(OrderId, String.valueOf(dataBean.getCarProduct().getPrice()), CardNo, String.valueOf("5"), finalS);
                        break;
                    default://单独支付
                        result = MeModel.OrderPay(OrderId, String.valueOf(dataBean.getCarProduct().getPrice()), "", String.valueOf(PayType), finalS);
                        break;
                }
                if (result != null)
                    emitter.onNext(result);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<String, BaseBean>() {
                    @Override
                    public BaseBean apply(String s) throws Exception {
                        Gson gson = new Gson();
                        return gson.fromJson(s, BaseBean.class);
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
                    public void onNext(BaseBean baseBean) {
                        switch (baseBean.get_metadata().getCode()) {
                            case 200:
                                /*调用支付  支付成功 finish  支付分为余额支付和微信支付  需要加判断  还有分次卡支付*/
                                //todo 还需要处理支付的逻辑  网页支付
                                switch (Integer.parseInt(fromPage)) {
                                    case 1:
                                        switch (PayType) {
                                            case 1://微信
                                                //todo 跳转到网页

                                                break;

                                            case 3://余额
                                                setClearData();
                                                CustomToast.showSuccessToast(ConfirmationOfOrderActivity.this, baseBean.get_metadata().getMessage());
                                                finish();
                                                break;
                                            case 0:
                                            case 5:
                                                setClearData();
                                                CustomToast.showSuccessToast(ConfirmationOfOrderActivity.this, baseBean.get_metadata().getMessage());
                                                finish();
                                                break;
                                        }


                                        break;
                                    case 2://订单列表跳转到订单确认传2
                                        switch (PayType) {
                                            case 1://微信
                                                //todo  跳转到网页

                                                break;

                                            case 3://余额
                                                CustomToast.showSuccessToast(ConfirmationOfOrderActivity.this, baseBean.get_metadata().getMessage());
                                                finish();
                                                break;
                                            case 0:
                                            case 5:
                                                CustomToast.showSuccessToast(ConfirmationOfOrderActivity.this, baseBean.get_metadata().getMessage());
                                                finish();
                                                break;
                                        }
                                        break;
                                }

                                break;
                            case 500:
                                showToast(baseBean.get_metadata().getMessage(), 3);
                                break;

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }
                });
    }

    @SuppressLint("WrongConstant")
    private void ShowPopUpWindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.item_pay_way, null);
        mPopupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, true);
        TextView tvWeChatPay = contentView.findViewById(R.id.tv_wechat_pay);
        TextView tvBalancePay = contentView.findViewById(R.id.tv_balance_pay);
        LinearLayout llPop = contentView.findViewById(R.id.ll_top);
        llPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });

        /*微信支付*/
        tvWeChatPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayType = 1;
                tvPayWay.setText(getResources().getString(R.string.app_order_wechat_pay));
                mPopupWindow.dismiss();
            }
        });
        /*余额支付*/
        tvBalancePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayType = 3;
                tvPayWay.setText(getResources().getString(R.string.app_order_balance_pay));
                mPopupWindow.dismiss();
                Double balance = Double.parseDouble(String.valueOf(UserInfoData.getBalance()));
                if (balance < Double.parseDouble(String.valueOf(dataBean.getCarProduct().getPrice()))) {
                    showToast(getResources().getString(R.string.balance_insufficient), 2);
                }
            }
        });

        mPopupWindow.setFocusable(true);//这里必须设置为true才能点击区域外或者消失

        mPopupWindow.setTouchable(true);//这个控制PopupWindow内部控件的点击事件
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //显示PopupWindow
        View rootview = LayoutInflater.from(this).inflate(R.layout.fragment_me, null);
        mPopupWindow.setAnimationStyle(R.style.contextMenuAnim);

        mPopupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
    }

}
