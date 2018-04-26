package com.soowin.staremblem.ui.index.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.soowin.staremblem.R;
import com.soowin.staremblem.ui.index.activity.ServiceEvaluationActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class EvaluationTwoFragment extends Fragment implements View.OnClickListener {

    private View view;
    ServiceEvaluationActivity a;
    TextView btnBuyServiceNext;

    public EvaluationTwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_evaluation_two, container, false);
        }
        a = (ServiceEvaluationActivity) getActivity();
        a.prePosition = 1;
//        initView();
        return view;
//        return inflater.inflate(R.layout.fragment_evaluation_two, container, false);
    }


   /* private void initView() {
        btnBuyServiceNext = view.findViewById(R.id.btn_buy_service_next);
        btnBuyServiceNext.setOnClickListener(this);
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           /* case R.id.btn_buy_service_next:
                a.switchFragment(2);
                break;*/
        }
    }
}
