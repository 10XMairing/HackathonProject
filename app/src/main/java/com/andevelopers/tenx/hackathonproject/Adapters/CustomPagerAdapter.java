package com.andevelopers.tenx.hackathonproject.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.andevelopers.tenx.hackathonproject.FragmentFeeds;
import com.andevelopers.tenx.hackathonproject.FragmentSubs;

import java.util.ArrayList;
import java.util.List;

public class CustomPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mList;
    public CustomPagerAdapter(FragmentManager fm) {
        super(fm);
        mList = new ArrayList<>();
        mList.add(new FragmentFeeds());
        mList.add(new FragmentSubs());
    }

    @Override
    public Fragment getItem(int i) {
        return mList.get(i);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
