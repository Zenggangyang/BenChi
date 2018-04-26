package com.soowin.staremblem.ui.index.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.soowin.staremblem.R;
import com.soowin.staremblem.ui.index.bean.BaseBean;
import com.soowin.staremblem.ui.index.bean.MessageBean;
import com.soowin.staremblem.ui.index.mModel.MeModel;
import com.soowin.staremblem.ui.login.activity.LoginActivity;
import com.soowin.staremblem.utils.BaseActivity;
import com.soowin.staremblem.utils.MyDateUtils;
import com.soowin.staremblem.utils.PublicApplication;
import com.soowin.staremblem.utils.ScreenManager;
import com.soowin.staremblem.utils.StrUtils;
import com.soowin.staremblem.utils.myView.MyTextView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 消息详情页面
 */
public class MessageActivity extends BaseActivity implements View.OnClickListener {
    private final String TAG = MessageActivity.class.getCanonicalName();
    MessageBean.DataBean mBean;
    private TextView tvTitle;
    private ImageView ivBack;
    private TextView tvMessageTitle;
    private TextView tvMessageCreatetime;
    private TextView tvMessageContent;
    private RelativeLayout rlMessageDetail;
    private TextView tvStringMessageDetail;
    private ImageView ivRightBack;
    private LinearLayout llMessageContent;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();//异步请求控制器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ScreenManager.getScreenManager().pushActivity(this);//加入栈
        initView();
    }

    private void initView() {
        initTitle();
        initContent();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (StrUtils.isEmpty(PublicApplication.loginInfo.getString("token", ""))) {
            startActivity(new Intent(MessageActivity.this, LoginActivity.class));
        }
    }

    private void initContent() {
        tvMessageTitle = findViewById(R.id.tv_message_title);
        tvMessageCreatetime = findViewById(R.id.tv_message_createtime);
        tvMessageContent = findViewById(R.id.tv_message_content);
        rlMessageDetail = findViewById(R.id.rl_message_detail);
        rlMessageDetail.setOnClickListener(this);
        llMessageContent = findViewById(R.id.ll_message_content);
        tvStringMessageDetail = findViewById(R.id.tv_String_message_detail);
        tvStringMessageDetail.setText(PublicApplication.resourceText.getString("app_message_detail2", getResources().getString(R.string.app_message_detail2)));

        ivRightBack = findViewById(R.id.iv_right_back);
        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_right_back", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_right_back))
                .error(getResources().getDrawable(R.drawable.img_right_back))
                .into(ivRightBack);
    }


    private void initData() {
        mBean = (MessageBean.DataBean) getIntent().getSerializableExtra("mBean");

        opdataState(mBean.getMessageid());//修改订单状态

        tvMessageTitle.setText(mBean.getTitle());
        tvMessageCreatetime.setText(MyDateUtils.getDataStr(mBean.getShowTime()).substring(0, 10));
        tvMessageContent.setText(Html.fromHtml(mBean.getSubtitleShow()));

//        String body = "[{\"name\":\"订单商品\",\"value\":\"商务接送\"},{\"name\":\"订单号\",\"value\":\"1523345107410\"},{\"name\":\"预约时间\",\"value\":\"<font color='#ff0000'>2018/4/12</font>\"},{\"name\":\"用车地点\",\"value\":\"北京市东城区建国门\"},{\"name\":\"目的地\",\"value\":\"北京市丰台区大红门\"},{\"name\":\"备注\",\"value\":\"感谢您的使用，如需变更或取消请在服务前4小时外通知我方\"}]";
        String body = mBean.getShowContent();
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(body).getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            String name = jsonObject.get("name").getAsString();
            String value = jsonObject.get("value").getAsString();

            LinearLayout layout = new LinearLayout(this);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layout.setLayoutParams(param);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setPadding(30, 5, 30, 5);
            //name
            LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 2);
            MyTextView tvName = new MyTextView(this);
            tvName.setLayoutParams(nameParams);
            tvName.setText(name);
            tvName.setTextSize(12);
//            if (name.equals("备注"))
//                tvName.setVisibility(View.GONE);
            layout.addView(tvName, nameParams);
            //value
            LinearLayout.LayoutParams valueParams = new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 5);

            MyTextView tvValue = new MyTextView(this);
            tvValue.setLayoutParams(valueParams);
            tvValue.setText(Html.fromHtml(value));
            tvValue.setTextSize(12);
            tvValue.setTextColor(getResources().getColor(R.color.black));
            layout.addView(tvValue, valueParams);

            llMessageContent.addView(layout, param);

        }
    }


    private void initTitle() {
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(PublicApplication.resourceText.getString("app_message_detail", getResources().getString(R.string.app_message_detail)));
        ivBack = findViewById(R.id.iv_back);
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(this);

        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_go_back", ""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_go_back))
                .error(getResources().getDrawable(R.drawable.img_go_back))
                .into(ivBack);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_message_detail:
                gotoMore();
                break;
        }
    }

    /**
     * 跳转到下详情
     */
    private void gotoMore() {
        switch (mBean.getType()) {
            case 1:
                startActivity(new Intent(MessageActivity.this, CouponListActivity.class));
                break;
            case 2:
                startActivity(new Intent(MessageActivity.this, OrderListActivity.class));
                break;
        }
    }

    /**
     * 更新消息状态
     *
     * @param
     * @param messageid
     */
    private void opdataState(final int messageid) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = MeModel.MessageUpdata(String.valueOf(messageid));

                if (result != null)
                    emitter.onNext(result);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<String, BaseBean>() {
                    @Override
                    public BaseBean apply(String s) throws Exception {
                        Gson gson = new Gson();
                        return gson.fromJson(s, BaseBean.class);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (mCompositeDisposable != null)
                            mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        switch (baseBean.get_metadata().getCode()) {
                            case 200:
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast(PublicApplication.resourceText.getString("app_error_message", getResources().getString(R.string.app_error_message)), 3);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mCompositeDisposable != null)
            mCompositeDisposable.clear();
    }
}
