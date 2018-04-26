package com.soowin.staremblem.ui.index.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.soowin.staremblem.R;
import com.soowin.staremblem.ui.login.bean.CityListBean;

import java.util.List;

/**
 * Created by hxt on 2018/3/28 0028.
 * 城市列表
 */

public class CityListAdapter extends BaseAdapter {
    private LayoutInflater inflater = null;
    private List<List<CityListBean.DataBean>> data;
    private CityGridAdapter mAdapter;
    private Context context;
    private OnCityClickLisener itemClickLisener;
    private int width;


    public interface OnCityClickLisener {
        void onItemClick(CityListBean.DataBean data);
    }

    public CityListAdapter(Context context, OnCityClickLisener itemClickLisener) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.itemClickLisener = itemClickLisener;
        this.context = context;
    }

    public void setData(List<List<CityListBean.DataBean>> data) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_city_list, null);
            holder = new ViewHolder();
            holder.tvInitials = view.findViewById(R.id.tv_initials);
            holder.gvContent = view.findViewById(R.id.gv_content);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        mAdapter = new CityGridAdapter(context);
        if (data.get(i).size() > 0)
            holder.tvInitials.setText(data.get(i).get(0).getSign());
        mAdapter.setData(data.get(i));
        holder.gvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int z, long l) {
//                if (data.get(i).get(z).getIsSelect().equals("1")) {
                itemClickLisener.onItemClick(data.get(i).get(z));
//                }
            }
        });
        holder.gvContent.setAdapter(mAdapter);
        return view;
    }

    static class ViewHolder {
        private TextView tvInitials;
        private GridView gvContent;
    }

}
