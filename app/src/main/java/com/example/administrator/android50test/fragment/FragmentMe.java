package com.example.administrator.android50test.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.android50test.R;

/**
 * Created by Administrator on 2018/5/4.
 */

public class FragmentMe extends Fragment {
    public static FragmentMe instance() {
        return new FragmentMe();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initListeners();
        initData();
    }

    private void initViews(View view) {

    }

    private void initListeners() {

    }

    private void initData() {

    }
}
