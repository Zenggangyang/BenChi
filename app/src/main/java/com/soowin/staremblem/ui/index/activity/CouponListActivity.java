package com.soowin.staremblem.ui.index.activity;


import android.annotation.SuppressLint;
import android.app.AlertDialog;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.soowin.staremblem.R;
import com.soowin.staremblem.ui.index.adapter.CouponListAdapter;

import com.soowin.staremblem.ui.index.bean.BaseBean;
import com.soowin.staremblem.ui.index.bean.CouponListBean;
import com.soowin.staremblem.ui.index.mModel.MeModel;

import com.soowin.staremblem.ui.login.activity.LoginActivity;
import com.soowin.staremblem.utils.BaseActivity;

import com.soowin.staremblem.utils.PublicApplication;
import com.soowin.staremblem.utils.StrUtils;
import com.soowin.staremblem.utils.Utlis;

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
 * 卡卷列表页  我的卡券
 */
public class CouponListActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = CouponListActivity.class.getSimpleName();
    public static final String NOOUTDATE = "no_out_date";
    public static final String OUTDATE = "out_date";
    public static final String COUPONDATA = "coupon_data";
    public static final String ORDERID = "order_id";
    /*tilte*/
    private TextView tvTitle;
    private ImageView ivBack;
    /*Recyclerview*/
    private RecyclerView rvCoupon;
    private CouponListAdapter couponListAdapter;
    /*视图切换*/
    private View mFirstIndicator;//第一个指示器
    private View mSecondIndicator;//第二个指示器
    private TextView tvNoOutDate;//未过期
    private TextView tvOutDate;//已过期
    private int displaypage = 1;

    private RelativeLayout rlNoOutDate;
    private RelativeLayout rkOutDate;
    private int width;


    private String IsOverdue = "";//是否过期 0：未过期  1：已过期
    private Boolean isFirst = true;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();//异步请求控制器

    //    未过期数据
    List<CouponListBean.DataBean> noOutDate = new ArrayList<>();
    /*已过期数据*/
    List<CouponListBean.DataBean> OutDate = new ArrayList<>();
    private PopupWindow mPopupWindowRule;
    private String OrderId = "";
    private PopupWindow mPopupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_list);

        noOutDate = (List<CouponListBean.DataBean>) getIntent().getSerializableExtra(NOOUTDATE);
        OutDate = (List<CouponListBean.DataBean>) getIntent().getSerializableExtra(OUTDATE);
        OrderId = getIntent().getStringExtra(ORDERID);
        if (StrUtils.isEmpty(PublicApplication.loginInfo.getString("token", ""))) {
            startActivity(new Intent(CouponListActivity.this, LoginActivity.class));
        } else {
            initWindow();
            initView();
        }

    }

    /*初始化View*/
    private void initView() {
        initTitle();
        initContent();
        if (noOutDate != null) {
            tvNoOutDate.setText(PublicApplication.resourceText.getString("app_coupon_not_out_date", getResources().getString(R.string.app_coupon_not_out_date)) + "(" + noOutDate.size() + ")");
        }
        if (OutDate != null) {
            tvOutDate.setText(PublicApplication.resourceText.getString("app_coupon_out_date", getResources().getString(R.string.app_coupon_out_date)) + "(" + OutDate.size() + ")");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (noOutDate != null && OutDate != null) {
            if (noOutDate.size() > 0 || OutDate.size() > 0) {
                tvNoOutDate.setText(PublicApplication.resourceText.getString("app_coupon_not_out_date", getResources().getString(R.string.app_coupon_not_out_date)) + "(" + noOutDate.size() + ")");
                tvOutDate.setText(PublicApplication.resourceText.getString("app_coupon_out_date", getResources().getString(R.string.app_coupon_out_date)) + "(" + OutDate.size() + ")");
                couponListAdapter.setData(noOutDate);
            } else {
                displaypage = 1;
                getData(displaypage);
            }
            if(noOutDate.size()==0)
            {
                showToast("暂无数据", 3);
            }
        } else {
            displaypage = 1;
            getData(displaypage);
        }
    }

    final Handler myHandler = new Handler();

    /*使用规则*/
    @SuppressLint({"WrongConstant", "JavascriptInterface"})
    public void showPopUpRuleWindow(String url) {

        View contentView = LayoutInflater.from(this).inflate(R.layout.item_coupon_rule, null);
        mPopupWindowRule = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, true);



        final WebView webView = contentView.findViewById(R.id.wv_coupon_rule);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.requestFocus();
        /*final JavaScriptInterface myJavaScriptInterface
                = new JavaScriptInterface(this);
        webView.addJavascriptInterface(myJavaScriptInterface, "android");*/
        /*var result =window.android.back();*/
        webView.loadUrl(url);
        /** 使webview自己处理打开网页事件，不调用系统浏览器打开*/
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        /**使webview能够响应back按键，点击back按键回退网页，不会退出整个Activity*/
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
                return false;

            }
        });
        //映射.可以调用js里面的方法
        webView.addJavascriptInterface(new JavaScriptInterface(), "jsi");

        ProgressBar mProgressBar =   contentView.findViewById(R.id.pb_progress);
        webView.setWebChromeClient(new WebChromeClient(mProgressBar));

        mPopupWindowRule.setTouchable(true);
        //如果不设置popupwindow的背景，无论是点击外部还是Back都无法dismiss弹框
        mPopupWindowRule.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindowRule.setOutsideTouchable(true);
        mPopupWindowRule.setFocusable(true);
        mPopupWindowRule.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindowRule.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示
        //显示PopupWindow
        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_transfer, null);
        mPopupWindowRule.setAnimationStyle(R.style.contextMenuAnim);
        rootview.getBackground().setAlpha(255);
        mPopupWindowRule.showAtLocation(rootview, Gravity.CENTER, 0, 0);

    }
    /**
     * 进度条状态修改
     */
    public class WebChromeClient extends android.webkit.WebChromeClient {
        private ProgressBar mProgress;
        public WebChromeClient(ProgressBar mProgressBar){
            mProgress = mProgressBar;
        }
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mProgress.setVisibility(View.GONE);
            } else {
                if (mProgress.getVisibility() == View.GONE)
                    mProgress.setVisibility(View.VISIBLE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    /*private final class JSInterface{

         * 注意这里的@JavascriptInterface注解， target是4.2以上都需要添加这个注解，否则无法调用
         * @param

        @JavascriptInterface
        public void showToast(String text){
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        }
        @JavascriptInterface
        public void showJsText(String text){
            webView.loadUrl("javascript:jsText('"+text+"')");
        }
    }*/
    private final class JavaScriptInterface {
        @JavascriptInterface
        public void close() {
            myHandler.post(new Runnable() {
                @Override
                public void run() {
//                     执行UI线程
                    mPopupWindowRule.dismiss();
                }
            });
        }
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


    /*初始化Content*/
    private void initContent() {
        rlNoOutDate = findViewById(R.id.rl_no_out_date);
        rkOutDate = findViewById(R.id.rl_out_date);
        rlNoOutDate.setOnClickListener(this);
        rkOutDate.setOnClickListener(this);
        rvCoupon = findViewById(R.id.rv_coupon);
        mFirstIndicator = findViewById(R.id.main_first_page_indicators);
        mSecondIndicator = findViewById(R.id.main_third_page_indicators);
        tvNoOutDate = findViewById(R.id.tv_no_out_date);
        tvOutDate = findViewById(R.id.tv_out_date);
        couponListAdapter = new CouponListAdapter(this, width / 7 * 6, CouponListActivity.this);
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvCoupon.setLayoutManager(llm);
        rvCoupon.setAdapter(couponListAdapter);

    }

    /*初始化title*/
    private void initTitle() {
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(PublicApplication.resourceText.getString("app_coupon_title", getResources().getString(R.string.app_coupon_title)));
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_go_back",""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_go_back))
                .error(getResources().getDrawable(R.drawable.img_go_back))
                .into(ivBack);
        ivBack.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_no_out_date://未过期
                mFirstIndicator.setVisibility(View.VISIBLE);
                mSecondIndicator.setVisibility(View.INVISIBLE);
                tvNoOutDate.setTextColor(getResources().getColor(R.color.theme_color));
                tvOutDate.setTextColor(getResources().getColor(R.color.font_text));
                couponListAdapter.setData(noOutDate);
                couponListAdapter.setType("0");
                couponListAdapter.notifyDataSetChanged();
                if(noOutDate!=null) {
                    if (noOutDate.size() <= 0)
                        showToast("暂无数据", 3);
                }

                break;
            case R.id.rl_out_date://已过期
                couponListAdapter.setData(OutDate);
                mFirstIndicator.setVisibility(View.INVISIBLE);
                mSecondIndicator.setVisibility(View.VISIBLE);
                tvNoOutDate.setTextColor(getResources().getColor(R.color.font_text));
                tvOutDate.setTextColor(getResources().getColor(R.color.theme_color));
                couponListAdapter.setType("1");
                couponListAdapter.notifyDataSetChanged();
                if(OutDate!=null) {
                    if (OutDate.size() <= 0)
                        showToast("暂无数据", 3);
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    /*获取数据源*/
    private void getData(final int display) {
        switch (display) {
            case 1:
                IsOverdue = "0";
                break;
            case 2:
                IsOverdue = "1";
                break;
        }
        showDialog();
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = MeModel.MyCardList(IsOverdue, OrderId);
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
                                switch (display) {
                                    case 1:
                                        noOutDate = data.getData();
                                        couponListAdapter.setData(noOutDate);
                                        break;
                                    case 2:
                                        OutDate = data.getData();
                                        break;
                                }
                                switch (display) {
                                    case 1:
                                        tvNoOutDate.setText(PublicApplication.resourceText.getString("app_coupon_not_out_date", getResources().getString(R.string.app_coupon_not_out_date)) + "(" + data.getData().size() + ")");
                                        break;
                                    case 2:
                                        tvOutDate.setText(PublicApplication.resourceText.getString("app_coupon_out_date", getResources().getString(R.string.app_coupon_out_date)) + "(" + data.getData().size() + ")");
                                        break;
                                }
                                break;
                            case 500:
                                showToast(data.get_metadata().getMessage(),3);
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
                        if (isFirst) {
                            getData(2);
                            isFirst = false;
                        } else {
                            dismissDialog();
                        }
                    }
                });
    }

    //    删除的编辑框
    public void showPopUpWindow(final String CardNo) {
        View contentView = LayoutInflater.from(this).inflate(R.layout.item_main_del_order, null);
        mPopupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, true);

        contentView.findViewById(R.id.ll_bg).setOnClickListener(this);
        /*确定*/
        contentView.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCard(CardNo);
            }
        });
        /*取消*/
        contentView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        contentView.findViewById(R.id.ll_frame).setOnClickListener(this);

        TextView tvDeleteOrder=contentView.findViewById(R.id.tv_sure_cancel_order);
        tvDeleteOrder.setText(PublicApplication.resourceText.getString("app_sure_coupon_delete", getResources().getString(R.string.app_sure_coupon_delete)));
        mPopupWindow.setTouchable(true);
        //如果不设置popupwindow的背景，无论是点击外部还是Back都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
//        mPopupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //显示PopupWindow
        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        mPopupWindow.setAnimationStyle(R.style.contextMenuAnim);
//        rootview.getBackground().setAlpha(255);
        mPopupWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * 删除卡券
     *
     * @param cardNo
     */
    private void deleteCard(final String cardNo) {

        showDialog();
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = MeModel.DelteCard(cardNo);
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
                    public void onNext(BaseBean data) {
                        switch (data.get_metadata().getCode()) {
                            case 200:

                                showToast(getResources().getString(R.string.app_coupon_delete_sucesss), 1);
                                mPopupWindow.dismiss();
                                break;
                            case 500:
                                showToast(data.get_metadata().getMessage(),3);
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mPopupWindow.dismiss();
                        dismissDialog();
                        showToast(PublicApplication.resourceText.getString("app_error_message", getResources().getString(R.string.app_error_message)), 3);
                    }

                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }
                });

    }

    /*传递序列化的数据*/
    public void returnHomePage(CouponListBean.DataBean dataBeans) {

        startActivity(new Intent(this, MainActivity.class));
        EventBus.getDefault().post(dataBeans);

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mCompositeDisposable != null)
            mCompositeDisposable.clear();
        dismissDialog();

    }
}
