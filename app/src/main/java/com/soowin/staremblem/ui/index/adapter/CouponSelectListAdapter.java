package com.soowin.staremblem.ui.index.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.View;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;

import android.widget.FrameLayout;

import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.soowin.staremblem.R;

import com.soowin.staremblem.ui.index.bean.CouponListBean;
import com.soowin.staremblem.utils.BaseRecyclerViewAdapter;
import com.soowin.staremblem.utils.BaseViewHolder;
import com.soowin.staremblem.utils.PublicApplication;
import com.soowin.staremblem.utils.StrUtils;

/**
 * Created by hongfu on 2018/3/26.
 * 类的作用：选择卡券的adapter
 * 版本号：1.0.0
 */

public class CouponSelectListAdapter extends BaseRecyclerViewAdapter<CouponListBean.DataBean> {
    private static final String TAG = CouponSelectListAdapter.class.getSimpleName();
    private Context context;
    private int width;
    private int getPosition = -1;
    private SparseBooleanArray mSelectArray;
    private String CardNo = "";

    public CouponSelectListAdapter(Context context, int width) {
        super(context);
        this.context = context;
        this.width = width;
        mSelectArray = new SparseBooleanArray();
    }


    @Override
    protected int inflaterItemLayout(int viewType) {
        return R.layout.item_select_coupon_list;
    }

    public SparseBooleanArray getmSelectArray() {
        return mSelectArray;
    }

    public int GetPosition() {
        return getPosition;
    }

    public void setChecked(String popops) {
        this.CardNo = popops;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void bindData(final BaseViewHolder holder, final int position, CouponListBean.DataBean dataBean) {
        holder.setIsRecyclable(false);
        holder.findViewById(R.id.fl_logo).setLayoutParams(new RelativeLayout.LayoutParams(width, width * 37 / 63));
        holder.findViewById(R.id.wv_coupon).setLayoutParams(new FrameLayout.LayoutParams(width, width * 37 / 63));
/*        holder.findViewById(R.id.fl_logo).setLayoutParams(new RelativeLayout.LayoutParams(width, width * 37 / 63));
        holder.findViewById(R.id.wv_coupon).setLayoutParams(new FrameLayout.LayoutParams(width, width * 37 / 63));
        holder.findViewById(R.id.iv_view_alpha).setLayoutParams(new FrameLayout.LayoutParams(width, width * 37 / 63));*/
        final WebView webView = holder.findViewById(R.id.wv_coupon);
        final TextView tvCardNumber = holder.findViewById(R.id.tv_card_number);
        TextView tvRight = holder.findViewById(R.id.tv_right);
        tvRight.setText(PublicApplication.resourceText.getString("app_select_coupon_use", context.getResources().getString(R.string.app_select_coupon_use)));

        webView.getSettings().setJavaScriptEnabled(true);
        webView.requestFocus();
        webView.loadUrl(dataBean.getShowHtml());
        /** 使webview自己处理打开网页事件，不调用系统浏览器打开*/
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setFocusable(false);//失去焦点
        /**使webview能够响应back按键，点击back按键回退网页，不会退出整个Activity*/
        webView.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
                return false;
            }
        });
        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示
        tvCardNumber.setText(PublicApplication.resourceText.getString("app_coupon_card_number", context.getResources().getString(R.string.app_coupon_card_number)) + dataBean.getCardNo());
        final CheckBox cbOrderCheck = holder.findViewById(R.id.cb_checked);

        if (!StrUtils.isEmpty(CardNo)) {

            if (CardNo.equals(dataBean.getCardNo())) {
                cbOrderCheck.setChecked(true);
            }
            if (cbOrderCheck.isChecked()) {
                getPosition = position;
            }
            holder.findViewById(R.id.cb_checked).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSelectArray.clear();
                    if(cbOrderCheck.isChecked())
                    {
                        Log.e(TAG, "onClick: true" );
                        mSelectArray.put(position, true);
                    }else
                    {
                        Log.e(TAG, "onClick: false" );
                        mSelectArray.put(position, true);
                    }

                    CardNo = "";
                    notifyDataSetChanged();
                    if (cbOrderCheck.isChecked()) {
                        getPosition = position;
                    }
                   /* else {
                        getPosition = -1;
                    }*/
                }
            });
            if (!CardNo.equals(dataBean.getCardNo())) {
                cbOrderCheck.setChecked(mSelectArray.get(position, false));
            }
        } else {
            holder.findViewById(R.id.cb_checked).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSelectArray.clear();
                    if(cbOrderCheck.isChecked())
                    {
                        mSelectArray.put(position, true);
                    }else
                    {
                        mSelectArray.put(position, true);
                    }
                    notifyDataSetChanged();
                    if (cbOrderCheck.isChecked()) {
                        getPosition = position;
                    }
                   /* else {
                        getPosition = -1;
                    }*/
                }
            });
            cbOrderCheck.setChecked(mSelectArray.get(position, false));

        }
        ProgressBar mProgressBar = holder.findViewById(R.id.pb_progress);
        webView.setWebChromeClient(new WebChromeClient(mProgressBar));
    }

    /**
     * 进度条状态修改
     */
    public class WebChromeClient extends android.webkit.WebChromeClient {
        private ProgressBar mProgress;

        public WebChromeClient(ProgressBar mProgressBar) {
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
