package com.starcity.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.starcity.activity.TestActivity;
import com.starcity.fragment.QuestionItemFragment;


/**
 * @author
 * @Description: ViewPager的数据适配器
 */
public class ViewPagerItemAdapter extends FragmentStatePagerAdapter {


    public ViewPagerItemAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int arg0) {
        return new QuestionItemFragment(arg0);
    }


    @Override
    public int getCount() {
        return TestActivity.qbQuestionList.size();
    }


}
