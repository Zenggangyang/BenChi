package com.soowin.staremblem.ui.index.activity;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import android.os.Bundle;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soowin.staremblem.R;
import com.soowin.staremblem.ui.index.bean.OrderListBean;
import com.soowin.staremblem.ui.index.fragment.EvaluationOneFragment;
import com.soowin.staremblem.ui.index.fragment.EvaluationThreeFragment;
import com.soowin.staremblem.ui.index.fragment.EvaluationTwoFragment;
import com.soowin.staremblem.utils.BaseActivity;
import com.soowin.staremblem.utils.PublicApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务评价页
 */
public class ServiceEvaluationActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = ServiceEvaluationActivity.class.getSimpleName();
    private TextView tvTitle;
    private ImageView ivBack;

    public int prePosition = -1;
    private static Fragment[] fgtArr = new Fragment[3];
    private FrameLayout flService;
    List<OrderListBean.DataBean> OrderData = new ArrayList<>();
    private Fragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_evaluation);
//        getFragmentManager().beginTransaction().

        initView();
    }

    private void initView() {
        initTitle();
        initContent();
    }

    private void initContent() {
        flService = findViewById(R.id.fl_service);
        fgtArr[0] = new EvaluationOneFragment();
        fgtArr[1] = new EvaluationTwoFragment();
        fgtArr[2] = new EvaluationThreeFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        changeFgt(0);
        switchFragment(0);
    }

    private void initTitle() {
        tvTitle = findViewById(R.id.tv_title);
        ivBack = findViewById(R.id.iv_back);

//        tvTitle.setText(getResources().getString(R.string.app_satisfactions_title));
        ivBack.setOnClickListener(this);
        ivBack.setVisibility(View.VISIBLE);

        Glide.with(this)
                .load(PublicApplication.resourceText.getString("img_go_back",""))
                .asBitmap()
                .placeholder(getResources().getDrawable(R.drawable.img_go_back))
                .error(getResources().getDrawable(R.drawable.img_go_back))
                .into(ivBack);

    }

    public void changeFgt(int curPosition) {
        if (curPosition == prePosition) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        if (prePosition != -1)
            transaction.hide(fgtArr[prePosition]);
        if (fgtArr[curPosition].isAdded()) {
            transaction.show(fgtArr[curPosition]).addToBackStack("fr" + curPosition);
        } else {
            transaction.add(R.id.fl_service, fgtArr[curPosition]).show(fgtArr[curPosition]).addToBackStack("fr" + curPosition);
        }


        transaction.commit();
        prePosition = curPosition;
    }

    public void switchFragment(int curposition) {
        Fragment baseFragment = fgtArr[curposition];
        if (tempFragment != baseFragment) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (!baseFragment.isAdded()) {

                if (tempFragment != null) {
                    ft.hide(tempFragment);
                }

                ft.add(R.id.fl_service, baseFragment);
            } else {
                if (tempFragment != null) {
                    ft.hide(tempFragment);
                }
                ft.show(baseFragment);
            }

            ft.commit();

            tempFragment = baseFragment;
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
              /*  Intent intent = new Intent();
                intent.setClass(ServiceEvaluationActivity.this, OrderListActivity.class);
                intent.putExtra("nulldata", (Serializable) OrderData);
                startActivity(intent);*/
                finish();
                break;

        }/**/

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       /* Intent intent = new Intent();
        intent.setClass(ServiceEvaluationActivity.this, OrderListActivity.class);
        intent.putExtra("nulldata", (Serializable) OrderData);
        startActivity(intent);
        finish();*/
       /* if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }*/
    }

}
