package com.soowin.staremblem.ui.index.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.soowin.staremblem.R;
import com.soowin.staremblem.ui.index.bean.CarListBean;
import com.soowin.staremblem.ui.login.bean.CityListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxt on 2018/3/29 0029.
 * 表单页面车辆列表图片展示
 */

public class CarListAdapter extends PagerAdapter {
    private LayoutInflater inflater = null;
    private List<CarListBean.DataBean> data;
    private Context context;
    private List<View> ivList = new ArrayList<>();
    private int width;

    public CarListAdapter(Context context, int width) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.width = width;
    }

    public void setData(List<CarListBean.DataBean> data) {
        ivList.clear();
        for (int i = 0; i < data.size(); i++) {
            View view = inflater.inflate(R.layout.item_car_banner, null);
            RelativeLayout rlBanner = view.findViewById(R.id.rl_banner);
            ImageView ivBg = view.findViewById(R.id.iv_bg);
            ImageView ivCar = view.findViewById(R.id.iv_car);

            rlBanner.setLayoutParams(new LinearLayout.LayoutParams(width, width * 2 / 5));
                /*Glide.with(context)
                        .load(data.get(i).getCarModelList().get(j).getCarBgImageUrl())
                        .asBitmap()
                        .into(ivBg);*/
            Glide.with(context)
                    .load(data.get(i).getCarImageUrl())
                    .asBitmap()
                    .into(ivCar);
            ivList.add(view);
        }
        this.data = data;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (ivList != null)
            return ivList.size();
        else
            return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    // PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView(ivList.get(position));
    }

    // 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        view.addView(ivList.get(position));
        return ivList.get(position);
    }
    /*private LayoutInflater inflater = null;
    private List<CityListBean.DataBean> data;
    private Context context;
    private int width;

    public CarListAdapter(Context context) {
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
        final CityGridAdapter.ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_city_grid, null);
            holder = new CityGridAdapter.ViewHolder();
            holder.tvName = view.findViewById(R.id.tv_name);
            view.setTag(holder);
        } else {
            holder = (CityGridAdapter.ViewHolder) view.getTag();
        }
        holder.tvName.setText(data.get(i).getName());
        if (data.get(i).getIsSelect().equals("1")) {
            holder.tvName.setBackgroundDrawable(context.getResources().getDrawable(
                    R.drawable.shape_theme_noradius));
            holder.tvName.setTextColor(context.getResources().getColor(R.color.theme_color));

        } else {
            holder.tvName.setBackgroundDrawable(context.getResources().getDrawable(
                    R.drawable.shape_gray_noradius));
            holder.tvName.setTextColor(context.getResources().getColor(R.color.font_text));
        }
        return view;
    }

    static class ViewHolder {
        private TextView tvName;
    }*/
}
