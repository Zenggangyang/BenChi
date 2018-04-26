package com.soowin.staremblem.ui.index.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soowin.staremblem.R;
import com.soowin.staremblem.ui.index.activity.OrderListActivity;
import com.soowin.staremblem.ui.index.bean.OrderListBean;
import com.soowin.staremblem.utils.BaseRecyclerViewAdapter;
import com.soowin.staremblem.utils.BaseViewHolder;
import com.soowin.staremblem.utils.DataUtils;
import com.soowin.staremblem.utils.PublicApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hongfu on 2018/3/22.
 * 类的作用：订单列表的adapter
 * 版本号：1.0.0
 */

public class OrderListAdapter extends BaseRecyclerViewAdapter<OrderListBean.DataBean> {
    private static final String TAG = OrderListAdapter.class.getSimpleName();
    private Context context;
    private OrderListActivity orderListActivity;

    List<OrderListBean.DataBean> OrderData = new ArrayList<>();

    public OrderListAdapter(Context context, OrderListActivity orderListActivity) {
        super(context);
        this.context = context;
        this.orderListActivity = orderListActivity;
    }

    public void setDatas(List<OrderListBean.DataBean> OrderData) {
        this.OrderData = OrderData;
        notifyDataSetChanged();
    }


    @Override
    protected int inflaterItemLayout(int viewType) {
        return R.layout.item_order_list;
    }

    @Override
    protected void bindData(BaseViewHolder holder, final int position, final OrderListBean.DataBean dataBean) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());
        String format = formatter.format(curDate);//系统当前时间

        TextView tvStatus = holder.findViewById(R.id.tv_status);
        TextView tvStringOrderStatus = holder.findViewById(R.id.tv_string_order_status);
        tvStringOrderStatus.setText(PublicApplication.resourceText.getString("app_order_status", context.getResources().getString(R.string.app_order_status)));
        TextView tvStringOrderDetail = holder.findViewById(R.id.tv_string_order_detail);
        tvStringOrderDetail.setText(PublicApplication.resourceText.getString("app_order_see_detail", context.getResources().getString(R.string.app_order_see_detail)));
        TextView tvStringOrderNumber = holder.findViewById(R.id.tv_string_order_number);
        TextView tvStringServiceType = holder.findViewById(R.id.tv_string_service_type);
        TextView tvStringPickUp = holder.findViewById(R.id.tv_string_pick_up);
        TextView tvStringCarPrice = holder.findViewById(R.id.tv_string_car_price);
        tvStringOrderNumber.setText(PublicApplication.resourceText.getString("app_order_number", context.getResources().getString(R.string.app_order_number)));
        tvStringServiceType.setText(PublicApplication.resourceText.getString("app_service_type", context.getResources().getString(R.string.app_service_type)));
        tvStringPickUp.setText(PublicApplication.resourceText.getString("app_string_pick_up", context.getResources().getString(R.string.app_string_pick_up)));
        tvStringCarPrice.setText(PublicApplication.resourceText.getString("app_string_car_price", context.getResources().getString(R.string.app_string_car_price)));
        ImageView imageView = holder.findViewById(R.id.iv_order_detail);
        //图片资源初始化
        Glide.with(context)
                .load(PublicApplication.resourceText.getString("img_order_detail", ""))
                .asBitmap()
                .placeholder(context.getResources().getDrawable(R.drawable.img_order_detail))
                .error(context.getResources().getDrawable(R.drawable.img_order_detail))
                .into(imageView);


        TextView tvOrderNumber = holder.findViewById(R.id.tv_order_number);
        TextView tvServiceType = holder.findViewById(R.id.tv_service_type);
        TextView tvPickUp = holder.findViewById(R.id.tv_pick_up);
        TextView tvCarPrice = holder.findViewById(R.id.tv_car_price);
        LinearLayout llOrderContent = holder.findViewById(R.id.ll_Order_Content);
        LinearLayout llOrderStatusOne = holder.findViewById(R.id.ll_order_status_one);//删除订单 去支付
        LinearLayout llOrderStatusTwo = holder.findViewById(R.id.ll_order_status_two);//取消订单
        TextView tvOrderDelete = holder.findViewById(R.id.tv_order_delete);
        tvOrderDelete.setText(PublicApplication.resourceText.getString("app_order_delete_left", context.getResources().getString(R.string.app_order_delete_left)));
        TextView tvOrderPay = holder.findViewById(R.id.tv_order_pay);
        tvOrderPay.setText(PublicApplication.resourceText.getString("app_order_pay", context.getResources().getString(R.string.app_order_pay)));
        TextView tvOrderCancel = holder.findViewById(R.id.tv_order_cancel);
        tvOrderCancel.setText(PublicApplication.resourceText.getString("app_order_cancel", context.getResources().getString(R.string.app_order_cancel)));
        ImageView ivOrderCancel = holder.findViewById(R.id.iv_order_cancel);
        String timeBoard = dataBean.getCarProduct().getTimeBoard().replace("T", " ");

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        int yearMin = 0;
        int monthMin = 0;
        int dayMin = 0;
        int hourMin = 0;
        int minuteMin = 0;
        try {
            Date date = sf.parse(timeBoard);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            yearMin = calendar.get(Calendar.YEAR);
            monthMin = calendar.get(Calendar.MONTH) + 1;
            dayMin = calendar.get(Calendar.DAY_OF_MONTH);
            hourMin = calendar.get(Calendar.HOUR_OF_DAY);
            minuteMin = calendar.get(Calendar.MINUTE);

        } catch (ParseException e) {
            e.printStackTrace();

        }
        String straboardM = "";
        String straboardD = "";
        String straboardH = "";
        String straboardMs = "";


        if (monthMin < 10)
            straboardM = "0" + monthMin;
        else
            straboardM = "" + monthMin;
        if (dayMin < 10)
            straboardD = "0" + dayMin;
        else
            straboardD = "" + dayMin;
        if (hourMin < 10)
            straboardH = "0" + hourMin;
        else
            straboardH = "" + hourMin;
        if (minuteMin < 10)
            straboardMs = "0" + minuteMin;
        else
            straboardMs = "" + minuteMin;
        String Date = "";
        Date = String.valueOf(yearMin + "-" + straboardM + "-" + straboardD + " " + straboardH + ":" + straboardMs);
        int remainHour = 0;
        try {
            remainHour = (int) (Double.parseDouble(DataUtils.getTimeDifferenceHour(Date, format)));
        } catch (Exception e) {
            Log.e(TAG, "bindData: " + e.getMessage());
        }
        switch (dataBean.getOrderStatus()) {
            case 0://待支付   删除订单 去支付
                llOrderContent.setVisibility(View.VISIBLE);
                tvStatus.setText(context.getResources().getString(R.string.app_order_unpaid));
                llOrderStatusOne.setVisibility(View.VISIBLE);
                llOrderStatusTwo.setVisibility(View.GONE);
                /*删除订单*/
                tvOrderDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        orderListActivity.deleteOrderPopWindow(String.valueOf(dataBean.getOrderId()));
                    }
                });
                /*去支付*/
                tvOrderPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        orderListActivity.unPayJump(String.valueOf(dataBean.getOrderId()));
                    }
                });
                break;
            case 1://预约完成   取消订单
                tvOrderCancel.setText(context.getResources().getString(R.string.app_order_cancel));
                llOrderContent.setVisibility(View.VISIBLE);
                llOrderStatusOne.setVisibility(View.GONE);
                llOrderStatusTwo.setVisibility(View.VISIBLE);
                tvStatus.setText(context.getResources().getString(R.string.app_order_appointment_complete));
                if (remainHour < 1 && remainHour > 0) {
                    ivOrderCancel.setVisibility(View.VISIBLE);
                    tvOrderCancel.setOnClickListener(null);
                } else {
                    ivOrderCancel.setVisibility(View.GONE);
                      /*取消订单*/
                    tvOrderCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            orderListActivity.showPopUpWindow(String.valueOf(dataBean.getOrderId()));

                        }
                    });
                }

                break;
            case 2://派单中   取消订单
                tvOrderCancel.setText(context.getResources().getString(R.string.app_order_cancel));

                llOrderContent.setVisibility(View.VISIBLE);
                llOrderStatusOne.setVisibility(View.GONE);
                llOrderStatusTwo.setVisibility(View.VISIBLE);
                tvStatus.setText(context.getResources().getString(R.string.app_order_send));
                if (remainHour < 1 && remainHour > 0) {
                    ivOrderCancel.setVisibility(View.VISIBLE);
                    tvOrderCancel.setOnClickListener(null);
                } else {
                    ivOrderCancel.setVisibility(View.GONE);
                      /*取消订单*/
                    tvOrderCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            orderListActivity.showPopUpWindow(String.valueOf(dataBean.getOrderId()));
                        }
                    });
                }

                break;
            case 3://派单完成  取消订单
                tvOrderCancel.setText(context.getResources().getString(R.string.app_order_cancel));

                llOrderContent.setVisibility(View.VISIBLE);
                llOrderStatusOne.setVisibility(View.GONE);
                llOrderStatusTwo.setVisibility(View.VISIBLE);
                tvStatus.setText(context.getResources().getString(R.string.app_order_send_finish));
                if (remainHour < 1 && remainHour > 0) {
                    ivOrderCancel.setVisibility(View.VISIBLE);
                    tvOrderCancel.setOnClickListener(null);
                } else {
                    ivOrderCancel.setVisibility(View.GONE);
                      /*取消订单*/
                    tvOrderCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            orderListActivity.showPopUpWindow(String.valueOf(dataBean.getOrderId()));
                        }
                    });
                }

                break;
            case 4://订单完成  去评价
                llOrderContent.setVisibility(View.VISIBLE);
                llOrderStatusOne.setVisibility(View.GONE);
                llOrderStatusTwo.setVisibility(View.VISIBLE);
                tvStatus.setText(context.getResources().getString(R.string.app_order_completion));
                tvOrderCancel.setText(context.getResources().getString(R.string.app_order_pingjia));
                  /*去评价*/
                tvOrderCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        orderListActivity.gotoServiceEvaluation(String.valueOf(dataBean.getApiOrderID()));
                    }
                });
                break;
            case 5://取消订单
                llOrderContent.setVisibility(View.VISIBLE);
                llOrderStatusOne.setVisibility(View.GONE);
                llOrderStatusTwo.setVisibility(View.GONE);
                tvStatus.setText(context.getResources().getString(R.string.app_order_cancel));
                break;
       /*     case 6://订单已删除
//                llOrderContent.setVisibility(View.GONE);
                tvStatus.setText(context.getResources().getString(R.string.app_order_delete));
                break;*/
        }
        /*查看详细*/
        LinearLayout llOrderDetail = holder.findViewById(R.id.ll_order_detail);
        llOrderDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderListActivity.orderDetail(OrderData.get(position));
            }
        });
        tvOrderNumber.setText(dataBean.getOrderCode());
        /*1-7  没有4*/
        switch (dataBean.getCarProduct().getServiceType()) {
            case 1://接机
                tvServiceType.setText(context.getResources().getString(R.string.app_transfer_form_type_name_01));
                break;
            case 2://送机
                tvServiceType.setText(context.getResources().getString(R.string.app_transfer_form_type_name_02));
                break;
            case 3://日租
                tvServiceType.setText(context.getResources().getString(R.string.app_order_daily_rental));
                break;
            case 5://商务
                tvServiceType.setText(context.getResources().getString(R.string.app_order_business));
                break;
            case 6://半日租
                tvServiceType.setText(context.getResources().getString(R.string.app_order_half_day_rent));
                break;
            case 7://接送套餐
                tvServiceType.setText(context.getResources().getString(R.string.app_order_transport_package));
                break;
        }
        tvPickUp.setText(dataBean.getCarProduct().getTimeBoard());
        tvCarPrice.setText(dataBean.getCarProduct().getPrice() + " 元");

    }

    @Override
    protected void onItemClickListener(View itemView, int position, OrderListBean.DataBean dataBean) {

    }


}
