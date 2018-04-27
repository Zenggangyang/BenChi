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
        webView.getSettings().setDefaultTextEncodingName("UTF-8");//设置默认为utf-8
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

        String html ="<html>\n" +
                        "<head>\n" +
                        "    <title>JSON在线解析及格式化验证 - JSON.cn</title>\n" +
                        "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                        "    <meta name=\"HandheldFriendly\" content=\"True\" />\n" +
                        "    <meta name=\"MobileOptimized\" content=\"320\" />\n" +
                        "    <meta http-equiv=\"Cache-Control\" content=\"max-age=7200\" />\n" +
                        "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
                        "    <meta name=\"baidu-site-verification\" content=\"mlJsiTNxiD\" />\n" +
                        "    <meta name=\"google-site-verification\" content=\"CPogK9tQWL5XIDF9F9x_tJyy1HtpDI8Rv6owOEIkUvM\" />\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                        "    <meta name=\"robots\" content=\"all\" />\n" +
                        "    <meta name=\"author\" content=\"json.cn\" />\n" +
                        "\n" +
                        "<meta name=\"keywords\" content=\"json,json在线解析,json格式化,json格式验证,json转xml,xml转json\"/>\n" +
                        "<meta name=\"description\" content=\"Json中文网致力于在中国推广Json,并提供相关的Json解析、验证、格式化、压缩、编辑器以及Json与XML相互转换等服务\"/>\n" +
                        "\n" +
                        "    <link href=\"/css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
                        "    <link href=\"/css/font-awesome.min.css\" rel=\"stylesheet\">\n" +
                        "    <link href=\"/css/base.css\" rel=\"stylesheet\">\n" +
                        "    <style></style>\n" +
                        "    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->\n" +
                        "    <!--[if lt IE 9]>\n" +
                        "      <script src=\"http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js\"></script>\n" +
                        "      <script src=\"http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js\"></script>\n" +
                        "    <![endif]-->\n" +
                        "    <!-- Favicons -->\n" +
                        "</head>\n" +
                        "<body style=\"over-flow:hidden;\">\n" +
                        "  <header class=\"header\">\n" +
                        "      <div class=\"row-fluid\" >\n" +
                        "          <div class=\"col-md-5\" style=\"position:relative;\">\n" +
                        "              <a class=\"logo\" href=\"/\">\n" +
                        "                Json.<span style=\"color:#4A5560;\">cn</span></a>\n" +
                        "\n" +
                        "          </div>\n" +
                        "          <nav class=\"col-md-7\" style=\"padding:10px 0; \"  align=\"right\">\n" +
                        "              <div  class=\"navi\" >\n" +
                        "                  <a href=\"/\" data-placement=\"bottom\">在线解析</a>\n" +
                        "                  <!--<a href=\"http://lab.json.cn/\" target=\"_blank\"  data-placement=\"bottom\">\n" +
                        "                    <span class=\"red\">JSON实验室</span>\n" +
                        "                </a>-->\n" +
                        "                  <a href=\"/wiki.html\"  data-placement=\"bottom\">什么是JSON</a>\n" +
                        "                  <a href=\"/code.html\"  data-placement=\"bottom\">JSON解析代码</a>\n" +
                        "                  <a href=\"/component.html\"  data-placement=\"bottom\">JSON组件</a>\n" +
                        "\n" +
                        "              </div>\n" +
                        "          </nav>\n" +
                        "          <br style=\"clear:both;\" />\n" +
                        "      </div>\n" +
                        "  </header>\n" +
                        "\n" +
                        "\n" +
                        "<main class=\"row-fluid\" style=\"height:85%;min-height:550px;\">\n" +
                        "    <div class=\"col-md-5\" style=\"padding:0px;height:100%;\">\n" +
                        "        <textarea id=\"json-src\" placeholder=\"在此输入json字符串或XML字符串...\"   class=\"form-control\"\n" +
                        "        style=\"height:100%;height: 87vh;min-height:520px;padding:10px 10px 10px 30px;border:0;border-right:solid 1px #E5EBEE;border-bottom:solid 1px #eee;border-radius:0;resize: none; outline:none;font-size:10px;\"></textarea>\n" +
                        "    </div>\n" +
                        "    <div class=\"col-md-7\" style=\"padding:0;position:relative;height:100%;\">\n" +
                        "        <div  class=\"tool\" style=\"position:absolute;\">\n" +
                        "            <a href=\"#\" class=\"tip zip\" title=\"压缩\"  data-placement=\"bottom\"><i class=\"fa fa-database\"></i></a>\n" +
                        "            <a href=\"#\" class=\"tip xml\" title=\"转XML\"  data-placement=\"bottom\"><i class=\"fa fa-file-excel-o\"></i></a>\n" +
                        "            <a href=\"#\" class=\"tip shown\"  title=\"显示行号\"  data-placement=\"bottom\"><i class=\"glyphicon glyphicon-sort-by-order\"></i></a>\n" +
                        "            <a href=\"#\" class=\"tip clear\" title=\"清空\"  data-placement=\"bottom\"><i class=\"fa fa-trash\"></i></a>\n" +
                        "            <a href=\"#\" class=\"tip save\" title=\"保存\"  data-placement=\"bottom\"><i class=\"fa fa-download\"></i></a>\n" +
                        "            <a href=\"#\" class=\"tip copy\" title=\"复制\" data-clipboard-target=\"#json-target\"  data-placement=\"bottom\"><i class=\"fa fa-copy\"></i></a>\n" +
                        "            <a href=\"#\" class=\"tip compress\" title=\"折叠\"  data-placement=\"bottom\"><i class=\"fa fa-compress\"></i></a>\n" +
                        "        </div>\n" +
                        "        <div id=\"right-box\"  style=\"width:100%;height: 87vh;min-height:520px;border:solid 1px #f6f6f6;border-radius:0;resize: none;overflow-y:scroll; outline:none;position:relative;font-size:12px;padding-top:40px;\">\n" +
                        "            <div id=\"line-num\" style=\"background-color:#fafafa;padding:0px 8px;float:left;border-right:dashed 1px #E5EBEE;display:none;z-index:-1;color:#999;position:absolute;text-align:center;over-flow:hidden;\">\n" +
                        "                <div>0</div>\n" +
                        "            </div>\n" +
                        "            <div class=\"ro\" id=\"json-target\" style=\"padding:0px 25px;white-space: pre-line;\">\n" +
                        "            </div>\n" +
                        "        </div>\n" +
                        "        <form id=\"form-save\" method=\"POST\"><input type=\"hidden\" value=\"\" id=\"txt-content\" name=\"content\"></form>\n" +
                        "    </div>\n" +
                        "    <br style=\"clear:both;\" />\n" +
                        "</main>\n" +
                        "<link href=\"/css/jquery.numberedtextarea.css\" rel=\"stylesheet\">\n" +
                        "<script src=\"/js/jquery.min.js\"></script>\n" +
                        "<script src=\"/js/jquery.message.js\"></script>\n" +
                        "<script src=\"/js/jquery.json.js\"></script>\n" +
                        "<script src=\"/js/jquery.xml2json.js\"></script>\n" +
                        "<script src=\"/js/jquery.json2xml.js\"></script>\n" +
                        "<script src=\"/js/json2.js\"></script>\n" +
                        "<script src=\"/js/jsonlint.js\"></script>\n" +
                        "<script src=\"/js/clipboard.min.js\"></script>\n" +
                        "<script src=\"/js/FileSaver.min.js\"></script>\n" +
                        "<script src=\"/js/bootstrap.min.js\"></script>\n" +
                        "<script src=\"/js/jquery.numberedtextarea.js\"></script>\n" +
                        "<script type=\"text/javascript\">\n" +
                        "$('textarea').numberedtextarea();\n" +
                        "var current_json = '';\n" +
                        "var current_json_str = '';\n" +
                        "var xml_flag = false;\n" +
                        "var zip_flag = false;\n" +
                        "var shown_flag = false;\n" +
                        "var compress_flag = false;\n" +
                        "$('.tip').tooltip();\n" +
                        "function init(){\n" +
                        "    xml_flag = false;\n" +
                        "    zip_flag = false;\n" +
                        "    shown_flag = false;\n" +
                        "    compress_flag = false;\n" +
                        "    renderLine();\n" +
                        "    $('.xml').attr('style','color:#999;');\n" +
                        "    $('.zip').attr('style','color:#999;');\n" +
                        "\n" +
                        "}\n" +
                        "$('#json-src').keyup(function(){\n" +
                        "    init();\n" +
                        "    var content = $.trim($(this).val());\n" +
                        "    var result = '';\n" +
                        "    if (content!='') {\n" +
                        "        //如果是xml,那么转换为json\n" +
                        "        if (content.substr(0,1) === '<' && content.substr(-1,1) === '>') {\n" +
                        "            try{\n" +
                        "                var json_obj = $.xml2json(content);\n" +
                        "                content = JSON.stringify(json_obj);\n" +
                        "            }catch(e){\n" +
                        "                result = '解析错误：<span style=\"color: #f1592a;font-weight:bold;\">' + e.message + '</span>';\n" +
                        "                current_json_str = result;\n" +
                        "                $('#json-target').html(result);\n" +
                        "                return false;\n" +
                        "            }\n" +
                        "\n" +
                        "        }\n" +
                        "        try{\n" +
                        "            current_json = jsonlint.parse(content);\n" +
                        "            current_json_str = JSON.stringify(current_json);\n" +
                        "            //current_json = JSON.parse(content);\n" +
                        "            result = new JSONFormat(content,4).toString();\n" +
                        "        }catch(e){\n" +
                        "            result = '<span style=\"color: #f1592a;font-weight:bold;\">' + e + '</span>';\n" +
                        "            current_json_str = result;\n" +
                        "        }\n" +
                        "\n" +
                        "        $('#json-target').html(result);\n" +
                        "    }else{\n" +
                        "        $('#json-target').html('');\n" +
                        "    }\n" +
                        "\n" +
                        "});\n" +
                        "$('.xml').click(function(){\n" +
                        "    if (xml_flag) {\n" +
                        "        $('#json-src').keyup();\n" +
                        "    }else{\n" +
                        "        var result = $.json2xml(current_json);\n" +
                        "        $('#json-target').html('<textarea style=\"width:100%;position:absolute;height: 80vh;min-height:480px;border:0;resize:none;\">'+result+'</textarea>');\n" +
                        "        xml_flag = true;\n" +
                        "        $(this).attr('style','color:#15b374;');\n" +
                        "    }\n" +
                        "\n" +
                        "});\n" +
                        "$('.shown').click(function(){\n" +
                        "    if (!shown_flag) {\n" +
                        "        renderLine();\n" +
                        "        $('#line-num').show();\n" +
                        "        $('.numberedtextarea-line-numbers').show();\n" +
                        "        shown_flag = true;\n" +
                        "        $(this).attr('style','color:#15b374;');\n" +
                        "    }else{\n" +
                        "        $('#line-num').hide();\n" +
                        "        $('.numberedtextarea-line-numbers').hide();\n" +
                        "        shown_flag = false;\n" +
                        "        $(this).attr('style','color:#999;');\n" +
                        "    }\n" +
                        "});\n" +
                        "function renderLine(){\n" +
                        "    var line_num = $('#json-target').height()/20;\n" +
                        "    $('#line-num').html(\"\");\n" +
                        "    var line_num_html = \"\";\n" +
                        "    for (var i = 1; i < line_num+1; i++) {\n" +
                        "        line_num_html += \"<div>\"+i+\"<div>\";\n" +
                        "    }\n" +
                        "    $('#line-num').html(line_num_html);\n" +
                        "}\n" +
                        "$('.zip').click(function(){\n" +
                        "    if (zip_flag) {\n" +
                        "        $('#json-src').keyup();\n" +
                        "    }else{\n" +
                        "        $('#json-target').html(current_json_str);\n" +
                        "        zip_flag = true;\n" +
                        "        $(this).attr('style','color:#15b374;');\n" +
                        "    }\n" +
                        "\n" +
                        "});\n" +
                        "$('.compress').click(function(){\n" +
                        "    if(!compress_flag){\n" +
                        "        $(this).attr('style','color:#15b374;');\n" +
                        "        //$(this).attr('title','取消折叠').tooltip('fixTitle').tooltip('show');\n" +
                        "        $($(\".fa-minus-square-o\").toArray().reverse()).click();\n" +
                        "        compress_flag = true;\n" +
                        "    }else{\n" +
                        "        while($(\".fa-plus-square-o\").length>0){\n" +
                        "            $(\".fa-plus-square-o\").click();\n" +
                        "        }\n" +
                        "        compress_flag = false;\n" +
                        "        $(this).attr('style','color:#555;');\n" +
                        "        $(this).attr('title','折叠').tooltip('fixTitle').tooltip('show');\n" +
                        "    }\n" +
                        "});\n" +
                        "$('.clear').click(function(){\n" +
                        "     $('#json-src').val('');\n" +
                        "     $('#json-target').html('');\n" +
                        "});\n" +
                        "(function($){\n" +
                        "   $.fn.innerText = function(msg) {\n" +
                        "         if (msg) {\n" +
                        "            if (document.body.innerText) {\n" +
                        "               for (var i in this) {\n" +
                        "                  this[i].innerText = msg;\n" +
                        "               }\n" +
                        "            } else {\n" +
                        "               for (var i in this) {\n" +
                        "                  this[i].innerHTML.replace(/&amp;lt;br&amp;gt;/gi,\"n\").replace(/(&amp;lt;([^&amp;gt;]+)&amp;gt;)/gi, \"\");\n" +
                        "               }\n" +
                        "            }\n" +
                        "            return this;\n" +
                        "         } else {\n" +
                        "            if (document.body.innerText) {\n" +
                        "               return this[0].innerText;\n" +
                        "            } else {\n" +
                        "               return this[0].innerHTML.replace(/&amp;lt;br&amp;gt;/gi,\"n\").replace(/(&amp;lt;([^&amp;gt;]+)&amp;gt;)/gi, \"\");\n" +
                        "            }\n" +
                        "         }\n" +
                        "   };\n" +
                        "})(jQuery);\n" +
                        "$('.save').click(function(){\n" +
                        "    // var content = JSON.stringify(current_json);\n" +
                        "    // $('#txt-content').val(content);\n" +
                        "    //var text = \"hell world\";\n" +
                        "    var html = $('#json-target').html().replace(/\\n/g,'<br/>').replace(/\\n/g,'<br>');\n" +
                        "    var text = $('#json-target').innerText().replace('　　', '    ');\n" +
                        "    var blob = new Blob([text], {type: \"application/json;charset=utf-8\"});\n" +
                        "    saveAs(blob, \"format.json\");\n" +
                        "});\n" +
                        "$('.copy').click(function(){\n" +
                        "    //$.msg(\"成功复制到粘贴板\",\"color:#00D69C;\");\n" +
                        "    // $(this).tooltip('toggle')\n" +
                        "    //       .attr('data-original-title', \"复制成功！\")\n" +
                        "    //       .tooltip('fixTitle')\n" +
                        "    //       .tooltip('toggle');\n" +
                        "});\n" +
                        "var clipboard = new Clipboard('.copy');\n" +
                        "$('#json-src').keyup();\n" +
                        "</script>\n" +
                        "\n" +
                        "\n" +
                        "<footer style=\"padding:20px;text-align:center;color:#999;position:relative;\">©2014 JSON.cn All right reserved.\n" +
                        "<a href=\"http://www.miitbeian.gov.cn/\" style=\"font-size:12px;\" target=\"_blank\">京ICP备15025187号-1</a>\n" +
                        "邮箱：service@json.cn\n" +
                        "<script>\n" +
                        "var _hmt = _hmt || [];\n" +
                        "(function() {\n" +
                        "  var hm = document.createElement(\"script\");\n" +
                        "  hm.src = \"https://hm.baidu.com/hm.js?2ea5d7a3a13a1da3236117fd8ee2b071\";\n" +
                        "  var s = document.getElementsByTagName(\"script\")[0];\n" +
                        "  s.parentNode.insertBefore(hm, s);\n" +
                        "})();\n" +
                        "</script>\n" +
                        "\n" +
                        "</footer>\n" +
                        "\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>\n";
//        webView.loadData(html, "text/html; charset=UTF-8", null);

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
