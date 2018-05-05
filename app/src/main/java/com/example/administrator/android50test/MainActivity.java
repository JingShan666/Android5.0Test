package com.example.administrator.android50test;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.example.administrator.android50test.fragment.FragmentAttention;
import com.example.administrator.android50test.fragment.FragmentHome;
import com.example.administrator.android50test.fragment.FragmentMe;
import com.example.administrator.android50test.fragment.FragmentTable;

public class MainActivity extends FragmentActivity {

    private static final int HOME      = 0;
    private static final int TABLE     = 1;
    private static final int ATTENTION = 2;
    private static final int ME        = 3;
    private Fragment mCurrentFragment;
    private RadioGroup mRadioGroup;
    private int mCurrentTab;
    private FragmentHome mFragmentHome;
    private FragmentTable mFragmentTable;
    private FragmentAttention mFragmentAttention;
    private FragmentMe mFragmentMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
        initEvent();
        initData();



    }
    private void initView() {

        mRadioGroup = findViewById(R.id.group_bottom_navigation_bar);

    }

    private void initEvent() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                switch (checkId){
                    case R.id.radio_tab_home_page:
                        switchTab(HOME);
                        break;
                    case R.id.radio_tab_table:
                        switchTab(TABLE);
                        break;
                    case R.id.radio_tab_attention:
                        switchTab(ATTENTION);
                        break;
                    case R.id.radio_tab_me:
                        switchTab(ME);
                        break;

                        default:

                            break;
                }
            }
        });

    }



    private void initData() {

        mCurrentTab = -1;
        mFragmentHome = FragmentHome.instance();
        mFragmentTable = FragmentTable.instance();
        mFragmentAttention = FragmentAttention.instance();
        mFragmentMe = FragmentMe.instance();
        switchTab(HOME);

    }

    private void switchTab(int tab) {
        if (mCurrentTab==tab){
            return;
        }

        switch (tab){

            case HOME:
                mRadioGroup.check(R.id.radio_tab_home_page);
                if (mFragmentHome!=null){
                    switchFragment(R.id.layout_main_fragment_content, mFragmentHome);

                }

                break;
            case TABLE:
                mRadioGroup.check(R.id.radio_tab_table);
                if (mFragmentTable != null) {
                    switchFragment(R.id.layout_main_fragment_content, mFragmentTable);
                }
                break;
            case ATTENTION:
                mRadioGroup.check(R.id.radio_tab_attention);
                if (mFragmentAttention != null) {
                    switchFragment(R.id.layout_main_fragment_content, mFragmentAttention);
                }
                break;
            case ME:
                mRadioGroup.check(R.id.radio_tab_me);
                if (mFragmentMe != null) {
                    switchFragment(R.id.layout_main_fragment_content, mFragmentMe);
                }
                break;

                default:
                    break;
        }
        mCurrentTab= tab;


    }

    private void switchFragment(int layoutId, Fragment fragment) {

        FragmentManager fm= getSupportFragmentManager();

        FragmentTransaction ft= fm.beginTransaction();

        if (mCurrentFragment==null){
            ft.add(layoutId,fragment).commit();
        }else if (mCurrentFragment!=fragment){
            if (!fragment.isAdded()){
                ft.hide(mCurrentFragment).add(layoutId,fragment).commit();
            }else {
                ft.hide(mCurrentFragment).show(fragment).commit();
            }
        }

        mCurrentFragment= fragment;


    }

}
