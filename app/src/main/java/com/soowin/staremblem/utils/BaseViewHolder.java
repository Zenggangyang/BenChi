package com.soowin.staremblem.utils;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * <p>描述：Recycerlview的BaseAdapter</p>
 * 作者： hongfu
 * 日期： 2017/11/20. <br>
 * 版本： v1.0<br>
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    //创建View容器，根据key为控件id
    private SparseArray<View> viewArray;
    private int viewType;

    public BaseViewHolder(View itemView) {
        super(itemView);
        viewArray=new SparseArray<>();
    }
    /**
     * 获取布局中的View
     */
    public <T extends View> T findViewById(@IdRes int viewId) {
        View view = viewArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            viewArray.put(viewId, view);
        }
        return (T) view;
    }

    public Context getContext() {
        return itemView.getContext();
    }
    public View getView(int viewId) {
        return findViewById(viewId);
    }
    public TextView getTextView(int resId){
        return (TextView) getView(resId);
    }

    public void setText(int resId,String str){
        getTextView(resId).setText(str);
    }

    public void setViewTyep(int viewType) {
        this.viewType = viewType;
    }

    public int getViewType() {
        return viewType;
    }
}
