package com.soowin.staremblem.ui.index.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soowin.staremblem.R;
import com.soowin.staremblem.ui.index.activity.ServiceEvaluationActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class EvaluationThreeFragment extends Fragment {
    private View view;
    ServiceEvaluationActivity a;
    public EvaluationThreeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_evaluation_three, container, false);
        }
        a = (ServiceEvaluationActivity) getActivity();
        a.prePosition = 2;
        return view;
//        return inflater.inflate(R.layout.fragment_evaluation_three, container, false);
    }

}
