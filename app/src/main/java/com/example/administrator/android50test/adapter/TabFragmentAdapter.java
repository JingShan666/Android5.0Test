package com.example.administrator.android50test.adapter;




import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018/5/4.
 */

public class TabFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;

    private List<String> mTitles;

    public TabFragmentAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList, List<String> titleList) {
        super(fragmentManager);
        mFragments = fragmentList;
        this.mTitles = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {

        return mTitles.get(position);

    }


}
