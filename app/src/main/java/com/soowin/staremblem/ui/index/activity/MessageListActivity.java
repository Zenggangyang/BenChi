package com.soowin.staremblem.ui.index.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.soowin.staremblem.R;
import com.soowin.staremblem.ui.index.adapter.MessageAdapter;
import com.soowin.staremblem.ui.index.bean.BaseBean;
import com.soowin.staremblem.ui.index.bean.MessageBean;
import com.soowin.staremblem.ui.index.mModel.MeModel;
import com.soowin.staremblem.ui.login.activity.LoginActivity;
import com.soowin.staremblem.utils.BaseActivity;
import com.soowin.staremblem.utils.PublicApplication;
import com.soowin.staremblem.utils.ScreenManager;
import com.soowin.staremblem.utils.StrUtils;

import java.util.ArrayList;
import java.util.List;

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
 * 消息列表页
 */
public class MessageListActivity extends BaseActivity implements View.OnClickListener {

    private ListView lvMessage;
    private TextView tvTitle;
    private ImageView ivBack;
    private MessageAdapter mAdapter;
    private List<MessageBean.DataBean> mList = new ArrayList<>();

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();//异步请求控制器


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        ScreenManager.getScreenManager().pushActivity(this);//加入栈
        initView();
    }

    private void initView() {
        initTitle();
        initContent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (StrUtils.isEmpty(PublicApplication.loginInfo.getString("token", ""))) {
            startActivity(new Intent(MessageListActivity.this, LoginActivity.class));
        } else {
            MessageBean mBean = (MessageBean) getIntent().getSerializableExtra("bean");
            if (mBean != null) {
                mList = mBean.getData();
                mAdapter.setData(mList);
                mAdapter.notifyDataSetChanged();
            } else {
                getData();
            }
        }

    }

    private void getData() {
        showDialog();
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String result = MeModel.MessageList();

                if (result != null)
                    emitter.onNext(result);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<String, MessageBean>() {
                    @Override
                    public MessageBean apply(String s) throws Exception {
                        Gson gson = new Gson();
                        return gson.fromJson(s, MessageBean.class);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MessageBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (mCompositeDisposable != null)
                            mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(MessageBean baseBean) {
                        switch (baseBean.get_metadata().getCode()) {
                            case 200:
                                mList.clear();
                                mList.addAll(baseBean.getData());
                                mAdapter.notifyDataSetChanged();
                                break;
                            case 500:
                                showToast(baseBean.get_metadata().getMessage(), 3);
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast(PublicApplication.resourceText.getString("app_error_message", getResources().getString(R.string.app_error_message)), 3);
                        dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }
                });
    }

    private void initTitle() {
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(PublicApplication.resourceText.getString("app_message_list", getResources().getString(R.string.app_message_list)));
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

    private void initContent() {
        lvMessage = findViewById(R.id.lv_message);
        mAdapter = new MessageAdapter(this, mList, R.layout.item_message);
        lvMessage.setAdapter(mAdapter);

        lvMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mList.get(position).getState() == 0) {
                    mList.get(position).setState(1);
                    mAdapter.notifyDataSetChanged();
//                    opdataState(mList.get(position).getMessageid(),position);
                }
                MessageBean.DataBean mBean = mList.get(position);
                //跳转到消息详情界面
                startActivity(new Intent(MessageListActivity.this, MessageActivity.class).putExtra("mBean", mBean));
            }
        });

    }


    @Override
    public void onPause() {
        super.onPause();
        if (mCompositeDisposable != null)
            mCompositeDisposable.clear();
        dismissDialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
