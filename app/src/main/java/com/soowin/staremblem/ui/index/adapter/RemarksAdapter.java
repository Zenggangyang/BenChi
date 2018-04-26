package com.soowin.staremblem.ui.index.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soowin.staremblem.R;
import com.soowin.staremblem.ui.index.bean.RemarksBean;
import com.soowin.staremblem.utils.baseAdapterUtils.CommonAdaper;
import com.soowin.staremblem.utils.baseAdapterUtils.ViewHolder;


import java.util.List;

/**
 * Created by Administrator on 2018/3/30.
 * 备注的adapter
 */

public class RemarksAdapter extends CommonAdaper<RemarksBean> {
    private Context context;

    public RemarksAdapter(Context context, List<RemarksBean> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void convert(ViewHolder holder, RemarksBean item) {
        TextView itemRemark = holder.getView(R.id.tv_item_remark);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) itemRemark.getLayoutParams();
        lp.setMargins(20, 20, 20, 20);

        itemRemark.setText(item.getContent());
        if (item.isChecked()) {
            itemRemark.setBackground(context.getResources().getDrawable(R.drawable.shape_theme_stroke_soild_30));
            itemRemark.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            itemRemark.setBackground(context.getResources().getDrawable(R.drawable.shape_theme_stroke_30));
            itemRemark.setTextColor(context.getResources().getColor(R.color.theme_color));
        }

    }
}
