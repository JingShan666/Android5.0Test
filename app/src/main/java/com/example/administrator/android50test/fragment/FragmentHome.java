package com.example.administrator.android50test.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.android50test.R;
import com.example.administrator.android50test.adapter.TabFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/4.
 */

public class FragmentHome extends Fragment {

    public static FragmentHome instance() {
        return new FragmentHome();
    }

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initListeners();
        initData();
    }

    private void initViews(View view) {
        mTabLayout = view.findViewById(R.id.tab_layout_title);
        mViewPager = view.findViewById(R.id.page_collapsing);
    }

    private void initListeners() {

    }

    private void initData() {
        List<String> titleList = new ArrayList<>();
        titleList.add("推荐");
        titleList.add("抖音");
        titleList.add("音乐");
        titleList.add("搞笑");
        titleList.add("社会");
        titleList.add("影视");
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(TabPagerFragment.newInstance(15, "推荐"));
        fragmentList.add(TabPagerFragment.newInstance(10, "抖音"));
        fragmentList.add(TabPagerFragment.newInstance(20, "音乐"));
        fragmentList.add(TabPagerFragment.newInstance(1, "搞笑"));
        fragmentList.add(TabPagerFragment.newInstance(24, "社会"));
        fragmentList.add(TabPagerFragment.newInstance(2, "影视"));


        mViewPager.setAdapter(new TabFragmentAdapter(getFragmentManager(),fragmentList,titleList));

        mTabLayout.setupWithViewPager(mViewPager);



    }


}
