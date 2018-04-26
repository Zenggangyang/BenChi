package com.soowin.staremblem.ui.index.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.soowin.staremblem.R;
import com.soowin.staremblem.ui.login.bean.CityListBean;

import java.util.List;

/**
 * Created by hxt on 2018/3/30 0030.
 * 表单页面车辆详细
 */


public class FromCarInfoAdapter extends BaseAdapter {
    private LayoutInflater inflater = null;
    private List<CityListBean.DataBean> data;
    private Context context;
    private int width;

    public FromCarInfoAdapter(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    public void setData(List<CityListBean.DataBean> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (data != null)
            return data.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_city_grid, null);
            holder = new ViewHolder();
            holder.tvName = view.findViewById(R.id.tv_name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tvName.setText(data.get(i).getName());
        holder.tvName.setBackgroundDrawable(context.getResources().getDrawable(
                R.drawable.shape_theme_noradius));
        holder.tvName.setTextColor(context.getResources().getColor(R.color.theme_color));
        return view;
    }

    static class ViewHolder {
        private TextView tvName;
    }
}
