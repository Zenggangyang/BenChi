package com.soowin.staremblem.ui.login.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soowin.staremblem.R;
import com.soowin.staremblem.ui.index.activity.CouponListActivity;
import com.soowin.staremblem.utils.BaseActivity;
import com.soowin.staremblem.utils.PublicApplication;
import com.soowin.staremblem.utils.ScreenManager;


/**
 * web的Acitivty
 */
public class WebViewActivity extends BaseActivity implements View.OnClickListener {
    final public static String URLS = "url";
    final public static String TITLE = "title";
    private String url;
    private String title = "";

    private TextView tvTitle;
    private ImageView ibBack;

    private WebView wvMyWv;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        url = this.getIntent().getStringExtra(URLS);
        title = getIntent().getStringExtra(TITLE);
        initTitle();
        initView();
    }

    private void initView() {
        wvMyWv = findViewById(R.id.wv_my_wv);

        init();
    }

    private void initTitle() {
        tvTitle = findViewById(R.id.tv_title);
        ibBack = findViewById(R.id.iv_back);
        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_go_back", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_go_back))
                .error(getResources().getDrawable(R.drawable.img_go_back))
                .into(ibBack);
        tvTitle.setText(title);
        ibBack.setOnClickListener(this);
        ibBack.setVisibility(View.VISIBLE);

    }

    final Handler myHandler = new Handler();

    @SuppressLint({"AddJavascriptInterface", "SetJavaScriptEnabled"})
    private void init() {
        wvMyWv.loadUrl(url);

        //设置编码
        wvMyWv.getSettings().setDefaultTextEncodingName("utf-8");
        //支持js
        wvMyWv.getSettings().setJavaScriptEnabled(true);

        wvMyWv.getSettings().setAllowFileAccess(true);// 设置允许访问文件数据
        wvMyWv.getSettings().setSupportZoom(true);
        wvMyWv.getSettings().setBuiltInZoomControls(true);
        wvMyWv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wvMyWv.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        wvMyWv.getSettings().setDomStorageEnabled(true);
        wvMyWv.getSettings().setDatabaseEnabled(true);
        //映射.可以调用js里面的方法
        wvMyWv.addJavascriptInterface(new JavaScriptInterface(), "jsi");
        wvMyWv.setWebChromeClient(new WebChromeClient());// 设置浏览器可弹窗

        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        wvMyWv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

        mProgressBar = findViewById(R.id.pb_progress);
        wvMyWv.setWebChromeClient(new WebChromeClient());
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mProgressBar.setVisibility(View.GONE);
            } else {
                if (mProgressBar.getVisibility() == View.GONE)
                    mProgressBar.setVisibility(View.VISIBLE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    private final class JavaScriptInterface {
        @JavascriptInterface
        public void close() {
            myHandler.post(new Runnable() {
                @Override
                public void run() {// 执行UI线程
                    Intent intent = new Intent();
                    WebViewActivity.this.setResult(1, intent);
                    WebViewActivity.this.finish();
                }
            });
        }

        @JavascriptInterface
        public String getToken() {
            return PublicApplication.loginInfo.getString("token", "");
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (wvMyWv.canGoBack())
                    wvMyWv.goBack();
                else
                    finish();
                break;
        }
    }
}
