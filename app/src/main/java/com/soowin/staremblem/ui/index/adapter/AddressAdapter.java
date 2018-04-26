package com.soowin.staremblem.ui.index.adapter;

import android.content.Context;

import com.soowin.staremblem.R;
import com.soowin.staremblem.ui.index.bean.AddressBean;
import com.soowin.staremblem.utils.baseAdapterUtils.CommonAdaper;
import com.soowin.staremblem.utils.baseAdapterUtils.ViewHolder;

import java.util.List;

/**
 * 地址选择列表
 */

public class AddressAdapter extends CommonAdaper<AddressBean.DataBean.ResultBean> {

    public AddressAdapter(Context context, List<AddressBean.DataBean.ResultBean> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, AddressBean.DataBean.ResultBean item) {
        if(item!=null){
            holder.setText(R.id.poi_field_id,item.getName());
            holder.setText(R.id.poi_value_id,item.getCity()+ item.getDistrict());

        }
     }
}
