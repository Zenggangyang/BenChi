package com.soowin.staremblem.ui.index.fragment;



import android.os.Build;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soowin.staremblem.R;
import com.soowin.staremblem.ui.index.activity.ServiceEvaluationActivity;

/**
 *
 */
@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
//public class EvaluationOneFragment extends Fragment implements View.OnClickListener {
public class EvaluationOneFragment extends Fragment  {
    private View view;
    ServiceEvaluationActivity a;
    TextView btnSatisfactionsNext ;

    private ImageView ivStar1, ivStar2, ivStar3, ivStar4, ivStar5;
    private LinearLayout llStar;
    private int starPosition;
    private TextView tvEvalText;
    private LinearLayout  llChoose5;


    public EvaluationOneFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_evaluation_one, container, false);
        }
        a = (ServiceEvaluationActivity) getActivity();
        a.prePosition = 0;
//        initView();
        return view;
    }


    /*private void initView() {
        btnSatisfactionsNext = view.findViewById(R.id.btn_satisfactions_next);
        btnSatisfactionsNext.setOnClickListener(this);
        tvEvalText = view.findViewById(R.id.tv_eval_text);
        llChoose5 = view.findViewById(R.id.ll_choose5);
        llStar = view.findViewById(R.id.ll_star);
        ivStar1 = view.findViewById(R.id.iv_star1);
        ivStar2 = view.findViewById(R.id.iv_star2);
        ivStar3 = view.findViewById(R.id.iv_star3);
        ivStar4 = view.findViewById(R.id.iv_star4);
        ivStar5 = view.findViewById(R.id.iv_star5);

        for (int i = 0; i < 5; i++) {
            llStar.getChildAt(i).setOnClickListener(this);
        }


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_star1:
                setStar(1);
                starPosition = 1 ;
                resetView();
                break;
            case R.id.iv_star2:
                setStar(2);
                starPosition = 2 ;
                resetView();
                break;
            case R.id.iv_star3:
                setStar(3);
                starPosition = 3 ;
                resetView();
                break;
            case R.id.iv_star4:
                setStar(4);
                starPosition = 4 ;
                resetView();
                break;
            case R.id.iv_star5:
                setStar(5);
                starPosition = 5 ;
                resetView();
                break;
            case R.id.btn_satisfactions_next:
                a.switchFragment(1);
                break;
        }
    }

    private void resetView() {
        if (starPosition != 5 ){
            tvEvalText.setText(getResources().getString(R.string.app_satisfactions_good));
            llChoose5.setVisibility(View.VISIBLE);
        } else {
            tvEvalText.setText(getResources().getString(R.string.app_satisfactions_exellent));
            llChoose5.setVisibility(View.INVISIBLE);
        }
    }

    private void setStar(int index) {
        switch (index) {
            case 1:
                ivStar1.setImageResource(R.drawable.img_star_full);
                ivStar2.setImageResource(R.drawable.img_star_empty);
                ivStar3.setImageResource(R.drawable.img_star_empty);
                ivStar4.setImageResource(R.drawable.img_star_empty);
                ivStar5.setImageResource(R.drawable.img_star_empty);
                break;
            case 2:
                ivStar1.setImageResource(R.drawable.img_star_full);
                ivStar2.setImageResource(R.drawable.img_star_full);
                ivStar3.setImageResource(R.drawable.img_star_empty);
                ivStar4.setImageResource(R.drawable.img_star_empty);
                ivStar5.setImageResource(R.drawable.img_star_empty);
                break;
            case 3:
                ivStar1.setImageResource(R.drawable.img_star_full);
                ivStar2.setImageResource(R.drawable.img_star_full);
                ivStar3.setImageResource(R.drawable.img_star_full);
                ivStar4.setImageResource(R.drawable.img_star_empty);
                ivStar5.setImageResource(R.drawable.img_star_empty);
                break;
            case 4:
                ivStar1.setImageResource(R.drawable.img_star_full);
                ivStar2.setImageResource(R.drawable.img_star_full);
                ivStar3.setImageResource(R.drawable.img_star_full);
                ivStar4.setImageResource(R.drawable.img_star_full);
                ivStar5.setImageResource(R.drawable.img_star_empty);
                break;
            case 5:
                ivStar1.setImageResource(R.drawable.img_star_full);
                ivStar2.setImageResource(R.drawable.img_star_full);
                ivStar3.setImageResource(R.drawable.img_star_full);
                ivStar4.setImageResource(R.drawable.img_star_full);
                ivStar5.setImageResource(R.drawable.img_star_full);
                break;
        }
    }*/
}
