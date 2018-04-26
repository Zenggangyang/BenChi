package com.soowin.staremblem.ui.index.activity;


import android.annotation.SuppressLint;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
import com.soowin.staremblem.ui.index.adapter.OrderListAdapter;
import com.soowin.staremblem.ui.index.bean.BaseBean;

import com.soowin.staremblem.ui.index.bean.CouponListBean;
import com.soowin.staremblem.ui.index.bean.OrderListBean;

import com.soowin.staremblem.ui.index.mModel.MeModel;
import com.soowin.staremblem.ui.login.activity.LoginActivity;
import com.soowin.staremblem.ui.login.activity.WebViewActivity;
import com.soowin.staremblem.utils.BaseActivity;
import com.soowin.staremblem.utils.PublicApplication;
import com.soowin.staremblem.utils.StrUtils;

import com.soowin.staremblem.utils.myView.CustomToast;

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
 * 订单列表页   订单状态（-1:加载全部订单 0:待支付 1:预约完成 2:派单中 3:派单完成 4:订单完成 5:取消订单 6:订单已删除）
 */
public class OrderListActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = OrderListActivity.class.getSimpleName();
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();//异步请求控制器
    /*title*/
    private TextView tvTitle;
    private ImageView ivBack;
    /*初始化View*/
    private RecyclerView rvOrder;
    private OrderListAdapter orderListAdapter;
    private int displayge = -1;//页面切换

    private ImageView ivPopWindow;//图片三角按钮
    private PopupWindow mPopupWindow;
    private PopupWindow mDeletePopUpWindow;
    private PopupWindow mOrderPopupWindow;//订单状态的PopUpWIndow
    View contentView;
    List<OrderListBean.DataBean> OrderData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        OrderData = (List<OrderListBean.DataBean>) getIntent().getSerializableExtra("nulldata");
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (StrUtils.isEmpty(PublicApplication.loginInfo.getString("token", ""))) {
            startActivity(new Intent(OrderListActivity.this, LoginActivity.class));
        } else {
            OrderData = new ArrayList<>();
            displayge = -1;
            orderListAdapter.setData(OrderData);
            initData();
        }
    }

    /*初始化View*/
    private void initView() {
        initTitle();
        initContent();
    }

    /*初始化View*/
    private void initContent() {
        tvTitle = findViewById(R.id.tv_title);
        ivBack = findViewById(R.id.iv_back);
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(this);
        tvTitle.setText(PublicApplication.resourceText.getString("app_order_title", getResources().getString(R.string.app_order_title)));
        //图片资源初始化
        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_go_back", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_go_back))
                .error(getResources().getDrawable(R.drawable.img_go_back))
                .into(ivBack);
        tvTitle.setOnClickListener(this);
        rvOrder = findViewById(R.id.rv_order_list);
        orderListAdapter = new OrderListAdapter(this, OrderListActivity.this);
        rvOrder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvOrder.setAdapter(orderListAdapter);
        contentView = LayoutInflater.from(this).inflate(R.layout.item_order_detail, null);
        mPopupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, true);
    }

    private void initTitle() {
        ivPopWindow = findViewById(R.id.iv_popwindow);
        ivPopWindow.setOnClickListener(this);
        //图片资源初始化
        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_icon_order_down", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_icon_order_down))
                .error(getResources().getDrawable(R.drawable.img_icon_order_down))
                .into(ivPopWindow);
    }


    /*初始化数据*/
    private void initData() {
        getMyOrderList();
    }


    //    取消订单 的编辑框
    public void showPopUpWindow(final String OrderId) {

        View contentView = LayoutInflater.from(this).inflate(R.layout.item_main_del_order, null);
        mDeletePopUpWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, true);

        contentView.findViewById(R.id.ll_bg).setOnClickListener(this);
        /*确定*/
        contentView.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelOrder(OrderId);
            }
        });
        /*取消*/
        contentView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeletePopUpWindow.dismiss();
            }
        });
        contentView.findViewById(R.id.ll_frame).setOnClickListener(this);
        TextView tvTishi = contentView.findViewById(R.id.tv_sure_cancel_order_tishi);
        tvTishi.setVisibility(View.VISIBLE);
        TextView tvCancelOrders = contentView.findViewById(R.id.tv_cancel_order);
        tvCancelOrders.setText(PublicApplication.resourceText.getString("app_order_cancel", getResources().getString(R.string.app_order_cancel)));
        tvTishi.setText(PublicApplication.resourceText.getString("app_sure_cancel_order_tishi", getResources().getString(R.string.app_sure_cancel_order_tishi)));
        TextView tvCancelOrder = contentView.findViewById(R.id.tv_sure_cancel_order);
        tvCancelOrder.setText(PublicApplication.resourceText.getString("app_sure_cancel_order", getResources().getString(R.string.app_sure_cancel_order)));
        mDeletePopUpWindow.setTouchable(true);
        //如果不设置popupwindow的背景，无论是点击外部还是Back都无法dismiss弹框
        mDeletePopUpWindow.setBackgroundDrawable(new BitmapDrawable());
        mDeletePopUpWindow.setOutsideTouchable(true);
        mDeletePopUpWindow.setFocusable(true);
//        mPopupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        mDeletePopUpWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //显示PopupWindow
        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        mDeletePopUpWindow.setAnimationStyle(R.style.contextMenuAnim);
//        rootview.getBackground().setAlpha(255);
        mDeletePopUpWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
    }

    /*去支付  还缺少测试*/
    public void unPayJump(String OrderId) {
    /*卡券Data数据*/
        CouponListBean.DataBean couponData = new CouponListBean.DataBean();
        Intent intent = new Intent(this, ConfirmationOfOrderActivity.class);
        Bundle mbundle = new Bundle();
        intent.putExtra(ConfirmationOfOrderActivity.ORDERID, OrderId);
        intent.putExtra(ConfirmationOfOrderActivity.FROM_PAGE, "2");
        mbundle.putSerializable(CouponListActivity.COUPONDATA, couponData);
        intent.putExtra("data", mbundle);
        startActivity(intent);
    }

    /*去评价 还缺少测试*/
    public void gotoServiceEvaluation(String orderId) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(WebViewActivity.TITLE, PublicApplication.loginInfo.getString(
                "app_order_evaluate", getResources().getString(R.string.app_order_evaluate)));
        intent.putExtra(WebViewActivity.URLS, PublicApplication.urlData.hostUrl +
                PublicApplication.urlData.OrderEvaluationIndex + "?OrderId=" + orderId);
        startActivity(intent);
    }

    //    删除订单 的编辑框  还缺少测试
    public void deleteOrderPopWindow(final String OrderId) {

        View contentView = LayoutInflater.from(this).inflate(R.layout.item_main_del_order, null);
        mDeletePopUpWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, true);

        contentView.findViewById(R.id.ll_bg).setOnClickListener(this);
        /*确定*/
        contentView.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteOrder(OrderId);
            }
        });
        /*取消*/
        contentView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeletePopUpWindow.dismiss();
            }
        });
        contentView.findViewById(R.id.ll_frame).setOnClickListener(this);

        TextView tvDeleteOrder = contentView.findViewById(R.id.tv_sure_cancel_order);
        TextView tvCancelOrder = contentView.findViewById(R.id.tv_cancel_order);
        tvCancelOrder.setText(PublicApplication.resourceText.getString("app_order_delete_left", getResources().getString(R.string.app_order_delete_left)));

        tvDeleteOrder.setText(PublicApplication.resourceText.getString("app_sure_delete_order", getResources().getString(R.string.app_sure_delete_order)));
        mDeletePopUpWindow.setTouchable(true);
        //如果不设置popupwindow的背景，无论是点击外部还是Back都无法dismiss弹框
        mDeletePopUpWindow.setBackgroundDrawable(new BitmapDrawable());
        mDeletePopUpWindow.setOutsideTouchable(true);
        mDeletePopUpWindow.setFocusable(true);
//        mPopupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        mDeletePopUpWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //显示PopupWindow
        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        mDeletePopUpWindow.setAnimationStyle(R.style.contextMenuAnim);
//        rootview.getBackground().setAlpha(255);
        mDeletePopUpWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
    }

    /*删除订单*/
    public void deleteOrder(final String OrderId) {
        showDialog();
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = MeModel.RemoveOrder(OrderId);

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
                                CustomToast.showSuccessToast(OrderListActivity.this, getResources().getString(R.string.app_coupon_delete_sucesss));
                                mDeletePopUpWindow.dismiss();
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mDeletePopUpWindow.dismiss();
                        dismissDialog();
                        showToast(PublicApplication.resourceText.getString("app_error_message", getResources().getString(R.string.app_error_message)), 3);
                    }

                    @Override
                    public void onComplete() {
                        getMyOrderList();
                    }
                });
    }


    //    显示订单的详情的popUpWindow
    @SuppressLint("WrongConstant")
    public void showOrderPopUpWindow(OrderListBean.DataBean dataBean) {

/*  */
        ImageView IvClose = contentView.findViewById(R.id.iv_close);
        IvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        TextView tvOrderSure = contentView.findViewById(R.id.tv_order_sure);

        tvOrderSure.setText(PublicApplication.resourceText.getString("app_address_sure", getResources().getString(R.string.app_address_sure)));
        tvOrderSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        showStatisDisplay();
        TextView tvOrderNo = contentView.findViewById(R.id.tv_order_number);//订单编号
        TextView tvCarPrice = contentView.findViewById(R.id.tv_car_price);//车型价格


        TextView tvServiceType = contentView.findViewById(R.id.tv_service_type);//服务类型
        LinearLayout llShuttleaba = contentView.findViewById(R.id.ll_shuttle_aba);//商务接送
        LinearLayout llDailyRental = contentView.findViewById(R.id.ll_dailt_rental);//商务日租
        LinearLayout llTransfer = contentView.findViewById(R.id.ll_transfer);//接送机服务
        LinearLayout llShuttleabc = contentView.findViewById(R.id.ll_shuttle_abc);//接送套餐


        TextView tvCarServiceType = contentView.findViewById(R.id.tv_car_service_type);//服务车型：
        TextView tvCustomerMessage = contentView.findViewById(R.id.tv_customer_message);//贵宾信息
        TextView tvServiceSpare = contentView.findViewById(R.id.tv_service_spare);//服务以及备品
        tvOrderNo.setText(String.valueOf(dataBean.getOrderCode()));
        /*商务接送*/
        TextView tvStringPickUpaba = contentView.findViewById(R.id.tv_string_pick_up_aba);
        tvStringPickUpaba.setText(PublicApplication.resourceText.getString("app_string_pick_up", getResources().getString(R.string.app_string_pick_up)));
        TextView tvPickUpaba = contentView.findViewById(R.id.tv_pick_up_aba);
        TextView tvStringCarCityaba = contentView.findViewById(R.id.tv_string_car_city_aba);
        tvStringCarCityaba.setText(PublicApplication.resourceText.getString("app_order_car_City", getResources().getString(R.string.app_order_car_City)));
        TextView tvCarCityaba = contentView.findViewById(R.id.tv_car_city_aba);
        TextView tvStringCarSiteaba = contentView.findViewById(R.id.tv_string_car_site_aba);
        tvStringCarSiteaba.setText(PublicApplication.resourceText.getString("app_order_car_site", getResources().getString(R.string.app_order_car_site)));
        TextView tvCarSiteaba = contentView.findViewById(R.id.tv_car_site_aba);
        TextView tvStringOrderDestinationaba = contentView.findViewById(R.id.tv_string_order_destination_aba);
        tvStringOrderDestinationaba.setText(PublicApplication.resourceText.getString("app_confirm_order_destination", getResources().getString(R.string.app_confirm_order_destination)));
        TextView tvOrderDestinationaba = contentView.findViewById(R.id.tv_order_destination_aba);
        /*接送套餐*/
        TextView tvStringSetOutabc = contentView.findViewById(R.id.tv_string_set_out_abc);
        tvStringSetOutabc.setText(PublicApplication.resourceText.getString("app_string_set_out", getResources().getString(R.string.app_string_set_out)));
        TextView tvSetOutabc = contentView.findViewById(R.id.tv_set_out_abc);

        TextView tvStringCarCityabc = contentView.findViewById(R.id.tv_string_car_city_abc);
        tvStringCarCityabc.setText(PublicApplication.resourceText.getString("app_order_car_City", getResources().getString(R.string.app_order_car_City)));
        TextView tvCarCityabc = contentView.findViewById(R.id.tv_car_city_abc);

        TextView tvStringCarSiteabc = contentView.findViewById(R.id.tv_string_car_site_abc);
        tvStringCarSiteabc.setText(PublicApplication.resourceText.getString("app_order_car_site", getResources().getString(R.string.app_order_car_site)));
        TextView tvCarSiteabc = contentView.findViewById(R.id.tv_car_site_abc);

        TextView tvStringOrderDestionabc = contentView.findViewById(R.id.tv_string_order_destination_abc);
        tvStringOrderDestionabc.setText(PublicApplication.resourceText.getString("app_confirm_order_destination", getResources().getString(R.string.app_confirm_order_destination)));
        TextView tvOrderDestionabc = contentView.findViewById(R.id.tv_order_destination_abc);

        TextView tvStringBackTrackingabc = contentView.findViewById(R.id.tv_string_back_tracking_abc);
        tvStringBackTrackingabc.setText(PublicApplication.resourceText.getString("app_string_back_tracking", getResources().getString(R.string.app_string_back_tracking)));
        TextView tvBackTracingabc = contentView.findViewById(R.id.tv_back_tracking_abc);

        TextView tvStringBackOrderDestinationabc = contentView.findViewById(R.id.tv_string_back_order_destination_abc);
        tvStringBackOrderDestinationabc.setText(PublicApplication.resourceText.getString("app_confirm_order_destination", getResources().getString(R.string.app_confirm_order_destination)));
        TextView tvBackOrderDestinationabc = contentView.findViewById(R.id.tv_back_order_destination_abc);

        /*接送机服务*/
        TextView tvStringFlightNumberTransfer = contentView.findViewById(R.id.tv_string_flightnumber_transfer);
        tvStringFlightNumberTransfer.setText(PublicApplication.resourceText.getString("app_airport_message", getResources().getString(R.string.app_airport_message)));
        TextView tvFlightNumberTransfer = contentView.findViewById(R.id.tv_flight_number_transfer);
        TextView tvStringJieJiTimeTransfer = contentView.findViewById(R.id.tv_string_jieji_time_transfer);
        TextView tvJieJiTransfer = contentView.findViewById(R.id.tv_jieji_time_transfer);
        TextView tvStringOrderDestination = contentView.findViewById(R.id.tv_string_order_destination);
        tvStringOrderDestination.setText(PublicApplication.resourceText.getString("app_confirm_order_destination", getResources().getString(R.string.app_confirm_order_destination)));
        TextView tvOrderDestination = contentView.findViewById(R.id.tv_order_destination_transfer);

        /*商务日租*/
        TextView tvStringPickUpDaily = contentView.findViewById(R.id.tv_string_pick_up_daily);
        tvStringPickUpDaily.setText(PublicApplication.resourceText.getString("app_string_pick_up", getResources().getString(R.string.app_string_pick_up)));
        TextView tvPickUpDaily = contentView.findViewById(R.id.tv_pick_up_daily);
        TextView tvStringCarCityDaily = contentView.findViewById(R.id.tv_string_car_city_daily);
        tvStringCarCityDaily.setText(PublicApplication.resourceText.getString("app_order_car_City", getResources().getString(R.string.app_order_car_City)));
        TextView tvCarCityDaily = contentView.findViewById(R.id.tv_car_city_daily);
        TextView tvStringCarSiteDaily = contentView.findViewById(R.id.tv_string_car_site_daily);
        tvStringCarSiteDaily.setText(PublicApplication.resourceText.getString("app_order_car_site", getResources().getString(R.string.app_order_car_site)));
        TextView tvCarSiteDaily = contentView.findViewById(R.id.tv_car_site_daily);


             /*1-7  没有4*/
        switch (dataBean.getCarProduct().getServiceType()) {
            case 1://接机
                tvServiceType.setText(PublicApplication.resourceText.getString("app_transfer_form_type_name_01", getResources().getString(R.string.app_transfer_form_type_name_01)));
                llTransfer.setVisibility(View.VISIBLE);
                llDailyRental.setVisibility(View.GONE);
                llShuttleaba.setVisibility(View.GONE);
                llShuttleabc.setVisibility(View.GONE);
                tvStringJieJiTimeTransfer.setText(PublicApplication.resourceText.getString("app_order_jieji_time", getResources().getString(R.string.app_order_jieji_time)));
                tvFlightNumberTransfer.setText(" " + dataBean.getCarProduct().getFlightNumber());
                tvJieJiTransfer.setText("" + dataBean.getCarProduct().getTimeBoard());
                tvOrderDestination.setText("  " + dataBean.getCarProduct().getDestination());

                break;
            case 2://送机
                tvServiceType.setText(PublicApplication.resourceText.getString("app_transfer_form_type_name_02", getResources().getString(R.string.app_transfer_form_type_name_02)));
                llTransfer.setVisibility(View.VISIBLE);
                llDailyRental.setVisibility(View.GONE);
                llShuttleaba.setVisibility(View.GONE);
                llShuttleabc.setVisibility(View.GONE);
                tvStringJieJiTimeTransfer.setText(PublicApplication.resourceText.getString("app_order_songji_time", getResources().getString(R.string.app_order_songji_time)));
                tvFlightNumberTransfer.setText("" + dataBean.getCarProduct().getFlightNumber());
                tvJieJiTransfer.setText("" + dataBean.getCarProduct().getTimeBoard());
                tvOrderDestination.setText("  " + dataBean.getCarProduct().getAddress());
                break;
            case 3://日租
                tvServiceType.setText(PublicApplication.resourceText.getString("app_order_daily_rental", getResources().getString(R.string.app_order_daily_rental)));
                llTransfer.setVisibility(View.GONE);
                llDailyRental.setVisibility(View.VISIBLE);
                llShuttleaba.setVisibility(View.GONE);
                llShuttleabc.setVisibility(View.GONE);
                tvPickUpDaily.setText("" + dataBean.getCarProduct().getTimeBoard());
                tvCarCityDaily.setText("" + dataBean.getCarProduct().getCarCity());
                tvCarSiteDaily.setText("" + dataBean.getCarProduct().getDestination());
                break;
            case 5://商务
                tvServiceType.setText(PublicApplication.resourceText.getString("app_order_business", getResources().getString(R.string.app_order_business)));
                llTransfer.setVisibility(View.GONE);
                llDailyRental.setVisibility(View.GONE);
                llShuttleaba.setVisibility(View.VISIBLE);
                llShuttleabc.setVisibility(View.GONE);
                tvPickUpaba.setText("" + dataBean.getCarProduct().getTimeBoard());
                tvCarCityaba.setText("" + dataBean.getCarProduct().getCarCity());
                tvCarSiteaba.setText("" + dataBean.getCarProduct().getStartPlace());
                tvOrderDestinationaba.setText("  " + dataBean.getCarProduct().getDestination());
                break;
            case 6://半日租
                tvServiceType.setText(PublicApplication.resourceText.getString("app_order_half_day_rent", getResources().getString(R.string.app_order_half_day_rent)));
                break;
            case 7://接送套餐
                tvServiceType.setText(PublicApplication.resourceText.getString("app_order_transport_package", getResources().getString(R.string.app_order_transport_package)));
                llTransfer.setVisibility(View.GONE);
                llDailyRental.setVisibility(View.GONE);
                llShuttleaba.setVisibility(View.GONE);
                llShuttleabc.setVisibility(View.VISIBLE);
                tvSetOutabc.setText("" + dataBean.getCarProduct().getTimeBoard());
                tvCarCityabc.setText("" + dataBean.getCarProduct().getCarCity());
                tvCarSiteabc.setText("" + dataBean.getCarProduct().getStartPlace());
                tvOrderDestionabc.setText("  " + dataBean.getCarProduct().getDestination());
                tvBackTracingabc.setText("" + dataBean.getCarProduct().getReturnTimeBoard());
                tvBackOrderDestinationabc.setText("  " + dataBean.getCarProduct().getReturnDestination());
                break;
        }
        tvCarServiceType.setText(dataBean.getCarProduct().getCarBrand());
        tvCarPrice.setText(dataBean.getCarProduct().getPrice() + "元");
    /*    tvAirportInformation.setText("" + dataBean.getCarProduct().getFlightNumber());
        tvPickUpTime.setText(dataBean.getCarProduct().getTimeBoard().replace("T"," "));*/
        String take_mobile = dataBean.getCarProduct().getPassengerMobile();
        StringBuilder replacePhone = new StringBuilder(take_mobile);
        try {
            replacePhone.replace(3, 7, "****");
            replacePhone.substring(7, 11);
            tvCustomerMessage.setText(dataBean.getCarProduct().getPassengerName() + " " + replacePhone);
        } catch (Exception e) {
            Log.e(TAG, "showOrderPopUpWindow: " + e.getMessage());
            tvCustomerMessage.setText(dataBean.getCarProduct().getPassengerName() + " " + dataBean.getCarProduct().getPassengerMobile());
        }


        if (dataBean.getCarProduct().getRemark() != null && !StrUtils.isEmpty(dataBean.getCarProduct().getRemark())) {
            tvServiceSpare.setText(dataBean.getCarProduct().getRemark().substring(0, dataBean.getCarProduct().getRemark().length() - 1));
        } else {
            tvServiceSpare.setText("");
        }

        if (!mPopupWindow.isShowing()) {
            mPopupWindow.setTouchable(true);
            //如果不设置popupwindow的背景，无论是点击外部还是Back都无法dismiss弹框
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
            mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            mPopupWindow.setFocusable(true);
            //显示PopupWindow
            View rootview = findViewById(R.id.rl_title);
            mPopupWindow.showAtLocation(rootview, 0, 0, Gravity.CENTER);
        }

    }

    /*静态文字*/
    private void showStatisDisplay() {
        TextView tvStringOrderNumber = contentView.findViewById(R.id.tv_string_order_number);
        tvStringOrderNumber.setText(PublicApplication.resourceText.getString("app_order_number", getResources().getString(R.string.app_order_number)));
        TextView tvStringServiceType = contentView.findViewById(R.id.tv_string_service_type);
        TextView tvStringServiceCar = contentView.findViewById(R.id.tv_string_service_Car);
        tvStringServiceType.setText(PublicApplication.resourceText.getString("app_service_type", getResources().getString(R.string.app_service_type)));
        tvStringServiceCar.setText(PublicApplication.resourceText.getString("app_service_car", getResources().getString(R.string.app_service_car)));
        TextView tvStringCarPrice = contentView.findViewById(R.id.tv_string_car_price);
        TextView tvStringGuestMessage = contentView.findViewById(R.id.tv_string_guest_message);
        TextView tvStrinAirportBeiPin = contentView.findViewById(R.id.tv_string_airpory_beipin);

        tvStringCarPrice.setText(PublicApplication.resourceText.getString("app_string_car_price", getResources().getString(R.string.app_string_car_price)));
        tvStringGuestMessage.setText(PublicApplication.resourceText.getString("app_airport_guibin", getResources().getString(R.string.app_airport_guibin)));
        tvStrinAirportBeiPin.setText(PublicApplication.resourceText.getString("app_airport_beipin", getResources().getString(R.string.app_airport_beipin)));

    }
    /*订单详情*/

    public void orderDetail(final OrderListBean.DataBean data) {
        showOrderPopUpWindow(data);
    }

    /*取消订单*/
    public void cancelOrder(final String OrderId) {
        showDialog();
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = MeModel.CancelOrder(OrderId);

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
                                CustomToast.showSuccessToast(OrderListActivity.this, getResources().getString(R.string.app_order_cancel_sucess));
                                mDeletePopUpWindow.dismiss();
                                break;
                            case 500:
                                showToast(baseBean.get_metadata().getMessage(), 3);
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                        mDeletePopUpWindow.dismiss();
                        showToast(PublicApplication.resourceText.getString("app_error_message", getResources().getString(R.string.app_error_message)), 3);
                    }

                    @Override
                    public void onComplete() {
                        getMyOrderList();
                    }
                });
    }

    /*获取我的订单列表*/
    private void getMyOrderList() {
        showDialog();
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = MeModel.MyOrders(String.valueOf(displayge));

                if (result != null)
                    emitter.onNext(result);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<String, OrderListBean>() {
                    @Override
                    public OrderListBean apply(String s) throws Exception {
                        Gson gson = new Gson();
                        return gson.fromJson(s, OrderListBean.class);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (mCompositeDisposable != null)
                            mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(OrderListBean baseBean) {
                        switch (baseBean.get_metadata().getCode()) {
                            case 200:
                                List<OrderListBean.DataBean> newData = new ArrayList<>();
                                OrderData.clear();
                                OrderData.addAll(baseBean.getData());

                                orderListAdapter.setDatas(OrderData);
                                orderListAdapter.setData(OrderData);
                                orderListAdapter.notifyDataSetChanged();
                                rvOrder.smoothScrollToPosition(0);
                                if (OrderData.size() == 0) {
                                    showToast("暂无数据", 3);
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
                        showToast(PublicApplication.resourceText.getString("app_error_message", getResources().getString(R.string.app_error_message)), 3);
                    }

                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_popwindow:
                showPopUpWindow();

                break;
            case R.id.iv_back:

                finish();
                break;
            case R.id.tv_title:
                showPopUpWindow();
                break;
        }
    }

    /*返回按钮*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
        OrderListActivity.this.finish();
    }

    /*弹出选择不同订单状态的数据*/
    @SuppressLint("WrongConstant")
    private void showPopUpWindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.item_order_status, null);
        mOrderPopupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, true);
        LinearLayout llOrderBottom = contentView.findViewById(R.id.ll_order_bottom);
        llOrderBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderPopupWindow.dismiss();
            }
        });
        LinearLayout llBottom = contentView.findViewById(R.id.ll_bottom);
        llBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderPopupWindow.dismiss();
            }
        });
        TextView tvTitle = contentView.findViewById(R.id.tv_title);
        tvTitle.setText(getResources().getString(R.string.app_order_title));
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderPopupWindow.dismiss();

            }
        });
        ImageView ivBack = contentView.findViewById(R.id.iv_back);
        ivBack.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_go_back", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_go_back))
                .error(getResources().getDrawable(R.drawable.img_go_back))
                .into(ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderPopupWindow.dismiss();
                finish();
            }
        });
        ImageView ivPopwindow = contentView.findViewById(R.id.iv_popwindow);
        //图片资源初始化
        Glide.with(this)
                .load(PublicApplication.resourceText.getString("icon_order_up", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.icon_order_up))
                .error(getResources().getDrawable(R.drawable.icon_order_up))
                .into(ivPopwindow);
        TextView tvAllorder = contentView.findViewById(R.id.tv_all_order);
        tvAllorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayge = -1;
                initData();
                mOrderPopupWindow.dismiss();
            }
        });
        TextView tvUnpay = contentView.findViewById(R.id.tv_unpay);
        TextView tvYuYueComplete = contentView.findViewById(R.id.tv_yuyue_complete);
        TextView tvPaidan = contentView.findViewById(R.id.tv_paidan);
        TextView tvPaidanComplete = contentView.findViewById(R.id.tv_paidan_complete);
        TextView tvOrderComplete = contentView.findViewById(R.id.tv_order_complete);
        TextView tvCancelOrder = contentView.findViewById(R.id.tv_cancel_order);
        tvUnpay.setOnClickListener(new View.OnClickListener() {//待支付 0
            @Override
            public void onClick(View v) {
                displayge = 0;
                initData();
                mOrderPopupWindow.dismiss();
            }
        });
        tvYuYueComplete.setOnClickListener(new View.OnClickListener() {//预约完成 1
            @Override
            public void onClick(View v) {
                displayge = 1;
                initData();
                mOrderPopupWindow.dismiss();
            }
        });
        tvPaidan.setOnClickListener(new View.OnClickListener() {//派单中 2
            @Override
            public void onClick(View v) {
                displayge = 2;
                initData();
                mOrderPopupWindow.dismiss();
            }
        });
        tvPaidanComplete.setOnClickListener(new View.OnClickListener() {//派单完成 3
            @Override
            public void onClick(View v) {
                displayge = 3;
                initData();
                mOrderPopupWindow.dismiss();
            }
        });
        tvOrderComplete.setOnClickListener(new View.OnClickListener() {//订单完成 4
            @Override
            public void onClick(View v) {
                displayge = 4;
                initData();
                mOrderPopupWindow.dismiss();
            }
        });
        tvCancelOrder.setOnClickListener(new View.OnClickListener() {//取消订单  5
            @Override
            public void onClick(View v) {
                displayge = 5;
                initData();
                mOrderPopupWindow.dismiss();
            }
        });
        ivPopwindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderPopupWindow.dismiss();
            }
        });
        mOrderPopupWindow.setTouchable(true);
        //如果不设置popupwindow的背景，无论是点击外部还是Back都无法dismiss弹框
        mOrderPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mOrderPopupWindow.setOutsideTouchable(true);
        mOrderPopupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        mOrderPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mOrderPopupWindow.setFocusable(true);
        //显示PopupWindow
        View rootview = findViewById(R.id.rl_title);
        mOrderPopupWindow.showAtLocation(rootview, Gravity.TOP, 0, 0);
        mOrderPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

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
}
