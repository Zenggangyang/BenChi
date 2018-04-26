package com.soowin.staremblem.ui.index.adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soowin.staremblem.R;
import com.soowin.staremblem.ui.index.activity.CouponListActivity;
import com.soowin.staremblem.ui.index.bean.CouponListBean;
import com.soowin.staremblem.ui.login.activity.WebViewActivity;
import com.soowin.staremblem.utils.BaseRecyclerViewAdapter;
import com.soowin.staremblem.utils.BaseViewHolder;
import com.soowin.staremblem.utils.PublicApplication;

/**
 * Created by hongfu on 2018/3/20.
 * 类的作用：优惠券列表
 * 版本号：1.0.0
 */

public class CouponListAdapter extends BaseRecyclerViewAdapter<CouponListBean.DataBean> {
    private static final String TAG = CouponListAdapter.class.getSimpleName();
    private Context context;
    private int width;
    private String Type = "0";//0是未过期 1是已过期
    private CouponListActivity couponListActivity;

    public CouponListAdapter(Context context, int width, CouponListActivity couponListActivity) {
        super(context);
        this.context = context;
        this.width = width;
        this.couponListActivity = couponListActivity;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    @Override
    protected int inflaterItemLayout(int viewType) {
        return R.layout.item_coupon_list;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void bindData(BaseViewHolder holder, int position, final CouponListBean.DataBean dataBean) {

        TextView tvCardNumber = holder.findViewById(R.id.tv_card_number);
        TextView tvRule = holder.findViewById(R.id.tv_rule);
        TextView tvRight = holder.findViewById(R.id.tv_right);
        tvCardNumber.setText(PublicApplication.resourceText.getString("app_coupon_card_number", context.getResources().getString(R.string.app_coupon_card_number)) + dataBean.getCardNo());
        tvRule.setText(PublicApplication.resourceText.getString("app_coupon_rule", context.getResources().getString(R.string.app_coupon_rule)));
        tvRight.setText(PublicApplication.resourceText.getString("app_coupon_right", context.getResources().getString(R.string.app_coupon_right)));

        View fengexian = holder.findViewById(R.id.view_fengexian);
        ImageView flLogo = holder.findViewById(R.id.iv_view_alpha);
        ImageView flNumberAlpha = holder.findViewById(R.id.iv_number_alpha);
        ImageView ivXiaHuaXian = holder.findViewById(R.id.iv_xiahuaxian);
        final WebView webView = holder.findViewById(R.id.wv_coupon);
        holder.findViewById(R.id.fl_logo).setLayoutParams(new RelativeLayout.LayoutParams(width, width * 37 / 63));
        holder.findViewById(R.id.wv_coupon).setLayoutParams(new FrameLayout.LayoutParams(width, width * 37 / 63));
        holder.findViewById(R.id.iv_view_alpha).setLayoutParams(new FrameLayout.LayoutParams(width, width * 37 / 63));
        switch (Type) {
            case "0"://未过期
                tvRule.setVisibility(View.VISIBLE);
                tvRight.setVisibility(View.VISIBLE);
                fengexian.setBackgroundColor(context.getResources().getColor(R.color.theme_color));
                tvRight.setText(context.getResources().getString(R.string.app_coupon_right));
                flLogo.setVisibility(View.GONE);
                flNumberAlpha.setVisibility(View.GONE);
                ivXiaHuaXian.setVisibility(View.GONE);
                /*点击规则*/
                tvRule.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        couponListActivity.showPopUpRuleWindow(dataBean.getInstructionsHtml());
                    }
                });

                    /*立即使用*/
                tvRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        couponListActivity.returnHomePage(dataBean);
                    }
                });
                break;
            case "1"://已过期
                flLogo.setVisibility(View.VISIBLE);
                tvRule.setVisibility(View.GONE);
                tvRight.setVisibility(View.VISIBLE);
                flNumberAlpha.setVisibility(View.VISIBLE);
                ivXiaHuaXian.setVisibility(View.VISIBLE);
                fengexian.setBackgroundColor(context.getResources().getColor(R.color.gray));
                tvRight.setText(context.getResources().getString(R.string.app_coupon_delete));
                   /*删除*/  //
                tvRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        couponListActivity.showPopUpWindow(dataBean.getCardNo());
                    }
                });
                break;
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.requestFocus();
        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示
        webView.loadUrl(dataBean.getShowHtml());
        /** 使webview自己处理打开网页事件，不调用系统浏览器打开*/
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        ProgressBar mProgressBar = holder.findViewById(R.id.pb_progress);
        webView.setWebChromeClient(new WebChromeClient(mProgressBar));

       /* webView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);

        TextView tvCoupon = holder.findViewById(R.id.tv_coupon);
        tvCoupon.setMovementMethod(ScrollingMovementMethod.getInstance());//滚动
        tvCoupon.setText(Html.fromHtml(dataBean.getShowHtml()));*/
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

    @Override
    protected void onItemClickListener(View itemView, int position, CouponListBean.DataBean dataBean) {

    }


}
