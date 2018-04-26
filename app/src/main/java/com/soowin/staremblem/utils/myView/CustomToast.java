package com.soowin.staremblem.utils.myView;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.soowin.staremblem.R;
import com.soowin.staremblem.utils.PublicApplication;

/**
 * Created by hongfu on 2017/12/21.
 * 类的作用：自定义弹出的logo
 * 版本号：1.0.0
 */

public class CustomToast {
    private static TextView tvContent;
    private static ImageView ivLogo;

    public static void showSuccessToast(Context context, String message) {
        //加载Toast布局
        View toastRoot = LayoutInflater.from(context).inflate(R.layout.item_toast, null);
        //初始化布局控件
        tvContent = toastRoot.findViewById(R.id.tv_content);
        ivLogo = toastRoot.findViewById(R.id.iv_logo);
        //为控件设置属性
        tvContent.setText(message);
//        tvContent.setTypeface(PublicApplication.typefaceC);
        Glide.with(context)
                .load(PublicApplication.resourceText.getString("pop_msg_succeed_xs",""))
                .asBitmap()
                .error(R.drawable.pop_msg_succeed_xs)
                .placeholder(R.drawable.pop_msg_succeed_xs)
                .fitCenter()
                .into(ivLogo);
        //Toast的初始化
        Toast toastStart = new Toast(context);
        //Toast的Y坐标是屏幕高度的1/3，不会出现不适配的问题
        toastStart.setGravity(Gravity.CENTER, 0, 0);
        toastStart.setDuration(Toast.LENGTH_SHORT);
        toastStart.setView(toastRoot);
        toastStart.show();
    }

    public static void showFailToast(Context context, String message) {
        //加载Toast布局
        View toastRoot = LayoutInflater.from(context).inflate(R.layout.item_toast, null);
        //初始化布局控件
        tvContent = toastRoot.findViewById(R.id.tv_content);
        ivLogo = toastRoot.findViewById(R.id.iv_logo);
        //为控件设置属性
        tvContent.setText(message);
//        tvContent.setTypeface(PublicApplication.typefaceC);
        Glide.with(context)
                .load(PublicApplication.resourceText.getString("pop_msg_error_xs",""))
                .asBitmap()
                .error(R.drawable.pop_msg_error_xs)
                .placeholder(R.drawable.pop_msg_error_xs)
                .fitCenter()
                .into(ivLogo);
        //Toast的初始化
        Toast toastStart = new Toast(context);
        //Toast的Y坐标是屏幕高度的1/3，不会出现不适配的问题
        toastStart.setGravity(Gravity.CENTER, 0, 0);
        toastStart.setDuration(Toast.LENGTH_SHORT);
        toastStart.setView(toastRoot);
        toastStart.show();
    }
    public static void showFailYellowToast(Context context, String message) {
        //加载Toast布局
        View toastRoot = LayoutInflater.from(context).inflate(R.layout.item_toast, null);
        //初始化布局控件
        tvContent = toastRoot.findViewById(R.id.tv_content);
        ivLogo = toastRoot.findViewById(R.id.iv_logo);
        //为控件设置属性
        tvContent.setText(message);
//        tvContent.setTypeface(PublicApplication.typefaceC);
        Glide.with(context)
                .load(PublicApplication.resourceText.getString("pop_msg_tip_xs",""))
                .asBitmap()
                .error(R.drawable.pop_msg_tip_xs)
                .placeholder(R.drawable.pop_msg_tip_xs)
                .fitCenter()
                .into(ivLogo);
        //Toast的初始化
        Toast toastStart = new Toast(context);
        //Toast的Y坐标是屏幕高度的1/3，不会出现不适配的问题
        toastStart.setGravity(Gravity.CENTER, 0, 0);
        toastStart.setDuration(Toast.LENGTH_SHORT);
        toastStart.setView(toastRoot);
        toastStart.show();
    }
}
