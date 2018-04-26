package com.soowin.staremblem.ui.index.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.soowin.staremblem.R;
import com.soowin.staremblem.ui.index.bean.MessageBean;
import com.soowin.staremblem.utils.MyDateUtils;
import com.soowin.staremblem.utils.StrUtils;
import com.soowin.staremblem.utils.Utlis;
import com.soowin.staremblem.utils.baseAdapterUtils.CommonAdaper;
import com.soowin.staremblem.utils.baseAdapterUtils.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/3/26.
 */

public class MessageAdapter extends CommonAdaper<MessageBean.DataBean> {
    public MessageAdapter(Context context, List<MessageBean.DataBean> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, MessageBean.DataBean item) {
        holder.setText(R.id.tv_item_message_title, item.getTitle());
        holder.setText(R.id.tv_item_message_createtime, MyDateUtils.getDataStr(item.getShowTime()).substring(0,19));

        holder.setText(R.id.tv_item_message_content, item.getSubtitle());
//        TextView T = holder.getView(R.id.tv_item_message_content);
//        T.setText(item.getSubtitle());

        if (item.getState() == 0) {
            holder.getView(R.id.tv_item_message_state).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.tv_item_message_state).setVisibility(View.INVISIBLE);
        }
    }
}
